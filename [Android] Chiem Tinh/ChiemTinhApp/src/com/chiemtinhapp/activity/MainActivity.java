package com.chiemtinhapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;

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
		if (((ChiemTinhApplication) getApplication()).getSelectedUser() == null) {
			showAuthenDialog();
		}
		else {
			Intent intent = new Intent(this, LoverActivity.class);
			startActivity(intent);
		}
	}
	
	public void btn_business_onClick(View v) {
		if (((ChiemTinhApplication) getApplication()).getSelectedUser() == null) {
			showAuthenDialog();
		}
		else {
			Intent intent = new Intent(this, BusinessActivity.class);
			startActivity(intent);
		}
	}
	
	private void showAuthenDialog() {
		new AlertDialog.Builder(this)
		.setTitle("Ban can co tai khoan")
		.setMessage("Ban can dang nhap tai khoan de su dung chuc nang nay. Dang nhap ngay?")
		.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				startProfileActivity();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}

	public void btn_profile_management_onClick(View v) {
		startProfileActivity();
	}
	
	public void btn_biology_onClick(View v) {
		if (((ChiemTinhApplication) getApplication()).getSelectedUser() == null) {
			showAuthenDialog();
		}
		else {
			Intent intent = new Intent(this, BiologyActivity.class);
			startActivity(intent);
		}
	}
	
	private void startProfileActivity() {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivityForResult(intent, USER_REQUEST);
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
