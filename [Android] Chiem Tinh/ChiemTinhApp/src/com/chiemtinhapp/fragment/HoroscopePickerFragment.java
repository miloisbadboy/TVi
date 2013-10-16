package com.chiemtinhapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiemtinhapp.R;

public class HoroscopePickerFragment extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_horoscope_picker, container, false);
		return view;
	}
}
