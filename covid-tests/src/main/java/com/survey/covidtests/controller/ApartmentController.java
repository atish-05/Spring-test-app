package com.survey.covidtests.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.survey.covidtests.exception.ResourceNotFoundException;
import com.survey.covidtests.model.Apartment;
import com.survey.covidtests.repository.ApartmentRepository;

@RestController
public class ApartmentController {
	
	@Autowired
	ApartmentRepository apartmentRepository;
	
	@GetMapping("/apartments")
	public Page<Apartment> getAllApartments(Pageable pageable) {
		return apartmentRepository.findAll(pageable);
	}
	
	@GetMapping("/apartments/{apartmentById}")
	public Apartment getApartmentById(@PathVariable Long apartmentById) {
		return apartmentRepository.findById(apartmentById)
				.orElseThrow(() -> new ResourceNotFoundException("ApartmentId " + apartmentById + " not found"));
	}

	@PostMapping("/apartments") 
	public Apartment createApartment (@RequestBody Apartment apartment) {
		return apartmentRepository.save(apartment);
	}

	@PutMapping("/apartments/{apartmentId}")
	public Apartment updateApartment(@PathVariable Long apartmentId, @RequestBody Apartment apartmentRequest) {

		return apartmentRepository.findById(apartmentId).map(apartment -> {
			apartment.setLocation(apartmentRequest.getLocation());
			apartment.setName(apartmentRequest.getName());
			return apartmentRepository.save(apartment);
			}).orElseThrow(() -> new ResourceNotFoundException("ApartmentId " + apartmentId + " not found"));
	}
	
	@DeleteMapping("/apartments/{apartmentId}")
	public ResponseEntity<?> deleteApartment(@PathVariable Long apartmentId) {
		Optional<Apartment> apt = apartmentRepository.findById(apartmentId);
		apartmentRepository.delete(apt.get());
		return ResponseEntity.ok().build();
	}
	
}
