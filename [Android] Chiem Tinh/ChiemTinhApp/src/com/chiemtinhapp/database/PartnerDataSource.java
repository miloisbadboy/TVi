package com.chiemtinhapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PartnerDataSource {
	private SQLiteDatabase database;
	private DbOpenHelper dbHelper;
	private String[] allColumns = { DbOpenHelper.COLUMN_PARTNERS_SIGN_ID, DbOpenHelper.COLUMN_PARTNERS_PARTNER_ID, 
					DbOpenHelper.COLUMN_PARTNERS_TYPE, DbOpenHelper.COLUMN_PARTNERS_CONTENT };
	public PartnerDataSource(Context context) {
		dbHelper = new DbOpenHelper(context);
	}
	public void open() {
		database = dbHelper.getReadableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	public String getResult(int signId, int partnerId, int type) {
		Cursor cursor = database.query(DbOpenHelper.TABLE_PARTNERS, new String[] { allColumns[3] }, 
				DbOpenHelper.COLUMN_PARTNERS_SIGN_ID + " = " + signId + " AND " + DbOpenHelper.COLUMN_PARTNERS_PARTNER_ID + " = " + partnerId
				 + " AND " + DbOpenHelper.COLUMN_PARTNERS_TYPE + " = " + type, 
				null, null, null, null);
		cursor.moveToFirst();
		String result = cursorToResult(cursor);
		Log.d("Test", result);
		cursor.close();
		return result;
	}
	private String cursorToResult(Cursor cursor) {
		return cursor.getString(0);
	}
}
