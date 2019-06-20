package com.vvitguntur.moviesapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vvitguntur.moviesapp.DataBase.FavMovies;
import com.vvitguntur.moviesapp.DataBase.FavMoviesDao;
import com.vvitguntur.moviesapp.DataBase.FavMoviesViewModel;
import com.vvitguntur.moviesapp.Loaders.TrailerLoader;
import com.vvitguntur.moviesapp.Models.Pojo;
import com.vvitguntur.moviesapp.Models.Trailers;
import com.vvitguntur.moviesapp.NetUtils.TrailerNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    TextView movie_title, release, vote_rate, synopsis;
    ImageView poster;
    Button buttonreview;
    private FavMoviesViewModel mFavMoviesViewModel;
    Pojo pojo1;
    Button button;
    private String videoString;
    RecyclerView recyclerView2;
    public String ij;
    public String links;
    public String trainame;
    List<Trailers> trailers=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        Pojo pojo = (Pojo)intent.getSerializableExtra("movie_details");
        pojo1=pojo;
        videoString=String.valueOf(pojo.getId());
        movie_title = findViewById(R.id.m_title_tv);
        poster = findViewById(R.id.image_tv);
        buttonreview=findViewById(R.id.button_reviewtv);
        recyclerView2 = findViewById(R.id.recycler_trilertv);
        button=findViewById(R.id.button_favourite);
        release = findViewById(R.id.release_tv);
        vote_rate = findViewById(R.id.vote_tv);
        synopsis = findViewById(R.id.synopsis_tv);
        movie_title.setText(pojo.getMtitle());
        mFavMoviesViewModel = ViewModelProviders.of(this).get(FavMoviesViewModel.class);
        Picasso.get().load("http://image.tmdb.org/t/p/w342" + pojo.getPoster())
                .placeholder(R.mipmap.ic_launcher).
                into(poster);
        release.append(pojo.getRelease());
        vote_rate.append(pojo.getRate());
        synopsis.append(pojo.getOverview());
        videosload();
        check(pojo1.getId());
    }
    public void videosload() {
        TrailerNetwork.setI(videoString);
        getLoaderManager().restartLoader(25, null, this);
    }


    public void check(int id)
    {
        FavMovies favMovies=mFavMoviesViewModel.checkMovie(id);
        if(favMovies!=null)
        {
            button.setText("Un Favourite this movie");
        }
        else
        {
            button.setText("Add To Favorites");
        }
    }



    public void reviews(View view) {

        Intent intent=new Intent(this,ReviewActivity.class);
        intent.putExtra("rdetail",pojo1.getId());
        this.startActivity(intent);

    }

    public void favourites(View view) {
        String bname = button.getText().toString();
        Pojo p = pojo1;
        if (bname.equals("Un Favourite this movie")) {
            FavMovies favMovies = new FavMovies(p.getMtitle(), p.getPoster(), p.getRelease(), p.getOverview(), p.getRate(), p.getId());
            mFavMoviesViewModel.delete(favMovies);
            button.setText("Add To Favorites");
        } else {
            FavMovies favMovies = new FavMovies(p.getMtitle(), p.getPoster(), p.getRelease(), p.getOverview(), p.getRate(), p.getId());
            mFavMoviesViewModel.insert(favMovies);
            Toast.makeText(this, favMovies.get_Title(), Toast.LENGTH_SHORT).show();
            button.setText("Un Favourite this movie");

        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new TrailerLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        if(data==null)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Trailers").setMessage("There are no trailers").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).show();
        }
        else {

            try {
                JSONObject json = new JSONObject(data);
                JSONArray jsonArray = json.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ij = jsonObject.getString("id");
                    links = jsonObject.getString("key");
                    trainame = jsonObject.getString("name");
                    Trailers trailer = new Trailers(ij, links, trainame);
                    trailers.add(trailer);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        TrailerAdapter recyclerForTrailer = new TrailerAdapter(this,trailers);
        recyclerView2.setAdapter(recyclerForTrailer);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
