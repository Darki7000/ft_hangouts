package com.example.ft_hangouts;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class App extends Application implements Application.ActivityLifecycleCallbacks{

    private int activityReferences = 0;
    Calendar lastTime;
    MessageMonitor monitor;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        monitor = new MessageMonitor();
        registerReceiver(monitor, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
    }

    @Override
    public void onTerminate() {
        unregisterReceiver(monitor);
        super.onTerminate();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

        if (++activityReferences == 1) {
            if(lastTime != null)
                Toast.makeText(activity, lastTime.get(Calendar.HOUR)
                        + ":" + lastTime.get(Calendar.MINUTE) + ":" + lastTime.get((Calendar.SECOND)), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (--activityReferences == 0) {
            lastTime = Calendar.getInstance();
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
