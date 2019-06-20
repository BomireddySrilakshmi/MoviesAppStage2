package com.vvitguntur.moviesapp.Loaders;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.vvitguntur.moviesapp.NetUtils.TrailerNetwork;

public class TrailerLoader extends AsyncTaskLoader<String>{

    Context context;
    public TrailerLoader(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();

    }

    @Override
    public String loadInBackground() {
        return TrailerNetwork.getVideos();
    }
}
