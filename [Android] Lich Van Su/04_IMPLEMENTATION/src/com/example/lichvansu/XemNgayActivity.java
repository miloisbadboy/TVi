package com.example.lichvansu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.annotation.TargetApi;
import android.os.Build;

public class XemNgayActivity extends Activity {
	TextView txtResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xem_ngay);
		// Show the Up button in the action bar.
		setupActionBar();
		
		txtResult = (TextView) findViewById(R.id.txtResult);
		txtResult.setMovementMethod(new ScrollingMovementMethod());

		String strUrl = "http://api.tamlinh.vn/lichVanSu/xemNgay/xem/ngay/23/thang/9/nam/2013/key/tvi1102";

		AsyncTask<String, Void, HashMap<String, String[]>> task = new DataFetchingOperation();
		task.execute(strUrl);

		try {
			HashMap<String, String[]> data = task.get();
			String strResult = "";

			Iterator<String> it = data.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next();

				strResult += "<p><b>" + data.get(key)[0] + ":</b>  "
						+ data.get(key)[1] + "</p>";
			}

			txtResult.setText(Html.fromHtml(strResult));		
		} catch (Exception e) {
			new AlertDialog.Builder(this).setTitle(e.getClass().toString())
					.setMessage(e.getMessage()).show();
		}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xem_ngay, menu);
		return true;
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

}
