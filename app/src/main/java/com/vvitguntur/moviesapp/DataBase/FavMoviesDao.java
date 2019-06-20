package com.vvitguntur.moviesapp.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavMoviesDao {

    @Insert
    void insert(FavMovies favMovies);

    @Delete
    void delete(FavMovies favMovies);

    @Query("Select * from favourite_movies")
    LiveData<List<FavMovies>> getAllData();

    @Query("Select * from favourite_movies where id = :t")
    FavMovies checkMovie(int t);
}
