package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Report extends AppCompatActivity {
    Spinner std, div;
    String[] stdd = {"select_std", "5", "6", "7", "8", "9", "10"};
    String[] divv1 = {"select_div", "a", "b", "c", "d"};
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;

    FirebaseAuth firebaseAuth;
    String user_msg_key, standard, division;
    private DatabaseReference dbr, dbr1;
    Button show;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching");

        progressDialog.setCancelable(false);
        setContentView(R.layout.activity_report);
        std = findViewById(R.id.std);
        div = findViewById(R.id.div);
        show = findViewById(R.id.showreport);
        ArrayAdapter aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stdd);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        std.setAdapter(aa);
        ArrayAdapter aa1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, divv1);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        div.setAdapter(aa1);
        firebaseAuth = FirebaseAuth.getInstance();

        std.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                standard = stdd[position].toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        div.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                division = divv1[position].toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listConversation.clear();
                progressDialog.show();
                if (std.getSelectedItem().equals("select_std") || div.getSelectedItem().equals("select_div")) {
                    Toast.makeText(getApplicationContext(), "select standard or division", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Intent i = new Intent(getApplicationContext(), Showreport.class);

                    i.putExtra("selected_std", standard);
                    i.putExtra("selected_div", division);
                    i.putExtra("code", "code");
                    startActivity(i);
                }
            }
        });
    }


}
