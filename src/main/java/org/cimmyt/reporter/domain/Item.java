package org.cimmyt.reporter.domain;

public class Item {
	private String city;
	private Integer id;
	private String name;
	private String street;

	
		public Item(Integer id, String name, String city, String street) {
			super();
			this.city = city;
			this.id = id;
			this.name = name;
			this.street = street;
		}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
}
