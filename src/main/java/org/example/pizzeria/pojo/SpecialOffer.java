package org.example.pizzeria.pojo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class SpecialOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "La data iniziale deve essere specificata")
    private LocalDate startDate;
	@NotNull(message = "La data finale deve essere specificata")
	@Future(message = "La data finale deve essere nel futuro")
    private LocalDate endDate;
    
    @NotBlank(message = "Il campo titolo non può essere vuoto")
    private String title;
    
    @NotNull(message = "Lo sconto deve essere specificato")
	@Positive(message = "Lo sconto deve essere positivo")
	@Min(value = 1, message = "Lo sconto deve essere compreso tra 1 e 100")
	@Max(value = 100, message = "Lo sconto deve essere compreso tra 1 e 100")
    private int discountPercentage;
    
    @ManyToOne
	@JoinColumn(nullable = false)
	private Pizza pizza;
    
    public SpecialOffer() { }
	public SpecialOffer(LocalDate startDate, LocalDate endDate, String title, int discountPercentage, Pizza pizza) {
		setStartDate(startDate);
		setEndDate(endDate);
		setTitle(title);
		setDiscountPercentage(discountPercentage);
		setPizza(pizza);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	
	public String getDiscountedPrice() {
		double pizzaPrice = getPizza().getPrezzo();

		float discountedPrice = (float) (pizzaPrice - (pizzaPrice * getDiscountPercentage() / 100));

		return String.format("%,.2f", discountedPrice);
	}
	
	@Override
	public String toString() {
		return "[" + getId() + "]" + getTitle() + " , Inizio: " + getStartDate() + " , fine: " + getEndDate() + " , sconto: " + getDiscountPercentage();
	}
}
