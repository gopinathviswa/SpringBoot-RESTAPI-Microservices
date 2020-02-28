package com.viswagopi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;

@Entity
@Table(name = "product", catalog = "product_info")
public class Product implements java.io.Serializable {

	private Long id;
	private String name;
	private Integer clients;
	private String branch;
	
	public Product() {
		super();
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "clients", nullable = false)
	public Integer getClients() {
		return clients;
	}
	public void setClients(Integer clients) {
		this.clients = clients;
	}
	
	@Column(name = "branch", nullable = false)
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}


}
