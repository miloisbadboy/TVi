package com.chiemtinhapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class PickerActivity extends Activity {
	public static final String HOROSCOPE_NUMBER = "horoscopeNumber";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picker);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void horoscope_onClick(View view) {
		Intent data = new Intent();
		data.putExtra(com.chiemtinhapp.PickerActivity.HOROSCOPE_NUMBER, view.getTag().toString());
		setResult(RESULT_OK, data);
		finish();
	}
}
