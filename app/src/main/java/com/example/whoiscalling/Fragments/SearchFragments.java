package com.example.whoiscalling.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.whoiscalling.Class.Contact;
import com.example.whoiscalling.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragments  extends Fragment {
    private ArrayList<MaterialButton> buttons = new ArrayList<MaterialButton>();
    private ImageButton fs_BTN_search;
    private Button fs_BTN_del;
    private DatabaseReference reference;
    private TextInputEditText fs_TextInputEditText_phoneNumber;
    private Contact contact;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        fs_TextInputEditText_phoneNumber = view.findViewById(R.id.fs_TextInputEditText_phoneNumber);
        clickOnSearch();

        return view;
    }

    private void clickOnSearch() {
        fs_BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance("https://who-is-calling-39f4b-default-rtdb.firebaseio.com/").getReference(fs_TextInputEditText_phoneNumber.getText().toString());
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
                        showDialog();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("ooo","error");
                    }
                });

            }
        });

    }
    private void showDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);
        ImageView dialog_IMV_statusIMV = dialog.findViewById(R.id.dialog_IMV_statusIMV);
        MaterialTextView dialog_TXT_Name = dialog.findViewById(R.id.dialog_TXT_Name);
        MaterialButton dialog_BTN_close = dialog.findViewById(R.id.dialog_BTN_close);
        Log.d("ooo", String.valueOf(contact) + "" +contact.getNameOfMember());
        if(contact !=null && contact.getNameOfMember()!= null)
            dialog_TXT_Name.setText(contact.getNickName());
        else{
            dialog_IMV_statusIMV.setImageResource(R.drawable.zoom   );
            dialog_TXT_Name.setText("Not Fount Contact");
        }

        dialog_BTN_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fs_TextInputEditText_phoneNumber.setText("");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void initView(View view){
        buttons = insertNumberButtonToArray(view);
        fs_BTN_search = view.findViewById(R.id.fs_BTN_search);
        fs_BTN_del = view.findViewById(R.id.fs_BTN_del);
        fs_BTN_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fs_TextInputEditText_phoneNumber.getText().length()!=0)
                    fs_TextInputEditText_phoneNumber.setText("" + fs_TextInputEditText_phoneNumber.getText().toString().substring(0, fs_TextInputEditText_phoneNumber.getText().length() - 1));


            }
        });
    }

    public void onKeyboardClick(View v){
        fs_TextInputEditText_phoneNumber.setText(""+fs_TextInputEditText_phoneNumber.getText() + ((MaterialButton)v).getText());
    }

    private ArrayList<MaterialButton> insertNumberButtonToArray(View view) {
        ArrayList<MaterialButton> newButtons = new ArrayList<MaterialButton>();

        for (int i = 0 ; i < 10 ; i++) {
            String name = "btn" + (i);
            int id = getResources().getIdentifier(name, "id", getActivity().getPackageName());
            newButtons.add(view.findViewById(id));
            view.findViewById(id).setOnClickListener(this::onKeyboardClick);
        }
            newButtons.add(view.findViewById(R.id.btnasterisk));
        newButtons.add(view.findViewById(R.id.btnLadder));
        view.findViewById(R.id.btnasterisk).setOnClickListener(this::onKeyboardClick);
        view.findViewById(R.id.btnLadder).setOnClickListener(this::onKeyboardClick);
        return newButtons;
    }
}
