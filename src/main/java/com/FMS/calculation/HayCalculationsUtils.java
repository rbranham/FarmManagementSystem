package com.FMS.calculation;

import java.util.ArrayList;

import com.FMS.data.DAOInterface;
import com.FMS.models.FarmTask;

/*
 * Holds calculation business logic related to hay making business
 */
public class HayCalculationsUtils {

	//Constants
	public final static double OPERATOR_PAY = 15.00;
	public final static double BALE_PRICE = 60.00;
	public final static double BURN_RATE = 4.00; //Fuel/Hour
	public final static double FUEL_COST = 3.00;
	
	public static double getBaleCountFromTaskList(ArrayList<FarmTask> tasks) {
		
		return tasks.stream()
		.filter(t -> t.getType().equals("BALE"))
		//Any tasks marked with bale code should have a single integer in notes TODO Should eventually hide implementation code in model class for reading data from notes
		.mapToDouble(t -> Double.parseDouble(t.getNotes()))  
		.reduce(0,Double::sum);
		
	}
	
	public static double getVarCostsFromTaskList(ArrayList<FarmTask> tasks) {
		
		//Get Hours Worked
		double totalHours = tasks.stream()
				.mapToDouble(FarmTask::getHours)
				.reduce(0, Double::sum);

		
		//Get some calculations
		double laborCosts = totalHours * OPERATOR_PAY; //Hours * Operator hourly rate
		double fuelCosts = totalHours * BURN_RATE * FUEL_COST;  //Hours * burn rate * cost per gallon
		double varCosts = laborCosts + fuelCosts;
		
		return varCosts;
	}
}
