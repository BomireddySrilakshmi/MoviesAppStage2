package com.vvitguntur.moviesapp.DataBase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavMoviesViewModel extends AndroidViewModel {
    private FavMoviesRepository mRepository;
    private LiveData<List<FavMovies>> mAllFavMovies;

    public FavMoviesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FavMoviesRepository(application);
        mAllFavMovies = mRepository.getAllFavMovies();
    }
    public FavMovies checkMovie(int id) {
        return mRepository.checkMovie(id);
    }
    public LiveData<List<FavMovies>> getAllMovies() {
        return mAllFavMovies;
    }
    public void insert(FavMovies word) {
        mRepository.insert(word);
    }
    public void delete(FavMovies favMovies) {
        mRepository.delete(favMovies);

    }
}