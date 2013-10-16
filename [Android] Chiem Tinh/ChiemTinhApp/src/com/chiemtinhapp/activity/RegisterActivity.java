package com.chiemtinhapp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chiemtinhapp.R;

public class RegisterActivity extends Activity {
	public static final int REQUEST_CODE = 2;
	public static final String NAME = "name";
	public static final String GENDER = "gender";
	public static final String BIRTHDAY = "birthday";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void btn_register_onClick(View v) {
		String name = ((EditText) findViewById(R.id.register_name)).getText().toString();
		
		RadioGroup genderSelection = (RadioGroup) findViewById(R.id.register_gender_selection);
		
		DatePicker datePicker = (DatePicker) findViewById(R.id.register_birthday);
		String birthday = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
		
		int selectedIndex = genderSelection.indexOfChild(findViewById(genderSelection.getCheckedRadioButtonId()));
		
		
		Intent data = new Intent();
		data.putExtra(NAME, name);
		data.putExtra(GENDER, selectedIndex);
		data.putExtra(BIRTHDAY, birthday);
		
		setResult(RESULT_OK, data);
		finish();
	}

}
