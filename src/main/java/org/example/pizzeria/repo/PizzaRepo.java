package org.example.pizzeria.repo;

import java.util.List;

import org.example.pizzeria.pojo.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepo extends JpaRepository<Pizza, Integer> {

	List<Pizza> findByNomeContainingAndDeletedFalse(String title);
	public List<Pizza> findByDeletedFalse();
	
}
