package com.vvitguntur.moviesapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vvitguntur.moviesapp.DataBase.FavMovies;
import com.vvitguntur.moviesapp.DataBase.FavMoviesDao;
import com.vvitguntur.moviesapp.DataBase.FavMoviesViewModel;
import com.vvitguntur.moviesapp.Loaders.TrailerLoader;
import com.vvitguntur.moviesapp.Models.Trailers;
import com.vvitguntur.moviesapp.NetUtils.TrailerNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavMoviesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    String linksfav ;
    String ij,trname;
    AlertDialog.Builder alertDialog;
    List<Trailers> trailersfav=new ArrayList<>();
    RecyclerView recyclerView2fav;
    String videoStringfav;
    TextView title2fav;
    TextView Descripfav;
    TextView release_datefav;
    TextView vote_ratefav;
    ImageView imgfav;
    FavMovies pojo2fav;
    Button buttonfav,buttonrfav;
    private FavMoviesViewModel mFavMoviesViewModelfav;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movies);
        Intent intent=getIntent();
        FavMovies pojo = (FavMovies) intent.getSerializableExtra("favobject");
        mFavMoviesViewModelfav = ViewModelProviders.of(this).get(FavMoviesViewModel.class);
        pojo2fav = pojo;
        buttonfav=findViewById(R.id.button_fav);
        buttonrfav=findViewById(R.id.button_rev);
        videoStringfav = String.valueOf(pojo.getId());
        recyclerView2fav = findViewById(R.id.recycler_favtrailertv);
        title2fav = findViewById(R.id.m_titlef);
        Descripfav = findViewById(R.id.synopsisf);
        release_datefav = findViewById(R.id.releasef);
        vote_ratefav = findViewById(R.id.votef);
        imgfav = findViewById(R.id.imagef);
        title2fav.append(pojo2fav.get_Title());
        release_datefav.append(pojo2fav.getRelease_Date());
        vote_ratefav.append(pojo2fav.getRating());
        Descripfav.append(pojo.get_Overview());
        Picasso.get().load("http://image.tmdb.org/t/p/w342" + pojo2fav.getPoster_path()).placeholder(R.mipmap.ic_launcher)
                .into(imgfav);
        videosload();
        checkfav(pojo2fav.getId());

    }


    private void checkfav(int id) {
        FavMovies favMovies=mFavMoviesViewModelfav.checkMovie(id);
        if(favMovies!=null)
        {
            buttonfav.setText("Un Favourite this movie");
        }
        else
        {
            buttonfav.setText("Add To Favorites");
        }
    }

    public void videosload() {
        TrailerNetwork.setI(videoStringfav);
        getLoaderManager().restartLoader(25, null, this);
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new TrailerLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

            try {
                JSONObject json = new JSONObject(data);
                JSONArray jsonArray = json.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ij = jsonObject.getString("id");
                    linksfav = jsonObject.getString("key");
                    trname = jsonObject.getString("name");
                    Trailers trailer = new Trailers(ij, linksfav, trname);
                    trailersfav.add(trailer);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            FavTrailerAdapter recyclerForTrailer = new FavTrailerAdapter(this, trailersfav);
            recyclerView2fav.setAdapter(recyclerForTrailer);
            recyclerView2fav.setLayoutManager(new LinearLayoutManager(this));


        }


    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    public void favouritefun(View view) {
        String bname = buttonfav.getText().toString();
        FavMovies p = pojo2fav;
        if (bname.equals("Un Favourite this movie")) {
            FavMovies favMovies = new FavMovies(p.get_Title(),  p.getPoster_path(),  p.getRelease_Date(), p.get_Overview(), p.getRating(), p.getId());
            mFavMoviesViewModelfav.delete(favMovies);
            buttonfav.setText("Add To Favorites");
        } else {
            FavMovies favMovies = new FavMovies(p.get_Title(),  p.getPoster_path(),  p.getRelease_Date(), p.get_Overview(), p.getRating(), p.getId());
            mFavMoviesViewModelfav.insert(favMovies);
            buttonfav.setText("Un Favourite this movie");
        }

    }

    public void reviewsfun(View view) {
        Intent intent=new Intent(this,ReviewFavActivity.class);
        intent.putExtra("detailfav",pojo2fav.getId());
        this.startActivity(intent);

    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }*/
}
