package com.example.lichvansu.dateconverter;

import java.util.Calendar;
import java.util.Currency;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lichvansu.R;
import com.example.lichvansu.helper.MyDateHelper;

public class DateConverterFragment extends Fragment implements NumberPicker.OnValueChangeListener {
	private NumberPicker _numberPickerDate, _numberPickerMonth,
			_numberPickerYear;
	private TextView _txtSolar, _txtLunar;
	private ImageView _imgMode;
	private View _masterView;
	private boolean _isSolar;
	private int[] _solarDate, _lunarDate;

	public DateConverterFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		_masterView = inflater.inflate(
			R.layout.fragment_date_converter,
			container,
			false);

		// Default convert mode
		_isSolar = true;

		// Initialize date as today
		Calendar currentDate = Calendar.getInstance();
		_solarDate = new int[3];
		_solarDate[0] = currentDate.get(Calendar.DATE);
		_solarDate[1] = currentDate.get(Calendar.MONTH) + 1;
		_solarDate[2] = currentDate.get(Calendar.YEAR);
		_lunarDate = MyDateHelper.convertSolar2Lunar(
			_solarDate[0],
			_solarDate[1],
			_solarDate[2]);

		// Date picker
		_numberPickerDate = (NumberPicker) _masterView
				.findViewById(R.id.numpicker_date);
		_numberPickerDate
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		_numberPickerDate.setOnValueChangedListener(this);

		// Month picker
		_numberPickerMonth = (NumberPicker) _masterView
				.findViewById(R.id.numpicker_month);
		_numberPickerMonth
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		_numberPickerMonth.setOnValueChangedListener(this);

		// Year picker
		_numberPickerYear = (NumberPicker) _masterView
				.findViewById(R.id.numpicker_year);
		_numberPickerYear.setMinValue(1800);
		_numberPickerYear.setMaxValue(2200);
		_numberPickerYear
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		_numberPickerYear.setOnValueChangedListener(this);

		// Other views
		_txtSolar = (TextView) _masterView.findViewById(R.id.txt_solar);
		_txtLunar = (TextView) _masterView.findViewById(R.id.txt_lunar);
		_imgMode = (ImageView) _masterView.findViewById(R.id.img_mode);

		_imgMode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				float solarX = _txtSolar.getX(), lunarX = _txtLunar.getX();
				int duration = Integer.parseInt(getResources().getString(
					R.string.animation_duration));

				_txtSolar.animate().x(lunarX).setDuration(duration);
				_txtLunar.animate().x(solarX).setDuration(duration);
				_imgMode.animate().rotationBy(180).setDuration(duration);

