package org.example.pizzeria.serv;

import java.util.List;
import java.util.Optional;

import org.example.pizzeria.pojo.SpecialOffer;
import org.example.pizzeria.repo.SpecialOfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferServ {
	
	@Autowired
	private SpecialOfferRepo specialOfferRepo;

	public List<SpecialOffer> findAll() {

		return specialOfferRepo.findAll();
	}
	public Optional<SpecialOffer> findById(int id) {

		return specialOfferRepo.findById(id);
	}
	public SpecialOffer save(SpecialOffer specialOffer) {

		return specialOfferRepo.save(specialOffer);
	}
	
	public void delete(SpecialOffer specialOffer) {

		specialOfferRepo.delete(specialOffer);
	}
}
