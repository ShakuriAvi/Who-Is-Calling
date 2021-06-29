package com.example.whoiscalling.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class Adapter_NamesToUser extends RecyclerView.Adapter<Adapter_NamesToUser.MyViewHolderNames> {
    private Map<String, List<Contact>> hashMapNames;
    private Context context;
    private List<String> names;

    // data is passed into the constructor
    public Adapter_NamesToUser(Context context, Map<String, List<Contact>> hashMapNames ) {
        this.context = context;
        this.hashMapNames = hashMapNames;
        Log.d("bbb","6666");
        names = new ArrayList<String>(this.hashMapNames.keySet());
        Log.d("bbb", String.valueOf(hashMapNames.size()));
    }

    // inflates the row layout from xml when needed
    @Override
    public MyViewHolderNames onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_name, parent, false);
        return new Adapter_NamesToUser.MyViewHolderNames(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderNames holder, int position) {
        List<Contact> contacts = hashMapNames.get(names.get(position));
     //   for (int i = 0; i <contacts.size() ; i++) {
     //       Log.d("bbb", contacts.get(i).getNickName());
     //   }

        holder.recycleName_TXT_names.setText(names.get(position));
        holder.recycleName_TXT_counts.setText(String.valueOf(contacts.size()));
        holder.recycleName_BTN_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( context, ContactsActivity.class);
                Gson gson = new Gson();
                String stringContacts = gson.toJson(contacts);
                i.putExtra("contact",stringContacts);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
              //  Log.d("aaaaa",context.toString());
                context.startActivity(i);
                //((Activity)  mInflater.getContext()).finish();
            }
        });
        //int id = (mInflater.getContext().getResources().getIdentifier(symbol, "drawable", (mInflater.getContext().getPackageName())));
        //Glide.with(mInflater.getContext()).load(id).into(holder.symbolTeams_IMG_symbol);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return hashMapNames.size();
    }


    // stores and recycles views as they are scrolled off screen


    // convenience method for getting data at click position
    List<Contact> getItem(String name) {
        return hashMapNames.get(name);
    }


    public class MyViewHolderNames extends RecyclerView.ViewHolder {


        MaterialTextView recycleName_TXT_names;
        TextView recycleName_TXT_counts;
        MaterialButton recycleName_BTN_show;


        MyViewHolderNames(View itemView) {
            super(itemView);

            recycleName_TXT_names = itemView.findViewById(R.id.recycleName_TXT_names);
            recycleName_TXT_counts = itemView.findViewById(R.id.recycleName_TXT_counts);
            recycleName_BTN_show = itemView.findViewById(R.id.recycleName_BTN_show);
        }
    }
}

