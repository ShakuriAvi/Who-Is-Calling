package com.example.whoiscalling.Class;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.whoiscalling.Activity.MainActivity;
import com.example.whoiscalling.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class MyPhoneStateListener extends PhoneStateListener {
    private Context context;
    private DatabaseReference reference;
    private Contact contact;
    public MyPhoneStateListener(Context context) {
        super();
        this.context = context;

    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
    //    Log.d("zzz", "here " );
        super.onCallStateChanged(state, phoneNumber);
       if(state == TelephonyManager.CALL_STATE_RINGING) {
           Log.d("zzz", "phone number " + phoneNumber);
           reference = FirebaseDatabase.getInstance("https://who-is-calling-39f4b-default-rtdb.firebaseio.com/").getReference(phoneNumber);
           reference.addListenerForSingleValueEvent(new ValueEventListener() {

               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   boolean existName = false;
                   for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                       if(existName == true)
                           break;
                       else {
                           //  Log.d("ooo", String.valueOf(snapshot.getValue(Contact.class)));
                           contact = snapshot.getValue(Contact.class);
                           existName = true;
                       }
                   }
                   getCall(phoneNumber);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   Log.d("ooo","error");
               }
           });

       }
    }
    private void getCall( String number) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"My notification");
        builder.setContentTitle("WhoIsCalling");
        if(contact !=null && contact.getNameOfMember()!= null)
             builder.setContentText(contact.getNickName() + " PhoneNumber " + number);
        else
            builder.setContentText("Not Fount Contact");
        builder.setSmallIcon(R.drawable.contacts);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1,builder.build());

    }
}
