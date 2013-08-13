package com.prl.cvto.frame;

import java.util.concurrent.ArrayBlockingQueue;

import com.prl.cvto.widget.TaskThread;

public class FrameAnalysis extends TaskThread<Void, ArrayBlockingQueue<Void>> {

    private static final String TAG = "VEDR.FrameAnalysis";

    @Override
    protected boolean runImpl() {
        return true;
    }

    private void offerAnalysis(byte[] input) {

    }

	@Override
	protected ArrayBlockingQueue<Void> createQueue() {
		return null;
	}

}