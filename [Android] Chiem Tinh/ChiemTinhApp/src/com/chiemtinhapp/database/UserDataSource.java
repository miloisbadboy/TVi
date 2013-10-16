package com.chiemtinhapp.database;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chiemtinhapp.helper.DateFormatHelper;
import com.chiemtinhapp.helper.Gender;
import com.chiemtinhapp.model.User;

public class UserDataSource {
	private SQLiteDatabase database;
	private DbOpenHelper dbHelper;
	private String[] allColumns = {DbOpenHelper.COLUMN_USER_ID, DbOpenHelper.COLUMN_USER_NAME, 
			DbOpenHelper.COLUMN_USER_BIRTHDAY, DbOpenHelper.COLUMN_USER_GENDER};
	public UserDataSource(Context context) {
		dbHelper = new DbOpenHelper(context);
	}
	public void open() {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	public User addUser(String name, Date birthday, Gender gender) {
		ContentValues values = new ContentValues();
		values.put(DbOpenHelper.COLUMN_USER_NAME, name);
		values.put(DbOpenHelper.COLUMN_USER_BIRTHDAY, DateFormatHelper.formatter.format(birthday));
		values.put(DbOpenHelper.COLUMN_USER_GENDER, gender.ordinal());
		long id = database.insert(DbOpenHelper.TABLE_USERS, null, values);
		Cursor cursor = database.query(DbOpenHelper.TABLE_USERS, allColumns, DbOpenHelper.COLUMN_USER_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		User user = cursorToUser(cursor);
		cursor.close();
		return user;
	}
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		Cursor cursor = database.query(DbOpenHelper.TABLE_USERS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			users.add(cursorToUser(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		return users;
	}
	public void deleteUser(User user) {
		long id = user.getId();
		database.delete(DbOpenHelper.TABLE_USERS, DbOpenHelper.COLUMN_USER_ID + " = " + id, null);
	}
	private User cursorToUser(Cursor cursor) {
		try {
			return new User(cursor.getLong(0), cursor.getString(1), DateFormatHelper.formatter.parse(cursor.getString(2)), Gender.values()[cursor.getInt(3)]);
		}
		catch (ParseException e) {
			Log.w(e.getClass().toString(), e.getMessage());
			return null;
		}
	}
}
