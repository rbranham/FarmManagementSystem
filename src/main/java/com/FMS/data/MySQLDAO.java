package com.FMS.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.FMS.models.Employee;
import com.FMS.models.Equipment;
import com.FMS.models.FarmTask;
import com.FMS.models.Field;
import com.FMS.models.Job;
import com.FMS.models.Season;

import com.FMS.data.DAOUtil.*;

/*
 * Massive class for interacting with database, 
 * was a way to learn JDBC API, but needs major clean up
 * discovered a better way to do things halfway through
 * but have not refactored to make consistent across file. 
 */
public class MySQLDAO implements DAOInterface {

	/* --------------- Constants -------------------------------*/	
	//TODO: Make all sql variables private?
	
	//Important Strings for connecting
	final String databaseString = "farm_managment_database";
	
	//Important Strings for SQL on fields table
	final String fieldTable = "fields";
	final String fullFieldTable = wrapB(databaseString) + "." + wrapB(fieldTable);
	final String fieldId = "idfields";
	final String fieldNameString = "Name";
	final String acres = "Acres";
	final String fieldClassification = "Classification";
	final String fieldInsertString = "(" + fieldNameString + ", " + acres + ", " + fieldClassification + ") "; 	
	
	//Important Strings for SQL on Seasons Table
	final String seasonTable = "seasons";
	final String seasonsId = "idseasons";
	final String seasonNotes = "notes";
	final String seasonInsertString = "(" + seasonsId + ", " + seasonNotes + ") "; //Season Id equals year number, does not auto increment. 	
	
	//Important Strings for jobs SQL
	final String jobsTable = "jobs";
	final String jobsId = "idjobs";
	final String jobsName = "name";
	final String jobsStartDate = "start_date";
	final String jobsField = "fieldid";
	final String jobsSeason = "seasonid";
	final String jobsInsertString = "(" + jobsName + ", " + jobsStartDate + ", " + jobsField + ", " + jobsSeason + ") ";
	
	//Important Strings for Employees SQL
	final String employeesTable = "employees";
	final String employeeId = "idemployees";
	final String employeeFullName = "full_name";
	final String employeeHireDate = "hire_date";
	final String employeeNotes = "notes";
	final String employeesInsertString = "(" + employeeFullName + ", " + employeeHireDate + ", " + employeeNotes + ") ";	
	
	//Strings for Equipment
	final String equipmentTable = "equipment";
	final String equipmentId = "idequipment";
	final String equipmentName = "name";
	final String equipmentNotes = "notes";
	final String equipmentInsertString = "(" + equipmentName + ", " + equipmentNotes + ") ";
	
	//Strings for FarmTasks
	final String tasksTable = "farm_tasks";
	final String tasksId = "idfarm_tasks";
	final String tasksType = "type";
	final String tasksJob = "jobId";
	final String tasksHours = "hours";
	final String tasksNotes = "notes";
	final String tasksDate = "date";
	final String tasksInsertString = "(" + tasksType + ", " + tasksJob + ", " + tasksHours + ", " + tasksNotes + ", " + tasksDate + ") ";
	final String SQL_TASK_INSERT = "INSERT INTO " + tasksTable + " " + tasksInsertString + "VALUES (?, ?, ?, ?, ?);";
	
	//Task Joint Table Strings
	//Task-Employee Joint Table
	final String tasksEmployeesTable = "employee_task_join";
	final String tasksEmployeesEmployeeId = "employeeId";
	final String tasksEmployeesTaskId = "taskId";
	final String tasksEmployeesInsertString = "(" + tasksEmployeesEmployeeId + ", " + tasksEmployeesTaskId + ") ";
	final String SQL_TASK_EMPLOYEE_INSERT =  "INSERT INTO " + tasksEmployeesTable + " " + tasksEmployeesInsertString + "VALUES (?, ?);";
	
