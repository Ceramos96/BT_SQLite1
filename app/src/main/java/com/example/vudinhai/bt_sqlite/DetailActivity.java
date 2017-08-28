package com.example.vudinhai.bt_sqlite;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView img;
    TextView txt1,txt2,txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        img = (ImageView)findViewById(R.id.image);
        txt1 = (TextView)findViewById(R.id.text);
        txt2 = (TextView)findViewById(R.id.text2);
        txt3 = (TextView)findViewById(R.id.text3);

        Intent intent = getIntent();
        Movies movies = (Movies) intent.getSerializableExtra("Movies");
        Picasso.with(DetailActivity.this)
                .load(movies.getImage().replace("http","https"))
                .into(img);
        txt1.setText(movies.getTitle());
        txt2.setText(movies.getRating()+"");
        txt3.setText(movies.getReleaseYear()+"");


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
