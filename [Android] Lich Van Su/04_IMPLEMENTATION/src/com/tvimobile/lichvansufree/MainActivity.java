package com.tvimobile.lichvansufree;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tvimobile.lichvansufree.datecalendar.DateCalendarFragment;
import com.tvimobile.lichvansufree.dateconverter.DateConverterFragment;

public class MainActivity extends FragmentActivity implements OnClickListener {

	Button[] btnTabs;
	private int _activeTabIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnTabs = new Button[] {
				(Button) findViewById(R.id.btn_tab_date_calendar),
				(Button) findViewById(R.id.btn_tab_month_calendar),
				(Button) findViewById(R.id.btn_tab_date_converter), };

		for (Button btn : btnTabs) {
			btn.setOnClickListener(this);
		}

		Fragment fragment = new DateCalendarFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		Button btnTab = (Button) v;
		int oldTabIndex = _activeTabIndex;

		btnTabs[_activeTabIndex].setBackgroundColor(Color.TRANSPARENT);
		int i = 0;
		for (Button btn : btnTabs) {
			if (btn.equals(btnTab)) {
				_activeTabIndex = i;
				btn.setBackgroundColor(R.color.dark_red);
			}
			i++;
		}

		if (oldTabIndex != _activeTabIndex) {
			Fragment fragment = null;
			switch (_activeTabIndex) {
				case 0:
					fragment = new DateCalendarFragment();
					break;
				case 1:
					fragment = new DateCalendarFragment();
					break;
				case 2:
					fragment = new DateConverterFragment();
					break;
				case 3:
					break;
				default:
					new AlertDialog.Builder(this).setTitle("Error")
							.setMessage("Tab screen does not exist.").show();
					break;
			}
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		}
	}

}
