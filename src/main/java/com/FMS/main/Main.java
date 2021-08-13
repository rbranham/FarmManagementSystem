package com.FMS.main;

import com.FMS.data.DAOInterface;
import com.FMS.data.MySQLDAO;
import com.FMS.models.*;

public class Main {
	
	//Tool is for personal use and as such I didn't feel the need to build a UI.
	//It is also still very much a prototype and work in progress.
	//Requires MySQL db to run. This currently is only running on my local machine.
	//For future plan may possibly host DB on AWS rds server, and then 
	//convert this code into Spring Boot Rest api and host on AWS EC2 instance
	//only then would I want to create a frontend.
	public static void main(String[] args) {
		
		/* --- I change code here to interact with program --- */
		
		DAOInterface db = MySQLDAO.getInstance(); //Database object	
		
//		addJob(db);
//		addingField(db);
//		addingEquipment(db);
//		addingTask(db);	
		
		Display d = new Display(db); //Display holds various display functions,
		
		d.generateReportForField(1, 2021);
		//d.generateYearHayReport(2021);
		
//		d.displayFields();
//		d.displayEmployees();
//		d.displayJobs();
//		d.displayTasks();
//		d.displayEquipment();	
//		d.displayTasks();
		
		
	}
	
	//Helper functions for adding new stuff to database. 
	private static void addingField(DAOInterface db) {
		
		//Fields
		Field field = new Field(
				-1,
				"Pratt Road Field",
				6.75,
				"HAY"
				);
		
		int x = db.addField(field);
		  		
	}
	
	private static void addingTask(DAOInterface db) {
		
		
		FarmTask t = new FarmTask(
				-1,     //empty when adding
				"BALE",  //Type
				9,      //JobId
				5.5, 		//Hours
				"34", //Notes
				"07/18/21"
				);
		
		t.addEmployeeId(1);  //Employee Number  1 - Dad, 2 - me, 3 - Bjorn
		
		//Tractor Set up
		t.addEquipmentId(2); //Add Tractor, 1 - 1070  , 2 - 1270
		t.addEquipmentId(6); //Implement   5 - rake, 6 - baler
		
		db.addTask(t);
		
	}
	
	private static void addingEquipment(DAOInterface db) {
		
		Equipment e = new Equipment(
				-1,  	//Empty ID
				"1070 CASE",
				"No Notes"
				);
		
		db.addEquipment(e);
	}
	
	private static void addingEmployee(DAOInterface db) {
		
		Employee e = new Employee(
					-1,
					"Bjorn Branham",
					"2014",
					"Son of owner"
				);
		
		db.addEmployee(e);
		
	}
	
	private static void addJob(DAOInterface db) {
		
		Job j = new Job(
				-1, //id
				"1st Crop North Olson Field", //Name
				"7/17/2021", //Dates
				9, //field ID 
				2021 //Season id
				);
		
		db.addJob(j);
		
	}
	
	private static void addingSeason(DAOInterface db) {
		
		Season s = new Season(2021, "Dry year");		
		db.addSeason(s);
	
	}
	


}
