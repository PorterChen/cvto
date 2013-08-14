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
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.provider.Settings;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class DisplayActivity  extends FragmentActivity implements DisplayFragmentHelper{
	
	class DisplayPagerAdapter extends FragmentPagerAdapter {
		
    	private Fragment[] mFragments = new Fragment[] {
    			DisplayMockFragment.newInstance(R.layout.display_image),
    			DisplayImageFragment.newInstance(),
    			DisplayVideoFragment.newInstance(),    		 			
    			DisplayMockFragment.newInstance(R.layout.display_video)
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
    private static final long GESTURE_COMMAND_INTERVAL = 1000 * 1;
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
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.display);
		
		//mConfigPool = ((CloudVirtualTryOn) getApplication()).ConfigPool;
		//mCredential = ((CloudVirtualTryOn) getApplication()).getCredential();
		
		initializeUIComponent();
		initializeGestureDetector();
	        
		Log.d(TAG, "onCreate");
	}
	
	private void initializeGestureDetector() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() { 
        	
        	private boolean pageScrolling(float velocity) {
				int total = mPager.getAdapter().getCount();
				int current = mPager.getCurrentItem();
				
				if (velocity < -2000) {                   
            		if (current < total - 1) { 
            			mPager.setCurrentItem(current+1, true);
            		}
                    return true;
            	}
            	else if (velocity > 2000) {
            		if (current > 0) {
            			mPager.setCurrentItem(current-1, true);
            		}
                    return true;
            	}
            	return false;
        	}
        	
        	private boolean actionTaken(float velocity) {
        		long currentTime = System.currentTimeMillis();	
        		if (mLastGestureCommand + GESTURE_COMMAND_INTERVAL > currentTime ) return false;
            	return false;
        	}
        	
        	@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        		if (Math.abs(velocityX) > Math.abs(velocityY)) {
					pageScrolling(velocityX);
				}
				else {
					actionTaken(velocityY);
				}		
        		return false;
			}        	
        });
    }
	
	
	private void initializeUIComponent() {   
    	
    	// initialize pager
    	final DisplayPagerAdapter adapter = new DisplayPagerAdapter(getSupportFragmentManager());    	
    	mPager = (ViewPager)findViewById(R.id.pager);    	
    	mPager.setAdapter(adapter);    	
    	mPager.setCurrentItem(1);
    	mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {
				Log.d(TAG, "onPageScrollStateChagne: " + state);				
				if (state == ViewPager.SCROLL_STATE_IDLE) {
					int current = mPager.getCurrentItem();
					int last = mPager.getAdapter().getCount()-2;
					if (current == 0) {
						mPager.setCurrentItem(last, false);
					}
					else if (current > last) {
						mPager.setCurrentItem(1, false);
					}
				}	
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
			}
    		
    	});
    	
    	/*
    	DashboardTip mTipLayout = (DashboardTip) findViewById(R.id.dashboard_info_tip); 
		if (mConfigPool.FirstTimeConfig.getEnterDashboardInfo()) {
			mTipLayout.setVisibility(View.VISIBLE);
			mTipLayout.setOnTipFinishCallback(new OnTipFinishCallback() {

				@Override
				public void onFinish() {
					mConfigPool.FirstTimeConfig.setEnterDashboardInfo(false);
				}
				
			});
			
		} else {
			mTipLayout.setVisibility(View.GONE);
		}
    	 */
    }
    
	@Override
	protected void onDestroy() {
		Log.d(TAG, "OnDestroy Start");
		super.onDestroy();
	    	    	
		Log.d(TAG, "OnDestroy Complete");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		//initializeUsbDetector();
		initializeServices();
		Log.d(TAG, "onResume End");
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG, "OnPause Start");
		super.onPause();                   
		uninitializeServices();
		//uninitializeUsbDetector();	        
		Log.d(TAG, "OnPause End");
	}
	
	private void initializeServices() {
		
	}
	
	private void uninitializeServices() {
		
	}
	@Override
	public ConfigPool getConfigPool() {
		return mConfigPool;
	}
	    
	@Override    
	public ServiceContext getServiceContext() {
		return mServiceContext;
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {  
		if (!mEnableGesture) {
			return super.onTouchEvent(event);
		}
	    	
		return mGestureDetector.onTouchEvent(event);        
	}
	public void onBack(View v) {
		finish();
	}

	@Override
	public void setEnableGesture(boolean enable) {
		if (mEnableGesture != enable) {
			mEnableGesture = enable;
			mGestureEnableChangeListener.gestureChange(enable);
		}
	}
	@Override
	public boolean getEnableGesture() {
		return mEnableGesture;
	}
	
	private void uninitializeUsbDetector() {
        //mBatteryMonitor.stop();
	}
	
	@Override
	public void setGestureEnableChangeListener(OnGestureEnableChangeListener listener) {
		mGestureEnableChangeListener = listener;
	}

	@Override
	public UserCredential getCredential() {
		return mCredential;
	}

	
}
