package com.example.lichvansu.dateconverter;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.lichvansu.R;
import com.example.lichvansu.helper.DateHelper;

public class DateConverterFragment extends Fragment implements
		NumberPicker.OnValueChangeListener {
	NumberPicker numberPickerDate, numberPickerMonth, numberPickerYear,
			numberPickerDateLunar, numberPickerMonthLunar,
			numberPickerYearLunar;

	public DateConverterFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_date_converter,
				container, false);

		Date currentDate = new Date();

		numberPickerDate = (NumberPicker) view.findViewById(R.id.numpckr_date);
		numberPickerDate.setMinValue(1);
		numberPickerDate.setMaxValue(31);
		numberPickerDate.setValue(currentDate.getDate());

		numberPickerMonth = (NumberPicker) view
				.findViewById(R.id.numpckr_month);
		numberPickerMonth.setMinValue(1);
		numberPickerMonth.setMaxValue(12);
		numberPickerMonth.setValue(currentDate.getMonth() + 1);
		numberPickerMonth.setOnValueChangedListener(this);

		numberPickerYear = (NumberPicker) view.findViewById(R.id.numpckr_year);
		numberPickerYear.setMinValue(1990);
		numberPickerYear.setMaxValue(2200);
		numberPickerYear.setValue(currentDate.getYear() + 1900);

		numberPickerDateLunar = (NumberPicker) view
				.findViewById(R.id.numpckr_date_lunar);
		numberPickerDateLunar.setMinValue(1);
		numberPickerDateLunar.setMaxValue(31);

		numberPickerMonthLunar = (NumberPicker) view
				.findViewById(R.id.numpckr_month_lunar);
		numberPickerMonthLunar.setMinValue(1);
		numberPickerMonthLunar.setMaxValue(12);

		numberPickerYearLunar = (NumberPicker) view
				.findViewById(R.id.numpckr_year_lunar);
		numberPickerYearLunar.setMinValue(1990);
		numberPickerYearLunar.setMaxValue(2200);

		return view;
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

		if (picker.equals(numberPickerYear)) {
			if (numberPickerMonth.getValue() == 2) {
				if (DateHelper.isLeapYear(newVal)) {
					numberPickerDate.setMaxValue(29);
				} else {
					numberPickerDate.setMaxValue(28);
				}
			}			
		}
		
		if (picker.equals(numberPickerMonth)) {
			switch (newVal) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					numberPickerDate.setMaxValue(31);
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					numberPickerDate.setMaxValue(30);
					break;
				case 2:
					if (DateHelper.isLeapYear(numberPickerYear.getValue())) {
						numberPickerDate.setMaxValue(29);
					} else {
						numberPickerDate.setMaxValue(28);
					}
					break;
			}
		}
	}
}
