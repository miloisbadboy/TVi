package com.example.lichvansu.xemngay;

import com.example.lichvansu.R;
import com.example.lichvansu.R.id;
import com.example.lichvansu.R.layout;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

public class XemNgayFragment extends Fragment implements OnClickListener{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		TextView a = (TextView) getActivity().findViewById(R.id.txtBigDate);
		
//		gestureDetector = new GestureDetector(new MyGestureDetector());
//	    gestureListener = new View.OnTouchListener() {
//	        public boolean onTouch(View v, MotionEvent event) {
//	            return gestureDetector.onTouchEvent(event);
//	        }
//	    };
//		
//		a.setOnClickListener(this); 
//	    a.setOnTouchListener(gestureListener);
		
		return inflater.inflate(R.layout.fragment_xem_ngay, container, false);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}	
}
