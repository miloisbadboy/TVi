package com.chiemtinhapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.chiemtinhapp.R;

public class MainActivity extends Activity {

	public static final int USER_REQUEST = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void btn_horoscope_onClick(View v) {
		Intent intent = new Intent(this, HoroscopeActivity.class);
		startActivity(intent);
	}
	
	public void btn_lover_onClick(View v) {
		Intent intent = new Intent(this, LoverActivity.class);
		startActivity(intent);
	}
	
	public void btn_profile_management_onClick(View v) {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivityForResult(intent, USER_REQUEST);
	}
}
