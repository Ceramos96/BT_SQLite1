package com.example.vudinhai.bt_sqlite;

import java.io.Serializable;

/**
 * Created by vudinhai on 7/28/17.
 */

public class Movies implements Serializable {
    int id;
    String title;
    String image;
    float rating;
    int releaseYear;

    public Movies() {
    }

    public Movies(String title, String image, float rating, int releaseYear) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public Movies(int id, String title, String image, float rating, int releaseYear) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
