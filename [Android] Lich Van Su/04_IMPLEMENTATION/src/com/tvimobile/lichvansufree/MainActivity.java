package com.tvimobile.lichvansufree;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tvimobile.lichvansufree.R;
import com.tvimobile.lichvansufree.datecalendar.DateCalendarFragment;
import com.tvimobile.lichvansufree.dateconverter.DateConverterFragment;
import com.tvimobile.lichvansufree.monthcalendar.MonthCalendarFragment;

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

		// Hide status bar
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Fragment fragment = new DateCalendarFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();

		// Get reference to ActionBar
		// ActionBar actionBar = getActionBar();

		// Enable action bar navigation tabs
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Hide action bar details
		// actionBar.setDisplayShowTitleEnabled(false);
		// actionBar.setDisplayShowHomeEnabled(false);

		// Set tabs for action bar
		// actionBar
		// .addTab(actionBar.newTab()
		// .setIcon(R.drawable.icon_dc)
		// .setTabListener((TabListener) this));
		// actionBar.addTab(actionBar.newTab().setText("Lịch Tháng")
		// .setTabListener((TabListener) this));
		// actionBar.addTab(actionBar.newTab().setText("�?ổi Ngày")
		// .setTabListener((TabListener) this));
		// actionBar.addTab(actionBar.newTab().setText("...")
		// .setTabListener((TabListener) this));

		// setTabsMaxWidth();

		// FrameLayout a = (FrameLayout) findViewById(R.id.container);
		// gestureDetector = new GestureDetector(new MyGestureDetector());
		// gestureListener = new View.OnTouchListener() {
		// public boolean onTouch(View v, MotionEvent event) {
		// return gestureDetector.onTouchEvent(event);
		// }
		// };

		// a.setOnClickListener(this);
		// a.setOnTouchListener(gestureListener);
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
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.container, fragment).commit();
		}
	}

}