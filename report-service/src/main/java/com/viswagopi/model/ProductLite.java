package com.viswagopi.model;

public class ProductLite{

	private Integer clients;
	private String branch;
	
	public ProductLite() {
		super();
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

//	@Override
//    public int compareTo(ProductLite o) {
//        return this.getClients().compareTo(o.getClients());
//    }

}
