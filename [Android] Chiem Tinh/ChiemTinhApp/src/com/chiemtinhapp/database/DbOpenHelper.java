package com.chiemtinhapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_GENDER = "gender";
	public static final String TABLE_USERS = "users";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "chiemtinhdb";
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_USERS + " (" + 
			COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " TEXT NOT NULL, " + 
			COLUMN_BIRTHDAY + " TEXT NOT NULL, " + COLUMN_GENDER + " integer);";

	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DbOpenHelper.class.getName(), "Upgrading from " + oldVersion + " to " + newVersion
				+ " will erase all data");
		database.execSQL("drop table if exist " + TABLE_USERS);
		onCreate(database);
	}

}
