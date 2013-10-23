package com.example.lichvansu.datecalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lichvansu.R;

public class DateCalendarMainElement extends Fragment {

	public static DateCalendarMainElement newInstance(int date, int month,
			int year, boolean isHoliday, int[] lunarTitle, int lunarStatus) {
		
		DateCalendarMainElement fragment = new DateCalendarMainElement();
		
		Bundle args = new Bundle();
		args.putInt("date", date);
		args.putInt("month", month);
		args.putInt("year", year);
		args.putInt("lunarStatus", lunarStatus);
		args.putIntArray("lunarTitle", lunarTitle);
		args.putBoolean("isHoliday", isHoliday);
		
		fragment.setArguments(args);
		return fragment;
	}

	public DateCalendarMainElement() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment		
		View view = inflater.inflate(
				R.layout.fragment_date_calendar_main_element, container, false);
		
		Bundle args = getArguments();
		int date = args.getInt("date");
		int month = args.getInt("month");
		int year = args.getInt("year");
		int lunarStatus = args.getInt("lunarStatus");
		int[] lunarTitle = args.getIntArray("lunarTitle");
		boolean isHoliday = args.getBoolean("isHoliday");

		TextView txtDate = (TextView) view.findViewById(R.id.txt_date);
		txtDate.setText(date + "");
		if (isHoliday) {
			txtDate.setTextColor(getResources().getColor(R.color.red));
		}

		TextView txtMonthYear = (TextView) view
				.findViewById(R.id.txt_month_year);
		txtMonthYear.setText("Tháng " + month + " Năm " + year);
		if (isHoliday) {
			txtMonthYear.setTextColor(getResources().getColor(R.color.red));
		}

		TextView txtStatus = (TextView) view.findViewById(R.id.txt_status);
		if (lunarStatus > 0) {
			txtStatus.setText("Hoàng đạo");
			txtStatus.setTextColor(getResources().getColor(R.color.red));
			txtStatus.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_star_red, 0, 0, 0);
		} else if (lunarStatus < 0) {
			txtStatus.setText("Hắc đạo");
			txtStatus.setTextColor(getResources().getColor(R.color.gray));
			txtStatus.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_star_black, 0, 0, 0);
		} else {
			txtStatus.setVisibility(TextView.INVISIBLE);
		}

		ImageView imgBackground = (ImageView) view
				.findViewById(R.id.img_background);
		int backgroundRes = R.drawable.zodiac_rat_red;
		if (isHoliday) {
			switch (lunarTitle[1]) {
				case 0:
					backgroundRes = R.drawable.zodiac_rat_red;
					break;
				case 1:
					backgroundRes = R.drawable.zodiac_buffalo_red;
					break;
				case 2:
					backgroundRes = R.drawable.zodiac_tiger_red;
					break;
				case 3:
					backgroundRes = R.drawable.zodiac_rabbit_red;
					break;
				case 4:
					backgroundRes = R.drawable.zodiac_dragon_red;
					break;
				case 5:
					backgroundRes = R.drawable.zodiac_snake_red;
					break;
				case 6:
					backgroundRes = R.drawable.zodiac_horse_red;
					break;
				case 7:
					backgroundRes = R.drawable.zodiac_goat_red;
					break;
				case 8:
					backgroundRes = R.drawable.zodiac_monkey_red;
					break;
				case 9:
					backgroundRes = R.drawable.zodiac_rooster_red;
					break;
				case 10:
					backgroundRes = R.drawable.zodiac_dog_red;
					break;
				case 11:
					backgroundRes = R.drawable.zodiac_pig_red;
					break;
			}
		} else {
			switch (lunarTitle[1]) {
				case 0:
					backgroundRes = R.drawable.zodiac_rat_blue;
					break;
				case 1:
					backgroundRes = R.drawable.zodiac_buffalo_blue;
					break;
				case 2:
					backgroundRes = R.drawable.zodiac_tiger_blue;
					break;
				case 3:
					backgroundRes = R.drawable.zodiac_rabbit_blue;
					break;
				case 4:
					backgroundRes = R.drawable.zodiac_dragon_blue;
					break;
				case 5:
					backgroundRes = R.drawable.zodiac_snake_blue;
					break;
				case 6:
					backgroundRes = R.drawable.zodiac_horse_blue;
					break;
				case 7:
					backgroundRes = R.drawable.zodiac_goat_blue;
					break;
				case 8:
					backgroundRes = R.drawable.zodiac_monkey_blue;
					break;
				case 9:
					backgroundRes = R.drawable.zodiac_rooster_blue;
					break;
				case 10:
					backgroundRes = R.drawable.zodiac_dog_blue;
					break;
				case 11:
					backgroundRes = R.drawable.zodiac_pig_blue;
					break;
			}
		}		
		imgBackground.setImageResource(backgroundRes);

		return view;
	}
}
