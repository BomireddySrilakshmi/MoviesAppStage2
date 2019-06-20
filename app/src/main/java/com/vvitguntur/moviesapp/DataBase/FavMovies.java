package com.vvitguntur.moviesapp.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "favourite_movies")
public class FavMovies implements Serializable {
    @PrimaryKey
    @NonNull
    int id;
    String title;
    String overview;
    String poster_path;
    String release_Date;
    String rating;
    @Ignore
    public FavMovies(){

    }
    public FavMovies(String title, String poster_path, String release_Date,String overview, String rating,int id) {
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_Date = release_Date;
        this.rating = rating;
        this.id=id;
    }
    public String get_Title() {
        return title;
    }

    public String get_Overview() {
        return overview;
    }
    public String getPoster_path() {
        return poster_path;
    }
    public String getRelease_Date() {
        return release_Date;
    }
    public String getRating() {
        return rating;
    }
    public int getId() {
        return id;
    }

}
