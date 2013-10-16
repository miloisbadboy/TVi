package com.chiemtinhapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PickerFragment.OnDoneButtonClickedListener;
import com.facebook.widget.PickerFragment.OnSelectionChangedListener;


public class FacebookOperation {
	private FragmentManager fm;
	private FriendPickerFragment friendPicker;
	private GraphUser friend;
	
	public FacebookOperation(FragmentManager fm,
			FriendPickerFragment friendPicker) {
		super();
		this.fm = fm;
		this.friendPicker = friendPicker;
	}
	
	public void showPicker() {
		fm.beginTransaction().replace(R.id.picker_fragment, friendPicker).addToBackStack(null).commit();
		
		fm.executePendingTransactions();
		
		friendPicker.loadData(false);
	}
	
	public void setUpListener(OnSelectionChangedListener listener) {
		friendPicker.setOnSelectionChangedListener(listener);
		friendPicker.setOnDoneButtonClickedListener(new OnDoneButtonClickedListener() {

			@Override
			public void onDoneButtonClicked(PickerFragment<?> fragment) {
				// TODO Auto-generated method stub
				fm.popBackStack();
			}
		});
	}

	public GraphUser getFriend() {
		return friend;
	}
	
	public static void postStatus(Bundle postParams, Request.Callback callback) {
		Request request = new Request(Session.getActiveSession(), "me/feed", postParams, HttpMethod.POST, callback);
		request.executeAsync();
	}
}
