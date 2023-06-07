package org.example.pizzeria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.pizzeria.pojo.Pizza;
import org.example.pizzeria.pojo.SpecialOffer;
import org.example.pizzeria.auth.Role;
import org.example.pizzeria.auth.User;
import org.example.pizzeria.auth.RoleService;
import org.example.pizzeria.auth.UserService;
import org.example.pizzeria.pojo.Ingredient;
import org.example.pizzeria.serv.IngredientServ;
import org.example.pizzeria.serv.PizzaService;
import org.example.pizzeria.serv.SpecialOfferServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private SpecialOfferServ specialOfferServ;
	
	@Autowired
	private IngredientServ ingredientServ;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Ingredient i1 = new Ingredient("Pomodoro");
		Ingredient i2 = new Ingredient("Mozzarella");
		Ingredient i3 = new Ingredient("Basilico");
		Ingredient i4 = new Ingredient("Olio");
		
		ingredientServ.save(i1);
		ingredientServ.save(i2);
		ingredientServ.save(i3);
		ingredientServ.save(i4);
		
		Pizza p1 = new Pizza("Margherita", "Buona.", "https://static.cookist.it/wp-content/uploads/sites/21/2018/04/pizza-margherita-fatta-in-casa.jpg", 6.00, i1, i3);
		Pizza p2 = new Pizza("Diavola", "Buonissima.", "https://i1.wp.com/www.piccolericette.net/piccolericette/wp-content/uploads/2017/06/3236_Pizza.jpg?resize=895%2C616&ssl=1", 8.00, i2, i4);
		Pizza p3 = new Pizza("Quattro formaggi", "Buonissimissima.", "https://cdn.ilclubdellericette.it/wp-content/uploads/2020/04/pizza-ai-quattro-formaggi-fatta-in-casa-1280x720.jpg", 7.00, i2, i3);
		
		pizzaService.save(p1);
		pizzaService.save(p2);
		pizzaService.save(p3);
		
		List<Pizza> pizze = pizzaService.findAll();
		System.out.println(pizze);
		
		SpecialOffer so1 = new SpecialOffer(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-30"), "Offerta speciale 1", 5, p1);
		SpecialOffer so2 = new SpecialOffer(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-30"), "Offerta speciale 2",  10, p2);
		SpecialOffer so3 = new SpecialOffer(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-30"), "Offerta speciale 3", 15, p3);
		SpecialOffer so4 = new SpecialOffer(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-30"), "Offerta speciale 4", 20, p1);
		SpecialOffer so5 = new SpecialOffer(LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-30"), "Offerta speciale 5", 25, p2);
		
		specialOfferServ.save(so1);
		specialOfferServ.save(so2);
		specialOfferServ.save(so3);
		specialOfferServ.save(so4);
		specialOfferServ.save(so5);
		
		for(Pizza pizza : pizze) {

			Optional<Pizza> optPizzaOffer = pizzaService.findByIdWithSpecialOffer(pizza.getId());
			Pizza pizzaOffer = optPizzaOffer.get();
			System.out.println(pizzaOffer.getSpecialOffers());

		}
		
		Role userRole = new Role("USER");
		Role adminRole = new Role("ADMIN");
		
		roleService.save(userRole);
		roleService.save(adminRole);
		
		final String psw = new BCryptPasswordEncoder().encode("password");
		
		User userUser = new User("user", psw, userRole);
		User userAdmin = new User("admin", psw, adminRole);
				
		userService.save(userUser);
		userService.save(userAdmin);
	}

	
	

}
