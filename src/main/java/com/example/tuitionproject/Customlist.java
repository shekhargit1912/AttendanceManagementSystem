package com.example.tuitioproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Customlist extends ArrayAdapter<Staff> {
    private Activity context;
    List<Staff> staff;

    public Customlist(Activity context, List<Staff> staff) {
        super(context, R.layout.custom, staff);
        this.context = context;
        this.staff = staff;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.custom, null, true);

        TextView Sname = (TextView) listviewitem.findViewById(R.id.sname);
        TextView Scontact = (TextView) listviewitem.findViewById(R.id.scontact);
        TextView Saddress = (TextView) listviewitem.findViewById(R.id.saddress);
        TextView Squalification = (TextView) listviewitem.findViewById(R.id.squal);
        TextView Sstd = (TextView) listviewitem.findViewById(R.id.allstd);
        TextView Sdiv = (TextView) listviewitem.findViewById(R.id.alldiv);

        Staff staf = staff.get(position);

        Sname.setText(staf.getStaffname());
        Scontact.setText(staf.getStaffcontact());
        Saddress.setText(staf.getStaffadrress());
        Squalification.setText(staf.getStaffqulification());
        Sstd.setText(staf.getAllocatedStd());
        Sdiv.setText(staf.getAllocatedDiv());

        return listviewitem;
    }
}
