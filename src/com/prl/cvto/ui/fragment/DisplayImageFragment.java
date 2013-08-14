package com.prl.cvto.ui.fragment;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prl.cvto.R;
import com.prl.cvto.CloudVirtualTryOn.ConfigPool;
import com.prl.cvto.config.ServiceContext;
import com.prl.cvto.ui.fragment.helper.DisplayFragmentHelper;

public class DisplayImageFragment extends Fragment {
private static final String TAG = "CVTO.DisplayImageFragment";
	
	private ScheduledThreadPoolExecutor mScheduler;
	private ConfigPool mConfigPool = null;
	private ServiceContext mServiceContext = null;
	private Activity mAttachedActivity = null;
	private DisplayFragmentHelper mFragmentHelper = null;	
	private Handler mHandler = null;
     
	
	boolean mIsPaused = true;

	public static  DisplayVideoFragment newInstance() {		
		return new  DisplayVideoFragment();
	}

	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    mAttachedActivity = activity;
	    mFragmentHelper = (DisplayFragmentHelper) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
        return inflater.inflate(R.layout.display_image, container, false);		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {	
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");
        
        mConfigPool = mFragmentHelper.getConfigPool();       
        
        mServiceContext = mFragmentHelper.getServiceContext();
                      
	}		
		
	@Override
	public void onResume() {
		super.onResume();
		mIsPaused = false;
		Log.d(TAG, "onResume");

        if (mHandler == null) {
        	mHandler = new Handler();
        }

     
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mIsPaused = true;
		mScheduler.shutdownNow();

       
	}	
	
}
