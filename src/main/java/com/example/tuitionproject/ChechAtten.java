package com.example.tuitioproject;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class ChechAtten extends AppCompatActivity {
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    ArrayList<String> selectedItems = new ArrayList<>();
    ListView listView;
    Button bt, shw;
    Spinner std, div;
    String item = null, item1 = null, phonenumber;
    String[] stdd = {"select_std", "5", "6", "7", "8", "9", "10"};
    String[] divv = {"select_div", "a", "b", "c", "d"};
    ListView lvDiscussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayList<String> listConversation2 = new ArrayList<String>();
    ArrayList<String> listConversation3 = new ArrayList<String>();
    ArrayList<String> listConversation4 = new ArrayList<String>();
    String[] listConversation1 = new String[]{};
    ArrayAdapter<String> arrayAdpt, aa, aaa;
    FirebaseAuth firebaseAuth;
    String user_msg_key, standard, division, msg;
    String code;
    DatabaseReference dbr, dbr1, dbr2, dbr3, dbr4, dbr5, dbr6;
    student student1, student2, student3;
    TextView date1;
    String mnth, yr, dd;
    final Calendar myCalendar = Calendar.getInstance();
    ProgressDialog progressDialog;
    int day, totalpresent;
    Average average;
    CheckBox checkedTextView;

    boolean[] checkeditems;
    DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chech_atten);
        date1 = findViewById(R.id.date);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching");
        standard = getIntent().getExtras().get("std").toString();
        division = getIntent().getExtras().get("div").toString();
        progressDialog.setCancelable(false);
        dbr2 = FirebaseDatabase.getInstance().getReference();
        shw = findViewById(R.id.show);

        checkedTextView = findViewById(R.id.check1);

        firebaseAuth = FirebaseAuth.getInstance();

        arrayAdpt = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listConversation2);
        progressDialog.show();
        dbr = FirebaseDatabase.getInstance().getReference().child("StudentRegistrationList").child(standard).child(division);
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateConversation(dataSnapshot);
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateConversation(dataSnapshot);
                progressDialog.dismiss();
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
        // listView.setAdapter(arrayAdpt);
        progressDialog.dismiss();


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
        date1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ChechAtten.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        shw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progressDialog.show();
                dbr4 = FirebaseDatabase.getInstance().getReference();

                if (date1.getText().toString().equals("SelectDate")) {
                    Toast.makeText(ChechAtten.this, "Please select date", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    dbr4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("StudentAttendancePresent").child(date1.getText().toString().trim()).child(standard).hasChild(division)) {
                                Toast.makeText(ChechAtten.this, "Attendace on date " + date1.getText().toString().trim() + " is already taken for Division " + division + " of Standard " + standard, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                listConversation3.clear();
                                listConversation1 = new String[listConversation2.size()];
                                listConversation1 = listConversation2.toArray(listConversation1);


                                checkeditems = new boolean[listConversation1.length];
                                final AlertDialog.Builder builder = new AlertDialog.Builder(ChechAtten.this);
                                builder.setTitle("Students List");
                                builder.setMultiChoiceItems(listConversation1, checkeditems, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        String abc = listConversation2.get(which);
                                        if (isChecked) {

                                            listConversation3.add(abc);


                                        } else {

                                            listConversation3.remove(abc);


                                        }


                                    }
                                });

                                builder.setCancelable(false);

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                           /* String item = "";
                            for (int i = 0; i < listConversation3.size(); i++) {
                                item = listConversation1[listConversation3.get(i)];
                                listConversation4.add(item);
                            }*/
                                        showSelectedItems(v);
                                        // showmsg(v);
                                        for (int i = 0; i < listConversation3.size(); i++) {
                                            item1 = listConversation3.get(i);

                                            if (dataSnapshot.child("StudentRegistration").child(standard).child(division).hasChild(item1)) {
                                                phonenumber = dataSnapshot.child("StudentRegistration").child(standard).child(division).child(item1).child("contact").getValue().toString().trim();
                                                msg = "date: " + date1.getText().toString() + "\n" + "Your Child " + item1 + " is Absent in school";
                                                SmsManager smsManager = SmsManager.getDefault();
                                                smsManager.sendTextMessage(phonenumber, null, msg, null, null);
                                                Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
                                                //Toast.makeText(ChechAtten.this, ""+msg, Toast.LENGTH_SHORT).show();


                                                //Toast.makeText(ChechAtten.this, ""+phonenumber, Toast.LENGTH_LONG).show();
                                            } else {
                                                // Toast.makeText(ChechAtten.this, "invalid", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        Intent i = new Intent(getApplicationContext(), StaffAccess.class);

                                        startActivity(i);
                                        listConversation3.clear();
                                        listConversation2.clear();
                                        dialog.dismiss();

                                        // Toast.makeText(ChechAtten.this, ""+listConversation4, Toast.LENGTH_SHORT).show();
                                    }

                                });
                                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }

    public void showSelectedItems(View view) {

        String itemes = "";

        for (int j = 0; j < listConversation2.size(); j++) {
            item = listConversation2.get(j);
            if (listConversation3.contains(item)) {
                dbr1 = FirebaseDatabase.getInstance().getReference().child("StudentAttendanceAbsent").child(date1.getText().toString().trim()).child(standard).child(division).push();
                student1 = new student("name:-" + item + "\n" + "absenteeism:- Absent");
                dbr1.setValue(student1);
                dbr3 = FirebaseDatabase.getInstance().getReference().child("Report").child(item).child(mnth + yr).child(dd + ":A").push();
                student2 = new student(dd + ":A");
                dbr3.setValue(student2);
                dbr5 = FirebaseDatabase.getInstance().getReference().child("ShowReport").child(item).child(mnth + yr).push();
                student3 = new student("A");
                dbr5.setValue(student3);


            } else {
                dbr1 = FirebaseDatabase.getInstance().getReference().child("StudentAttendancePresent").child(date1.getText().toString().trim()).child(standard).child(division).push();
                student1 = new student("name:-" + item + "\n" + "absenteeism:- Present");
                dbr1.setValue(student1);
                dbr3 = FirebaseDatabase.getInstance().getReference().child("Report").child(item).child(mnth + yr).child(dd + ":P").push();
                student2 = new student(dd + ":P");
                dbr3.setValue(student2);
                dbr5 = FirebaseDatabase.getInstance().getReference().child("ShowReport").child(item).child(mnth + yr).push();
                student3 = new student("P");
                dbr5.setValue(student3);


            }
        }
        //     itemes += ":" + item + "\n";
        // Toast.makeText(this, "You Have Selected" + itemes, Toast.LENGTH_SHORT).show();
    }

    public void showmsg(View view) {
        dbr4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        String myFormat1 = "MM";
        String myFormat3 = "dd";
        String myFormat2 = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        SimpleDateFormat sdf3 = new SimpleDateFormat(myFormat3, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
        date1.setText(sdf.format(myCalendar.getTime()));
        mnth = sdf1.format(myCalendar.getTime());
        yr = sdf2.format(myCalendar.getTime());
        dd = sdf3.format(myCalendar.getTime());
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
}
