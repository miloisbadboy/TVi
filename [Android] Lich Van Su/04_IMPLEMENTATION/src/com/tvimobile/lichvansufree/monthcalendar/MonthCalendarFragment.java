package com.tvimobile.lichvansufree.monthcalendar;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tvimobile.lichvansufree.R;
import com.tvimobile.lichvansufree.datecalendar.DateCalendarMainElement;

public class MonthCalendarFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Bundle args = getArguments();
		View view = inflater.inflate(R.layout.fragment_month_calendar,
				container, false);

		getChildFragmentManager()
				.beginTransaction()
				.add(R.id.element_container,
						getElementFragment(Calendar.getInstance())).commit();;

		return view;
	}

	private Fragment getElementFragment(Calendar date) {
		Fragment frag = new MonthCalendarElementFragment();
		Bundle args = new Bundle();
		Calendar currentDate = Calendar.getInstance();
		args.putInt("month", currentDate.get(Calendar.MONTH));
		args.putInt("year", currentDate.get(Calendar.YEAR));
		frag.setArguments(args);

		return frag;
	}
}