	//Task-Equipment Joint Table
	final String tasksEquipmentTable = "equipment_task_join";
	final String tasksEquipmentEquipmentId = "equipmentId";
	final String tasksEquipmentTaskId = "taskid";
	final String tasksEquipmentInsertString = "(" + tasksEquipmentEquipmentId + ", " + tasksEquipmentTaskId + ") ";
	final String SQL_TASK_EQUIPMENT_INSERT =  "INSERT INTO " + tasksEquipmentTable + " " + tasksEquipmentInsertString + "VALUES (?, ?);";
	
	
	private Connection conn = null;
	
	public static DAOInterface getInstance() {
		MySQLDAO db = new MySQLDAO();
		db.connect();	
		return db;		
	}
	
	private void connect() {
		
        try {
        	
        	conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/farm_managment_database", "root", "Admin");
        	
            if (conn != null) {
                System.out.println("Connected to the database!");
                
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<Field> getFields() {
		ArrayList<Field> fields = new ArrayList<Field>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + fieldTable + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				fields.add(new Field(
						rs.getInt(fieldId),
						rs.getString(fieldNameString),
						rs.getDouble(acres),
						rs.getString(fieldClassification)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return fields;
	}

	
	public int addField(Field f) {
		try {
			
			//Should first do an ID look up to make sure not already in database
			
			Statement stmt = conn.createStatement();
			
			String[] vals = {f.getName(), Double.toString(f.getAcres()), f.getClassification()};
			
			String sql = "INSERT INTO " + fieldTable 
					+ fieldInsertString
					+ generateValuesString(vals)
					+ ";";
			
			stmt.executeUpdate(sql);
			return 1;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return -1;
		}	
	}

	@Override
	public Field getField(int id) {
		Field f = null;
				try {
					Statement stmt = conn.createStatement();
					
					String sql = "SELECT * FROM " + fieldTable
							+ " WHERE " + this.fieldId + " = " + id
							+ ";";
					
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()) {
						f = new Field(
								rs.getInt(fieldId),
								rs.getString(fieldNameString),
								rs.getDouble(acres),
								rs.getString(fieldClassification)
								);
					} else {
						System.out.println("No result found for field with id: " + id );
					}

				} catch (SQLException e) {
					
					e.printStackTrace();

				}
				
			return f;
	}

	
	@Override
	public int deleteField(int id) {
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "DELETE FROM " + fieldTable
					+ " WHERE " + this.fieldId + " = " + id + ";";
			
			stmt.executeUpdate(sql);		

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateField(Field f) {
		//Have not tested new version
		
		try {		
			
			Statement stmt = conn.createStatement();
			
			String sql = "UPDATE " + fullFieldTable
					+ " SET "
					+ generateUpdateAttributeLine(fieldNameString, f.getName(), true)
					+ generateUpdateAttributeLine(acres, f.getAcres(), true)
					+ generateUpdateAttributeLine(fieldClassification, f.getClassification(), false)
					+ " WHERE(" + wrapB(fieldId) + " = " + "'" + f.getId() + "'"
					+ ");";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			return f.getId();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return -1;
		}	
		
	}

	/*                        SEASONS                    */
	@Override
	public ArrayList<Season> getSeasons() {
		ArrayList<Season> seasons = new ArrayList<Season>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + seasonTable + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				seasons.add(new Season(
						rs.getInt(seasonsId),
						rs.getString(seasonNotes)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return seasons;
	}

	@Override
	public Season getSeason(int id) {
		Season s = null;
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + seasonTable
					+ " WHERE " + this.seasonsId + " = " + id
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				s = new Season(
						rs.getInt(seasonsId),
						rs.getString(seasonNotes)
						);
			} else {
				System.out.println("No result found for Season with id: " + id );
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
	return s;
	}

	@Override
	public void addSeason(Season s) {
		try {
			
			//Should first do an ID look up to make sure not already in database
			
			Statement stmt = conn.createStatement();
			
			String[] vals = {Integer.toString(s.getSeasonId()), s.getNotes()};
			
			String sql = "INSERT INTO " + seasonTable 
					+ seasonInsertString
					+ generateValuesString(vals)
					+ ";";
			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
	}

	/*                     Jobs                               */
	
	@Override
	public ArrayList<Job> getAllJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + jobsTable + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				jobs.add(new Job(
						rs.getInt(jobsId),
						rs.getString(jobsName),
						rs.getString(jobsStartDate),
						rs.getInt(jobsField),
						rs.getInt(jobsSeason)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return jobs;
	}

	@Override
	public Job getJob(int jobId) {
		Job j = null;
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + jobsTable 
					+ " WHERE " + this.jobsId + " = " + jobId
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
				
				if(rs.next()) {
					j = new Job(
							rs.getInt(jobsId),
							rs.getString(jobsName),
							rs.getString(jobsStartDate),
							rs.getInt(jobsField),
							rs.getInt(jobsSeason)
							);
				} else {
					System.out.println("No result found for Season with id: " + jobId );
				}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return j;
	}

	@Override
	public ArrayList<Job> getJobs(int fieldId, int seasonId) {
		ArrayList<Job> jobs = new ArrayList<Job>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + jobsTable 
					+ " WHERE " + this.jobsField + " = " + fieldId
					+ " AND " + this.jobsSeason + " = " + seasonId
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				jobs.add(new Job(
						rs.getInt(jobsId),
						rs.getString(jobsName),
						rs.getString(jobsStartDate),
						rs.getInt(jobsField),
						rs.getInt(jobsSeason)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return jobs;
	}

	@Override
	public void addJob(Job j) {
		try {
			
			//Should first do an ID look up to make sure not already in database
			
			Statement stmt = conn.createStatement();
			
			String[] vals = {
						j.getName(),
						j.getStartDate(),
						Integer.toString(j.getAssignedFieldId()),
						Integer.toString(j.getSeasonId())
					};
			
			String sql = "INSERT INTO " + jobsTable 
					+ jobsInsertString
					+ generateValuesString(vals)
					+ ";";
			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteJob(int jobId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateJob(Job j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + employeesTable + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				employees.add(new Employee(
						rs.getInt(employeeId),
						rs.getString(employeeFullName),
						rs.getString(employeeHireDate),
						rs.getString(employeeNotes)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return employees;
	}

	@Override
	public Employee getEmployee(int employeeId) {
		Employee e = null;
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + employeesTable 
					+ " WHERE " + this.employeeId + " = " + employeeId
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
				
				if(rs.next()) {
					e = new Employee(
							rs.getInt(employeeId),
							rs.getString(employeeFullName),
							rs.getString(employeeHireDate),
							rs.getString(employeeNotes)
							);
				} else {
					System.out.println("No result found for Season with id: " + employeeId );
				}

		} catch (SQLException ex) {
			
			ex.printStackTrace();

		}
		
		return e;
	}

	@Override
	public void addEmployee(Employee e) {
		try {
			
			//Should first do an ID look up to make sure not already in database
			
			Statement stmt = conn.createStatement();
			
			String[] vals = {
						e.getFullName(),
						e.getHireDate(),
						e.getNotes()
					};
			
			String sql = "INSERT INTO " + employeesTable 
					+ employeesInsertString
					+ generateValuesString(vals)
					+ ";";
			
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		}	
		
	}

	@Override
	public void updateEmployee(Employee e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Equipment> getEquipment() {
		ArrayList<Equipment> equipment = new ArrayList<Equipment>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + equipmentTable + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				equipment.add(new Equipment(
						rs.getInt(equipmentId),
						rs.getString(equipmentName),
						rs.getString(equipmentNotes)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return equipment;
	}

	@Override
	public Equipment getEquipment(int id) {
		Equipment e = null;
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + equipmentTable 
					+ " WHERE " + this.equipmentId + " = " + id
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
				
				if(rs.next()) {
					e = new Equipment(
							rs.getInt(equipmentId),
							rs.getString(equipmentName),
							rs.getString(equipmentNotes)
							);
				} else {
					System.out.println("No result found for Season with id: " + id );
				}

		} catch (SQLException ex) {
			
			ex.printStackTrace();

		}
		
		return e;
	}

	@Override
	public void addEquipment(Equipment eq) {
		try {
			
			//Should first do an ID look up to make sure not already in database
			
			Statement stmt = conn.createStatement();
			
			String[] vals = {
						eq.getName(),
						eq.getNotes()
					};
			
			String sql = "INSERT INTO " + equipmentTable 
					+ equipmentInsertString
					+ generateValuesString(vals)
					+ ";";
			
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		}	
		
	}

	@Override
	public FarmTask getTask(int taskId) {
		FarmTask t = null;
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + tasksTable 
					+ " WHERE " + this.tasksId + " = " + taskId
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
				
				if(rs.next()) {
					t = new FarmTask(
							rs.getInt(tasksId),
							rs.getString(tasksType),
							rs.getInt(tasksJob),
							rs.getDouble(tasksHours),
							rs.getString(tasksNotes),
							rs.getString(tasksDate)
							);
				} else {
					System.out.println("No result found for Season with id: " + taskId );
				}

		} catch (SQLException ex) {
			
			ex.printStackTrace();

		}
		
		return t;
	}

	@Override
	public ArrayList<FarmTask> getAllTasks() {
		ArrayList<FarmTask> tasks = new ArrayList<FarmTask>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + tasksTable + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				tasks.add(new FarmTask(
						rs.getInt(tasksId),
						rs.getString(tasksType),
						rs.getInt(tasksJob),
						rs.getDouble(tasksHours),
						rs.getString(tasksNotes),
						rs.getString(tasksDate)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return tasks;
	}


	@Override
	public void addTask(FarmTask t) {
		
			if(t.getId() != -1) {
				System.out.println("Are you sure you want to insert this id?");
				return; 
			}
		
			Integer k = null;
			
			//SQL_TASK_INSERT
			
			Object[] values = {
				t.getType(),
				t.getJobId(),
				t.getHours(),
				t.getNotes(),
				t.getDate()
			};
			
			try(
				//Should be creating connection here in the future to reduce scope? 
				PreparedStatement statement = DAOUtil.prepareStatement(conn, SQL_TASK_INSERT, true, values);
			){
				
				int affectedRows = statement.executeUpdate();
	            if (affectedRows == 0) {
	                //throw new DAOException("Creating user failed, no rows affected.");
	            	System.out.println("ERROR Creating user, no rows affected");
	            }
	            
	            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            	
	            	if (generatedKeys.next()) {
//	                    user.setId(generatedKeys.getLong(1));
	            		k = generatedKeys.getInt(1);
	                } else {
	                    //throw new DAOException("Creating user failed, no generated key obtained.");
	                	System.out.println("No Key obtained");
	                }
	            }
	            
			}  catch (SQLException e) {
	            //throw new DAOException(e);
				e.printStackTrace();
	        }
			
//			System.out.println("Task with key: " + k);

				/*            Joint Table stuff               */
			
			for(int i : t.getAssignedEmployeeIds()) {	
				System.out.println("Attempting to enter task " + k + " and employee id " + i);
				addEmployeeTaskJoint(k, i);
			}
			
			for(int i : t.getAssignedEquipmentIds()) {			
				addEquipmentTaskJoint(k, i);
			}


			
			
	}

	private void addEquipmentTaskJoint(int t, int eq) {
		
		try {	
			//Should first do an ID look up to make sure not already in database
			
			Statement stmt = conn.createStatement();
			
			String[] vals = { Integer.toString(eq), Integer.toString(t),};
			
			String sql = "INSERT INTO " + tasksEquipmentTable 
					+ tasksEquipmentInsertString
					+ generateValuesString(vals)
					+ ";";
			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
	}
	
	private void addEmployeeTaskJoint(int t, int em) {
		
		try {	
			//Should first do an ID look up to make sure not already in database
			
			Statement stmt = conn.createStatement();
			
			String[] vals = { Integer.toString(em), Integer.toString(t),};
			
			String sql = "INSERT INTO " + tasksEmployeesTable 
					+ tasksEmployeesInsertString
					+ generateValuesString(vals)
					+ ";";
			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
	}
	
	
	
	@Override
	public ArrayList<FarmTask> getTasksForJob(int jobsId) {
		
		
		ArrayList<FarmTask> tasks = new ArrayList<FarmTask>();
		
		try {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM " + tasksTable 
					+ " WHERE " + tasksJob + " = " + jobsId
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				tasks.add(new FarmTask(
						rs.getInt(tasksId),
						rs.getString(tasksType),
						rs.getInt(tasksJob),
						rs.getDouble(tasksHours), 
						rs.getString(tasksNotes),
						rs.getString(tasksDate)
						));
				
			}

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return tasks;

	}
	

	@Override
	public ArrayList<FarmTask> getTasksByEmployeeId(int employeeId) {
		return getTasksByIdAndJointTable(employeeId, tasksEmployeesTable, tasksEmployeesEmployeeId, tasksEmployeesTaskId );
	}

	@Override
	public ArrayList<FarmTask> getTasksByEquipmentId(int equipmentId) {
		return getTasksByIdAndJointTable(equipmentId, tasksEquipmentTable, tasksEquipmentEquipmentId, tasksEquipmentTaskId);
	}
	
	//Helper function to reduce boiler plate
	private ArrayList<FarmTask> getTasksByIdAndJointTable(int id, String tableString, String lookupColumn, String taskIdColumn){
		ArrayList<FarmTask> tasks = new ArrayList<FarmTask>();
		
		try { 
			//Search Joint employee Table.
			
			Statement stmt = conn.createStatement();
			
			//Not Correct yet
			String sql = "SELECT * FROM " + tableString 
					+ " WHERE " + lookupColumn + " = " + id
					+ ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			//loop through 			
			while(rs.next()) {
				
				tasks.add( this.getTask(rs.getInt(taskIdColumn)) ); //Make sure this string is correct  for joing table: "tasksid"
				
			}
		
		} catch (SQLException e) {
			
			e.printStackTrace();
	
		}
	
		return tasks;
	}
	
	
	/*                  Helper functions for writing sql               */
	//TODO: Should probably be refactored into sql util class
	private String generateUpdateAttributeLine(String columnHeader, String value, boolean isComma) {
		String s = columnHeader + " = " + wrapSingleQuote(value);
		
		if(isComma) {
			s = s  + ",";
		}
		
		return s;
	}
	
	private String generateUpdateAttributeLine(String columnHeader, double value, boolean isComma) {
		String s = columnHeader + " = " + wrapSingleQuote(value);
		
		if(isComma) {
			s = s  + ",";
		}
		
		return s;
	}
	
	private String wrapSingleQuote(String s) {
		return "'" + s +"'"; 
	}
	
	private String wrapSingleQuote(int i) {
		return wrapSingleQuote(Integer.toString(i));
	}
	
	private String wrapSingleQuote(double d) {
		return wrapSingleQuote(Double.toString(d));
	}
	
	private String wrapB(String s) {
		return "`" + s + "`";
	}
	
	private String addComma(String s) {
		return s + ",";
	}
	
	private String generateValuesString(String[] values) {
		String s = " VALUES( ";
		
		for(String t : values) {
			s = s + wrapSingleQuote(t) + ",";
		}
		
		return s.substring(0,s.length()-1) + ")";
	}

	
	
}
