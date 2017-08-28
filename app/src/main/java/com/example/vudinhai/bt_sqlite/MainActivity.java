package com.example.vudinhai.bt_sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Movies> arrayList;
    ArrayList<Movies> results;
    MoviesAdapter moviesAdapter;
    DBHelper db;

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt =(TextView)findViewById(R.id.textView);

        db = new DBHelper(MainActivity.this);

        gridView = (GridView)findViewById(R.id.grid);
        arrayList = db.getAllMovies();
        results = db.getAllMovies();
        if(arrayList.size() > 0){
            txt.setVisibility(View.GONE);
        }
        moviesAdapter = new MoviesAdapter(MainActivity.this,
                                          R.layout.layout_cell,
                                          arrayList  );
        gridView.setAdapter(moviesAdapter);

        registerForContextMenu(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("Movies",arrayList.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Movies> tmp = new ArrayList<Movies>();
                for(Movies m : results){
                    if(m.getTitle().toLowerCase().contains(newText.toLowerCase())){
                        tmp.add(m);
                    }
                }

                if(tmp.size() > 0){
                    moviesAdapter.clear();
                    moviesAdapter.addAll(tmp);
                    moviesAdapter.notifyDataSetChanged();

                }else {
                    txt.setVisibility(View.VISIBLE);
//                    moviesAdapter.clear();
//                    moviesAdapter.addAll(results);
//                    moviesAdapter.notifyDataSetChanged();
                }
                if(newText.isEmpty()){
                    txt.setVisibility(View.GONE);
                    moviesAdapter.clear();
                    moviesAdapter.addAll(results);
                    moviesAdapter.notifyDataSetChanged();
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnuAdd){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Insert Movie");
            builder.setCancelable(false);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_input,null);
            builder.setView(view);
            final EditText edt1 = (EditText)view.findViewById(R.id.editText);
            final EditText edt2 = (EditText)view.findViewById(R.id.editText2);
            final EditText edt3 = (EditText)view.findViewById(R.id.editText3);
            final EditText edt4 = (EditText)view.findViewById(R.id.editText4);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String title = edt1.getText().toString();
                    String image = edt2.getText().toString();
                    float rating = 0.0f;
                    if(!edt3.getText().toString().isEmpty()) {
                        rating = Float.valueOf(edt3.getText().toString());
                    }
                    int releaseYear = 2010;
                    if(!edt4.getText().toString().isEmpty()) {
                        releaseYear = Integer.valueOf(edt4.getText().toString());
                    }
                    Movies movies = new Movies(title,image,rating,releaseYear);
                    db.inertMovies(movies);
                    results.clear();
                    results = db.getAllMovies();
//                    arrayList.add(movies);
                    moviesAdapter.clear();
                    moviesAdapter.addAll(db.getAllMovies());
                    moviesAdapter.notifyDataSetChanged();

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });



            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.mnuUpdate:
                final Movies movies1 = arrayList.get(info.position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update Movie");
                builder.setCancelable(false);

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_input,null);
                builder.setView(view);
                final EditText edt1 = (EditText)view.findViewById(R.id.editText);
                edt1.setText(movies1.getTitle());
                final EditText edt2 = (EditText)view.findViewById(R.id.editText2);
                edt2.setText(movies1.getImage());
                final EditText edt3 = (EditText)view.findViewById(R.id.editText3);
                edt3.setText(movies1.getRating()+"");
                final EditText edt4 = (EditText)view.findViewById(R.id.editText4);
                edt4.setText(movies1.getReleaseYear()+"");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = edt1.getText().toString();
                        String image = edt2.getText().toString();
                        float rating = 0.0f;
                        if(!edt3.getText().toString().isEmpty()) {
                            rating = Float.valueOf(edt3.getText().toString());
                        }
                        int releaseYear = 2010;
                        if(!edt4.getText().toString().isEmpty()) {
                            releaseYear = Integer.valueOf(edt4.getText().toString());
                        }
                        Movies movies = new Movies(movies1.getId(),title,image,rating,releaseYear);
                        db.updateMovies(movies);
                        results.clear();
                        results = db.getAllMovies();
//                    arrayList.add(movies);
                        moviesAdapter.clear();
                        moviesAdapter.addAll(db.getAllMovies());
                        moviesAdapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });



                AlertDialog alertDialog = builder.create();
                alertDialog.show();



                break;
            case R.id.mnuDelete:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Delete Movie");
                builder1.setCancelable(false);
                final Movies movies = arrayList.get(info.position);
                builder1.setMessage("Ban co muon xoa " + movies.getTitle());

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteMovies(movies);
                        results.clear();
                        results = db.getAllMovies();
//                        arrayList.remove(info.position);
                        moviesAdapter.clear();
                        moviesAdapter.addAll(db.getAllMovies());
                        moviesAdapter.notifyDataSetChanged();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}
