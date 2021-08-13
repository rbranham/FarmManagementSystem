package com.FMS.data;

import java.util.ArrayList;
import java.util.Objects;

import com.FMS.models.Field;
import com.FMS.models.Task;

public class MockDAO  { //implements DAOInterface
	
	private ArrayList<Field> fields;
	
	private MockDAO() {
		//Private
	}

//	public static DAOInterface getInstance() {
//		MockDAO db = new MockDAO();
//		db.intializeDB();
//		return db;
//		
//	}


	
	
	//Fields
	public ArrayList<Field> getFields() {
		return fields;
	}
	
	
	//INITIALIZATION CODE
	public void intializeDB() {
		
		//Creation Code Here
		initializeFields();
			
	}
	
	private void initializeFields() {
		fields = new ArrayList<Field>();
		
		
//		//Fields
//		fields.add(new Field(
//				1,
//				"Bus Field",
//				21.46f,
//				new ArrayList<Task>() //Empty Tasks for now
//				));
//		
//		
//		fields.add(new Field(
//				2,
//				"Lime Pile Field",
//				19.6f,
//				new ArrayList<Task>()
//				));
//		
//		
//		fields.add(new Field(
//				3,
//				"Back 80 Field",
//				23.8f,
//				new ArrayList<Task>()
//				));
//		
//		
//		fields.add(new Field(
//				4,
//				"Nordiens Field",
//				23.8f,
//				new ArrayList<Task>()
//				));
		
	}

//	@Override
//	public int addField(Field f) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Field getField(int id) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int deleteField(int id) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int updateField(Field f) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
