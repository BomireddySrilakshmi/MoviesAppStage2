package com.vvitguntur.moviesapp.Loaders;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.vvitguntur.moviesapp.NetUtils.ReviewNetwork;

public class ReviewLoader extends AsyncTaskLoader<String> {

    Context context;
    String url;

    public ReviewLoader(Context context,String url) {
        super(context);
        this.context=context;
        this.url=url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();

    }

    @Override
    public String loadInBackground() {
        return ReviewNetwork.getReview(url);
    }
}
