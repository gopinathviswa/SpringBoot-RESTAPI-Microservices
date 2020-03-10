package com.viswagopi.controller;

import java.net.URI;
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

import com.viswagopi.components.ProdcutNotFoundException;
import com.viswagopi.model.Product;
import com.viswagopi.model.ProjectStatus;
import com.viswagopi.repository.ProductRepository;

@RestController
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

	@GetMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Product> retrieveAllProducts() {
		return productRepository.findAll();
	}
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Product retrieveProduct(@PathVariable long id) {
		Optional<Product> product = productRepository.findById(id);
	
		if (!product.isPresent())
			throw new ProdcutNotFoundException("id-" + id);
	
	   return product.get();
	}

	@GetMapping(path="/branchs",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<?> retrieveAllBranches() {
		return productRepository.findBranchsbysumofClients();
	}
	@DeleteMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public void deleteProduct(@PathVariable long id) {
		productRepository.deleteById(id);
	}

	@PostMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		Product savedProduct = productRepository.save(product);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(savedProduct.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PostMapping(path="/import",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ProjectStatus> createProduct(@RequestBody List<Product> product) {
		try {
			productRepository.saveAll(product);
			ProjectStatus status = new ProjectStatus();
			status.setStatus("Successfully imported Products");
			return new ResponseEntity<ProjectStatus>(status, HttpStatus.OK);
		}catch(Exception exc) {
			ProjectStatus status = new ProjectStatus();
			status.setStatus("Invalid data for Products");
			return new ResponseEntity<ProjectStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	  
	@PutMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable long id) {

		Optional<Product> studentOptional = productRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		product.setId(id);
	    
		productRepository.save(product);

		return ResponseEntity.noContent().build();
	}
}