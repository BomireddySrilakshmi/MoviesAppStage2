package com.vvitguntur.moviesapp.DataBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FavMoviesRepository {
    private FavMoviesDao mFavMoviesDao;
    private LiveData<List<FavMovies>> mAllFavMovies;

    public FavMoviesRepository(Application application) {
        FavMoviesRoomDatabase db = FavMoviesRoomDatabase.getDatabase(application);
        mFavMoviesDao = db.favMoviesDao();
        mAllFavMovies = mFavMoviesDao.getAllData();
    }
    public FavMovies checkMovie(int id)
    {
        return mFavMoviesDao.checkMovie(id);
    }

    public LiveData<List<FavMovies>> getAllFavMovies() {
        return mAllFavMovies;
    }

    public void insert(FavMovies favMovies) {
        new insertAsyncTask(mFavMoviesDao).execute(favMovies);
    }

    public void delete(FavMovies favMovies) {
        new deleteAsyncTask(mFavMoviesDao).execute(favMovies);
    }

    private static class insertAsyncTask extends AsyncTask<FavMovies, Void, Void> {

        private FavMoviesDao mAsyncTaskDao;

        insertAsyncTask(FavMoviesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FavMovies... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<FavMovies, Void, Void> {

        private FavMoviesDao mAsyncTaskDao;

        deleteAsyncTask(FavMoviesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FavMovies... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
