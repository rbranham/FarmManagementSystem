package com.FMS.models;

public class Job {
	
	private int id; 
	private String name; 
	private String startDate; 
	private int assignedFieldId;
	private int seasonId;
	//TODO: Probably want to add some job types like, MakeHay, plantField, etc... Will make some filtering easier
	
	public Job(int id, String name, String startDate, int assignedFieldId, int seasonId) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.assignedFieldId = assignedFieldId;
		this.seasonId = seasonId;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", name=" + name + ", startDate=" + startDate + ", assignedFieldId=" + assignedFieldId
				+ ", seasonId=" + seasonId + "]";
	}
	
	public int getSeasonId() {
		return seasonId;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public int getAssignedFieldId() {
		return assignedFieldId;
	}
	public void setAssignedFieldId(int assignedFieldId) {
		this.assignedFieldId = assignedFieldId;
	} 
	
	
	
	
}
