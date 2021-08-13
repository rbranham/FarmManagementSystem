package com.FMS.models;

public class Equipment {

	private int id;
	private String name; 
	private String notes; 
	//private String purchaseDate; // This could probably be calculated later as well from transactions
	//Purchase price, current value and more can be calculated through transactions
	
	public Equipment(int id, String name, String notes) {
		super();
		this.id = id;
		this.name = name;
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Equipment [id=" + id + ", name=" + name + ", notes=" + notes + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}
