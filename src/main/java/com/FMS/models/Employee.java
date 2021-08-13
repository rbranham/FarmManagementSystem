package com.FMS.models;

public class Employee {
	
	private int id; 
	private String fullName; 
	private String hireDate; 
	private String notes;
	
	
	public Employee(int id, String fullName, String hireDate, String notes) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.hireDate = hireDate;
		this.notes = notes;
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", fullName=" + fullName + ", hireDate=" + hireDate + ", notes=" + notes + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getHireDate() {
		return hireDate;
	}


	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	} 
	
	

}
