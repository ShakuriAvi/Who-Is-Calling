package com.example.whoiscalling.Activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whoiscalling.MyService;
import com.example.whoiscalling.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.whoiscalling.MyService.START_FOREGROUND_SERVICE;
import static com.example.whoiscalling.MyService.STOP_FOREGROUND_SERVICE;

public class StartActivity extends AppCompatActivity {


    private  SharedPreferences.Editor editor;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private MaterialButton login_BTN_start;
    private ImageView home_IMV_contactImage;
    private TextInputLayout login_EDT_Name;
    private TextInputLayout login_EDT_Phone;
    private String name;
    private Intent intent ;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        showStart();
    }
    private void writeSharedPreferences(String name,String number){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        editor.putString("name", name);
        editor.putString("phoneNumber", number);
        editor.apply();
    }
    private void readSharedPreferences(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
       // SharedPreferences.Editor editor = prefs.edit();
       // editor.clear().commit();
        name = prefs.getString("name", "No name defined");//"No name defined" is the default value.

    }
    private void showStart() {
        Log.d("bbbb","here0");
        readSharedPreferences();
        Log.d("bbbb","here0.5");
        initHomeView();
        //view = R.layout.home_view;
        Log.d("bbbb","here1");
        if (!name.equals("No name defined")) {
            login_BTN_start.setVisibility(View.GONE);
            login_EDT_Name.setVisibility(View.GONE);
            login_EDT_Phone.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Path path = new Path();
                path.arcTo(0f, 0f, 1000f, 1000f, 270f, -170f, true);
                ObjectAnimator animation = ObjectAnimator.ofFloat(home_IMV_contactImage, View.X, View.Y, path);
                animation.setDuration(2000);
                animation.start();
            }

            Log.d("bbbb","here1.5");
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    intent = new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);
                }

            };
            thread.start();

        }   else {

            login_BTN_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ttt", String.valueOf(login_EDT_Name.getEditText().getText().toString().length()));
                    if(login_EDT_Name.getEditText().getText().toString().length() > 2 &&  validNumber(login_EDT_Phone.getEditText().getText().toString())!=null) {

                        writeSharedPreferences(login_EDT_Name.getEditText().getText().toString(),validNumber(login_EDT_Phone.getEditText().getText().toString()));

                    intent = new Intent(v.getContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    }else{
                        Toast.makeText(v.getContext(), " your Name is not valid", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
    private String validNumber(String numberPhone){
        String[] numbers = numberPhone.split("-");
        String temp = "";
        for (int i = 0; i <numbers.length ; i++) {
            temp += numbers[i];
        }
        Log.d("ppp","" + temp);
        if(temp.length() >8)
            return temp;
        else {
            Toast.makeText(this, " your phone is not valid",
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }
    private void initHomeView() {
        login_BTN_start = findViewById(R.id.login_BTN_start);
        login_EDT_Name = findViewById(R.id.login_EDT_Name);
        login_EDT_Phone = findViewById(R.id.login_EDT_Phone);
        home_IMV_contactImage = findViewById(R.id.home_IMV_contactImage);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Intent intent = new Intent(this, MyService.class);
//        intent.setAction(STOP_FOREGROUND_SERVICE);
//        //if(AutoStartPermissionHelper.getInstance().getAutoStartPermission(this))
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        } else {
//            startService(new Intent(this, MyService.class));
//        }
//    }
}
