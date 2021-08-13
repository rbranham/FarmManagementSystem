package com.FMS.models;

import java.util.ArrayList;

public class FarmTask {

	private int id; 
	private String type;
	private int jobId;
	private double hours; 
	private String notes;
	private String date;
	
	//Employees
	private ArrayList<Integer> assignedEmployeeIds;
	private ArrayList<Employee> assignedEmployees; 
	
	//Assigned Equipment
	private ArrayList<Integer> assignedEquipmentIds;
	private ArrayList<Equipment> assignedEquipment;

	
	public FarmTask(int id, String type, int jobId, double hours, String notes, String date) {
		super();
		this.id = id;
		this.type = type;
		this.jobId = jobId;
		this.hours = hours;
		this.notes = notes;
		this.date = date;
	}


	@Override
	public String toString() {
		return "FarmTask [id=" + id + ", type=" + type + ", jobId=" + jobId + ", hours=" + hours + "]";
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getJobId() {
		return jobId;
	}


	public void setJobId(int jobId) {
		this.jobId = jobId;
	}


	public double getHours() {
		return hours;
	}


	public void setHours(double hours) {
		this.hours = hours;
	}

	public void addEmployeeId(int employeeId) {
		
		if(this.assignedEmployeeIds == null) {
			this.assignedEmployeeIds = new ArrayList<Integer>();
		}
		
		this.assignedEmployeeIds.add(employeeId);
	}
	
	public void addEquipmentId(int equipmentId) {
		
		if(this.assignedEquipmentIds == null) {
			this.assignedEquipmentIds = new ArrayList<Integer>();
		}
		
		this.assignedEquipmentIds.add(equipmentId);
	}

	public ArrayList<Integer> getAssignedEmployeeIds() {
		return assignedEmployeeIds;
	}


	public void setAssignedEmployeeIds(ArrayList<Integer> assignedEmployeeIds) {
		this.assignedEmployeeIds = assignedEmployeeIds;
	}


	public ArrayList<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}


	public void setAssignedEmployees(ArrayList<Employee> assignedEmployees) {
		this.assignedEmployees = assignedEmployees;
	}


	public ArrayList<Integer> getAssignedEquipmentIds() {
		return assignedEquipmentIds;
	}


	public void setAssignedEquipmentIds(ArrayList<Integer> assignedEquipmentIds) {
		this.assignedEquipmentIds = assignedEquipmentIds;
	}


	public ArrayList<Equipment> getAssignedEquipment() {
		return assignedEquipment;
	}


	public void setAssignedEquipment(ArrayList<Equipment> assignedEquipment) {
		this.assignedEquipment = assignedEquipment;
	} 
	
	
	
}
