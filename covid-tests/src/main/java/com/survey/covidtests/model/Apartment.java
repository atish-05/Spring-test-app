package com.survey.covidtests.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.sun.istack.NotNull;

@Entity
@Table(name="apartments")	
public class Apartment {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(unique = true)
	private String name;
	
	@NotNull
	@Column(unique = false)
	private String location;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "apartment")
	private Set<Resident> residents = new HashSet<>();
	

}


