package com.viswagopi.controller;

import java.net.URI;
import java.util.List;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.viswagopi.components.TownNotFoundException;
import com.viswagopi.model.Town;
import com.viswagopi.repository.TownRepository;

@RestController
public class TownController {
	
	@Autowired
	private TownRepository townRepository;

	@GetMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Town> retrieveAllTowns() {
		return townRepository.findAll();
	}
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Town retrieveTown(@PathVariable long id) {
		Optional<Town> town = townRepository.findById(id);
	
		if (!town.isPresent())
			throw new TownNotFoundException("id-" + id);
	
	   return town.get();
	}
	
	@GetMapping(path="/name/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Town retrieveTownbyName(@PathVariable String id) {
		return townRepository.findByName(id);
	}

	@DeleteMapping("/{id}")
	public void deleteTown(@PathVariable long id) {
		townRepository.deleteById(id);
	}

	@PostMapping("/")
	public ResponseEntity<Object> createTown(@RequestBody Town town) {
		Town savedTown = townRepository.save(town);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(savedTown.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PostMapping("/import")
	public ResponseEntity<String> createTown(@RequestBody List<Town> town) {
		try {
			townRepository.saveAll(town);

			return new ResponseEntity<String>("Successfully imported Towns", HttpStatus.OK);
		}catch(Exception exc) {
			return new ResponseEntity<String>(" Invalid data for Town", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	  
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateTown(@RequestBody Town town, @PathVariable long id) {

		Optional<Town> studentOptional = townRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		town.setId(id);
	    
		townRepository.save(town);

		return ResponseEntity.noContent().build();
	}
}