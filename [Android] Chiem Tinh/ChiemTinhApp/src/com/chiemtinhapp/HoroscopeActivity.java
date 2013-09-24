package com.chiemtinhapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HoroscopeActivity extends Activity {
	private TextView result;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == RESULT_OK) {
			result = (TextView) findViewById(R.id.horoscope_result);
			result.setText("You selected horoscope number: " + data.getStringExtra(PickerActivity.HOROSCOPE_NUMBER));
		}
	}
	protected void onCreate(Bundle savedInstanceState) {
		result = (TextView) findViewById(R.id.horoscope_result);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horoscope);
		
		startPickerActivity();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void btn_horoscope_list_onClick(View view) {
		startPickerActivity();
	}
	
	private void startPickerActivity() {
		Intent intent = new Intent(this, PickerActivity.class);
		startActivityForResult(intent, 0);
	}
	
	private String buildUri(String number) {
		return "http://api.tamlinh.vn/chiemTinh/TuViHD2012/xem/ngaysinh/02/thangsinh/" + number + "/key/tvi1102";
	}
}