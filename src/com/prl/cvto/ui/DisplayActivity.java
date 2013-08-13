package com.prl.cvto.ui;

import com.prl.cvto.CloudVirtualTryOn.ConfigPool;
import com.prl.cvto.R;
import com.prl.cvto.author.UserCredential;
import com.prl.cvto.config.ServiceContext;
import com.prl.cvto.ui.fragment.DisplayImageFragment;
import com.prl.cvto.ui.fragment.DisplayMockFragment;
import com.prl.cvto.ui.fragment.DisplayVideoFragment;
import com.prl.cvto.ui.fragment.helper.DisplayFragmentHelper;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisplayActivity  extends FragmentActivity implements DisplayFragmentHelper{
	class DisplayPagerAdapter extends FragmentPagerAdapter {

    	private Fragment[] mFragments = new Fragment[] {
    			DisplayMockFragment.newInstance(R.layout.display_video),
    			DisplayVideoFragment.newInstance(),
    			DisplayImageFragment.newInstance(),    			
    			DisplayMockFragment.newInstance(R.layout.display_image)
    	};
    	
        public DisplayPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	return mFragments[position];
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        	// Well, we don't destroy anything
        }
        
        @Override
        public int getCount() {
            return mFragments.length;
        }
    }

    private static final String TAG = "CVTO.DisplayActivity";
    private final ServiceContext mServiceContext = new ServiceContext();
    private ConfigPool mConfigPool = null;    
    private UserCredential mCredential = null;
    private Handler mMyHandler; // TODO: may combine with mCustomMessageHandler
    
    private boolean mEnableGesture = true;
    private GestureDetector mGestureDetector = null;
    private long mLastGestureCommand = 0;
	private OnGestureEnableChangeListener mGestureEnableChangeListener;
	private long mLastTouchScreenTime = 0;
	
	private ViewPager mPager;
    
}
