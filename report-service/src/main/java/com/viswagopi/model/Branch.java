package com.viswagopi.model;

public class Branch implements Comparable<Branch> {

	private String name;
	private String town;
	private Integer clients;
	
	public Branch() {
		super();
	}
		public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
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
	
    @Override
    public String toString() {
        return "Clients [count=" + clients + "]";
    }
    
	@Override
	public int compareTo(Branch o) {
		return this.getClients().compareTo(o.getClients());
	}

	
}
