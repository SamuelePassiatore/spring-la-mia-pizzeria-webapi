package org.example.pizzeria.pojo;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "Il campo nome non può essere nullo")
	private String nome;
	@NotBlank(message = "Il campo descrizione non può essere nullo")
    private String descrizione;
	@NotBlank(message = "Il campo foto non può essere nullo")
    private String foto;
    @Min(value = 1, message = "Il prezzo minimo è di 1 euro")
    @Max(value = 1000, message = "Il prezzo massimo è di 1000 euro")
    private double prezzo;
    
    private boolean deleted = false;
    
    @OneToMany(mappedBy = "pizza")
    @JsonManagedReference
	private List<SpecialOffer> specialOffers;
    
	@ManyToMany
	private List<Ingredient> ingredients;
    
    public Pizza() { }
    public Pizza(String nome, String descrizione, String foto, double prezzo) {
    	setNome(nome);
    	setDescrizione(descrizione);
    	setFoto(foto);
    	setPrezzo(prezzo);
	 }
	
	public Pizza(String nome, String descrizione, String foto, double prezzo, Ingredient... ingredients) {

		setNome(nome);
    	setDescrizione(descrizione);
    	setFoto(foto);
    	setPrezzo(prezzo);

		setIngredients(ingredients);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public String getFormattedPrezzo() {
            return String.format("%,.2f€", prezzo);
    }
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public List<SpecialOffer> getSpecialOffers() {
		return specialOffers;
	}
	public void setSpecialOffers(List<SpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	@JsonSetter
	public void setIngredients(Ingredient[] ingredients) {
		setIngredients(Arrays.asList(ingredients));
	}
	
	public void deleteIngredient(Ingredient ingredient) {
		getIngredients().remove(ingredient);		
	}

	
	@Override
	public String toString() {
		
		return "[" + getId() + "] "
				+ "\nNome: " + getNome() 
				+ "\nDescrizione: " + getDescrizione()
				+ "\nFoto: " + getFoto()
				+ "\nPrezzo: " + getPrezzo();
	}
}
