package com.chiemtinhapp;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.chiemtinhapp.database.HoroscopeDataSource;

public class HoroscopeDataFetchingOperation extends
		AsyncTask<Integer, Void, ArrayList<String[]>> {
	private HoroscopeDataSource resultDataSource;
	@Override
	public ArrayList<String[]> doInBackground(Integer... params) {
		try {
			resultDataSource.open();
			// get the result from database
			int id = params[0];

			ArrayList<String[]> data = resultDataSource.getResult(id);
			resultDataSource.close();
			return data;
		} catch (Exception e) {
			return null;
		}
	} // doInBackground
	public HoroscopeDataSource getResultDataSource() {
		return resultDataSource;
	}
	public void setResultDataSource(HoroscopeDataSource resultDataSource) {
		this.resultDataSource = resultDataSource;
	}
} // class DataFetchingOperation