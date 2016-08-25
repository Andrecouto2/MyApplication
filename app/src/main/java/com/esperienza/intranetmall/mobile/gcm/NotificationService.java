package com.esperienza.intranetmall.mobile.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ThinkPad on 03/08/2016.
 */
public class NotificationService extends Service {
    private Timer mTimer;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        mTimer  = new Timer();
        mTimer.schedule(timerTask,2000,2*1000);
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        try
        {

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.onStartCommand(intent,flags,startId);
    }
    public void onDestroy()
    {
        try
        {
           mTimer.cancel();
            timerTask.cancel();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Intent intent = new Intent("");
        sendBroadcast(intent);
    }
}
