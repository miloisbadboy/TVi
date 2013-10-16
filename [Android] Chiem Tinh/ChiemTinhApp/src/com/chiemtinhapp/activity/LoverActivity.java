package com.chiemtinhapp.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiemtinhapp.PartnerDataFetchingOperation;
import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;
import com.chiemtinhapp.database.PartnerDataSource;
import com.chiemtinhapp.helper.DateFormatHelper;

public class LoverActivity extends PartnerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lover);		
		// Show the Up button in the action bar.
		setupActionBar();

		selectedUser = ((ChiemTinhApplication) getApplication()).getSelectedUser();
		if (selectedUser == null) {
			startProfileActivity();
		}

		userProfile = (TextView) findViewById(R.id.user_profile);
		partnerProfile = (TextView) findViewById(R.id.lover_profile);
		userProfile.setText(DateFormatHelper.formatter.format(selectedUser.getBirthday()));

		userImage = (ImageView) findViewById(R.id.user_image);
		partnerImage = (ImageView) findViewById(R.id.lover_image);

		setUpDefaultImage();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void startProfileActivity() {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivityForResult(intent, 0);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	protected void getResult() {
		try {
			partnerImage.animate().alpha(0).setDuration(2000).withEndAction(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ImageView icon = ((ImageView) findViewById(R.id.lover_heart_icon));
					icon.animate().scaleX(2).scaleY(2).alpha(1).setDuration(2000).withLayer();
					partnerImage.setImageResource(R.drawable.aquarius_symbols);
					partnerImage.animate().alpha(1).withEndAction(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							int signId = getUserHoroscopeNumber(selectedUser);
							int partnerId = getUserHoroscopeNumber(partner);
							PartnerDataFetchingOperation task = new PartnerDataFetchingOperation();
							task.setDataSource(new PartnerDataSource(LoverActivity.this));
							task.execute(signId, partnerId, 1);
							try {
								String result = task.get();
								startResultActivity(result);
							}
							catch (Exception e) {
								new AlertDialog.Builder(LoverActivity.this).setTitle(e.getClass().toString()).setMessage(e.getMessage()).show();
							}
						}
					});
					if (selectFromFriends) {
						partnerProfile.setText(DateFormatHelper.displayFormatter.format(partner.getBirthday()));
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
