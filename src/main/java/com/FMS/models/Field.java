package com.FMS.models;

import java.util.ArrayList;

public class Field {
	
	private int id; 
	private String name; 
	private double acres;
	private String classification;
	private ArrayList<Task> tasks; //Probably shouldn't keep tasks like this to be honest. Should make a call to db for tasks with fieldID=x, 
	
	
	public Field(int id, String name, double acres, String classification) {
		super();
		this.name = name;
		this.acres = acres;
		this.id = id; 
		this.classification = classification;
	}


	
	
	@Override
	public String toString() {
		return "Field [id=" + id +", name=" + name + ", acres=" + acres + "]";
	}


	//GETTERS AND SETTERS
	public String getName() {
		return name;
	}


	public double getAcres() {
		return acres;
	}


	public ArrayList<Task> getTasks() {
		return tasks;
	} 
	
	public void addTask(Task t) {
		tasks.add(t);
	}


	public String getClassification() {
		return classification;
	}




	public int getId() {
		return id;
	}
	
	

}
