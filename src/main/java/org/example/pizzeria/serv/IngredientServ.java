package org.example.pizzeria.serv;

import java.util.List;
import java.util.Optional;

import org.example.pizzeria.pojo.Ingredient;
import org.example.pizzeria.repo.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServ {

	@Autowired
	private IngredientRepo ingredientRepo;

	public List<Ingredient> findAll() {

		return ingredientRepo.findAll();
	}
	public Optional<Ingredient> findById(int id) {

		return ingredientRepo.findById(id);
	}
	public Ingredient save(Ingredient ingredient) {

		return ingredientRepo.save(ingredient);
	}
	
	public void delete(Ingredient ingredient) {

		ingredientRepo.delete(ingredient);
	}
}
