package com.example.whoiscalling.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whoiscalling.Class.Contact;
import com.example.whoiscalling.Class.MyPhoneStateListener;
import com.example.whoiscalling.Class.MyScreenUtils;
import com.example.whoiscalling.MyService;
import com.example.whoiscalling.R;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.whoiscalling.Fragments.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.whoiscalling.MyService.START_FOREGROUND_SERVICE;
import static com.example.whoiscalling.MyService.STOP_FOREGROUND_SERVICE;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private static final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 0;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 2;
    private BottomNavigationView main_MENU_menu;
    private static final int PHONE_NUMBER_HINT = 100;
    private String name;
    private String phoneNumber;
    private Boolean save;
    private  SharedPreferences.Editor editor;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private  String data;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Contact contact;
    private static final int MY_PERMISSION_REQUEST_CODE_PHONE_STATE = 1;
    private Map<String, String> conatcts;
    Intent mServiceIntent;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d("bbbb", "here");
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        readSharedPreferences();

        contact = new Contact(phoneNumber, name, "");
        initView();
        if (phoneNumber != null && save == false) {
            conatcts = new HashMap<String, String>();
            getContacts();
            addContactToFirebase();
        }
        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }

        if (getApplicationContext().checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS},
                    MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS);
        }
        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG},
                    MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My notification","My notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        MyPhoneStateListener myPhoneStateListener =  new MyPhoneStateListener(this);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }



    private void writeSharedPreferences(boolean bool){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean("save", bool);
        editor.apply();
    }
    private void readSharedPreferences(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        phoneNumber = prefs.getString("phoneNumber", "No name defined");//"No name defined" is the default value.
        save = prefs.getBoolean("save", false);//"No name defined" is the default value.
        Log.d("bbb","hii - " + name +  " " + phoneNumber + " " + save);
    }
    private void addContactToFirebase(){
        database = FirebaseDatabase.getInstance("https://who-is-calling-39f4b-default-rtdb.firebaseio.com/");
        for (Map.Entry<String, String> con : conatcts.entrySet()) {
            Log.d("kkk","hii - " + name +  " " + phoneNumber+ " " +" "+con.getKey()  +" "+ con.getValue() );
            contact.setNickName(con.getKey());
            myRef = database.getReference(con.getValue());
            myRef.child(contact.getPhoneNumber()).setValue(contact);
        }
        writeSharedPreferences(true);

    }

    public void initView() {
        main_MENU_menu = findViewById(R.id.bottom_navigation);
      //  MenuItem item = findViewById(R.id.page_search);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragments()).commit();
      //  item.setIconTintList(ColorStateList.valueOf((Color.BLUE)));
        main_MENU_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.page_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragments()).commit();
                        item.setChecked(true);
//                        getActionBar().setTitle("Who Is Calling");
//                        getSupportActionBar().setTitle("Who Is Calling");
                        break;

                    case R.id.page_names:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NamesFragments()).commit();
                        item.setChecked(true);
//                        getActionBar().setTitle("My Names");
//                        getSupportActionBar().setTitle("My Names");
                        break;
                }
                return true;
            }
        });

        database = FirebaseDatabase.getInstance();

    }

    private void getContacts() {
        boolean isGranted = checkForPermission();

        if (!isGranted) {
            requestPermission();
            return;
        }

       data = "";
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String nickName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNo = validNumber(phoneNo);
                        if(phoneNo !=null) {
                            if (conatcts.containsKey(nickName) == false){
                                conatcts.put(nickName,phoneNo);
                            }
                        }
                        data += "\n" + nickName + ": " + phoneNo;

                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }

        Log.d("aaa", data);
    }
    private String validNumber(String numberPhone){

        String[] numbers = numberPhone.split("-");
        String temp = "";
        for (int i = 0; i <numbers.length ; i++) {
            Log.d("ppp","" +  numbers[i]);
            temp += numbers[i];
        }
        if(temp.length() > 8 && temp.charAt(0) == '0')
            return temp;
        else return null;

    }
    private static final int PERMISSION_CONTACTS_REQUEST_CODE = 123;
    private static final int MANUALLY_CONTACTS_PERMISSION_REQUEST_CODE = 124;

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                PERMISSION_CONTACTS_REQUEST_CODE);
    }

    private void requestPermissionWithRationaleCheck() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            Log.d("pttt", "shouldShowRequestPermissionRationale = true");
            // Show user description for what we need the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSION_CONTACTS_REQUEST_CODE);
        } else {
            Log.d("pttt", "shouldShowRequestPermissionRationale = false");
            openPermissionSettingDialog();
        }
    }

    private void openPermissionSettingDialog() {
        String message = "Setting screen if user have permanently disable the permission by clicking Don't ask again checkbox.";
        AlertDialog alertDialog =
                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .setPositiveButton(getString(android.R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, MANUALLY_CONTACTS_PERMISSION_REQUEST_CODE);
                                        dialog.cancel();
                                    }
                                }).show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MANUALLY_CONTACTS_PERMISSION_REQUEST_CODE) {
            getContacts();
        }
        if (requestCode == MY_PERMISSION_REQUEST_CODE_PHONE_STATE) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == PHONE_NUMBER_HINT && resultCode == RESULT_OK) {
            Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
             phoneNumber = credential.getId();
            Log.d("bbb",phoneNumber);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CONTACTS_REQUEST_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    requestPermissionWithRationaleCheck();
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }


    private boolean checkForPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;

    }



    @Override
    protected void onDestroy() {
        mServiceIntent = new Intent(this, MyService.class);
        mServiceIntent.setAction(START_FOREGROUND_SERVICE);
        startService(mServiceIntent);
        Log.i("zzz", "onDestroy!");
        super.onDestroy();

    }

}