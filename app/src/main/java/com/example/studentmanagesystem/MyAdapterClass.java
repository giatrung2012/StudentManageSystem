package com.example.studentmanagesystem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterClass extends ArrayAdapter<Room> {
    ArrayList<Room> classList;

    public MyAdapterClass(@NonNull Context context, int resource, ArrayList<Room> objects) {
        super(context, resource, objects);
        classList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.my_class, null);
        TextView txtCodeClass = v.findViewById(R.id.txtclasscode);
        TextView txtNameclass = v.findViewById(R.id.txtclassname);
        TextView txtNumberClass = v.findViewById(R.id.txtclassnumber);

//        if (position == 0) {
//            txtCodeClass.setBackground(Color.WHITE);
//            txtNameclass.setBackground(Color.WHITE);
//            txtNumberClass.setBackground(Color.WHITE);
//        }

        txtCodeClass.setText(classList.get(position).getCode_class());
        txtNameclass.setText(classList.get(position).getName_class());
        txtNumberClass.setText(classList.get(position).getClass_number());
        return v;
    }
}
