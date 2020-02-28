package com.viswagopi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.viswagopi.model.Branch;
import com.viswagopi.model.Empcard;
import com.viswagopi.model.Employee;
import com.viswagopi.model.Product;
import com.viswagopi.model.ProductLite;
import com.viswagopi.model.Town;
import com.viswagopi.model.Towns;

@RestController
public class FreeCardsController {

	@Autowired
    private RestTemplate restTemplate;
	
	/*
	 * localhost:8100 - town-service
	 * localhost:8101 - branch-service
	 * localhost:8102 - empcard-service
	 * localhost:8103 - product-service
	 * localhost:8104 - emp-service
	 */
	@GetMapping("/free-cards")
    public void getFreecards(HttpServletResponse response) throws Exception{
		
//		Employee[] empcards = restTemplate.getForObject("http://localhost:8104/", Employee[].class);
		Employee[] empcards = restTemplate.getForObject("http://emp-service/", Employee[].class);
		ArrayList<String> list = new ArrayList<String>();
		for (Employee card : empcards) {
			list.add(card.getCard());
		}

//		Empcard[] inactivecards = restTemplate.getForObject("http://localhost:8102/"+list, Empcard[].class);
		Empcard[] inactivecards = restTemplate.getForObject("http://empcard-service/"+list, Empcard[].class);
		String filename = "freecards.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + filename + "\"");

		//create a csv writer
		StatefulBeanToCsv<Empcard> writer = new StatefulBeanToCsvBuilder<Empcard>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.withOrderedResults(false)
				.build();

		//write all users to csv file
		writer.write(Arrays.asList(inactivecards));
		//return Arrays.asList(inactivecards);
    }
	
	@GetMapping("/productiveemployees")
	public void getProductiveemployees(HttpServletResponse response) throws Exception{
		
		Product[] branchs = restTemplate.getForObject("http://product-service/", Product[].class);
		ArrayList<String> list = new ArrayList<String>();
		for (Product branch : branchs) {
			list.add(branch.getBranch());
		}
		Employee[] productiveemployees = restTemplate.getForObject("http://emp-service/"+list, Employee[].class);
	
		String filename = "productiveemployees.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + filename + "\"");

		//create a csv writer
		StatefulBeanToCsv<Employee> writer = new StatefulBeanToCsvBuilder<Employee>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.withOrderedResults(false)
				.build();

		//write all users to csv file
		writer.write(Arrays.asList(productiveemployees));
    }
	
	@GetMapping("/top-branchs")
	public void getTopBranchs(HttpServletResponse response) throws Exception{
		
		Object[] branchs = restTemplate.getForObject("http://product-service/branchs", Object[].class);
		List<Object> branchsList = Arrays.asList(branchs);  
		ArrayList<Branch> topBranchs = new ArrayList<Branch>();
		for (Object branch : branchsList) {
			String[] branchvalue = branch.toString().split(",");
		
			String branchname= branchvalue[0].substring(1);
			Integer clients = Integer.parseInt(branchvalue[1].substring(1,branchvalue[1].length()-1));
			
			Branch branchdetail = restTemplate.getForObject("http://branch-service/name/"+branchname, Branch.class);
			branchdetail.setClients(clients);
			topBranchs.add(branchdetail);
		}
		Collections.sort(topBranchs, Collections.reverseOrder());
		String filename = "topBranchs.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + filename + "\"");

		//create a csv writer
		StatefulBeanToCsv<Branch> writer = new StatefulBeanToCsvBuilder<Branch>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.build();

		//write all users to csv file
		writer.write(topBranchs);
    }

	@GetMapping("/towns")
	public void getTowns(HttpServletResponse response) throws Exception{
		
		Object[] branchs = restTemplate.getForObject("http://product-service/branchs", Object[].class);
		List<Object> branchsList = Arrays.asList(branchs);  
		ArrayList<Branch> topBranchs = new ArrayList<Branch>();
		for (Object branch : branchsList) {
			String[] branchvalue = branch.toString().split(",");
		
			String branchname= branchvalue[0].substring(1);
			Integer clients = Integer.parseInt(branchvalue[1].substring(1,branchvalue[1].length()-1));
			
			Branch branchdetail = restTemplate.getForObject("http://branch-service/name/"+branchname, Branch.class);
			branchdetail.setClients(clients);
			topBranchs.add(branchdetail);
		}
		
		ArrayList<Towns> topTowns = new ArrayList<Towns>();
		for (Branch bnch : topBranchs) {
			Town twn = restTemplate.getForObject("http://town-service/name/"+bnch.getTown(), Town.class);
			Towns newTown = new Towns();
			newTown.setClients(bnch.getClients());
			newTown.setName(bnch.getName());
			newTown.setTown(bnch.getTown());
			newTown.setPopulation(twn.getPopulation());
			
			topTowns.add(newTown);
		}
//		Collections.sort(topBranchs, Collections.reverseOrder());
		String filename = "Towns.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + filename + "\"");

		//create a csv writer
		StatefulBeanToCsv<Towns> writer = new StatefulBeanToCsvBuilder<Towns>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.build();

		//write all users to csv file
		writer.write(topTowns);
    }
}
