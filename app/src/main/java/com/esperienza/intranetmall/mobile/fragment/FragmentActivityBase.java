package com.esperienza.intranetmall.mobile.fragment;



/**
 * Created by ThinkPad on 30/11/2015.
 */


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.esperienza.intranetmall.mobile.logger.LogWrapper;



public class FragmentActivityBase extends AppCompatActivity {
    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    /** Set up targets to receive log data */
    public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        com.esperienza.intranetmall.mobile.logger.Log.setLogNode(logWrapper);

        com.esperienza.intranetmall.mobile.logger.Log.i(TAG, "Ready");
    }
}