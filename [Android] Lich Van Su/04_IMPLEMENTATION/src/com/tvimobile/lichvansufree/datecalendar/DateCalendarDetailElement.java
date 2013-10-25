package com.tvimobile.lichvansufree.datecalendar;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvimobile.lichvansufree.R;

public class DateCalendarDetailElement extends Fragment {

	public static DateCalendarDetailElement newInstance(String time,
			int date, int month, int year, int dayOfWeek, int lunarDate,
			int lunarMonth, int lunarYear, int lunarLeap, boolean isHoliday,
			int[] lunarHourTitle, int[] lunarDateTitle, int[] lunarMonthTitle,
			int[] lunarYearTitle, int lunarHoursStatus, int lunarDateStatus) {

		DateCalendarDetailElement fragment = new DateCalendarDetailElement();

		Bundle args = new Bundle();
		args.putString("time", time);
		args.putInt("date", date);
		args.putInt("month", month);
		args.putInt("year", year);
		args.putInt("dayOfWeek", dayOfWeek);
		args.putInt("lunarDate", lunarDate);
		args.putInt("lunarMonth", lunarMonth);
		args.putInt("lunarYear", lunarYear);
		args.putInt("lunarLeap", lunarLeap);
		args.putBoolean("isHoliday", isHoliday);
		args.putIntArray("lunarHourTitle", lunarHourTitle);
		args.putIntArray("lunarDateTitle", lunarDateTitle);
		args.putIntArray("lunarMonthTitle", lunarMonthTitle);
		args.putIntArray("lunarYearTitle", lunarYearTitle);
		args.putInt("lunarHoursStatus", lunarHoursStatus);
		args.putInt("lunarDateStatus", lunarDateStatus);

		fragment.setArguments(args);
		return fragment;
	}

	public DateCalendarDetailElement() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(
			R.layout.fragment_date_calendar_detail_element,
			container,
			false);

		Bundle args = getArguments();
		String time = args.getString("time");
//		int date = args.getInt("date");
//		int month = args.getInt("month");
//		int year = args.getInt("year");
		int dayOfWeek = args.getInt("dayOfWeek");
		int lunarDate = args.getInt("lunarDate");
		int lunarMonth = args.getInt("lunarMonth");
		int lunarYear = args.getInt("lunarYear");
		int lunarLeap = args.getInt("lunarLeap");
		boolean isHoliday = args.getBoolean("isHoliday");
		int[] lunarHourTitle = args.getIntArray("lunarHourTitle");
		int[] lunarDateTitle = args.getIntArray("lunarDateTitle");
		int[] lunarMonthTitle = args.getIntArray("lunarMonthTitle");
		int[] lunarYearTitle = args.getIntArray("lunarYearTitle");
//		int lunarHourStatus = args.getInt("lunarHourStatus");
//		int lunarDateStatus = args.getInt("lunarDateStatus");

		TextView txtDayOfWeek = (TextView) view
				.findViewById(R.id.txt_day_of_week);
		String strDayOfWeek = null;
		switch (dayOfWeek) {
			case Calendar.SUNDAY:
				strDayOfWeek = "Chủ nhật";
				break;
			case Calendar.MONDAY:
				strDayOfWeek = "Thứ hai";
				break;
			case Calendar.TUESDAY:
				strDayOfWeek = "Thứ ba";
				break;
			case Calendar.WEDNESDAY:
				strDayOfWeek = "Thứ tư";
				break;
			case Calendar.THURSDAY:
				strDayOfWeek = "Thứ năm";
				break;
			case Calendar.FRIDAY:
				strDayOfWeek = "Thứ sáu";
				break;
			case Calendar.SATURDAY:
				strDayOfWeek = "Thứ bảy";
				break;
		}
		txtDayOfWeek.setText(strDayOfWeek + "");
		if (isHoliday) {
			txtDayOfWeek.setTextColor(getResources().getColor(R.color.red));
//			View panel = view.findViewById(R.id.panel_details);
//			panel.setBackgroundResource(R.drawable.rounded_rectangle_red);
		}

		TextView txtLunarHour = (TextView) view
				.findViewById(R.id.txt_lunar_hour);
		txtLunarHour.setText(time);

		TextView txtLunarDate = (TextView) view
				.findViewById(R.id.txt_lunar_date);
		txtLunarDate.setText(lunarDate + "");
		
		TextView txtLunarMonth = (TextView) view
				.findViewById(R.id.txt_lunar_month);
		txtLunarMonth.setText(lunarMonth + ((lunarLeap == 0) ? "" : "*"));
		
		TextView txtLunarYear = (TextView) view
				.findViewById(R.id.txt_lunar_year);
		txtLunarYear.setText(lunarYear + "");
		
		TextView txtTitleHour = (TextView) view
				.findViewById(R.id.txt_title_hour);
		txtTitleHour.setText(getStringTitle(lunarHourTitle) + "");
		
		TextView txtTitleDate = (TextView) view
				.findViewById(R.id.txt_title_date);
		txtTitleDate.setText(getStringTitle(lunarDateTitle));
		
		TextView txtTitleMonth = (TextView) view
				.findViewById(R.id.txt_title_month);
		txtTitleMonth.setText(getStringTitle(lunarMonthTitle));
		
		TextView txtTitleYear = (TextView) view
				.findViewById(R.id.txt_title_year);
		txtTitleYear.setText(getStringTitle(lunarYearTitle));

		return view;
	}

	private String getStringTitle(int[] title) {
		String result = "";
		
		switch (title[0]) {
			case 0:
				result += "Giáp";
				break;
			case 1:
				result += "Ất";
				break;
			case 2:
				result += "Bính";
				break;
			case 3:
				result += "Đinh";
				break;
			case 4:
				result += "Mậu";
				break;
			case 5:
				result += "Kỷ";
				break;
			case 6:
				result += "Canh";
				break;
			case 7:
				result += "Tân";
				break;
			case 8:
				result += "Nhâm";
				break;
			case 9:
				result += "Quý";
				break;
		}
		
		result += " ";		
		switch (title[1]) {
			case 0:
				result += "Tý";
				break;
			case 1:
				result += "Sửu";
				break;
			case 2:
				result += "Dần";
				break;
			case 3:
				result += "Mão";
				break;
			case 4:
				result += "Thìn";
				break;
			case 5:
				result += "Tỵ";
				break;
			case 6:
				result += "Ngọ";
				break;
			case 7:
				result += "Mùi";
				break;
			case 8:
				result += "Thân";
				break;
			case 9:
				result += "Dậu";
				break;
			case 10:
				result += "Tuất";
				break;
			case 11:
				result += "Hợi";
				break;
		}

		return result;
	}
}
