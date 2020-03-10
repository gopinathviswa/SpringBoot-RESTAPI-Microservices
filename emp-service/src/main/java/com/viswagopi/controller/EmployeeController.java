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

import com.viswagopi.components.ProdcutNotFoundException;
import com.viswagopi.model.Employee;
import com.viswagopi.model.ProjectStatus;
import com.viswagopi.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Employee> retrieveAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Employee retrieveEmployee(@PathVariable long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
	
		if (!employee.isPresent())
			throw new ProdcutNotFoundException("id-" + id);
	
	   return employee.get();
	}

	@GetMapping(path="/[{branchs}]",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Employee> retrieveInactiveEmpcard(@PathVariable String[] branchs) {
//		for (String card : branchs) {
//			System.out.println(card);
//		}
		Collection<String> employee = new ArrayList(Arrays.asList(branchs));
		List<Employee> empcard = employeeRepository.findByBranchInOrderByFirstnameAsc(employee);
	
	   return empcard;
	}
	
	@DeleteMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public void deleteEmployee(@PathVariable long id) {
		employeeRepository.deleteById(id);
	}

	@PostMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(savedEmployee.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PostMapping(path="/import",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ProjectStatus> createEmployee(@RequestBody List<Employee> employee) {
		try {
			employeeRepository.saveAll(employee);
			ProjectStatus status = new ProjectStatus();
			status.setStatus("Successfully imported Employee");
			return new ResponseEntity<ProjectStatus>(status, HttpStatus.OK);
		}catch(Exception exc) {
			ProjectStatus status = new ProjectStatus();
			status.setStatus("Invalid data for Employee");
			return new ResponseEntity<ProjectStatus>(status, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	  
	@PutMapping(path="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee, @PathVariable long id) {

		Optional<Employee> studentOptional = employeeRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		employee.setId(id);
	    
		employeeRepository.save(employee);

		return ResponseEntity.noContent().build();
	}
}