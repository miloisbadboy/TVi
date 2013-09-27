package com.chiemtinhapp.application;

import java.util.ArrayList;

import android.app.Application;

public class ChiemTinhApplication extends Application {
	private ArrayList<String[]> horoscopeData = new ArrayList<String[]>();

	public ArrayList<String[]> getHoroscopeData() {
		return horoscopeData;
	}

	public void setHoroscopeData(ArrayList<String[]> horoscopeData) {
		this.horoscopeData = horoscopeData;
	}
}
