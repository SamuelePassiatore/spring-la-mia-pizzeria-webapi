package org.example.pizzeria.api;

import java.util.List;
import java.util.Optional;

import org.example.pizzeria.pojo.Pizza;
import org.example.pizzeria.pojo.SpecialOffer;
import org.example.pizzeria.serv.PizzaService;
import org.example.pizzeria.serv.SpecialOfferServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ApiController {

		@Autowired
		private PizzaService pizzaService;
		
		@Autowired
		private SpecialOfferServ specialOfferServ;
		
		@GetMapping("/pizze")
	    public ResponseEntity<List<Pizza>> getPizze(@RequestParam(required = false) String nome) {
	        if (nome != null) {
	        	List<Pizza> pizze = pizzaService.findByNome(nome);
	        	return new ResponseEntity<>(pizze, HttpStatus.OK);
	        }
	        
	        List<Pizza> pizze = pizzaService.findAll();
			return new ResponseEntity<>(pizze, HttpStatus.OK);
	    }
			
		
	    @GetMapping("/pizze/{id}")
	    public ResponseEntity<Pizza> getPizzaById(@PathVariable int id) {
	    	Optional<Pizza> optPizza = pizzaService.findById(id);
	    	
	    	if (optPizza.isEmpty()) {
				
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}

			Pizza pizza = optPizza.get();

			return new ResponseEntity<>(pizza, HttpStatus.OK);
	    }
	    
	    
	    @PostMapping("/pizze/store")
	    public ResponseEntity<Pizza> storePizza(@RequestBody Pizza pizza) {
	        Pizza pizzaCreated = pizzaService.save(pizza);
	        
	        return new ResponseEntity<>(pizzaCreated, HttpStatus.OK);
	    }
	    
	    @PutMapping("/pizze/update")
		public ResponseEntity<Pizza> updatePizza(@RequestBody Pizza pizza) {

			Pizza pizzaCreated = pizzaService.save(pizza);

			return new ResponseEntity<>(pizzaCreated, HttpStatus.OK);	
		}
	    
	    @DeleteMapping("/pizze/delete/{id}")
		public ResponseEntity<Pizza> deletePizza(@PathVariable int id) {

			Optional<Pizza> optPizza = pizzaService.findById(id);

			if (optPizza.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
			}

			Pizza pizza = optPizza.get();
			
			for (SpecialOffer so : pizza.getSpecialOffers()) {
				specialOfferServ.delete(so);
			}
			
			pizzaService.deletePizza(pizza);

			return new ResponseEntity<>(HttpStatus.OK);
		}

	}
