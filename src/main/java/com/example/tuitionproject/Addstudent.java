package com.example.tuitioproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Addstudent extends AppCompatActivity {
    EditText studentname, studentname1, studentname2, classs, contact, address, division, cast, registerno, dateofbirth;
    Button register, cancel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        studentname = findViewById(R.id.studentname);
        studentname1 = findViewById(R.id.studentname1);
        studentname2 = findViewById(R.id.studentname2);
        classs = findViewById(R.id.classs);
        division = findViewById(R.id.divv);
        dateofbirth = findViewById(R.id.dateofbirth);
        cast = findViewById(R.id.cast);
        registerno = findViewById(R.id.registerno);
        contact = findViewById(R.id.mobileno);
        address = findViewById(R.id.address);
        register = findViewById(R.id.register);

        cancel = findViewById(R.id.cancel);
        firebaseDatabase = FirebaseDatabase.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    sendData();
                    Toast.makeText(Addstudent.this, "registration successful", Toast.LENGTH_SHORT).show();
                    studentname.getText().clear();
                    studentname1.getText().clear();
                    studentname2.getText().clear();
                    classs.getText().clear();
                    division.getText().clear();
                    contact.getText().clear();
                    address.getText().clear();
                    cast.getText().clear();
                    registerno.getText().clear();
                    dateofbirth.getText().clear();

                } else {
                    Toast.makeText(Addstudent.this, "registration unsuccessful", Toast.LENGTH_SHORT).show();
                    studentname.getText().clear();
                    studentname1.getText().clear();
                    studentname2.getText().clear();
                    classs.getText().clear();
                    division.getText().clear();
                    contact.getText().clear();
                    address.getText().clear();
                    cast.getText().clear();
                    registerno.getText().clear();
                    dateofbirth.getText().clear();


                }

            }
        });


    }

    private boolean validate() {
        boolean result = false;
        String name = studentname.getText().toString();
        String name1 = studentname1.getText().toString();
        String name2 = studentname2.getText().toString();
        String std = classs.getText().toString();
        String number = contact.getText().toString();
        String addre = address.getText().toString();
        String renumber = address.getText().toString();
        String scast = cast.getText().toString();
        String sdate = dateofbirth.getText().toString();
        String regino = registerno.getText().toString();
        if (name.isEmpty() || name1.isEmpty() || name2.isEmpty() || std.isEmpty() || number.isEmpty() || addre.isEmpty() || renumber.isEmpty() || scast.isEmpty() || regino.isEmpty() || sdate.isEmpty()) {
            Toast.makeText(this, "enter empty fields", Toast.LENGTH_SHORT).show();
            studentname.setError("Enter Empty Field..!");
            studentname1.setError("Enter Empty Field..!");
            studentname2.setError("Enter Empty Field..!");
            classs.setError("Enter Empty Field..!");
            contact.setError("Enter Empty Field..!");
            address.setError("Enter Empty Field..!");
            division.setError("Enter Empty Field..!");
            cast.setError("Enter Empty Field..!");
            registerno.setError("Enter Empty Field..!");
            dateofbirth.setError("Enter Empty Field..!");

        } else {
            result = true;
        }
        return result;
    }

    public void sendData() {
        name = studentname.getText().toString().trim() + " " + studentname1.getText().toString().trim() + " " + studentname2.getText().toString().trim();
        databaseReference = firebaseDatabase.getReference().child("StudentRegistration").child(classs.getText().toString().trim()).child(division.getText().toString().trim()).child(name);
        databaseReference2 = firebaseDatabase.getReference().child("StudentRegistrationList").child(classs.getText().toString().trim()).child(division.getText().toString().trim()).push();
        databaseReference1 = firebaseDatabase.getReference().child("Student").child(classs.getText().toString().trim()).child(division.getText().toString().trim()).push();
        student stud = new student(name, classs.getText().toString().trim(), contact.getText().toString().trim(), address.getText().toString().trim(), division.getText().toString().trim(), cast.getText().toString().trim(), registerno.getText().toString().trim(), dateofbirth.getText().toString().trim());
        student stud1 = new student("Name:-" + name + "\n" + "RegisterNo:-" + registerno.getText().toString().trim() + "\n" + "Cast:-" + cast.getText().toString().trim() + "\n" + "D.O.B:-" + dateofbirth.getText().toString().trim() + "\n" + "Contact:-" + contact.getText().toString().trim() + "\n" + "Address:-" + address.getText().toString().trim());
        student stud2 = new student(name);
        databaseReference.setValue(stud);
        databaseReference1.setValue(stud);
        databaseReference2.setValue(stud2);
    }


}
