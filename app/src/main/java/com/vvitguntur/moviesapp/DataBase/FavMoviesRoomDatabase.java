package com.vvitguntur.moviesapp.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavMovies.class},version = 1,exportSchema = false)

public abstract class FavMoviesRoomDatabase extends RoomDatabase{

    public abstract FavMoviesDao favMoviesDao();

    private static volatile FavMoviesRoomDatabase INSTANCE;

    static FavMoviesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavMoviesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavMoviesRoomDatabase.class, "movies_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}
