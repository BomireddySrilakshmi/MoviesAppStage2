package com.vvitguntur.moviesapp.Loaders;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.vvitguntur.moviesapp.NetUtils.Networkutils;

public class AsynctaskLoader extends AsyncTaskLoader<String> {

public static ProgressDialog progressBar;
    private Context c;

    public AsynctaskLoader(Context context) {
        super(context);
        c=context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return Networkutils.getMovieInfo();
    }
}
