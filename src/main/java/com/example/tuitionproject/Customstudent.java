package com.example.tuitioproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Customstudent extends ArrayAdapter<student> {
    private Activity context;
    List<student> staff;

    public Customstudent(Activity context, List<student> staff) {
        super(context, R.layout.customstudent, staff);
        this.context = context;
        this.staff = staff;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.customstudent, null, true);

         final TextView Sname = (TextView) listviewitem.findViewById(R.id.stname);
         final TextView Sscontact = (TextView) listviewitem.findViewById(R.id.stcontact);
         final TextView Saddress = (TextView) listviewitem.findViewById(R.id.staddress);
        TextView Squalification = (TextView) listviewitem.findViewById(R.id.streg);
        TextView Sstd = (TextView) listviewitem.findViewById(R.id.stcast);
        TextView Sdiv = (TextView) listviewitem.findViewById(R.id.stdob);


        student staf = staff.get(position);

        Sname.setText(staf.getName());
        Sscontact.setText(staf.getContact());
        Saddress.setText(staf.getAddress());
        Squalification.setText(staf.getRegistorNo());
        Sstd.setText(staf.getCast());
        Sdiv.setText(staf.getDob());

        Sscontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Call");
                builder.setMessage("Do you want to call \n"+Sname.getText().toString().trim()+" parent");
                builder.setCancelable(false);
                builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:-"+Sscontact.getText().toString().trim()));
                        context.startActivity(i);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog builder1=builder.create();
                builder1.show();

            }
        });

        return listviewitem;
    }

}
