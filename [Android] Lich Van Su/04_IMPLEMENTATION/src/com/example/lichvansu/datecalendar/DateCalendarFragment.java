package com.example.lichvansu.datecalendar;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.example.lichvansu.R;

@SuppressWarnings("deprecation")
public class DateCalendarFragment extends Fragment implements OnClickListener {

	private static final int SWIPE_MIN_DISTANCE = 20;
	private GestureDetector gestureDetector;
	private View.OnTouchListener gestureListener;
	private Date currentDate;

	{
		gestureDetector = new GestureDetector(
				new DateCalendarSwipeGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		currentDate = new Date();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		container.setOnClickListener(this);
		container.setOnTouchListener(gestureListener);

		View view = inflater.inflate(R.layout.fragment_date_calendar,
				container, false);

		getChildFragmentManager().beginTransaction()
				.add(R.id.element_container, getElementFragment(currentDate))
				.commit();

		return view;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
	}

	private void nextDate() {
		// Change current state
		currentDate.setTime(currentDate.getTime() + 86400000);

		// Do the fragment transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.anim.slide_right_mid,
						R.anim.slide_mid_left)
				.replace(R.id.element_container,
						getElementFragment(currentDate)).commit();
	}

	private void prevDate() {
		// Change current state
		currentDate.setTime(currentDate.getTime() - 86400000);

		// Do the fragment transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.anim.slide_left_mid,
						R.anim.slide_mid_right)
				.replace(R.id.element_container,
						getElementFragment(currentDate)).commit();
	}

	private void nextMonth() {
		// Change current state
		currentDate.setMonth(currentDate.getMonth() + 1);

		// Do the fragment transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.anim.slide_top_mid,
						R.anim.slide_mid_bottom)
				.replace(R.id.element_container,
						getElementFragment(currentDate)).commit();
	}

	private void prevMonth() {
		// Change current state
		currentDate.setMonth(currentDate.getMonth() - 1);

		// Do the fragment transaction
		getChildFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.anim.slide_bottom_mid,
						R.anim.slide_mid_top)
				.replace(R.id.element_container,
						getElementFragment(currentDate)).commit();
	}

	private Fragment getElementFragment(Date date) {
		Fragment frag = new DateCalendarElementFragment();
		Bundle args = new Bundle();
		args.putInt("date", currentDate.getDate());
		args.putInt("month", currentDate.getMonth());
		args.putInt("year", currentDate.getYear());
		frag.setArguments(args);

		return frag;
	}

	private class DateCalendarSwipeGestureDetector extends
			SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > Math.abs(e1.getX()
						- e2.getX())) {
					if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
						// TODO implement UPWARD swipe action
						prevMonth();
					} else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
						// TODO implement DOWNWARD swipe action
						nextMonth();
					}
				} else {
					if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
						// TODO implement LEFT swipe action
						nextDate();
					} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
						// TODO implement RIGHT swipe action
						prevDate();
					}
				}

			} catch (Exception e) {
				// TODO catch exception
			}
			return false;
		}
	}
}
