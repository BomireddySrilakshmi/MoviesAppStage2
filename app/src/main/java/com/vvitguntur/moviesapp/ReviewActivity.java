package com.vvitguntur.moviesapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vvitguntur.moviesapp.Loaders.ReviewLoader;
import com.vvitguntur.moviesapp.Models.Reviews;
import com.vvitguntur.moviesapp.NetUtils.ReviewNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    String reviewString;
    String url;
    List<Reviews> reviewPojoList=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        recyclerView=findViewById(R.id.recycler_reviews);
        Intent intent = getIntent();
        reviewString = String.valueOf(intent.getIntExtra("rdetail",0));
        reviewLoad();

    }

    private void reviewLoad() {
        url = String.valueOf(ReviewNetwork.buildUri(reviewString));
        getLoaderManager().restartLoader(30,null,this);
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ReviewLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data == null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Reviews").setMessage("There are no Reviews").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).show();

        } else {

            try {
                JSONObject object = new JSONObject(data);
                JSONArray jsonArray = object.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String auth = jsonObject1.getString("author");
                    String cont = jsonObject1.getString("content");
                    Reviews reviewPojo = new Reviews(auth, cont);
                    reviewPojoList.add(reviewPojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ReviewAdapter recyclerForReview = new ReviewAdapter(this, reviewPojoList);
            recyclerView.setAdapter(recyclerForReview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
