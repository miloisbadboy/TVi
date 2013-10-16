package com.chiemtinhapp.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.chiemtinhapp.HoroscopeDataFetchingOperation;
import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;
import com.chiemtinhapp.database.HoroscopeDataSource;
import com.chiemtinhapp.fragment.HoroscopeResultFragment;

public class HoroscopeActivity extends FragmentActivity {

	private ResultCollectionPagerAdapter adapter;
	private ViewPager viewPager;	

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == RESULT_OK) {

			HoroscopeDataFetchingOperation task = new HoroscopeDataFetchingOperation();
			task.setResultDataSource(new HoroscopeDataSource(this));
			int id = Integer.parseInt(data.getStringExtra(PickerActivity.HOROSCOPE_NUMBER));
			task.execute(id);

			try {
				ArrayList<String[]> resultData = task.get();

				setUpSwipeView(resultData);

				//Save the data;
				((ChiemTinhApplication) getApplication()).setHoroscopeData(resultData);
			}
			catch (Exception e) {
				new AlertDialog.Builder(this).setTitle(e.getClass().toString())
				.setMessage(e.getMessage()).show();
			}
		}
		else if (((ChiemTinhApplication) getApplication()).getHoroscopeData().size() == 0){
			finish();
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
			
			// Skip the first 3 fields (Id, Title, Birthday)
			i += 3;
			
			String strResult = data.get(i)[1];
			Fragment fragment = new HoroscopeResultFragment();
			Bundle args = new Bundle();
			
			args.putInt(HoroscopeResultFragment.INDEX, i - 3);
			args.putInt(HoroscopeResultFragment.HORO_NUMBER, (Integer.parseInt(data.get(0)[1])));
			args.putString(HoroscopeResultFragment.RESULT, strResult);
			
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			// Skip 3 first field
			return data.size() - 3;
		}

		@Override
		public String getPageTitle(int i) {
			return data.get(i + 3)[0];
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horoscope);

		// Restore saved data
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
		((TextView) findViewById(R.id.horoscope_title)).setText(data.get(1)[1]);
		adapter = new ResultCollectionPagerAdapter(getSupportFragmentManager(), data);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(adapter);
		
	}

	private void startPickerActivity() {
		Intent intent = new Intent(this, PickerActivity.class);
		startActivityForResult(intent, 0);
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