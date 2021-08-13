package com.FMS.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.FMS.calculation.HayCalculationsUtils;
import com.FMS.data.DAOInterface;
import com.FMS.models.*;

public class Display {

	private DAOInterface db; 
	
	public Display(DAOInterface db) {
		this.db = db; 
	}
	
	//TODO: Some boiler plate between still, may want to do more refactoring out of reports
	public void generateReportForField(int fieldId, int seasonId) {
		//Header
		String headerString = "--------------------Field Report For FieldID: " + fieldId + " and Season: "+ seasonId + "-------------------";
		System.out.println(headerString);
		
		//Get field data
		Field field = db.getField(fieldId); 
		
		//Get All jobs for field and Season
		ArrayList<Job> jobs = db.getJobs(fieldId, seasonId);
		displayJobs(jobs);
		
		//Get All Tasks for each job //TODO: Refactor a general function to do this accumulation
		ArrayList<FarmTask> tasks = new ArrayList<FarmTask>(); //List to dump all tasks into 
		for(Job j : jobs) {
			
			ArrayList<FarmTask> tempTasks = db.getTasksForJob(j.getId());
			tasks.addAll(tempTasks);
			
		}
		
		//Display those Tasks
		displayTasks(tasks);	
		
		displayFinancialHayStats(
				field.getAcres(),
				HayCalculationsUtils.getBaleCountFromTaskList(tasks),
				HayCalculationsUtils.getVarCostsFromTaskList(tasks)
				);
	}
	
	//Generate Report for year
	public void generateYearHayReport(int seasonId) {
		
		//Header
		String headerString = "--------------------Season Hay Report For " + seasonId + "-------------------";
		System.out.println(headerString);
				
		//Get List of Hay fields
		ArrayList<Field> fields = new ArrayList<Field>(
					db.getFields().stream().filter(f -> f.getClassification().equals("HAY")).collect(Collectors.toList()) //Only hay fields
				);
		
		double acreSum = fields.stream()
				.mapToDouble(Field::getAcres)
				.reduce(0, Double::sum);
				
		//TODO: Lots of boiler plate for accumulating should refactor into singular function
		//Accumulator for jobs from fields
		ArrayList<Job> jobs = new ArrayList<Job>();
		for(Field f : fields) {
			//Get All jobs for each field and add to master list
			 jobs.addAll(db.getJobs(f.getId(), seasonId));
		}		
		
		//Accumulator of tasks from jobs
		ArrayList<FarmTask> tasks = new ArrayList<FarmTask>(); //List to dump all tasks into 
		for(Job j : jobs) {
			
			ArrayList<FarmTask> tempTasks = db.getTasksForJob(j.getId());
			tasks.addAll(tempTasks);
			
		}
		
		displayFinancialHayStats(
				acreSum,
				HayCalculationsUtils.getBaleCountFromTaskList(tasks),
				HayCalculationsUtils.getVarCostsFromTaskList(tasks)
				);
				
	}
	
	// Helper Report Function
	private void displayFinancialHayStats(double acres, double bales, double varCosts) {
		// TODO: make better formatted table 
		
		//Bales
		System.out.println("Number of bales made: " + bales + "        - Per Acre: " + (bales / acres));
		
		//Revenue 
		double revenue = bales * 60;  //number of bales * value per bale
		System.out.println("Gross revenue: $" + revenue + "            - Per Acre: $" + (revenue / acres));
		
		//Costs
		System.out.println("Costs of labor and fuel: $" + varCosts + "   - Per Acre: $" + (varCosts / acres));
		
		//Returns to equipment/management
		double rte = revenue - varCosts;
		System.out.println("Returns to equipment: $" + rte + "     - Per Acre: $" + (rte / acres));
	}
	
	//  ------------ Simple Display Functions ----------------------------------
	
	private void printHeader(String title) {
		System.out.println("-------------------" + title + "-------------------");
	}
	
	public void displayFields() {	
		this.printHeader(" Fields ");
		for(Field f : db.getFields()) { System.out.println(f); }	
		this.printHeader("--------");
	}
	
	public void displayJobs() {
		displayJobs(db.getAllJobs());
	}
	
	private void displayJobs(ArrayList<Job> jobs) {
		this.printHeader(" Jobs ");
		for(Job j : jobs) { System.out.println(j); }
		this.printHeader("--------");
	}
	
	public void displayEmployees() {
		this.printHeader(" Employees ");
		for(Employee e : db.getAllEmployees()) { System.out.println(e); }
		this.printHeader("-----------");
	}
	
	public void displayTasks() {
		displayTasks(db.getAllTasks());
	}

	private void displayTasks(List<FarmTask> tasks) {
		this.printHeader(" All Tasks ");
		for(FarmTask t: tasks) { System.out.println(t); }
		this.printHeader("-----------");
	}
	
	public void displayEquipment() {
		this.printHeader(" Equipment ");
		for(Equipment e: db.getEquipment()) { System.out.println(e); }
		this.printHeader("-----------");
	}
	
	public void displaySeasons() {
		this.printHeader(" Seasons ");
		for(Season s: db.getSeasons()) { System.out.println(s); }
		this.printHeader("-----------");
	}
	
	

}
