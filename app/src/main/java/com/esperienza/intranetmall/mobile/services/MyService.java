package com.esperienza.intranetmall.mobile.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by ThinkPad on 11/01/2016.
 */
public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // START YOUR TASKS
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // STOP YOUR TASKS
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
}
