package com.chiemtinhapp.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiemtinhapp.PartnerDataFetchingOperation;
import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;
import com.chiemtinhapp.database.PartnerDataSource;
import com.chiemtinhapp.helper.DateFormatHelper;

public class BusinessActivity extends PartnerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business);

		selectedUser = ((ChiemTinhApplication) getApplication()).getSelectedUser();
		if (selectedUser == null) {
			startProfileActivity();
		}

		userProfile = (TextView) findViewById(R.id.user_profile);
		partnerProfile = (TextView) findViewById(R.id.partner_profile);
		userProfile.setText(DateFormatHelper.formatter.format(selectedUser.getBirthday()));

		userImage = (ImageView) findViewById(R.id.user_image);
		partnerImage = (ImageView) findViewById(R.id.partner_image);

		setUpDefaultImage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.business, menu);
		return true;
	}

	@Override
	@TargetApi(16)
	protected void getResult() {
		// TODO Auto-generated method stub
		{
			try {
				partnerImage.animate().alpha(0).setDuration(2000).withEndAction(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						partnerImage.setImageResource(R.drawable.aquarius_symbols);
						partnerImage.animate().alpha(1).withEndAction(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								int signId = getUserHoroscopeNumber(selectedUser);
								int partnerId = getUserHoroscopeNumber(partner);
								PartnerDataFetchingOperation task = new PartnerDataFetchingOperation();
								task.setDataSource(new PartnerDataSource(BusinessActivity.this));
								task.execute(signId, partnerId, 2);
								try {
									String result = task.get();
									startResultActivity(result);
								}
								catch (Exception e) {
									new AlertDialog.Builder(BusinessActivity.this).setTitle(e.getClass().toString()).setMessage(e.getMessage()).show();
								}
							}
						});
						if (selectFromFriends) {
							partnerProfile.setText(DateFormatHelper.formatter.format(partner.getBirthday()));
						}
						else {
							partnerProfile.setText(horoNames[getUserHoroscopeNumber(partner) - 1]);
						}
					}
				});
			}
			catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	protected void startResultActivity(String result) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra(ResultActivity.RESULT, result);
		startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		userProfile.setText(DateFormatHelper.formatter.format(selectedUser.getBirthday()));
	}
}
