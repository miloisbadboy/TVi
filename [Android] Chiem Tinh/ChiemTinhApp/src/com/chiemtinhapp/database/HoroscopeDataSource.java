package com.chiemtinhapp.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HoroscopeDataSource {
	private SQLiteDatabase database;
	private DbOpenHelper dbHelper;
	private String[] allCollumns = { DbOpenHelper.COLUMN_HOROSCOPE_ID, DbOpenHelper.COLUMN_HOROSCOPE_NAME, 
			DbOpenHelper.COLUMN_HOROSCOPE_BIRTHDAY, DbOpenHelper.COLUMN_HOROSCOPE_PERSONALITY,
			DbOpenHelper.COLUMN_HOROSCOPE_LOVE, DbOpenHelper.COLUMN_HOROSCOPE_CAREER,
			DbOpenHelper.COLUMN_HOROSCOPE_FORTUNE, DbOpenHelper.COLUMN_HOROSCOPE_LUCK };
	private String[] titles = { "Id", "Name", "Ngày sinh", "Tính cách", "Tình yêu", "Sự nghiệp", "Tiền bạc", "May mắn" };
	public HoroscopeDataSource(Context context) {
		dbHelper = new DbOpenHelper(context);
	}
	public void open() {
		Log.d("OPENING","Opening");
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	public ArrayList<String[]> getResult(int id) {
		Cursor cursor = database.query(DbOpenHelper.TABLE_GENERAL_HOROSCOPE, allCollumns, DbOpenHelper.COLUMN_HOROSCOPE_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		ArrayList<String[]> data = cursorToResult(cursor);
		cursor.close();
		return data;
	}
	private ArrayList<String[]> cursorToResult(Cursor cursor) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < 8; i++) {			
			data.add(new String[] { titles[i], cursor.getString(i) });
		}
		return data;
	}
}