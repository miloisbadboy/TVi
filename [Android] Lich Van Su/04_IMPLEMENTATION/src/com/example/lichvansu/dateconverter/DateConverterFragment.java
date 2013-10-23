package com.example.lichvansu.dateconverter;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.lichvansu.R;
import com.example.lichvansu.helper.MyDateHelper;

public class DateConverterFragment extends Fragment implements
		NumberPicker.OnValueChangeListener {
	NumberPicker numberPickerDate, numberPickerMonth, numberPickerYear;

	public DateConverterFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_date_converter,
				container, false);

		Calendar currentDate = Calendar.getInstance();

		numberPickerDate = (NumberPicker) view.findViewById(R.id.numpicker_date);
		numberPickerDate.setMinValue(1);
		numberPickerDate.setMaxValue(31);
		numberPickerDate.setValue(currentDate.get(Calendar.DATE));
		numberPickerDate.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		numberPickerDate.setOnValueChangedListener(this);

		numberPickerMonth = (NumberPicker) view
				.findViewById(R.id.numpicker_month);
		numberPickerMonth.setMinValue(1);
		numberPickerMonth.setMaxValue(12);
		numberPickerMonth.setValue(currentDate.get(Calendar.MONTH) + 1);
		numberPickerMonth.setDisplayedValues(new String[] {"Một", "Hai", "Ba", "Bốn", "Năm", "Sáu", "Bảy", "Tám", "Chín", "Mười", "Mười một", "Mười hai"});
//		numberPickerMonth.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		numberPickerMonth.setOnValueChangedListener(this);

		numberPickerYear = (NumberPicker) view.findViewById(R.id.numpicker_year);
		numberPickerYear.setMinValue(1800);
		numberPickerYear.setMaxValue(2200);
		numberPickerYear.setValue(currentDate.get(Calendar.YEAR));
		numberPickerYear.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		numberPickerYear.setOnValueChangedListener(this);

		convertSolarToLunar();

		return view;
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		boolean solarToLunar = true;

		if (picker.equals(numberPickerYear)) {
			if (numberPickerMonth.getValue() == 2) {
				if (MyDateHelper.isSolarLeapYear(newVal)) {
					numberPickerDate.setMaxValue(29);
				} else {
					numberPickerDate.setMaxValue(28);
				}
			}
		}

		else if (picker.equals(numberPickerMonth)) {
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
					if (MyDateHelper.isSolarLeapYear(numberPickerYear.getValue())) {
						numberPickerDate.setMaxValue(29);
					} else {
						numberPickerDate.setMaxValue(28);
					}
					break;
			}
		}

		else if (picker.equals(numberPickerDate)) {

		}

		else {
			solarToLunar = false;
		}

		if (solarToLunar) {
			convertSolarToLunar();
		} else {
			convertLunarToSolar();
		}
	}

	private void convertSolarToLunar() {
//		int[] result = MyDateHelper.convertSolar2Lunar(
//				numberPickerDate.getValue(), numberPickerMonth.getValue(),
//				numberPickerYear.getValue(), 7.00);
//
//		numberPickerDateLunar.setValue(result[0]);
//		numberPickerMonthLunar.setValue(result[1]);
//		numberPickerYearLunar.setValue(result[2]);
	}

	private void convertLunarToSolar() {
//		int[] result = MyDateHelper.convertLunar2Solar(
//				numberPickerDateLunar.getValue(),
//				numberPickerMonthLunar.getValue(),
//				numberPickerYearLunar.getValue(), 0, 7.00);
//
//		numberPickerDate.setValue(result[0]);
//		numberPickerMonth.setValue(result[1]);
//		numberPickerYear.setValue(result[2]);
	}
}
