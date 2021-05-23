package com.example.tuitioproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class StaffViewAttendance extends AppCompatActivity {
    Spinner std, div;

    TextView datePicker;
    Button s, s1;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;
    ListView listView;
    FirebaseAuth firebaseAuth;
    String user_msg_key, standard, division;
    private DatabaseReference dbr, dbr1;
    ProgressDialog dialog;


    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffview_attendance);

        datePicker = findViewById(R.id.date);
        s = findViewById(R.id.showattend);
        s1 = findViewById(R.id.showabsent);
        listView = findViewById(R.id.listview1);
        standard = getIntent().getExtras().get("std").toString();
        division = getIntent().getExtras().get("div").toString();
        firebaseAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        /*dialog.setCancelable(true);*/
        dialog.setMessage("Fetching");

        arrayAdpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        datePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(StaffViewAttendance.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listConversation.clear();
                dialog.show();
                if (datePicker.getText().equals("SelectDate")) {
                    Toast.makeText(getApplicationContext(), "select Date", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {

                    dbr = FirebaseDatabase.getInstance().getReference().child("StudentAttendancePresent").child(datePicker.getText().toString().trim()).child(standard).child(division);
                    /*Map<String, Object> map = new HashMap<String, Object>();
                    user_msg_key = dbr.push().getKey();
                    dbr.updateChildren(map);
                    DatabaseReference dbr2 = dbr.child(user_msg_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    dbr2.updateChildren(map2);*/

                    dbr.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            updateConversation(dataSnapshot);
                            dialog.dismiss();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            updateConversation(dataSnapshot);
                            dialog.dismiss();

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    listView.setAdapter(arrayAdpt);
                    listView.setBackgroundColor(Color.GREEN);


                }
            }
        });

        s1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listConversation.clear();
                dialog.show();
                if (datePicker.getText().equals("SelectDate")) {
                    Toast.makeText(getApplicationContext(), "selectdate", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    dbr1 = FirebaseDatabase.getInstance().getReference().child("StudentAttendanceAbsent").child(datePicker.getText().toString().trim()).child(standard).child(division);
                    dbr1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            updateConversation(dataSnapshot);
                            dialog.dismiss();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            updateConversation(dataSnapshot);
                            dialog.dismiss();

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    listView.setAdapter(arrayAdpt);
                    listView.setBackgroundResource(R.color.red);

                }
            }
        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datePicker.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateConversation(DataSnapshot dataSnapshot) {
        String msg, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            msg = (String) ((DataSnapshot) i.next()).getValue();
            conversation = msg;
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),StaffAccess.class);
        i.putExtra("std",standard);
        i.putExtra("div",division);
    }*/
}
