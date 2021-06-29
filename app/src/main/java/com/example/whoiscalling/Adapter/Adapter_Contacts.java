package com.example.whoiscalling.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whoiscalling.Activity.ContactsActivity;
import com.example.whoiscalling.Class.Contact;
import com.example.whoiscalling.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Adapter_Contacts extends RecyclerView.Adapter< Adapter_Contacts.MyViewHolderContacts> {
    private LayoutInflater mInflater;
    List<Contact> contacts;

    // data is passed into the constructor
    public Adapter_Contacts(Context context,  List<Contact> contacts ) {
        this.mInflater = LayoutInflater.from(context);
        this.contacts = contacts;
    }

    // inflates the row layout from xml when needed
    @Override
    public  Adapter_Contacts.MyViewHolderContacts onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_contacts, parent, false);

        return new Adapter_Contacts.MyViewHolderContacts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Contacts.MyViewHolderContacts holder, int position) {
        holder.recycleContacts_TXT_names.setText(contacts.get(position).getNameOfMember());
        holder.recycleContacts_TXT_numbers.setText(contacts.get(position).getPhoneNumber());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    // stores and recycles views as they are scrolled off screen


    // convenience method for getting data at click position
    Contact getItem(int place) {
        return contacts.get(place);
    }


    public class MyViewHolderContacts extends RecyclerView.ViewHolder {


        MaterialTextView recycleContacts_TXT_names;
        TextView recycleContacts_TXT_numbers;



        MyViewHolderContacts(View itemView) {
            super(itemView);

            recycleContacts_TXT_names = itemView.findViewById(R.id.recycleContacts_TXT_names);
            recycleContacts_TXT_numbers = itemView.findViewById(R.id.recycleContacts_TXT_numbers);

        }
    }
}
