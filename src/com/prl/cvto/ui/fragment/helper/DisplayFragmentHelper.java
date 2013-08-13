package com.prl.cvto.ui.fragment.helper;


public interface DisplayFragmentHelper extends FragmentBase {

	/**
	 * Interface for parent to notify gesture change
	 * @OriginAuthor zillians/giggle
	 *
	 */
	public interface OnGestureEnableChangeListener {
		/**
		 * Notify the fragment that the activity gesture change
		 * @param enable, true if activity gesture is enabled
		 */
		void gestureChange(boolean enable);
	}
	
    /**
     * Enable/disable the gesture control of the parent activity
     * @param enable, true if the parent could formulate its gesture
     */
    void setEnableGesture(boolean enable);
    
    /**
     * The current gesture enable/disable state of the parent activity
     * @return true, if the activity enable gesture
     */
    boolean getEnableGesture();
    
    /**
     * A listener for fragment to listen whether the parent activity gesture 
     * enabling is changed.
     * @param listener to listen to the activity gesture change
     */
    void setGestureEnableChangeListener(OnGestureEnableChangeListener listener);
    
    
}
