package org.example.pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.example.pizzeria.pojo.Ingredient;
import org.example.pizzeria.pojo.Pizza;
import org.example.pizzeria.serv.IngredientServ;
import org.example.pizzeria.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
	
	@Autowired
	private IngredientServ ingredientServ;

	@Autowired
	private PizzaService pizzaServ;
	
	@GetMapping
	public String index(Model model) {

		List<Ingredient> ingredients = ingredientServ.findAll();
		model.addAttribute("ingredients", ingredients);

		return "ingredient_index";
	}
	
	@GetMapping("/admin/create")
	public String create(Model model) {
		model.addAttribute("ingredient", new Ingredient());

		return "ingredient_create";
	}

	@PostMapping("/admin/store")
	public String store(@Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			model.addAttribute("ingredient", ingredient);
			model.addAttribute("errors", bindingResult);

			return "ingredient_create";
		}

		ingredientServ.save(ingredient);

		return "redirect:/ingredients";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String delete(@PathVariable("id") int id) {

		Optional<Ingredient> ingredientOpt = ingredientServ.findById(id);
		Ingredient ingredient = ingredientOpt.get();
		
		for (Pizza pizza : pizzaServ.findAll()) {
			pizza.deleteIngredient(ingredient);
			pizzaServ.save(pizza);
		}
		
		ingredientServ.delete(ingredient);

		return "redirect:/ingredients";
	}


}
