package com.example.whoiscalling.Activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whoiscalling.Adapter.Adapter_Contacts;
import com.example.whoiscalling.Adapter.Adapter_NamesToUser;
import com.example.whoiscalling.Class.Contact;
import com.example.whoiscalling.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity  {
    private Intent intent;
    private ArrayList<Contact> contacts;
    private RecyclerView contactsActivity_LST_contacts;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.d("zzz","9999");
        setContentView(R.layout.activity_contacts);
        getData();
        contactsActivity_LST_contacts = findViewById(R.id.contactsActivity_LST_contacts);
        contactsActivity_LST_contacts.setLayoutManager(new LinearLayoutManager(this));
        Adapter_Contacts adapter_contacts = new Adapter_Contacts(this,contacts);
        contactsActivity_LST_contacts.setAdapter(adapter_contacts);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void getData() {
        intent = getIntent();
        Gson gson = new Gson();
        String stringContacts = intent.getStringExtra("contact");
        contacts = gson.fromJson(stringContacts,new TypeToken<ArrayList<Contact>>(){}.getType());

    }
}
