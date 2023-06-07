package org.example.pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.example.pizzeria.pojo.Ingredient;
import org.example.pizzeria.pojo.Pizza;
import org.example.pizzeria.pojo.SpecialOffer;
import org.example.pizzeria.serv.IngredientServ;
import org.example.pizzeria.serv.PizzaService;
import org.example.pizzeria.serv.SpecialOfferServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class PizzaController {

		@Autowired
		private PizzaService pizzaService;
		
		@Autowired
		private IngredientServ ingredientServ;
		
		@Autowired
		private SpecialOfferServ specialOfferServ;

		@GetMapping("/")
		public String home() {
			return "home";
		}
		
		@GetMapping("/pizze")
		public String getHome(Model model) {
			
			List<Pizza> pizze = pizzaService.findAllNotDeleted();
			
			model.addAttribute("pizze", pizze);
			
			return "pizza_index";
		}
		
		@PostMapping("/pizze/nome")
		public String getPizzaByNome(Model model, @RequestParam(required = false) String nome) {

			List<Pizza> pizze = pizzaService.findByNome(nome);
			model.addAttribute("pizze", pizze);
			model.addAttribute("nome", nome);

			return "pizza_index";
		}
		
		@GetMapping("/pizze/{id}")
		public String getPizza(
				Model model,
				@PathVariable("id") int id
		) {
			
			Optional<Pizza> optPizza = pizzaService.findByIdWithSpecialOffer(id);
			Pizza pizza = optPizza.get();
			
			model.addAttribute("pizza", pizza);
			
			return "pizza_show";
		}
		
		@GetMapping("/admin/pizze/create")
		public String createPizza(Model model) {
			
			List<Ingredient> ingredients = ingredientServ.findAll();
			
			model.addAttribute("pizza", new Pizza());
			model.addAttribute("ingredients", ingredients);
			
			return "pizza_create";
		}

		@PostMapping("/admin/pizze/store")
		public String storePizza(
				Model model,
				@Valid @ModelAttribute Pizza pizza,
				BindingResult bindingResult) {

			if (bindingResult.hasErrors()) {

				for (ObjectError err : bindingResult.getAllErrors()) 
					System.err.println("error: " + err.getDefaultMessage());

				model.addAttribute("pizza", pizza);
				model.addAttribute("errors", bindingResult);
				
				return "pizza_create";
				
			}
			
			pizzaService.save(pizza);

			return "redirect:/pizze";
		}
		
		@GetMapping("/admin/pizze/edit/{id}")
		public String editPizza(
				Model model,
				@PathVariable int id
			) {
			
			List<Ingredient> ingredients = ingredientServ.findAll();
			
			Optional<Pizza> pizzaOpt = pizzaService.findById(id);
			Pizza pizza = pizzaOpt.get();
			model.addAttribute("pizza", pizza);
			model.addAttribute("ingredients", ingredients);

			return "pizza_update";
		}
		
		@PostMapping("/admin/pizze/update/{id}")
		public String updatePizza(
				Model model,
				@PathVariable int id,
				@Valid @ModelAttribute Pizza pizza,
				BindingResult bindingResult){
			
			if (bindingResult.hasErrors()) {
				
				for (ObjectError err : bindingResult.getAllErrors()) 
					System.err.println("error: " + err.getDefaultMessage());
				model.addAttribute("pizza", pizza);
				model.addAttribute("errors", bindingResult);
				
				return "pizza_update";
			}

			pizzaService.save(pizza);

			return "redirect:/pizze";
		}
		
		@GetMapping("/admin/pizze/delete/{id}")
		public String deletePizza(
				@PathVariable int id
			) {

			Optional<Pizza> pizzaOpt = pizzaService.findById(id);
			Pizza pizza = pizzaOpt.get();
			
			for (SpecialOffer so : pizza.getSpecialOffers()) {
				specialOfferServ.delete(so);
			}
			
			pizzaService.deletePizza(pizza);

			return "redirect:/pizze";
		}
		
		@GetMapping("/admin/pizze/soft-delete/{id}")
		public String softDeletePizza(
				@PathVariable int id
			) {

			Optional<Pizza> pizzaOpt = pizzaService.findById(id);
			Pizza pizza = pizzaOpt.get();

			pizza.setDeleted(true);
			pizzaService.save(pizza);

			return "redirect:/pizze";
		}
}
