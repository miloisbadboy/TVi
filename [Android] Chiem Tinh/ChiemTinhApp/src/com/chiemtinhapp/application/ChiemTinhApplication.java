package com.chiemtinhapp.application;

import java.util.ArrayList;

import android.app.Application;

import com.chiemtinhapp.model.User;

public class ChiemTinhApplication extends Application {
	private ArrayList<String[]> horoscopeData = new ArrayList<String[]>();
	private User selectedUser;
	private User fbUser;

	public ArrayList<String[]> getHoroscopeData() {
		return horoscopeData;
	}

	public void setHoroscopeData(ArrayList<String[]> horoscopeData) {
		this.horoscopeData = horoscopeData;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public User getFbUser() {
		return fbUser;
	}

	public void setFbUser(User fbUser) {
		this.fbUser = fbUser;
	}
}
