package com.example.tuitioproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class Showreport extends AppCompatActivity {
    TextView dateTextView, start, end, present, absent, average, days;
    ListView l1, l2;
    Button b1, b2, b3;
    String dates[];
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayList<String> listConversation1 = new ArrayList<String>();
    ArrayList<String> listConversation2 = new ArrayList<String>();
    ArrayList<String> listConversation3 = new ArrayList<String>();
    ArrayList<String> listConversation4 = new ArrayList<String>();
    ArrayList<String> pp = new ArrayList<String>();
    ArrayList<String> aa = new ArrayList<String>();
    ArrayList<String> av = new ArrayList<String>();
    ArrayList<String> da = new ArrayList<String>();
    ArrayAdapter arrayAdpt, adapter, adapter1, adapter2;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String item1;


    String SelectedTopic, user_msg_key1, user_msg_key, mnth, yr, SelectedStd, SelectedDiv, code;
    DatabaseReference dbr, dbr1, databaseReference;
    final Calendar myCalendar = Calendar.getInstance();
    Calendar cStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showreport);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching");

        progressDialog.setCancelable(true);
        dateTextView = findViewById(R.id.month);
        l1 = findViewById(R.id.present);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);

        b1 = findViewById(R.id.yes);
        b2 = findViewById(R.id.analysis);
        b3 = findViewById(R.id.PDF);
        b2.setEnabled(false);
        b3.setEnabled(false);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        code = getIntent().getExtras().get("code").toString();
        if (code.equals("code")) {
            SelectedStd = getIntent().getExtras().get("selected_std").toString();
            SelectedDiv = getIntent().getExtras().get("selected_div").toString();
        } else {
            SelectedStd = getIntent().getExtras().get("std").toString();
            SelectedDiv = getIntent().getExtras().get("div").toString();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        arrayAdpt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation2);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation3);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listConversation4);
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

        dateTextView.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                listConversation3.clear();
                progressDialog.show();
                new DatePickerDialog(Showreport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dbr1 = FirebaseDatabase.getInstance().getReference().child("StudentRegistrationList").child(SelectedStd).child(SelectedDiv);
                dbr1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        updateConversation1(dataSnapshot);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        updateConversation1(dataSnapshot);
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


            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listConversation.clear();
                b2.setEnabled(true);


                //Toast.makeText(Showreport.this, ""+listConversation1 , Toast.LENGTH_LONG).show();


                progressDialog.show();
                if (dateTextView.getText().toString().equals("SelectMonth")) {
                    Toast.makeText(Showreport.this, "Select Month", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else {
                    for (int i = 0; i < listConversation3.size(); i++) {

                        final String item = listConversation3.get(i);
                        final StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("| ");
                        dbr = FirebaseDatabase.getInstance().getReference().child("Report").child(item).child(mnth + yr);
                        dbr.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String msg, conversation, abc = "| ";
                                Iterator i = dataSnapshot.getChildren().iterator();
                                while (i.hasNext()) {
                                    /*msg = (String) ((DataSnapshot) i.next()).getValue();
                                    conversation = msg;*/
                                    stringBuilder.append(((DataSnapshot) i.next()).getKey() + " | ");
                                    /*listConversation1.add(conversation);*/

                                }
                                da.add(stringBuilder.toString());

                                arrayAdpt.insert(item + " " + stringBuilder.toString(), arrayAdpt.getCount());
                                arrayAdpt.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    l1.setAdapter(arrayAdpt);
                    progressDialog.dismiss();
                    listConversation.clear();


                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3.setEnabled(true);
                progressDialog.show();
                listConversation4.clear();
                for (int i = 0; i < listConversation3.size(); i++) {
                    final int[] p = {0};
                    final int[] a = {0};

                    final String itemm = listConversation3.get(i);
                    final String li = listConversation.get(i);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("ShowReport").child(itemm).child(mnth + yr);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String n = (snapshot).getValue().toString();
                                if (n.equals("{name=P}")) {
                                    p[0] = p[0] + 1;
                                } else {
                                    a[0] = a[0] + 1;
                                }
                            }
                            pp.add("" + p[0]);
                            aa.add("" + a[0]);
                            DecimalFormat format = new DecimalFormat("##.##");
                            float day = p[0] + a[0];
                            float avg = (p[0] / day) * 100;
                            av.add("" + format.format(avg));
                            adapter2.insert(li + " Present=" + p[0] + " Absent=" + a[0] + " Average=" + format.format(avg) + "%", adapter2.getCount());
                            adapter2.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                l1.setAdapter(adapter2);
                progressDialog.dismiss();
                listConversation4.clear();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                PdfDocument pdfDocument = new PdfDocument();
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1500, 1000, 1).create();
                PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
                Paint paint = new Paint();
                int x = 10, y = 25;
                for (int i = 0; i < listConversation4.size(); i++) {
                    String name = listConversation4.get(i);
                    for (String line : name.split("\n")) {
                        myPage.getCanvas().drawText(line, x, y, paint);
                        y += paint.descent() - paint.ascent();
                    }

                }

                pdfDocument.finishPage(myPage);
                String path = Environment.getExternalStorageDirectory().getPath() + "/" + SelectedStd + SelectedDiv + mnth + yr + "Student.pdf";
                File file = new File(path);

                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(Showreport.this, "Pdf generated", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Showreport.this, "" + e, Toast.LENGTH_SHORT).show();
                }


                progressDialog.dismiss();


                pdfDocument.close();
                String path1 = Environment.getExternalStorageDirectory().getPath() + "/" + SelectedStd + SelectedDiv + mnth + yr + "Student.xls";
                File file1 = new File(path1);
                Workbook wb = new HSSFWorkbook();
                Cell cell = null;
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                Sheet sheet = null;
                sheet = wb.createSheet("Name of sheet");
                Row row1 = sheet.createRow(0);
                cell = row1.createCell(0);
                cell.setCellValue("Name");
                cell.setCellStyle(cellStyle);
                cell = row1.createCell(1);
                cell.setCellValue("Dates");
                cell.setCellStyle(cellStyle);
                cell = row1.createCell(2);
                cell.setCellValue("TotalPresent");
                cell.setCellStyle(cellStyle);
                cell = row1.createCell(3);
                cell.setCellValue("TotalAbsent");
                cell.setCellStyle(cellStyle);
                cell = row1.createCell(4);
                cell.setCellValue("Average");
                cell.setCellStyle(cellStyle);
                //Now column and row
                for (int i = 0; i < arrayAdpt.getCount(); i++) {
                    String name = listConversation3.get(i);
                    String dates = da.get(i);
                    String present = pp.get(i);
                    String absent = aa.get(i);
                    String avg = av.get(i);
                    i = i + 1;
                    Row row = sheet.createRow(i);
                    cell = row.createCell(0);
                    cell.setCellValue(name);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0, (10 * 200));
                    cell = row.createCell(1);
                    cell.setCellValue(dates);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0, (10 * 200));
                    cell = row.createCell(2);
                    cell.setCellValue(present);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0, (10 * 200));
                    cell = row.createCell(3);
                    cell.setCellValue(absent);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0, (10 * 200));
                    cell = row.createCell(4);
                    cell.setCellValue(avg);
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(0, (10 * 200));
                    i = i - 1;
                }
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file1);
                    wb.write(outputStream);

                    Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "NO OK", Toast.LENGTH_LONG).show();
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });

    }


    public void updateConversation1(DataSnapshot dataSnapshot) {
        String msg, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            msg = (String) ((DataSnapshot) i.next()).getValue();
            conversation = msg;
            adapter1.insert(conversation, adapter1.getCount());
            adapter1.notifyDataSetChanged();
        }
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM";
        String myFormat3 = "yyyy-MM-dd";
        String myFormat1 = "MM";
        String myFormat2 = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf3 = new SimpleDateFormat(myFormat3, Locale.US);
        SimpleDateFormat sdf4 = new SimpleDateFormat(myFormat3, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
        dateTextView.setText(sdf.format(myCalendar.getTime()));

        mnth = sdf1.format(myCalendar.getTime());
        yr = sdf2.format(myCalendar.getTime());
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!code.equals("code"))
        {
            Intent i=new Intent(getApplicationContext(),StaffAccess.class);

            i.putExtra("std",SelectedStd);
            i.putExtra("div",SelectedDiv);
        }
    }*/
}
