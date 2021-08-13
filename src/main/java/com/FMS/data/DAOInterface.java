package com.FMS.data;
import java.util.ArrayList;

import com.FMS.models.*;

public interface DAOInterface {
	
	/* Tasks Data */
	public FarmTask getTask(int taskId);//Get task by task id
	public ArrayList<FarmTask> getAllTasks();
	public void addTask(FarmTask t); //Add Task
	//Delete Task
	//Update Task
	
	/*Task data needing to work with joint table */
	public ArrayList<FarmTask> getTasksForJob(int jobsId);//Get tasks by jobID
	public ArrayList<FarmTask> getTasksByEmployeeId(int employeeId);//Get tasks by EmployeeID and SeasonID
	public ArrayList<FarmTask> getTasksByEquipmentId(int equipmentId);
	
	
	//Field Data
	public ArrayList<Field> getFields();
	public Field getField(int id);
	public int addField(Field f);
	public int deleteField(int id);
	public int updateField(Field f);
	
	//Equipment Data
	public ArrayList<Equipment> getEquipment();//Get List
	public Equipment getEquipment(int id);//Get specific
	public void addEquipment(Equipment eq);//Add
	//Delete
	//Update
	
	/* Season Data*/
	public ArrayList<Season> getSeasons();
	public Season getSeason(int id);
	public void addSeason(Season s);
	
	/* Job Data */	
	public ArrayList<Job> getAllJobs(); //Get all List
	public Job getJob(int jobId);//Get Job by Id specific
	public ArrayList<Job> getJobs(int fieldId, int seasonId);//Get jobs by fieldID and Season ID
	public void addJob(Job j);//Add
	public void deleteJob(int jobId);//Delete
	public void updateJob(Job j);//Update
	
		
	
	/* Employee ID */
	public ArrayList<Employee> getAllEmployees(); //Get all Employees
	public Employee getEmployee(int employeeId);//Get Employee by id
	public void addEmployee(Employee e); //Add Employee
	//Delete Task
	public void updateEmployee(Employee e);//Update Task
	
	

}
