package com.FMS.models;

public class Task {
	
	enum TaskCode {
		CUTHAY,
		RAKEHAY,
		BALEHAY,
		MOVEHAY,
	}
	
	private float hours; 
	private Task taskCode;
	
	
	public Task(float hours, Task taskCode) {
		super();
		this.hours = hours;
		this.taskCode = taskCode;
	}


	public float getHours() {
		return hours;
	}


	public Task getTaskCode() {
		return taskCode;
	} 
	
	
	
	
}
