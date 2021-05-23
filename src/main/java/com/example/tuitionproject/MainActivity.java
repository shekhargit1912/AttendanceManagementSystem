package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2;
    TextView textView, accountlogin;
    Button b1;
    CardView cardView;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String user, pass, std, div;
    ProgressDialog dialog;
    Animation uptodown, downtoup;
    LinearLayout linearLayout;
    View v;

    Connectiondetect cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=findViewById(R.id.layout);
        cd=new Connectiondetect(this);
        v=findViewById(android.R.id.content);

        int per = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);

        e1 = findViewById(R.id.user);
        textView = findViewById(R.id.text);
        cardView = findViewById(R.id.crd);
        e2 = findViewById(R.id.pwd);
        b1 = findViewById(R.id.registration);
        accountlogin = findViewById(R.id.accountlogin);
        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        textView.setAnimation(uptodown);
        cardView.setAnimation(downtoup);
        accountlogin.setAnimation(uptodown);



        if (per == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }


        /*dialog.setCancelable(true);*/
        dialog.setMessage("Please Wait Login");
        firebaseDatabase = FirebaseDatabase.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cd.isConnected()) {
                    dialog.show();
                    if (e1.getText().toString().equals("admin") && e2.getText().toString().equals("admin123")) {
                        Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();
                        SharedPref sharedPref=new SharedPref(MainActivity.this);
                        sharedPref.setName("admin");
                        Intent intent = new Intent(MainActivity.this, Add.class);
                        startActivity(intent);
                        finish();
                        e1.setText("");
                        e2.setText("");
                        dialog.dismiss();
                    } else {
                        databaseReference = firebaseDatabase.getReference();
                        try {
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child("StaffRegistration").hasChild(e1.getText().toString().trim())) {
                                        user = dataSnapshot.child("StaffRegistration").child(e1.getText().toString().trim()).child("username").getValue().toString().trim();
                                        pass = dataSnapshot.child("StaffRegistration").child(e1.getText().toString().trim()).child("password").getValue().toString().trim();
                                        std = dataSnapshot.child("StaffRegistration").child(e1.getText().toString().trim()).child("allocatedStd").getValue().toString().trim();
                                        div = dataSnapshot.child("StaffRegistration").child(e1.getText().toString().trim()).child("allocatedDiv").getValue().toString().trim();
                                        if (e2.getText().toString().trim().equals(pass)) {
                                            Toast.makeText(MainActivity.this, "Staff Login Successfull", Toast.LENGTH_SHORT).show();
                                            SharedPref sharedPref=new SharedPref(MainActivity.this);
                                            sharedPref.setName("staff");
                                            sharedPref.setDiv(div);
                                            sharedPref.setStd(std);
                                            Intent intent = new Intent(MainActivity.this, StaffAccess.class);
                                            intent.putExtra("std", std);
                                            intent.putExtra("div", div);
                                            startActivity(intent);
                                            finish();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "username not found", Toast.LENGTH_SHORT).show();
                                        e1.setError("Enter Empty Field..!");
                                        e2.setError("Enter Empty Field..!");
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
                else
                {
                    Snackbar snackbar= Snackbar.make(v,"No Internet Connection",Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getApplication().getResources().getColor(R.color.red));
                    snackbar.show();
                }
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

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
    private boolean havenetwork()
    {
        boolean have_WIFI=false;
        boolean have_MobileData=false;
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo=connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info:networkInfo)
        {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                have_MobileData=true;
        }
        return have_MobileData||have_WIFI;
    }

}
