package com.tvimobile.lichvansufree.datecalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.tvimobile.lichvansufree.R;
import com.tvimobile.lichvansufree.helper.MyDateHelper;

@SuppressWarnings("deprecation")
public class DateCalendarFragment extends Fragment implements OnClickListener {

	private static final int SWIPE_MIN_DISTANCE = 10;
	private GestureDetector _gestureDetector;
	private View.OnTouchListener _gestureListener;
	private boolean _isAnimating = false;
	private Calendar currentDate;	

	{
		_gestureDetector = new GestureDetector(
				new DateCalendarSwipeGestureDetector());
		_gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return _gestureDetector.onTouchEvent(event);
			}
		};
		currentDate = Calendar.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		container.setOnClickListener(this);
		container.setOnTouchListener(_gestureListener);

		View view = inflater.inflate(
			R.layout.fragment_date_calendar,
			container,
			false);

		getChildFragmentManager().beginTransaction()
				.add(R.id.element_main, getMainElement(currentDate)).commit();

		getChildFragmentManager().beginTransaction()
				.add(R.id.element_detail, getDetailElement(currentDate))
				.commit();

		return view;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
	}

	private void nextDate() {
		// Change current state
		currentDate.add(Calendar.DATE, 1);

		// Do the element transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(
					R.anim.slide_right_mid,
					R.anim.slide_mid_left)
				.replace(R.id.element_main, getMainElement(currentDate))
				.commit();

		getChildFragmentManager().beginTransaction()
				.replace(R.id.element_detail, getDetailElement(currentDate))
				.commit();
	}

	private void prevDate() {
		// Change current state
		currentDate.add(Calendar.DATE, -1);

		// Do the element transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(
					R.anim.slide_left_mid,
					R.anim.slide_mid_right)
				.replace(R.id.element_main, getMainElement(currentDate))
				.commit();

		getChildFragmentManager().beginTransaction()
				.replace(R.id.element_detail, getDetailElement(currentDate))
				.commit();
	}

	private void nextMonth() {
		// Change current state
		currentDate.add(Calendar.MONTH, 1);

		// Do the element transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(
					R.anim.slide_top_mid,
					R.anim.slide_mid_bottom)
				.replace(R.id.element_main, getMainElement(currentDate))
				.commit();

		getChildFragmentManager().beginTransaction()
				.replace(R.id.element_detail, getDetailElement(currentDate))
				.commit();
	}

	private void prevMonth() {
		// Change current state
		currentDate.add(Calendar.MONTH, -1);

		// Do the element transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(
					R.anim.slide_bottom_mid,
					R.anim.slide_mid_top)
				.replace(R.id.element_main, getMainElement(currentDate))
				.commit();

		getChildFragmentManager().beginTransaction()
				.replace(R.id.element_detail, getDetailElement(currentDate))
				.commit();
	}

	private Fragment getMainElement(Calendar date) {
		boolean isHoliday = false;
		if (currentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			isHoliday = true;
		}

		int[] lunarDate = MyDateHelper.convertSolar2Lunar(
			currentDate.get(Calendar.DATE),
			currentDate.get(Calendar.MONTH) + 1,
			currentDate.get(Calendar.YEAR));

		int lunarStatus = MyDateHelper.getLunarDateStatus(
			lunarDate[0],
			lunarDate[1],
			lunarDate[2],
			lunarDate[3] != 0);

		int[] lunarTitle = MyDateHelper.getLunarDateTitle(
			lunarDate[0],
			lunarDate[1],
			lunarDate[2],
			lunarDate[3] != 0);

		return DateCalendarMainElement.newInstance(
			currentDate.get(Calendar.DATE),
			currentDate.get(Calendar.MONTH) + 1,
			currentDate.get(Calendar.YEAR),
			isHoliday,
			lunarTitle,
			lunarStatus);
	}

	@SuppressLint("SimpleDateFormat")
	private Fragment getDetailElement(Calendar date) {
		boolean isHoliday = false;
		if (currentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			isHoliday = true;
		}

		int[] lunarDate = MyDateHelper.convertSolar2Lunar(
			currentDate.get(Calendar.DATE),
			currentDate.get(Calendar.MONTH) + 1,
			currentDate.get(Calendar.YEAR));

		// Hour
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
		String strHour = dateFormat.format(Calendar.getInstance().getTime());
		dateFormat = new SimpleDateFormat("mm");
		String strTime = strHour + ":"
				+ dateFormat.format(Calendar.getInstance().getTime());

		// Lunar hour title
		int[] lunarHourTitle = MyDateHelper.getLunarHourTitle(
			Integer.parseInt(strHour),
			lunarDate[0],
			lunarDate[1],
			lunarDate[2],
			lunarDate[3] != 0);

		// Lunar date title
		int[] lunarDateTitle = MyDateHelper.getLunarDateTitle(
			lunarDate[0],
			lunarDate[1],
			lunarDate[2],
			lunarDate[3] != 0);

		// Lunar month title
		int[] lunarMonthTitle = MyDateHelper.getLunarMonthTitle(
			lunarDate[1],
			lunarDate[2]);

		// Lunar year title
		int[] lunarYearTitle = MyDateHelper.getLunarYearTitle(currentDate
				.get(Calendar.YEAR));

		// Hour status
		int lunarHourStatus = MyDateHelper.getLunarHourStatusOnDate(
			lunarDate[0],
			lunarDate[1],
			lunarDate[2],
			lunarDate[3] != 0)[lunarHourTitle[1]];

		// Date status
		int lunarDateStatus = MyDateHelper.getLunarDateStatus(
			lunarDate[0],
			lunarDate[1],
			lunarDate[2],
			lunarDate[3] != 0);

		return DateCalendarDetailElement.newInstance(
			strTime,
			currentDate.get(Calendar.DATE),
			currentDate.get(Calendar.MONTH) + 1,
			currentDate.get(Calendar.YEAR),
			currentDate.get(Calendar.DAY_OF_WEEK),
			lunarDate[0],
			lunarDate[1],
			lunarDate[2],
			lunarDate[3],
			isHoliday,
			lunarHourTitle,
			lunarDateTitle,
			lunarMonthTitle,
			lunarYearTitle,
			lunarHourStatus,
			lunarDateStatus);
	}

	private class DateCalendarSwipeGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (_isAnimating)
				return false;
			
			try {
				if (Math.abs(e1.getY() - e2.getY()) > Math.abs(e1.getX()
						- e2.getX())) {
					if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
						// UPWARD swipe action
						prevMonth();
						_isAnimating = true;
					} else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
						// DOWNWARD swipe action
						nextMonth();
						_isAnimating = true;
					}
				} else {
					if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
						// LEFT swipe action
						nextDate();
						_isAnimating = true;
					} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
						// RIGHT swipe action
						prevDate();
						_isAnimating = true;
					}
				}

			} catch (Exception e) {
				// TODO catch exception
			}
			
			if (_isAnimating) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						_isAnimating = false;
					}
				}, (long) (Long.parseLong(getResources().getString(R.string.animation_duration)) * 1.2));
			}
			
			
			return _isAnimating;
		}
	}
}
