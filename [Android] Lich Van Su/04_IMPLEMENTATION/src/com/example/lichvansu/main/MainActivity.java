package com.example.lichvansu.main;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lichvansu.R;
import com.example.lichvansu.xemngay.XemNgayActivity;
import com.example.lichvansu.xemngay.XemNgayFragment;

public class MainActivity extends FragmentActivity implements TabListener,
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getActionBar(); // Get reference to ActionBar

		// Enable ActionBar navigation tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.addTab(actionBar.newTab().setText("Lịch Ngày")
				.setTabListener((TabListener) this));
		actionBar.addTab(actionBar.newTab().setText("Chọn Ngày")
				.setTabListener((TabListener) this));
		actionBar.addTab(actionBar.newTab().setText("Đổi Ngày")
				.setTabListener((TabListener) this));

		FrameLayout a = (FrameLayout) findViewById(R.id.container);
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};

		a.setOnClickListener(this);
		a.setOnTouchListener(gestureListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void btn_xem_ngay_onClick(View view) {
		Intent intent = new Intent(this, XemNgayActivity.class);
		startActivity(intent);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// When the given tab is selected, show the tab contents in the
		// container view.
		Fragment fragment = new XemNgayFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER,
				tab.getPosition() + 1);
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	/** * A dummy fragment representing a section of the app */

	public static class DummySectionFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "placeholder_text";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
	}

	private static final int SWIPE_MIN_DISTANCE = 10;
	private static final int SWIPE_MAX_OFF_PATH = 100;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.w("Debug", "(" + e1.getX() + "; " + e1.getY() + ") ; (" + e2.getX() + "; " + e2.getY() + ")");
	        try {
	        	if (Math.abs(e1.getY() - e2.getY()) > Math.abs(e1.getX() - e2.getX())) {
	        		// downward swipe
	        		if (e2.getY() - e1.getY() > SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY)
		                Toast.makeText(MainActivity.this, "Downward Swipe", Toast.LENGTH_SHORT).show();
		            else if (e1.getY() - e2.getY() > SWIPE_MAX_OFF_PATH && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY)
		                Toast.makeText(MainActivity.this, "Upward Swipe", Toast.LENGTH_SHORT).show();	
	        	} else {
	        		// right to left swipe
		            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		                Toast.makeText(MainActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
		            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		                Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
		            }        		
	        	}
	        } catch (Exception e) {
	            // nothing
	        }
	        return false;
	    }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
