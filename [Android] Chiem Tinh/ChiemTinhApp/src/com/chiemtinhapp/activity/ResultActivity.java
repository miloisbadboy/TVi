package com.chiemtinhapp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chiemtinhapp.FacebookOperation;
import com.chiemtinhapp.R;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.UiLifecycleHelper;

public class ResultActivity extends FragmentActivity {
	public static final String RESULT = "result";
	private final String TAG = "TAG";
	private UiLifecycleHelper uiHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partner_result);
		
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		
		// Retrieve result string
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		
		((TextView) findViewById(R.id.partner_result)).setText(data.getString(RESULT));
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	public void partner_result_share_onClick(View v) {
		// Check to see if user has logged in his facebook account.
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Check to see if publish permission is granted.
			if (hasPublishPermission()) {
				performShareAction();
			}
			else {
				// Request additional permission
				session.requestNewPublishPermissions(new NewPermissionsRequest(this, "publish_actions"));
				return;
			}
		}
		else {
			// Redirect to account management page
			Intent intent = new Intent(this, ProfileActivity.class);
			startActivity(intent);
		}
	}
	
	private void performShareAction() {
//		FacebookDialog dialog = createShareDialogBuilder().build();
//		uiHelper.trackPendingDialogCall(dialog.present());
		Bundle data = getIntent().getExtras();
		Bundle postParams = new Bundle();
		postParams.putString("name", "Chiem tinh app");
		postParams.putString("message", data.getString(RESULT));
		postParams.putString("description", "Chiem Tinh App testing");
		
		Request.Callback callback = new Request.Callback() {
			
			@Override
			public void onCompleted(Response response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "onCompleted FacebookRequest Done");
	            JSONObject graphResponse = response.getGraphObject()
	                    .getInnerJSONObject();
	            try {
	                graphResponse.getString("id");
	            } catch (JSONException e) {
	                Log.i(TAG, "JSON error " + e.getMessage());
	            }
	            FacebookRequestError error = response.getError();
	            if (error != null) {
	                Log.i(TAG, "FacebookRequestError" + error.getErrorMessage());
	                Toast.makeText(ResultActivity.this,
	                        error.getErrorMessage(), Toast.LENGTH_SHORT).show();
	            } else {
	                Log.i(TAG, "FacebookRequest Done");
	                Toast.makeText(ResultActivity.this,
	                        "Story Published to Your Wall", Toast.LENGTH_LONG).show();
	            }
			}
		};
		FacebookOperation.postStatus(postParams, callback);
	}
	
//	private FacebookDialog.ShareDialogBuilder createShareDialogBuilder() {
//        return new FacebookDialog.ShareDialogBuilder(this)
//                .setName("ChiemTinh App")
//                .setDescription("Chiem tinh app testing")
//                .setLink("http://developers.facebook.com/android");
//    }
	
	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null && session.getPermissions().contains("publish_actions");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		uiHelper.onPause();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
