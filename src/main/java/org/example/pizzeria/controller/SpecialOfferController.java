package org.example.pizzeria.controller;

import java.util.Optional;

import org.example.pizzeria.pojo.Pizza;
import org.example.pizzeria.pojo.SpecialOffer;
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

import jakarta.validation.Valid;

@Controller
public class SpecialOfferController {
	@Autowired
	private SpecialOfferServ specialOfferServ;
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping("/admin/pizze/{pizzaId}/special-offer/create")
	public String createSpecialOffer(Model model, @PathVariable("pizzaId") int pizzaId) {
		
		model.addAttribute("specialOffer", new SpecialOffer());
		model.addAttribute("pizzaId", pizzaId);
		return "offers_create";
	}
	
	@PostMapping("/admin/pizze/{pizzaId}/special-offer/store")
	public String storeSpecialOffer(
			@PathVariable("pizzaId") int pizzaId,
			Model model,
			@Valid @ModelAttribute SpecialOffer specialOffer,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			for (ObjectError err : bindingResult.getAllErrors()) 
				System.err.println("error: " + err.getDefaultMessage());

			model.addAttribute("specialOffer", specialOffer);
			model.addAttribute("errors", bindingResult);
			
			return "offers_create";
			
		}
		
		Optional<Pizza> optPizza = pizzaService.findById(pizzaId);
		Pizza pizza = optPizza.get();

		specialOffer.setPizza(pizza);
		
		specialOfferServ.save(specialOffer);

		return "redirect:/pizze/" + pizzaId;
	}
	

	@GetMapping("/admin/pizze/{pizzaId}/special-offer/edit/{id}")
	public String edit(@PathVariable("id") int id, @PathVariable("pizzaId") int pizzaId, Model model) {

		Optional<SpecialOffer> specialOfferOpt = specialOfferServ.findById(id);
		SpecialOffer specialOffer = specialOfferOpt.get();

		model.addAttribute("specialOffer", specialOffer);
		model.addAttribute("pizzaId", pizzaId);

		return "offers_update";
	}

	@PostMapping("/admin/pizze/{pizzaId}/special-offer/update/{id}")
	public String update(@PathVariable("pizzaId") int pizzaId, @Valid @ModelAttribute SpecialOffer specialOffer, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			model.addAttribute("specialOffer", specialOffer);
			model.addAttribute("errors", bindingResult);

			return "offers_update";
		}

		Optional<Pizza> pizzaOpt = pizzaService.findById(pizzaId);
		Pizza pizza = pizzaOpt.get();
		specialOffer.setPizza(pizza);

		specialOfferServ.save(specialOffer);

		return "redirect:/pizze/" + pizzaId;
	}


}
