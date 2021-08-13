package com.FMS.models;

public class Season {

	private int seasonId;
	private String notes;
	
	
	public Season(int seasonId, String notes) {
		super();
		this.seasonId = seasonId;
		this.notes = notes;
	}


	@Override
	public String toString() {
		return "Season [seasonId=" + seasonId + ", notes=" + notes + "]";
	}


	public int getSeasonId() {
		return seasonId;
	}


	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}
