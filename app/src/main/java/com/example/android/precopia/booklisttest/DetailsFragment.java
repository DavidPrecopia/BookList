package com.example.android.precopia.booklisttest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {
	
	
	public static DetailsFragment newInstance() {
		Bundle args = new Bundle();
		
		DetailsFragment fragment = new DetailsFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.details_fragment, container, false);
		
		TextView textView = rootView.findViewById(R.id.text_view_fragment_placeholder);
		textView.setText("PLACEHOLDER");
		
		return rootView;
	}
}