package com.example.vudinhai.bt_sqlite;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vudinhai on 7/28/17.
 */

public class MoviesAdapter extends ArrayAdapter<Movies> {

    Context context;
    int layout;
    ArrayList<Movies> arrayList;

    public MoviesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Movies> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout,null);

        ImageView img = (ImageView)convertView.findViewById(R.id.image);
        TextView txt = (TextView)convertView.findViewById(R.id.text);

        txt.setText(arrayList.get(position).getTitle());
        Picasso.with(context).load(arrayList.get(position).getImage().replace("http","https")).into(img);


        return convertView;
    }
}
