package com.chiemtinhapp.activity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;
import com.chiemtinhapp.database.UserDataSource;
import com.chiemtinhapp.helper.DateFormatHelper;
import com.chiemtinhapp.helper.Gender;
import com.chiemtinhapp.model.User;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class ProfileActivity extends ListActivity {
	private UserDataSource userData;
	private UiLifecycleHelper uiHelper;

	private User fbUser;

	private Session.StatusCallback callback = new StatusCallback() {

		@Override
		public void call(Session session, SessionState state, Exception exception) {
			// TODO Auto-generated method stub
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (session.isOpened()) {
			makeMeRequest(session);
			finish();
		}
		else if (session.isClosed()) {
			((UserProfileAdapter) getListAdapter()).remove(fbUser);
			User selectedUser = ((ChiemTinhApplication) getApplication()).getSelectedUser();
			ChiemTinhApplication app = (ChiemTinhApplication) getApplication();
			if (selectedUser!= null && selectedUser.equals(fbUser)) {
				app.setSelectedUser(null);
				((UserProfileAdapter) getListAdapter()).selectedUser = null;
			}
			fbUser = null;
			app.setFbUser(null);
			session.closeAndClearTokenInformation();
		}
	}

	private void makeMeRequest(final Session session) {
		//Make an API call with a new callback
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, Response response) {
				// TODO Auto-generated method stub
				if (session == Session.getActiveSession()) {
					if (user != null) {
						//Set the selected user to facebook user
						try {
							fbUser = new User(Long.parseLong(user.getId()), user.getName(), DateFormatHelper.formatter.parse(user.getBirthday()), Gender.parseString(user.getProperty("gender").toString()));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						((ChiemTinhApplication) getApplication()).setSelectedUser(fbUser);
						((ChiemTinhApplication) getApplication()).setFbUser(fbUser);

						//Get and display the user information
						((UserProfileAdapter) getListAdapter()).insert(fbUser, 0);
					}
				}
			}
		});
		request.executeAsync();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == RegisterActivity.REQUEST_CODE && resultCode == RESULT_OK) {
			try {
				Bundle bundle = data.getExtras();

				String name = bundle.getString(RegisterActivity.NAME);
				Date birthday = DateFormatHelper.formatter.parse(bundle.getString(RegisterActivity.BIRTHDAY));
				Gender gender = Gender.values()[bundle.getInt(RegisterActivity.GENDER)];

				User newUser = userData.addUser(name, birthday, gender);
				
				UserProfileAdapter adapter = (UserProfileAdapter) getListAdapter();
				adapter.add(newUser);
			}
			catch (Exception e) {
				new AlertDialog.Builder(ProfileActivity.this).setTitle(e.getClass().toString()).setMessage(e.getMessage());
				return;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		LoginButton button = (LoginButton) findViewById(R.id.facebook_login);
		button.setPublishPermissions("user_birthday", "user_status", "friends_birthday");

		userData = new UserDataSource(this);
		userData.open();

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		List<User> allUsers = userData.getUsers();

		setListAdapter(new UserProfileAdapter(ProfileActivity.this, R.layout.profile_list_element, allUsers));
		
		fbUser = ((ChiemTinhApplication) getApplication()).getFbUser();

		if (fbUser != null) {
			((UserProfileAdapter) getListAdapter()).insert(fbUser, 0);
		}
	}

	private class UserProfileAdapter extends ArrayAdapter<User> {
		private List<User> users;
		private User selectedUser;

		public UserProfileAdapter(Context context, int textViewResourceId,
				List<User> users) {
			super(context, textViewResourceId, users);
			this.users = users;
			selectedUser = ((ChiemTinhApplication) getApplication()).getSelectedUser();
		}

		@Override
		public User getItem(int position) {
			// TODO Auto-generated method stub
			return users.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = View.inflate(ProfileActivity.this, R.layout.profile_list_element, null);
			}
			User user = users.get(position);
			if (user != null) {
				CheckedTextView nameText = (CheckedTextView) convertView.findViewById(R.id.name);
				nameText.setText(user.getName());
				nameText.setTag(user);
				if (selectedUser != null && selectedUser.equals(user)) {
					nameText.setChecked(true);
				}
				else {
					nameText.setChecked(false);
				}

				Drawable genderIcon;

				// Gender as icon
				if (user.getGender() == Gender.Male) {
					genderIcon = getResources().getDrawable(R.drawable.icon_male);
				}
				else {
					genderIcon = getResources().getDrawable(R.drawable.icon_female);
				}
				((ImageView) convertView.findViewById(R.id.icon_gender)).setImageDrawable(genderIcon);
				
				ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.btn_delete_profile);
				deleteButton.setTag(user);
				if (fbUser != null && user.equals(fbUser)) {
					deleteButton.setEnabled(false);
				}
			}
			return convertView;
		}

		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		uiHelper.onResume();
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
		uiHelper.onStop();
	}

	public void user_profile_onClick(View v) {
		((ChiemTinhApplication) getApplication()).setSelectedUser((User) v.getTag());
		finish();
	}

	public void btn_new_user_onClick(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivityForResult(intent, RegisterActivity.REQUEST_CODE);
	}

	public void btn_delete_onClick(View v) {
		try {
			final User[] user = new User[] { (User) v.getTag(), ((ChiemTinhApplication) getApplication()).getSelectedUser() };
			new AlertDialog.Builder(ProfileActivity.this)
			.setTitle("Alert")
			.setMessage("Are you sure want to delete this?")
			.setPositiveButton("Yes", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					UserProfileAdapter adapter = (UserProfileAdapter) getListAdapter();
					adapter.remove(user[0]);
					adapter.notifyDataSetChanged();
					userData.deleteUser(user[0]);
					if (user[1] != null && user[0].equals(user[1])) {
						((ChiemTinhApplication) getApplication()).setSelectedUser(null);
						((UserProfileAdapter) getListAdapter()).selectedUser = null;
					}
				}
			})
			.setNegativeButton("No", null)
			.show();
		}
		catch (Exception e) {
			Log.w(e.getClass().toString(), e.getMessage());
			return;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}	
}
