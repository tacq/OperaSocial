package com.ramytech.piaxi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
	 private DataBaseHelper helper;  
	 private SQLiteDatabase db;
	 
	 public DataBaseManager(Context context)
	 {
		 helper = new DataBaseHelper(context);
		 db = helper.getWritableDatabase(); 
	 }
	 
	 public void insertData(String databaseName,Object data)
	 {
		 
	 }
	 
	 public void updateDatabase(String databaseName, Object data)
	 {
		 
	 }
	 
	 public void closeDataBase() 
	 {  
		 db.close();  
	 }  
}
