package com.example.whoiscalling;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.whoiscalling.Activity.MainActivity;
import com.example.whoiscalling.Activity.StartActivity;
import com.example.whoiscalling.Class.MyPhoneStateListener;

import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {

    public static final String START_FOREGROUND_SERVICE = "START_FOREGROUND_SERVICE";
    public static final String PAUSE_FOREGROUND_SERVICE = "PAUSE_FOREGROUND_SERVICE";
    public static final String STOP_FOREGROUND_SERVICE = "STOP_FOREGROUND_SERVICE";

    public static int NOTIFICATION_ID = 2705;
    private int lastShownNotificationId = -1;
    public static String CHANNEL_ID = "com.whoiscalling.CHANNEL_ID_FOREGROUND";
    public static String MAIN_ACTION = "com.whoiscalling.MyPhoneStateListener.action.main";

    private NotificationCompat.Builder notificationBuilder;
    private boolean isServiceRunningRightNow = false;

    Timer timer = new Timer();
    private int counter = 0;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
            Log.d("zzz","onStartCommand " + action);
        if (intent.getAction().equals(START_FOREGROUND_SERVICE)) {
            if (isServiceRunningRightNow) {
                return START_STICKY;
            }

            isServiceRunningRightNow = true;
            notifyToUserForForegroundService();
            startRecording();
            return START_STICKY;
        } else if (intent.getAction().equals(PAUSE_FOREGROUND_SERVICE)) {

        } else if (intent.getAction().equals(STOP_FOREGROUND_SERVICE)) {
            stopRecording();
            stopForeground(true);
            stopSelf();
            isServiceRunningRightNow = false;
            return START_NOT_STICKY;
        }
        return START_STICKY;
    }





    private void startRecording() {
        Log.d("zzz","startRecording");
        scheduleTimer();


    }
    public void scheduleTimer() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
              Log.d("zzz","seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                Log.d("zzz","done");
            }

        }.start();

}
    private void stopRecording() {
       // startRecording();
        Intent broadcastIntent = new Intent(this, BroadcastReceiver.class);
        Log.d("zzz","stopRecording");
        sendBroadcast(broadcastIntent);


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zzz", "ondestroy!");
        Intent broadcastIntent = new Intent(this,  BroadcastReceiver.class);
        sendBroadcast(broadcastIntent);

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // // // // // // // // // // // // // // // // Notification  // // // // // // // // // // // // // // //


    private void notifyToUserForForegroundService() {
        // On notification click
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder = getNotificationBuilder(this,
                CHANNEL_ID,
                NotificationManagerCompat.IMPORTANCE_LOW); //Low importance prevent visual appearance for this notification channel on top

        notificationBuilder.setContentIntent(pendingIntent) // Open activity
                .setOngoing(true)
                .setSmallIcon(R.drawable.contacts)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                .setContentTitle("App in progress")
                .setContentText("Content");

        Notification notification = notificationBuilder.build();

        startForeground(NOTIFICATION_ID, notification);

        if (NOTIFICATION_ID != lastShownNotificationId) {
            // Cancel previous notification
            final NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
            notificationManager.cancel(lastShownNotificationId);
        }
        lastShownNotificationId = NOTIFICATION_ID;
    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        return builder;
    }

    @TargetApi(26)
    private static void prepareChannel(Context context, String id, int importance) {
        final String appName = context.getString(R.string.app_name);
        String notifications_channel_description = "Cycling map channel";
        String description = notifications_channel_description;
        final NotificationManager nm = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        if(nm != null) {
            NotificationChannel nChannel = nm.getNotificationChannel(id);

            if (nChannel == null) {
                nChannel = new NotificationChannel(id, appName, importance);
                nChannel.setDescription(description);

                // from another answer
                nChannel.enableLights(true);
                nChannel.setLightColor(Color.BLUE);

                nm.createNotificationChannel(nChannel);
            }
        }
    }
}


