package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class ViewAttendance extends AppCompatActivity {
    Spinner std, div;
    String[] stdd = {"select_std", "5", "6", "7", "8", "9", "10"};
    String[] divv1 = {"select_div", "a", "b", "c", "d"};
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
        setContentView(R.layout.activity_view_attendance);
        std = findViewById(R.id.std);
        div = findViewById(R.id.div);
        datePicker = findViewById(R.id.date);
        s = findViewById(R.id.showattend);
        s1 = findViewById(R.id.showabsent);
        listView = findViewById(R.id.listview1);

        firebaseAuth = FirebaseAuth.getInstance();
        ArrayAdapter aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stdd);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        std.setAdapter(aa);
        ArrayAdapter aa1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, divv1);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        div.setAdapter(aa1);
        dialog = new ProgressDialog(this);
        /*dialog.setCancelable(true);*/
        dialog.setMessage("Fetching");

        arrayAdpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation);


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
                new DatePickerDialog(ViewAttendance.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listConversation.clear();
                dialog.show();
                if (std.getSelectedItem().equals("select_std") || div.getSelectedItem().equals("select_div") || datePicker.getText().equals("SelectDate")) {
                    Toast.makeText(getApplicationContext(), "select standard or division", Toast.LENGTH_SHORT).show();
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
                if (std.getSelectedItem().equals("select_std") || div.getSelectedItem().equals("select_div") || datePicker.getText().equals("SelectDate")) {
                    Toast.makeText(getApplicationContext(), "select standard or division", Toast.LENGTH_SHORT).show();
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

}
