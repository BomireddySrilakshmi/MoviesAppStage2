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

public class ReviewFavActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    String reviewStringfav;
    String urlfav;
    RecyclerView recyclerViewreviewfav;
    List<Reviews> reviewPojoListfav=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_fav);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_fav);
        Intent intent = getIntent();
        reviewStringfav = String.valueOf(intent.getIntExtra("detailfav",0));
        recyclerViewreviewfav=findViewById(R.id.recycler_favreviews);
        reviewLoadfav();

    }
    private void reviewLoadfav() {
        urlfav = String.valueOf(ReviewNetwork.buildUri(reviewStringfav));
        getLoaderManager().restartLoader(30, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ReviewLoader(this,urlfav);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {


            try {
                JSONObject object = new JSONObject(data);
                JSONArray jsonArray = object.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String auth = jsonObject1.getString("author");
                    String cont = jsonObject1.getString("content");
                    Reviews reviewPojo = new Reviews(auth, cont);
                    reviewPojoListfav.add(reviewPojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FavreviewAdapter recyclerForfavReview = new FavreviewAdapter(this, reviewPojoListfav);
            recyclerViewreviewfav.setAdapter(recyclerForfavReview);
            recyclerViewreviewfav.setLayoutManager(new LinearLayoutManager(this));

        }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
