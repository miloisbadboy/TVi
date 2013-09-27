package com.chiemtinhapp.activity;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.chiemtinhapp.R;
import com.chiemtinhapp.database.UserDataSource;
import com.chiemtinhapp.helper.Gender;
import com.chiemtinhapp.model.User;

public class ProfileActivity extends ListActivity {
	private UserDataSource userData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		userData = new UserDataSource(this);
		userData.open();

		setListAdapter(new UserProfileAdapter(ProfileActivity.this, R.layout.profile_list_element, userData.getUsers()));
	}

	private class UserProfileAdapter extends ArrayAdapter<User> {
		private List<User> users;
		public UserProfileAdapter(Context context, int textViewResourceId,
				List<User> users) {
			super(context, textViewResourceId, users);
			this.users = users;
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
				((TextView) convertView.findViewById(R.id.name)).setText(user.getName());
				((TextView) convertView.findViewById(R.id.birthday)).setText(UserDataSource.formatter.format(user.getBirthday()));
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
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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

	public void btn_register_onClick(View v) {
		try {
			String name = ((EditText) findViewById(R.id.register_username)).getText().toString();
			Date birthday = UserDataSource.formatter.parse("1992-02-02");
			if (!name.equals("")) {
				User user = userData.addUser(name, birthday, Gender.Male);
				((UserProfileAdapter) getListAdapter()).add(user);
			}
		}
		catch (Exception e) {
			new AlertDialog.Builder(this).setTitle(e.getClass().toString()).show();
			//Log.w(e.getClass().toString(), e.getMessage());
			return;
		}
	}

}
