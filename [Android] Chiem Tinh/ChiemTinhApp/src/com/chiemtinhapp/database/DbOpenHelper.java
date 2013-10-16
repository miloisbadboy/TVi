package com.chiemtinhapp.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {
	// Users database
	public static final String COLUMN_USER_ID = "_id";
	public static final String COLUMN_USER_NAME = "name";
	public static final String COLUMN_USER_BIRTHDAY = "birthday";
	public static final String COLUMN_USER_GENDER = "gender";
	public static final String TABLE_USERS = "users";

	// General horoscope database
	public static final String COLUMN_HOROSCOPE_ID = "_id";
	public static final String COLUMN_HOROSCOPE_NAME = "cung";
	public static final String COLUMN_HOROSCOPE_BIRTHDAY = "ngaysinh";
	public static final String COLUMN_HOROSCOPE_PERSONALITY = "tinhcach";
	public static final String COLUMN_HOROSCOPE_LOVE = "tinhyeu";
	public static final String COLUMN_HOROSCOPE_CAREER = "sunghiep";
	public static final String COLUMN_HOROSCOPE_FORTUNE = "tienbac";
	public static final String COLUMN_HOROSCOPE_LUCK = "vanmay";
	public static final String TABLE_GENERAL_HOROSCOPE= "general_horoscope";

	// Partners TABLE
	public static final String COLUMN_PARTNERS_SIGN_ID = "SignId";
	public static final String COLUMN_PARTNERS_PARTNER_ID = "PartnerId";
	public static final String COLUMN_PARTNERS_TYPE = "PartnerType";
	public static final String COLUMN_PARTNERS_CONTENT = "ContentVN";
	public static final String TABLE_PARTNERS = "partners";

	private static final int DATABASE_VERSION = 1;
	private String DATABASE_PATH = "/data/data/com.chiemtinhapp/databases/";
	private static final String DATABASE_NAME = "chiemtinhdb";
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_USERS + " (" + 
			COLUMN_USER_ID + " integer primary key autoincrement, " + COLUMN_USER_NAME + " TEXT NOT NULL, " + 
			COLUMN_USER_BIRTHDAY + " TEXT NOT NULL, " + COLUMN_USER_GENDER + " integer);";

	private Context context;

	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		// Copy the database from asset folder.
		this.context = context;
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

	public void createDataBase() throws IOException{

		boolean dbExist = checkDataBase();

		if(dbExist){
			//do nothing - database already exist
		}else{

			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();
				this.close();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	private boolean checkDataBase(){

		SQLiteDatabase checkDB = null;

		try{
			String myPath = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e){

			//database does't exist yet.

		}

		if(checkDB != null){

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = context.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DATABASE_PATH + DATABASE_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}
}
