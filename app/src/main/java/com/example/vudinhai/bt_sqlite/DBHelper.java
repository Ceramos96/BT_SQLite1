package com.example.vudinhai.bt_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by vudinhai on 7/28/17.
 */

public class DBHelper {
    String DATABASE_NAME = "MoviesDB.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;

    public DBHelper(Context context) {
        this.context = context;

        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Copy successful !!!", Toast.LENGTH_SHORT).show();

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDatabaseSystem();

            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            OutputStream databaseOutputStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = databaseInputStream.read(buffer)) > 0){
                databaseOutputStream.write(buffer,0,length);
            }


            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public ArrayList<Movies> getAllMovies(){
        ArrayList<Movies> arrayList = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        String sql = "SELECT * FROM Movies";

        Cursor cursor  = db.rawQuery(sql ,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String image = cursor.getString(2);
            float rating = cursor.getFloat(3);
            int releaseYear = cursor.getInt(4);
            arrayList.add(new Movies(id,title,image,rating,releaseYear));
        }
        return arrayList;
    }

    public void inertMovies(Movies movies){

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("title",movies.getTitle());
        contentValues.put("image",movies.getImage());
        contentValues.put("rating",movies.getRating());
        contentValues.put("releaseYear",movies.getReleaseYear());

        if(db.insert("Movies",null,contentValues) > 0){
            Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateMovies(Movies movies){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("title",movies.getTitle());
        contentValues.put("image",movies.getImage());
        contentValues.put("rating",movies.getRating());
        contentValues.put("releaseYear",movies.getReleaseYear());

        if(db.update("Movies",contentValues,"id =" + movies.getId(),null) > 0){
            Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteMovies(Movies movies){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        if(db.delete("Movies","id =" + movies.getId(),null) > 0){
            Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
        }
    }

}
