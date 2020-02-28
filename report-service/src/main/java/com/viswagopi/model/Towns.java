package com.viswagopi.model;

public class Towns {

	private String name;
	private String town;
	private Integer clients;
	private Integer population;
	
	public Towns() {
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
	
    public Integer getPopulation() {
		return population;
	}
    
	public void setPopulation(Integer population) {
		this.population = population;
	}
	@Override
    public String toString() {
        return "Clients [count=" + clients + "]";
    }

	
}
