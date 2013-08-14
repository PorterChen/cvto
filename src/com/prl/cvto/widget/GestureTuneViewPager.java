package com.prl.cvto.widget;


import com.prl.cvto.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 *   
 * @OrginAuthor zillians
 *
 */

public class GestureTuneViewPager extends ViewPager {

	private static final String TAG = "CVTO.GestureTuneViewPager";
	private boolean mSwipeEnable = true;
	
	public GestureTuneViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GestureTuneViewPager, 0, 0);

		try {
			mSwipeEnable = a.getBoolean(R.styleable.GestureTuneViewPager_swipe_support, true);
		} finally {
			a.recycle();
		}
	}
	
	public void setEnableSwipe(boolean enable) {
		mSwipeEnable = enable;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "Intercept: pointer count = " + ev.getPointerCount() + " action = " + motionToString(ev));

		if (mSwipeEnable) 
			return super.onInterceptTouchEvent(ev);
		
		return false;
	}
	
	private String motionToString(MotionEvent e) {
		int action = e.getActionMasked();
		String actionType = "";
		switch (action) {
		case MotionEvent.ACTION_CANCEL: actionType="cancel"; break;
		case MotionEvent.ACTION_DOWN: actionType = "down"; break;
		case MotionEvent.ACTION_UP: actionType ="up"; break;
		case MotionEvent.ACTION_MOVE: actionType ="move"; break;
		case MotionEvent.ACTION_POINTER_DOWN: actionType ="another down"; break;
		case MotionEvent.ACTION_POINTER_UP: actionType ="another up"; break;
		}
		return actionType;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (mSwipeEnable) 
			return super.onTouchEvent(e);
		
		return false;

	}
}