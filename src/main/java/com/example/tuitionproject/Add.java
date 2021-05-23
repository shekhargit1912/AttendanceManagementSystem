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

public class Add extends AppCompatActivity {
    CardView addstaff, addstudent, viewstaff, viewstudent, list, viewattend, report, send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //addstudent = findViewById(R.id.addstudent);
        addstaff = findViewById(R.id.addstaff);
        viewstudent = findViewById(R.id.viewstudent);
        viewstaff = findViewById(R.id.viewstaff);
        viewattend = findViewById(R.id.viewattendance);
        report = findViewById(R.id.report);
        send = findViewById(R.id.marksend);

        //list = findViewById(R.id.list);

        /*addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, Addstudent.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });*/
        addstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, Addstaff.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slant, R.anim.slant);

            }
        });
        viewstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, ViewStudent.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        viewstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, Viewstaff.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);


            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, Report.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);

            }
        });
        /*list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, ChechAtten.class);
                intent.putExtra("code", "admin");
                startActivity(intent);
                overridePendingTransition(R.anim.slidedown, R.anim.slidedown);

            }
        });*/
        viewattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, ViewAttendance.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, reportpage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left);
            }
        });
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
            new SharedPref(Add.this).removeUser();
            Intent i = new Intent(Add.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if (id == R.id.aboutus) {
            Intent i = new Intent(Add.this, MainActivity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       /* Intent i = new Intent(Add.this, MainActivity.class);
        startActivity(i);
        finish();*/
    }
}
