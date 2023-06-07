package org.example.pizzeria.serv;

import java.util.List;
import java.util.Optional;

import org.example.pizzeria.pojo.Pizza;
import org.example.pizzeria.repo.PizzaRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepo pizzaRepo;
	
	public List<Pizza> findAll() {
		
		return pizzaRepo.findAll();
	}
	public List<Pizza> findAllNotDeleted() {
		
		return pizzaRepo.findByDeletedFalse();
	}
	
	@Transactional
	public Optional<Pizza> findByIdWithSpecialOffer(int id) {

		Optional<Pizza> pizzaOpt = pizzaRepo.findById(id);
		Hibernate.initialize(pizzaOpt.get().getSpecialOffers());

		return pizzaOpt;
	}
	public Pizza save(Pizza pizza) {
		
		return pizzaRepo.save(pizza);
	}
	public Optional<Pizza> findById(int id) {
		
		return pizzaRepo.findById(id);
	}
	
	public List<Pizza> findByNome(String title) {

		return pizzaRepo.findByNomeContainingAndDeletedFalse(title);
	}
	
	public void deletePizza(Pizza pizza) {

		pizzaRepo.delete(pizza);
	}
}

