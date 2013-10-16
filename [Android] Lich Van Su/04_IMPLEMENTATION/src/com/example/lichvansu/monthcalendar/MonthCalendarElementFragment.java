package com.example.lichvansu.monthcalendar;

import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.lichvansu.R;
import com.example.lichvansu.helper.DateHelper;

public class MonthCalendarElementFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Bundle args = getArguments();
		View view = inflater.inflate(R.layout.fragment_month_calendar_element,
				container, false);

		Calendar time = Calendar.getInstance();
		time.set(Calendar.MONTH, args.getInt("month"));
		time.set(Calendar.YEAR, args.getInt("year"));

		GridView calendar = (GridView) view.findViewById(R.id.calendar);
		calendar.setNumColumns(7);
		calendar.setAdapter(new MonthCalendarAdapter(view.getContext(), time));

		return view;
	}

	private class MonthCalendarAdapter extends BaseAdapter {
		private Context context;
		private Calendar currentTime;
		private int offset;

		public MonthCalendarAdapter(Context context, Calendar time) {
			this.context = context;
			this.currentTime = time;

			// Calculate offset
			this.currentTime.set(Calendar.DATE, 1);
			this.offset = this.currentTime.get(Calendar.DAY_OF_WEEK) + 7 - 1;
		}

		@Override
		public int getCount() {
			return 42;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final float scale = getResources().getDisplayMetrics().density;
			View gridView;

			if (convertView == null) {
				gridView = new View(context);				

				if (position < 7) {
					gridView = inflater.inflate(
							R.layout.element_month_calendar_heading, null);
					
					int layoutHeight = (int) (35 * scale);
					
					gridView.setLayoutParams(new GridView.LayoutParams(
							GridView.LayoutParams.WRAP_CONTENT, layoutHeight));

					TextView txtHeading = (TextView) gridView
							.findViewById(R.id.txt_heading);

					String[] strHeadings = new String[] { "Th2", "Th3", "Th4",
							"Th5", "Th6", "Th7", "CN" };

					txtHeading.setText(strHeadings[position]);
				} else {
					gridView = inflater.inflate(
							R.layout.element_month_calendar_date, null);

					int layoutHeight = (int) (40 * scale);
					
					gridView.setLayoutParams(new GridView.LayoutParams(
							GridView.LayoutParams.WRAP_CONTENT, layoutHeight));
					
					Calendar date = (Calendar) this.currentTime.clone();
					date.add(Calendar.DATE, position - this.offset);

					TextView txtDate = (TextView) gridView
							.findViewById(R.id.txt_date);
					txtDate.setText(date.get(Calendar.DATE) + "");

					int lunarDate = DateHelper.convertSolar2Lunar(
							date.get(Calendar.DATE), date.get(Calendar.MONTH),
							date.get(Calendar.YEAR), 7.00)[0];

					TextView txtDateLunar = (TextView) gridView
							.findViewById(R.id.txt_date_lunar);
					txtDateLunar.setText(lunarDate + "");
				}

			} else {
				gridView = (View) convertView;
			}

			return gridView;
		}

	}
}
