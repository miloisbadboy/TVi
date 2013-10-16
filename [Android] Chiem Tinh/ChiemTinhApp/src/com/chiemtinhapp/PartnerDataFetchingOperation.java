package com.chiemtinhapp;

import com.chiemtinhapp.database.PartnerDataSource;

import android.os.AsyncTask;

public class PartnerDataFetchingOperation extends
		AsyncTask<Integer, Void, String> {
	private PartnerDataSource dataSource;
	@Override
	protected String doInBackground(Integer... arg) {
		// TODO Auto-generated method stub
		try {
			int signId = arg[0];
			int partnerId = arg[1];
			int typeId = arg[2];
			
			dataSource.open();
			String result = dataSource.getResult(signId, partnerId, typeId);
			dataSource.close();
			return result; 
		}
		catch (Exception e) {
			return null;
		}		
	}
	public void setDataSource(PartnerDataSource dataSource) {
		this.dataSource = dataSource;
	}
}
