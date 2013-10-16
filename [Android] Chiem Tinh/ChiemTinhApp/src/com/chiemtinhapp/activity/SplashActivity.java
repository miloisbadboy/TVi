package com.chiemtinhapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.chiemtinhapp.R;
import com.chiemtinhapp.database.DbOpenHelper;

public class SplashActivity extends Activity{
	private DbOpenHelper dbHelper;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new SetUpDatabase().execute();
	}
	
	private class SetUpDatabase extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			dbHelper = new DbOpenHelper(SplashActivity.this);
			try {
				dbHelper.createDataBase();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dbHelper.close();
			Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
		}
		
	}
}
