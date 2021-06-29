package com.example.whoiscalling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("zzz", "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, MyService.class));
    }
}
