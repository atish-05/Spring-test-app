package com.survey.covidtests.controller;

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
import com.survey.covidtests.model.Resident;
import com.survey.covidtests.repository.ApartmentRepository;
import com.survey.covidtests.repository.ResidentRepository;

@RestController
public class ResidentController {
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@Autowired
	private ResidentRepository residentRepository;
	
	@GetMapping("/apartments/{apartmentId}/residents")
	public Page<Resident> getAllResidentsByApartment(@PathVariable Long apartmentId, Pageable pageable){
		 
		return residentRepository.findByApartmentId(apartmentId, pageable);
	}
	
	@GetMapping("apartments/{apartmentId}/residents/{residentId}")
	public Resident getResidentById(@PathVariable(value = "apartmentId") Long apartmentId, 
			@PathVariable(value = "residentId") Long residentId) {
		
		if (!apartmentRepository.existsById(apartmentId)) {
			throw new ResourceNotFoundException("ApartmentId " + apartmentId + " not found");
		}
		
		return residentRepository.findByIdAndApartmentId(residentId, apartmentId)
				.orElseThrow(() -> new ResourceNotFoundException("ResidentId " + residentId + " not found"));
		
	}
	
	@PostMapping("/apartments/{apartmentId}/residents")
	public Resident createResident(@PathVariable (value = "apartmentId") Long apartmentId, @RequestBody Resident residentRequest) {
		
		return apartmentRepository.findById(apartmentId).map(apt -> {
			residentRequest.setApartment(apt);
			return residentRepository.save(residentRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("ApartmentId " + apartmentId + " not found"));
	
	}
	
	@PutMapping("/apartments/{apartmentId}/residents/{residentId}")
	public Resident updateResident(@PathVariable(value = "apartmentId") Long apartmentId,
			@PathVariable(value = "residentId") Long residentId, @RequestBody Resident residentResquest) {

		if (!apartmentRepository.existsById(apartmentId)) {
			throw new ResourceNotFoundException("ApartmentId " + apartmentId + " not found");
		}

		return residentRepository.findByIdAndApartmentId(residentId, apartmentId).map(resident -> {
			resident.setFirstName(residentResquest.getFirstName());
			resident.setLastName(residentResquest.getLastName());
			resident.setFlatNum(residentResquest.getFlatNum());
			return residentRepository.save(resident);
		}).orElseThrow(() -> new ResourceNotFoundException("ResidentId " + residentId + " not found"));
	}
	
	@DeleteMapping("/apartments/{apartmentId}/residents/{residentId}")
	public ResponseEntity<?> deleteCommnent(@PathVariable (value = "apartmentId") Long apartmentId, 
												@PathVariable (value = "residentId") Long residentId) {
		
		return residentRepository.findByIdAndApartmentId(residentId, apartmentId).map(resident -> {
			residentRepository.delete(resident);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new  ResourceNotFoundException("ResidentId " + residentId + "not found"));	
		
	}
	


}
