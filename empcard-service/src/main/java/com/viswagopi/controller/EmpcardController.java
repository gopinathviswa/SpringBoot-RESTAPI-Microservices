package com.viswagopi.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.viswagopi.components.EmpCardNotFoundException;
import com.viswagopi.model.Empcard;
import com.viswagopi.repository.EmpcardRepository;

@RestController
public class EmpcardController {
	
	@Autowired
	private EmpcardRepository empcardRepository;

	@GetMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Empcard> retrieveAllEmpcards() {
		return empcardRepository.findAll();
	}
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Empcard retrieveEmpcard(@PathVariable long id) {
		Optional<Empcard> empcard = empcardRepository.findById(id);
	
		if (!empcard.isPresent())
			throw new EmpCardNotFoundException("id-" + id);
	
	   return empcard.get();
	}
	
	@GetMapping(path="/[{cards}]",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Empcard> retrieveInactiveEmpcard(@PathVariable String[] cards) {
		for (String card : cards) {
			System.out.println(card);
		}
		Collection<String> empcards = new ArrayList(Arrays.asList(cards));
		List<Empcard> empcard = empcardRepository.findByNumberNotInOrderByNumberAsc(empcards);
	
	   return empcard;
	}

	@DeleteMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public void deleteEmpcard(@PathVariable long id) {
		empcardRepository.deleteById(id);
	}

	@PostMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> createEmpcard(@RequestBody Empcard empcard) {
		Empcard savedEmpcard = empcardRepository.save(empcard);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(savedEmpcard.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PostMapping(path="/import",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> createEmpcard(@RequestBody List<Empcard> empcard) {
		try {
			empcardRepository.saveAll(empcard);

			return new ResponseEntity<String>("Successfully imported Empcards", HttpStatus.OK);
		}catch(Exception exc) {
			return new ResponseEntity<String>(" Invalid data for Empcard", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	  
	@PutMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> updateEmpcard(@RequestBody Empcard empcard, @PathVariable long id) {

		Optional<Empcard> studentOptional = empcardRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		empcard.setId(id);
	    
		empcardRepository.save(empcard);

		return ResponseEntity.noContent().build();
	}
}