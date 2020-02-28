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

import com.viswagopi.components.BranchNotFoundException;
import com.viswagopi.model.Branch;
import com.viswagopi.repository.BranchRepository;

@RestController
public class BranchController {
	
	@Autowired
	private BranchRepository branchRepository;

	@GetMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Branch> retrieveAllBranches() {
		return branchRepository.findAll();
	}
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Branch retrieveTown(@PathVariable long id) {
		Optional<Branch> branch = branchRepository.findById(id);
	
		if (!branch.isPresent())
			throw new BranchNotFoundException("id-" + id);
	
	   return branch.get();
	}
	
	@GetMapping(path="/name/{name}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Branch retrievTopBranchs(@PathVariable String name) {
		return branchRepository.findByName(name);
	}
	 	
	@DeleteMapping("/{id}")
	public void deleteTown(@PathVariable long id) {
		branchRepository.deleteById(id);
	}

	@PostMapping("/")
	public ResponseEntity<Object> createTown(@RequestBody Branch branch) {
		Branch savedTown = branchRepository.save(branch);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(savedTown.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PostMapping("/import")
	public ResponseEntity<String> createTown(@RequestBody List<Branch> branch) {
		try {
			branchRepository.saveAll(branch);

			return new ResponseEntity<String>("Successfully imported Branches", HttpStatus.OK);
		}catch(Exception exc) {
			return new ResponseEntity<String>(" Invalid data for Branch"+"/n"+exc, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	  
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateTown(@RequestBody Branch branch, @PathVariable long id) {

		Optional<Branch> studentOptional = branchRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		branch.setId(id);
	    
		branchRepository.save(branch);

		return ResponseEntity.noContent().build();
	}
}