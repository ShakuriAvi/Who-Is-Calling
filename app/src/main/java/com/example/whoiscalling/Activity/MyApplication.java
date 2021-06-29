package com.example.whoiscalling.Activity;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.whoiscalling.MyService;
import com.judemanutd.autostarter.AutoStartPermissionHelper;

import static com.example.whoiscalling.MyService.START_FOREGROUND_SERVICE;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, MyService.class);
        intent.setAction(START_FOREGROUND_SERVICE);
        //if(AutoStartPermissionHelper.getInstance().getAutoStartPermission(this))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(new Intent(this, MyService.class));
        }

//        else
//            Log.d("aaa", "not run ");
    }

}

