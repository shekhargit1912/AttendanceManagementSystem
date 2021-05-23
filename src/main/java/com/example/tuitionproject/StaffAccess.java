package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class StaffAccess extends AppCompatActivity {
    CardView addstudent, viewstudent, getattendance, viewattendance, showreport, sendsms;
    String std, div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_access);
        addstudent = findViewById(R.id.addstudent);
        viewstudent = findViewById(R.id.viewstudent);
        getattendance = findViewById(R.id.getattendace);
        viewattendance = findViewById(R.id.viewattendance);
        showreport = findViewById(R.id.showreport);
        std = getIntent().getExtras().get("std").toString();
        div = getIntent().getExtras().get("div").toString();
        sendsms = findViewById(R.id.sendsms);
        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Addstudent.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slant, R.anim.slant);

            }
        });
        viewstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaffstudentView.class);
                intent.putExtra("std", std);
                intent.putExtra("div", div);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);
            }
        });
        getattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChechAtten.class);
                intent.putExtra("std", std);
                intent.putExtra("div", div);

                startActivity(intent);
                overridePendingTransition(R.anim.slidedown, R.anim.slidedown);

            }
        });
        viewattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaffViewAttendance.class);
                intent.putExtra("std", std);
                intent.putExtra("div", div);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);

            }
        });
        showreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Showreport.class);
                intent.putExtra("std", std);
                intent.putExtra("div", div);
                intent.putExtra("code", "staff");

                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);

            }
        });
       /* sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewAttendance.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);

            }
        });*/
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            new SharedPref(StaffAccess.this).removeUser();
            Intent i = new Intent(StaffAccess.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.aboutus) {
            Intent i = new Intent(StaffAccess.this, MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
      /*
        Intent i = new Intent(StaffAccess.this, MainActivity.class);
        startActivity(i);
        finishAffinity();
        finish();*/

    }
}
