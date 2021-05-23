package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Viewstaff extends AppCompatActivity {
    ListView lvDiscussion;
    List<Staff> staff;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference, dbr, dbr1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstaff);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        lvDiscussion = findViewById(R.id.listview);
        staff = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Staff");

        lvDiscussion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Staff staff1 = staff.get(position);
                Showdelete(staff1.getId(), staff1.getUsername(), staff1.getStaffname());
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                staff.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Staff aa = snapshot.getValue(Staff.class);
                    staff.add(aa);
                }
                Customlist customlist = new Customlist(Viewstaff.this, staff);
                lvDiscussion.setAdapter(customlist);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void Showdelete(final String id, final String username, final String name) {
        Context context;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_button, null);
        dialogBuilder.setView(dialogView);

        final Button button = (Button) dialogView.findViewById(R.id.btndelete);
        final AlertDialog b = dialogBuilder.create();
        //b.getWindow();
        b.setTitle("Delete Staff");
        b.setMessage("Are you sure that you want to delete this " + name);
        b.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletestaff(id, username);
                b.dismiss();
            }
        });
    }

    private boolean deletestaff(String id, String username) {
        dbr = FirebaseDatabase.getInstance().getReference().child("Staff").child(id);
        dbr.removeValue();
        dbr1 = FirebaseDatabase.getInstance().getReference().child("StaffRegistration").child(username);
        dbr1.removeValue();
        return true;
    }
}
