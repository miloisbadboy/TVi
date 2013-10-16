package com.chiemtinhapp.fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiemtinhapp.R;


public class HoroscopeResultFragment extends Fragment {
	public static final String INDEX = "index";
	public static final String HORO_NUMBER = "number";
	public static final String RESULT = "result";
	public static final String TITLE = "title";

	private int[] drawables = { R.drawable.zodiac_01, R.drawable.zodiac_02, R.drawable.zodiac_03, R.drawable.zodiac_04, R.drawable.zodiac_05,
			R.drawable.zodiac_06, R.drawable.zodiac_07, R.drawable.zodiac_08, R.drawable.zodiac_09, R.drawable.zodiac_010, R.drawable.zodiac_011,
			R.drawable.zodiac_012 };
	
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_horoscope_result, container, false);
		Bundle args = getArguments();
		int index = args.getInt(INDEX);
		int horoNum = args.getInt(HORO_NUMBER);
		
		ImageView image = (ImageView) view.findViewById(R.id.img_horoscope);
		
		if (index == 0) {
			// First result page
			// Set the image according to selected horoNum
			image.setImageResource(drawables[horoNum - 1]);
		}
		else {
			// Other results pages
			// Set the image to result icons.
		}
		((TextView) view.findViewById(R.id.horoscope_result)).setText(args.getString(RESULT));
		return view;
	}
}
