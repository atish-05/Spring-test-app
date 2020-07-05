package com.survey.covidtests.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.survey.covidtests.model.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>  {

}
