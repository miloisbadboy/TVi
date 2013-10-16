package com.chiemtinhapp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;
import com.chiemtinhapp.helper.DateFormatHelper;
import com.chiemtinhapp.model.User;

public class PickerActivity extends FragmentActivity {
	public static final String HOROSCOPE_NUMBER = "horoscopeNumber";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picker);
		
		User selectedUser = ((ChiemTinhApplication) getApplication()).getSelectedUser();
		if (selectedUser != null) {
			int horoscopeNumber = DateFormatHelper.horoscopeNumber(selectedUser.getBirthday());
			LinearLayout view = (LinearLayout) findViewById(R.id.activity_picker);
			TextView textView = (TextView) view.findViewWithTag("horoscopeText" + horoscopeNumber);
			textView.setTextSize(12f);
			textView.setTypeface(null, Typeface.BOLD);
			
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void horoscope_onClick(View view) {
		Intent data = new Intent();
		data.putExtra(com.chiemtinhapp.activity.PickerActivity.HOROSCOPE_NUMBER, view.getTag().toString());
		setResult(RESULT_OK, data);
		finish();
	}

}