				if (_isSolar) {
					_lunarDate = MyDateHelper.convertSolar2Lunar(
						_solarDate[0],
						_solarDate[1],
						_solarDate[2]);

					_isSolar = false;
					updatePicker();

					int leapMonth = MyDateHelper.isLunarLeapYear(_lunarDate[2]);
					boolean isLeap = (leapMonth != MyDateHelper.INVALID_RESULT);

					_numberPickerDate.setValue(_lunarDate[0]);
					if (isLeap && _lunarDate[1] > leapMonth || _lunarDate[3] != 0) {
						_numberPickerMonth.setValue(_lunarDate[1] + 1);
					} else {
						_numberPickerMonth.setValue(_lunarDate[1]);
					}
					_numberPickerYear.setValue(_lunarDate[2]);
				} else {
					_solarDate = MyDateHelper.convertLunar2Solar(
						_lunarDate[0],
						_lunarDate[1],
						_lunarDate[2],
						_lunarDate[3] != 0);

					_isSolar = true;
					updatePicker();

					_numberPickerDate.setValue(_solarDate[0]);
					_numberPickerMonth.setValue(_solarDate[1]);
					_numberPickerYear.setValue(_solarDate[2]);
				}
			}
		});

		updatePicker();
		_numberPickerDate.setValue(_solarDate[0]);
		_numberPickerMonth.setValue(_solarDate[1]);
		_numberPickerYear.setValue(_solarDate[2]);

		return _masterView;
	}

	private void updatePicker() {
		if (_isSolar) {
			int sMonth = _solarDate[1], sYear = _solarDate[2];

			_numberPickerDate.setMinValue(1);
			_numberPickerDate.setMaxValue(MyDateHelper.countSolarDates(
				sMonth,
				sYear));

			_numberPickerMonth.setMinValue(1);
			_numberPickerMonth.setMaxValue(12);
			_numberPickerMonth.setDisplayedValues(new String[] { "Một", "Hai",
					"Ba", "Tư", "Năm", "Sáu", "Bảy", "Tám", "Chín", "Mười",
					"Mười một", "Mười hai" });
		} else {
			int lMonth = _lunarDate[1], lYear = _lunarDate[2], lLeap = _lunarDate[3];
			int leapMonth = MyDateHelper.isLunarLeapYear(lYear);
			boolean isLeap = (leapMonth != MyDateHelper.INVALID_RESULT);

			_numberPickerDate.setMinValue(1);
			if (lLeap != 0) {
				_numberPickerDate.setMaxValue(MyDateHelper
						.countLunarLeapDates(lYear));
			} else {
				_numberPickerDate.setMaxValue(MyDateHelper.countLunarDates(
					lMonth,
					lYear));
			}

			_numberPickerMonth.setMinValue(1);
			if (isLeap) {
				_numberPickerMonth.setMaxValue(13);
				String[] strMonths = new String[] { "Giêng", "Hai", "Ba", "Tư",
						"Năm", "Sáu", "Bảy", "Tám", "Chín", "Mười", "Mười một",
						"Chạp", "" };
				String strLeapMonth = strMonths[(leapMonth + 12) % 13] + " *";

				for (int i = 11; i >= leapMonth; i--) {
					strMonths[i + 1] = strMonths[i];
				}
				strMonths[leapMonth] = strLeapMonth;
				_numberPickerMonth.setDisplayedValues(strMonths);
			} else {
				_numberPickerMonth.setMaxValue(12);
				_numberPickerMonth.setDisplayedValues(new String[] { "Giêng",
						"Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy", "Tám", "Chín",
						"Mười", "Mười một", "Chạp" });
			}
		}
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		updatePicker();
		if (_isSolar) {
			if (picker.equals(_numberPickerDate)) {
				_solarDate[0] = newVal;
			} else if (picker.equals(_numberPickerMonth)) {
				_solarDate[1] = newVal;
			} else if (picker.equals(_numberPickerYear)) {
				_solarDate[2] = newVal;
			}
		} else {
			if (picker.equals(_numberPickerDate)) {
				_lunarDate[0] = newVal;
			} else if (picker.equals(_numberPickerMonth)) {
				int leapMonth = MyDateHelper.isLunarLeapYear(_lunarDate[2]);
				if (leapMonth != MyDateHelper.INVALID_RESULT) {
					if (newVal == leapMonth + 1) {
						_lunarDate[3] = 1;
						_lunarDate[1] = newVal - 1;
					} else if (newVal > leapMonth + 1) {
						_lunarDate[3] = 0;
						_lunarDate[1] = newVal - 1;
					} else {
						_lunarDate[3] = 0;
						_lunarDate[1] = newVal;
					}
				} else {
					_lunarDate[3] = 0;
					_lunarDate[1] = newVal;
				}
			} else if (picker.equals(_numberPickerYear)) {
				_lunarDate[2] = newVal;

				int leapMonth = MyDateHelper.isLunarLeapYear(_lunarDate[2]);
				if (_lunarDate[1] == leapMonth) {
					_lunarDate[3] = 1;
				} else {
					_lunarDate[3] = 0;
				}
			}
		}
	}

	private void convertSolarToLunar() {
		// int[] result = MyDateHelper.convertSolar2Lunar(
		// numberPickerDate.getValue(), numberPickerMonth.getValue(),
		// numberPickerYear.getValue(), 7.00);
		//
		// numberPickerDateLunar.setValue(result[0]);
		// numberPickerMonthLunar.setValue(result[1]);
		// numberPickerYearLunar.setValue(result[2]);
	}

	private void convertLunarToSolar() {
		// int[] result = MyDateHelper.convertLunar2Solar(
		// numberPickerDateLunar.getValue(),
		// numberPickerMonthLunar.getValue(),
		// numberPickerYearLunar.getValue(), 0, 7.00);
		//
		// numberPickerDate.setValue(result[0]);
		// numberPickerMonth.setValue(result[1]);
		// numberPickerYear.setValue(result[2]);
	}
}
