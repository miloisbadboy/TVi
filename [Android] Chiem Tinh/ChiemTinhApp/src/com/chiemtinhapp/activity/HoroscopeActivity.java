package com.chiemtinhapp.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

import com.chiemtinhapp.DataFetchingOperation;
import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;
import com.chiemtinhapp.fragment.HoroscopeResultFragment;

public class HoroscopeActivity extends FragmentActivity {

	private ArrayList<String[]> resultData;
	private ResultCollectionPagerAdapter adapter;
	private ViewPager viewPager;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == RESULT_OK) {

			AsyncTask<String, Void, ArrayList<String[]>> task = new DataFetchingOperation();
			task.execute(buildUri(data.getStringExtra(PickerActivity.HOROSCOPE_NUMBER)));

			try {

				resultData = task.get();

				// Set up swipe view
				resultData.subList(0, 3).clear();

				setUpSwipeView(resultData);

				//Save the data;
				((ChiemTinhApplication) getApplication()).setHoroscopeData(resultData);
			}
			catch (Exception e) {
				new AlertDialog.Builder(this).setTitle(e.getClass().toString())
				.setMessage(e.getMessage()).show();
			}
		}
	}
	private class ResultCollectionPagerAdapter extends FragmentStatePagerAdapter {
		private ArrayList<String[]> data;
		public ResultCollectionPagerAdapter(FragmentManager fm, ArrayList<String[]> data) {
			super(fm);
			this.data = data;
		}

		@Override
		public Fragment getItem(int i) {
			// TODO Auto-generated method stub
			String strResult = "<p><b>" + data.get(i)[0] + ":</b>  "
					+ data.get(i)[1] + "</p>";
			Fragment fragment = new HoroscopeResultFragment();
			Bundle args = new Bundle();
			args.putString(HoroscopeResultFragment.RESULT, strResult);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public String getPageTitle(int i) {
			return data.get(i)[0];
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horoscope);

		ArrayList<String[]> data = ((ChiemTinhApplication) getApplication()).getHoroscopeData();

		if (data.size() == 0){
			startPickerActivity();
		}
		else {
			setUpSwipeView(data);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void btn_horoscope_list_onClick(View view) {
		startPickerActivity();
	}

	private void setUpSwipeView(ArrayList<String[]> data) {
		adapter = new ResultCollectionPagerAdapter(getSupportFragmentManager(), data);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(adapter);
	}

	private void startPickerActivity() {
		Intent intent = new Intent(this, PickerActivity.class);
		startActivityForResult(intent, 0);
	}

	private String buildUri(String number) {
		return "http://api.tamlinh.vn/chiemTinh/tuViTheoCung/xem/ngaysinh/02/thangsinh/" + number + "/key/tvi1102";
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}