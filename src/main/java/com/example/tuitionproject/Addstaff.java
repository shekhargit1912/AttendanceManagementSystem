package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Addstaff extends AppCompatActivity {
    EditText staffname, staffcontact, staffaddress, staffquali, usen, pswd, staffstd, staffdiv;
    Button regist, cancel;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Staff staff1, staff2;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;
    String msg,phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstaff);
        staffname = findViewById(R.id.staffname);
        staffcontact = findViewById(R.id.staffmobile);
        staffaddress = findViewById(R.id.staffaddress);
        usen = findViewById(R.id.username);
        pswd = findViewById(R.id.password);
        staffquali = findViewById(R.id.staffquali);
        staffstd = findViewById(R.id.staffstd);
        staffdiv = findViewById(R.id.staffdiv);
        regist = findViewById(R.id.register);
        cancel = findViewById(R.id.cancel);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addstaff.this, Add.class);
                startActivity(intent);
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    sendData();
                    Toast.makeText(Addstaff.this, "registration successful", Toast.LENGTH_SHORT).show();
                    staffname.getText().clear();
                    staffcontact.getText().clear();
                    staffaddress.getText().clear();
                    staffquali.getText().clear();
                    usen.getText().clear();
                    pswd.getText().clear();
                    staffstd.getText().clear();
                    staffdiv.getText().clear();
                } else {
                    Toast.makeText(Addstaff.this, "registration Failed", Toast.LENGTH_SHORT).show();
                    staffname.getText().clear();
                    staffcontact.getText().clear();
                    staffaddress.getText().clear();
                    staffquali.getText().clear();
                    usen.getText().clear();
                    pswd.getText().clear();
                    staffstd.getText().clear();
                    staffdiv.getText().clear();
                }
            }
        });
    }

    private boolean validate() {
        boolean result = false;
        String staffname1 = staffname.getText().toString();
        String staffcontact1 = staffcontact.getText().toString();
        String staffaddress1 = staffaddress.getText().toString();
        String staffquali1 = staffquali.getText().toString();
        String usern = usen.getText().toString();
        String pwd = pswd.getText().toString();
        String staffst = staffstd.getText().toString();
        String staffdi = staffdiv.getText().toString();
        if (staffname1.isEmpty() || staffcontact1.isEmpty() || staffaddress1.isEmpty() || staffquali1.isEmpty() || usern.isEmpty() || pwd.isEmpty() || staffst.isEmpty() || staffdi.isEmpty()) {
            Toast.makeText(this, "enter empty fields", Toast.LENGTH_SHORT).show();
            staffname.setError("Enter Empty Field..!");
            staffcontact.setError("Enter Empty Field..!");
            staffaddress.setError("Enter Empty Field..!");
            staffquali.setError("Enter Empty Field..!");
            usen.setError("Enter Empty Field..!");
            pswd.setError("Enter Empty Field..!");
            staffstd.setError("Enter Empty Field..!");
            staffdiv.setError("Enter Empty Field..!");

        } else {
            result = true;
        }
        return result;
    }

    public void sendData() {
        databaseReference = firebaseDatabase.getReference().child("StaffRegistration").child(usen.getText().toString().trim());
        String id = databaseReference.push().getKey();

        staff1 = new Staff(id, staffname.getText().toString().trim(), staffcontact.getText().toString().trim(), staffaddress.getText().toString().trim(), staffquali.getText().toString().trim(), usen.getText().toString().trim(), pswd.getText().toString().trim(), staffstd.getText().toString().trim(), staffdiv.getText().toString().trim());
        staff2 = new Staff(id, "Staffname:-" + staffname.getText().toString().trim(), "StaffContact:-" + staffcontact.getText().toString().trim(), "StaffAddress:-" + staffaddress.getText().toString().trim(), "StaffQualification:-" + staffquali.getText().toString().trim(), usen.getText().toString().trim(), pswd.getText().toString().trim(), "AllocatedStandard:-" + staffstd.getText().toString().trim(), "AllocatedDivision:-" + staffdiv.getText().toString().trim());
        databaseReference.setValue(staff1);

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Staff");
        databaseReference2.child(id).setValue(staff2);

        phonenumber = staffcontact.getText().toString().trim();
        msg = "SMVK details for login \n username: " + usen.getText().toString().trim() + "\n" + "Password: " + pswd.getText().toString().trim() ;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phonenumber, null, msg, null, null);
        Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
    }


}
