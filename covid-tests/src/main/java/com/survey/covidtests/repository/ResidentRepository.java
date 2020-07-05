package com.survey.covidtests.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.survey.covidtests.model.Resident;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long>{
	
	Page<Resident> findByApartmentId(Long apartmentId, Pageable pageable);
	
	Optional<Resident> findByIdAndApartmentId(Long id, Long apartmentId);
	
}
