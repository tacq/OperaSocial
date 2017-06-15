package com.ramytech.piaxi.db;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

public class DBHelper {

	private static DBHelper DBinsitance;
	private Context context;
	private static DbUtils db;
	
	public DBHelper(Context context){
		this.context = context;
	}

	public static DBHelper getIns(Context context) {
		if (DBinsitance == null) {
			synchronized (DBHelper.class) {
				if (DBinsitance == null) {
					DBinsitance = new DBHelper(context);
					db = DbUtils.create(context);
					db.configAllowTransaction(false);
					// FIXME 用于打印sql语句的log，可以关闭
					db.configDebug(true);
				}
			}
		}
		return DBinsitance;
	}

	public DbUtils getDB() {
		return db;
	}

	public void close() {
		db.close();
		db = null;
		context = null;
		DBinsitance = null;
	}
}
