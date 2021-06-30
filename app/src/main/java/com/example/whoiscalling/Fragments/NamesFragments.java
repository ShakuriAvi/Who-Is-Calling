package com.example.whoiscalling.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.whoiscalling.Adapter.Adapter_NamesToUser;
import com.example.whoiscalling.Class.Contact;
import com.example.whoiscalling.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class NamesFragments extends Fragment {
    private View view;
    private DatabaseReference reference;
    private RecyclerView fragmentNames_LST_names;
    private String phoneNumber;
    private Map<String, List<Contact>> hashMapNames;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_names, container, false);
        readSharedPreferences();
        hashMapNames = new HashMap<String, List<Contact>>();
        loadNamesUserFromFB();
        return view;
    }

    private void loadNamesUserFromFB() {
        reference = FirebaseDatabase.getInstance("https://who-is-calling-39f4b-default-rtdb.firebaseio.com/").getReference(phoneNumber);
        Log.d("bbb","4444");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contact contact = snapshot.getValue(Contact.class);
                    List<Contact> contacts;
                    if(hashMapNames.containsKey(contact.getNickName())== false){
                        contacts = new ArrayList<>();
                    }else{
                        contacts = hashMapNames.get(contact.getNickName());
                    }
                    contacts.add(contact);
                    hashMapNames.put(contact.getNickName(),contacts) ;
                }
                Log.d("bbb","5555");
                fragmentNames_LST_names = view.findViewById(R.id.fragmentNames_LST_names);
                fragmentNames_LST_names.setLayoutManager(new LinearLayoutManager(getActivity()));
                Adapter_NamesToUser adapter_namesToUser = new Adapter_NamesToUser(getActivity(),hashMapNames);
                fragmentNames_LST_names.setAdapter(adapter_namesToUser);
                showDialog();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("zzz","cannot find in fb");
            }
        });

    }
    private void showDialog(){


        //Log.d("ooo", String.valueOf(contact) + "" +contact.getNameOfMember());
        if(hashMapNames.size() == 0){
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.layout_dialog);
            ImageView dialog_IMV_statusIMV = dialog.findViewById(R.id.dialog_IMV_statusIMV);
            MaterialTextView dialog_TXT_Name = dialog.findViewById(R.id.dialog_TXT_Name);
            MaterialButton dialog_BTN_close = dialog.findViewById(R.id.dialog_BTN_close);
            dialog_IMV_statusIMV.setImageResource(R.drawable.zoom   );
            dialog_TXT_Name.setText("Not Fount Contacts for this Number");
            dialog_BTN_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }


    }
    private void readSharedPreferences(){
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("phoneNumber", "No name defined");//"No name defined" is the default value.
    }
}
