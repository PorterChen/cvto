package com.prl.cvto.widget;

import java.util.AbstractQueue;
import android.util.Log;


/**
 * 
 * @OriginAuthor zillians
 *  
 */


public abstract class TaskThread<TaskType, QueueType extends AbstractQueue<TaskType>> extends Thread {
    private static final String TAG = "CVTO.TaskThread";
    private static final long MIN_ADAPTIVE_WAIT = 4 * 1000;
    private static final long MAX_ADAPTIVE_WAIT = 60 * 1000;
    
    private boolean mRunning = false;
    private boolean mPause = false;
	private QueueType mTaskQueue = null;
	
	private long mAdaptiveWait = 0;   // miliseconds
   
    protected TaskThread() {
        mTaskQueue = createQueue();
    }
    
    abstract protected QueueType createQueue();

    public boolean addTask(TaskType t) {
    	if (mTaskQueue == null) return false;    	
    	return mTaskQueue.offer(t);
    }

    public void clearTasks() {
    	if (mTaskQueue == null) return;    	
    	mTaskQueue.clear();
    }
    
    public int getTaskCount() {
    	return mTaskQueue.size();
    }

    public TaskType getTask() {
    	if (mTaskQueue == null) return null;    	
        return mTaskQueue.poll();
    }

    protected boolean isStopped() {
        return !mRunning;
    }
    
    protected void resetAdaptiveWait() {
    	Log.d(TAG, "resetAdaptiveWait()");
    	mAdaptiveWait = 0;
    	this.interrupt();
    }
    
    public void enablePause(boolean pause) {
    	if (mPause != pause) {
    		mPause = pause;
    		
    		if (mPause == false) {
    			resetAdaptiveWait();
    		}
    	}
    }
    
    @Override
    public void run() {

        mRunning = true;

        // TODO: be more flexible 
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        runInit();
        
        while (mRunning) {
            if (mAdaptiveWait != 0) {
                try {
                    Log.d(TAG, "Adaptive wait: " + mAdaptiveWait);
                    Thread.sleep(mAdaptiveWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();                       
                }
            }
            
            boolean doAnything = false;
            
            if (!mPause) {
            	doAnything = runImpl();
            }

            if (!doAnything) {
                mAdaptiveWait *= 2;
                mAdaptiveWait = Math.min(mAdaptiveWait, MAX_ADAPTIVE_WAIT);
                mAdaptiveWait = Math.max(mAdaptiveWait, MIN_ADAPTIVE_WAIT);
            }
            else {
                mAdaptiveWait = 0;
            }
        }
        
        runFinish();
    }

    public void close() {
        mRunning = false;
        clearTasks();
    }

    protected void runInit() {
    }
    
    protected abstract boolean runImpl();
    
    protected void runFinish() {        
    }
}