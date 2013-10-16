package com.chiemtinhapp.activity;

import java.text.ParseException;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiemtinhapp.FacebookOperation;
import com.chiemtinhapp.R;
import com.chiemtinhapp.helper.DateFormatHelper;
import com.chiemtinhapp.helper.Gender;
import com.chiemtinhapp.model.User;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PickerFragment.OnSelectionChangedListener;

public abstract class PartnerActivity extends FragmentActivity {
	protected ImageView userImage;
	protected ImageView partnerImage;
	protected TextView userProfile;
	protected TextView partnerProfile;
	protected User selectedUser;
	protected User partner;
	protected boolean selectFromFriends;
	protected String[] horoNames = { "Bạch Dương", "Kim Ngưu", "Song Tử",
			"Cự Giải", "Sư Tử", "Xử Nữ", "Thiên Bình", "Bọ Cạp", "Xạ Thủ", "Ma Kết", "Thủy Bình", "Song Ngư" };
	
	public void horoscope_onClick(View v) {
		partner = new User();
		partner.setBirthday(new GregorianCalendar(1970, Integer.parseInt(v.getTag().toString()) - 1, 10).getTime());
		selectFromFriends = false;
		getResult();
	}
	
	protected abstract void startResultActivity(String result);
	
	protected void setUpDefaultImage() {
		if (selectedUser.getGender() == Gender.Male) {
			userImage.setImageResource(R.drawable.icon_male);
			partnerImage.setImageResource(R.drawable.icon_female);
		}
		else {
			userImage.setImageResource(R.drawable.icon_female);
			partnerImage.setImageResource(R.drawable.icon_male);
		}
	}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
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
		Intent intent = new Intent(PartnerActivity.this, ProfileActivity.class);
		startActivity(intent);
	}

	protected abstract void getResult();
	
	protected int getUserHoroscopeNumber(User user) {
		int number = DateFormatHelper.horoscopeNumber(user.getBirthday());
		return (number + 8) % 12 + 1;
	}

	public void btn_friend_picker_onClick(View v) {
		if (Session.getActiveSession().isOpened()) {
			showFriendPicker();
		}
		else {
			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
		}
	}

	protected void showFriendPicker() {
		FragmentManager fm = getSupportFragmentManager();
		FriendPickerFragment friendPicker = new FriendPickerFragment();
		FacebookOperation fbHelper = new FacebookOperation(fm, friendPicker);
		
		fbHelper.setUpListener(new PartnerPickerOnSelectionChangedListener());
		fbHelper.showPicker();
	}
	
	protected class PartnerPickerOnSelectionChangedListener implements OnSelectionChangedListener {		

		@Override
		public void onSelectionChanged(PickerFragment<?> fragment) {
			// TODO Auto-generated method stub
			final GraphUser friend = ((FriendPickerFragment) fragment).getSelection().get(0);
			
			String query = "SELECT sex, birthday_date FROM user WHERE uid=" + friend.getId();
			Bundle params = new Bundle();
			params.putString("q", query);
			Request request = new Request(Session.getActiveSession(), "/fql", params, HttpMethod.GET, new Request.Callback() {
				
				@Override
				public void onCompleted(Response response) {
					// TODO Auto-generated method stub
					try {
						JSONObject json = response.getGraphObjectAs(GraphUser.class).getInnerJSONObject();
						JSONObject jsonOb = json.getJSONArray("data").getJSONObject(0);
						String birthday = jsonOb.getString("birthday_date");
						String gender = jsonOb.getString("sex");
						partner = new User(Long.parseLong(friend.getId()), 
								friend.getName(), 
								DateFormatHelper.fbFormatter.parse(birthday), 
								Gender.parseString(gender));
						selectFromFriends = true;
						getResult();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.d(e.getClass().toString(), e.getMessage());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						Log.d(e.getClass().toString(), e.getMessage());
					} catch (ParseException e) {
						Log.d(e.getClass().toString(), e.getMessage());
					}
				}
			});
			Request.executeBatchAsync(request);
			getSupportFragmentManager().popBackStack();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
