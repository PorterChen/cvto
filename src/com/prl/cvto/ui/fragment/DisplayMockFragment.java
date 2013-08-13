package com.prl.cvto.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisplayMockFragment extends Fragment {
	private static final String BUNDLE_LAYOUT_ID = "com.prl.cvto.ui.fragment.DisplayMockFragment.layoutid";
	private int mLayout;
	
	public static DisplayMockFragment newInstance(int layout) {
		DisplayMockFragment fragment = new DisplayMockFragment();
		fragment.setLayout(layout);
		return fragment;
	}
	
	public void setLayout(int layout) {
		mLayout = layout;
	}
	
	@Override
	public void onSaveInstanceState(Bundle state) {
		state.putInt(BUNDLE_LAYOUT_ID, mLayout);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mLayout = savedInstanceState.getInt(BUNDLE_LAYOUT_ID);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(mLayout, container, false);
	}
}