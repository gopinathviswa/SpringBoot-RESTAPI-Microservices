package com.viswagopi.model;


public class Product {

	private Long id;
	private String name;
	private Integer clients;
	private String branch;
	
	public Product() {
		super();
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClients() {
		return clients;
	}
	public void setClients(Integer clients) {
		this.clients = clients;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}


}
