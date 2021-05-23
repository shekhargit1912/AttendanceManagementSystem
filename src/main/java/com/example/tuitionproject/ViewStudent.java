package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ViewStudent extends AppCompatActivity {
    ListView list;
    ArrayList<String> myList = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dbr, dbr1;
    ArrayAdapter<String> myAdapter;
    Spinner spinner;
    String[] std = {"select_std", "5", "6", "7", "8", "9", "10"};
    Button show;
    String stdd;
    TextView textView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        list = findViewById(R.id.listview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching");

        progressDialog.setCancelable(false);
        spinner = findViewById(R.id.spin);
        show = findViewById(R.id.show);
        textView = findViewById(R.id.txt);
        firebaseAuth = firebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        textView.setVisibility(View.INVISIBLE);
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        list.setAdapter(myAdapter);
        ArrayAdapter aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, std);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stdd = std[position].toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myList.clear();
                progressDialog.show();
                if (!spinner.getSelectedItem().equals("select_std")) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Student").hasChild(stdd)) {
                                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Student").child(stdd);

                                dbr.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Set<String> set = new HashSet<String>();
                                        Iterator i = dataSnapshot.getChildren().iterator();

                                        while (i.hasNext()) {
                                            set.add(((DataSnapshot) i.next()).getKey());

                                        }
                                        myAdapter.clear();
                                        myAdapter.addAll(set);
                                        myAdapter.notifyDataSetChanged();
                                        textView.setVisibility(View.VISIBLE);
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }


                                });
                            } else {
                                list.setAdapter(null);
                                progressDialog.dismiss();
                            }
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                                    i.putExtra("selected_topic", ((TextView) view).getText().toString());
                                    i.putExtra("selected_std", ((stdd)));
                                    startActivity(i);
                                }

                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(ViewStudent.this, "select std", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
