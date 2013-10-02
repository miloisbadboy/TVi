package com.example.lichvansu.datecalendar;

import java.util.Date;

import com.example.lichvansu.R;
import com.example.lichvansu.R.id;
import com.example.lichvansu.R.layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DateCalendarElementFragment extends Fragment {

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Bundle args = getArguments();
		View view = inflater.inflate(R.layout.fragment_date_calendar_element,
				container, false);
		
		Date currentDate = new Date(args.getInt("year"), args.getInt("month"), args.getInt("date")); 
		int date = currentDate.getDate();
		int month = currentDate.getMonth() + 1;
		int year = currentDate.getYear() + 1900;
		
		TextView txtBigDate = (TextView) view.findViewById(R.id.txt_big_date);
		txtBigDate.setText(date + "");
		
		TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
		txtTitle.setText("Tháng " + month + " năm " + year);
		
		TextView txtDayOfWeek = (TextView) view.findViewById(R.id.txt_day_of_week);
		String strDayOfWeek = "";
		switch (currentDate.getDay()) {
			case 0:
				strDayOfWeek = "Chủ nhật";
				txtBigDate.setTextColor(Color.RED);
				txtTitle.setTextColor(Color.RED);
				txtDayOfWeek.setTextColor(Color.RED);
				break;
			case 1:
				strDayOfWeek = "Thứ hai";
				break;
			case 2:
				strDayOfWeek = "Thứ ba";
				break;
			case 3:
				strDayOfWeek = "Thứ tư";
				break;
			case 4:
				strDayOfWeek = "Thứ năm";
				break;
			case 5:
				strDayOfWeek = "Thứ sáu";
				break;
			case 6:
				strDayOfWeek = "Thứ bảy";
				break;
		}
		txtDayOfWeek.setText(strDayOfWeek);
		
		return view;
	}

}
