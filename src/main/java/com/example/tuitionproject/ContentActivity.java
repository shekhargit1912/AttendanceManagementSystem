package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ContentActivity extends AppCompatActivity {
    ListView lvDiscussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;
    FirebaseAuth firebaseAuth;
    List<student> staff;
    String stdd, SelectedTopic, user_msg_key;
    ProgressDialog progressDialog;

    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        firebaseAuth = FirebaseAuth.getInstance();

        lvDiscussion = (ListView) findViewById(R.id.lvConversation);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        staff = new ArrayList<>();
        int perr= ContextCompat.checkSelfPermission(ContentActivity.this, Manifest.permission.CALL_PHONE);


        if (perr == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(ContentActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        }



        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        stdd = getIntent().getExtras().get("selected_std").toString();
        setTitle("Topic : " + SelectedTopic);
        dbr = FirebaseDatabase.getInstance().getReference().child("Student").child(stdd).child(SelectedTopic);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                staff.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    student aa = snapshot.getValue(student.class);
                    staff.add(aa);
                }
                Customstudent customlist = new Customstudent(ContentActivity.this, staff);
                lvDiscussion.setAdapter(customlist);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You dont have the permission,Please click allow to access the application", Toast.LENGTH_LONG).show();
                }
                break;


        }

    }


}
