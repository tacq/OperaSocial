package com.ramytech.piaxi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String NAME = "piaxi.db";
    private static final int version = 1;
    public DataBaseHelper(Context context)
    {
        super(context,NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    	//表名：play_classification 
    	//存储分类列列表的信息 
    	//表项：play_type分类类目，play_name分类名，play_encoding分类值，order顺序(大于 0 的可以调整顺序,小于 0 的不可调整顺序)
    	sqLiteDatabase
    		.execSQL("CREATE TABLE IF NOT EXISTS play_classification " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "play_type INTEGER," +
                "play_name VARCHAR(40)," +
                "play_encoding INTEGER," +
                "play_order INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS play_classification");
        onCreate(sqLiteDatabase);
    }
}
