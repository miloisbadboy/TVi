package com.chiemtinhapp.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chiemtinhapp.R;


public class HoroscopeResultFragment extends Fragment {
	public static final String RESULT = "result";
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_horoscope_result, container, false);
		Bundle args = getArguments();
		((TextView) view.findViewById(R.id.horoscope_result)).setText(Html.fromHtml(args.getString(RESULT)));
		return view;
	}
}
