package com.vvitguntur.moviesapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.vvitguntur.moviesapp.DataBase.FavMovies;
import com.vvitguntur.moviesapp.DataBase.FavMoviesViewModel;
import com.vvitguntur.moviesapp.Loaders.AsynctaskLoader;
import com.vvitguntur.moviesapp.Loaders.ReviewLoader;
import com.vvitguntur.moviesapp.Models.Pojo;
import com.vvitguntur.moviesapp.NetUtils.Networkutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    FavMoviesViewModel mFavMovieViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<FavMovies> results;
    FavMoviesAdapter favMovieAdapter;
    public static final String SAVED="S";
    public static final String K="K";
    public static final String T="T";
    private int POPULAR = 23;
    private int TOPRATED= 22;
    private RecyclerAdapterMain recyclerAdapter;
    private List<Pojo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_details);
        sharedPreferences=getSharedPreferences("share",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        recyclerAdapter = new RecyclerAdapterMain(this);
        favMovieAdapter = new FavMoviesAdapter(MainActivity.this);
        mFavMovieViewModel = ViewModelProviders.of(this).get(FavMoviesViewModel.class);
        if (savedInstanceState!=null)
        {
            String type = sharedPreferences.getString("type","popular");
            switch(type)
            {
                case "popular":
                    int position=savedInstanceState.getInt(SAVED);
                    list= (List<Pojo>) savedInstanceState.getSerializable(K);
                    recyclerAdapter.setPojo1s(list);
                    recyclerView.setAdapter(recyclerAdapter);
                    if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                        layoutManager = new GridLayoutManager(this, 2);
                    }else {
                        layoutManager = new GridLayoutManager(this, 3);
                    }
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.scrollToPosition(position);
                    editor.putString("type","popular");
                    break;
                case "toprated":
                    position=savedInstanceState.getInt(SAVED);
                    list= (List<Pojo>) savedInstanceState.getSerializable(K);
                    recyclerAdapter.setPojo1s(list);
                    recyclerView.setAdapter(recyclerAdapter);
                    if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                        layoutManager = new GridLayoutManager(this, 2);
                    }else {
                        layoutManager = new GridLayoutManager(this, 3);
                    }
                    recyclerView.setLayoutManager(layoutManager);
                    layoutManager.scrollToPosition(position);
                    editor.putString("type","toprated");
                    break;
                case "favourites":
                    loadFavorites();
                    position=savedInstanceState.getInt(SAVED);
                    results= (List<FavMovies>) savedInstanceState.getSerializable(K);
                    favMovieAdapter.setResult(results);
                    recyclerView.setAdapter(favMovieAdapter);
                    if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                        layoutManager = new GridLayoutManager(this, 2);
                    }else {
                        layoutManager = new GridLayoutManager(this, 3);
                    }
                    recyclerView.setLayoutManager(layoutManager);
                    layoutManager.scrollToPosition(position);
                    editor.putString("type","favourites");
                    break;
            }
            editor.apply();
            String title=savedInstanceState.getString("T");
            setTitle(title);

        }
        else {

            switch(sharedPreferences.getString("type","popular"))
            {
                case "popular":
                    jsonparse("popular");
                    editor.putString("type","popular");
                    break;
                case "toprated":
                    jsonparse("top_rated");
                    editor.putString("type","toprated");
                    break;
                case "favourites":
                    loadFavorites();
                    editor.putString("type","favourites");
                    break;
            }
            editor.apply();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_resource,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch(item.getItemId())
        {

            case R.id.popular:
                jsonparse("popular");
                editor.putString("type","popular");
                break;
            case R.id.top_rated:
                jsonparse("top_rated");
                editor.putString("type","toprated");
                break;
            case R.id.favourite:
                loadFavorites();
                editor.putString("type","favourites");
                break;
        }
        editor.apply();
        return super.onOptionsItemSelected(item);
    }

    private void loadFavorites() {
        mFavMovieViewModel.getAllMovies().observe(this, new Observer<List<FavMovies>>() {
            @Override
            public void onChanged( List<FavMovies> resultss)
            {
                if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                    layoutManager = new GridLayoutManager(MainActivity.this, 2);
                }else {
                    layoutManager = new GridLayoutManager(MainActivity.this, 3);
                }
                favMovieAdapter.setResult(resultss);
                recyclerView.setAdapter(favMovieAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManager!=null)
        {
            int pos=layoutManager.findFirstCompletelyVisibleItemPosition();
            outState.putInt(SAVED,pos);
        }
        switch(sharedPreferences.getString("type","popular"))
        {
            case "popular":
                outState.putSerializable(K, (Serializable) list);
                editor.putString("type","popular");
                break;
            case "toprated":
                outState.putSerializable(K, (Serializable) list);
                editor.putString("type","toprated");
                break;
            case "favourites":
                outState.putSerializable(K, (Serializable) results);
                editor.putString("type","favourites");
                break;
        }
        editor.apply();

        outState.putString(T,getTitle().toString());
    }






    private void jsonparse(String popular) {
        Networkutils.setGetQuery(popular);
        if(popular.equalsIgnoreCase("popular")) {

            getLoaderManager().restartLoader(POPULAR, null, this);
        }
        else
            getLoaderManager().restartLoader(TOPRATED,null,this);

    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsynctaskLoader(this);

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        list=new ArrayList<>();
        list.clear();
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonArray1=jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray1.length();i++) {
                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                String title = jsonObject1.getString("title");
                String popularity = jsonObject1.getString("popularity");
                String poster = jsonObject1.optString("poster_path").toString();
                String orgTitle = jsonObject1.getString("original_title");
                String release = jsonObject1.getString("release_date");
                String overview = jsonObject1.getString("overview");
                String rating =jsonObject1.getString("vote_average");
                int id=jsonObject1.getInt("id");
                Pojo pj = new Pojo(title,  poster,  release, overview,rating,id);
                list.add(pj);
            }
            if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = new GridLayoutManager(this, 2);
            }else {
                layoutManager = new GridLayoutManager(this, 3);
            }
            layoutManager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerAdapter.setPojo1s(list);
            recyclerView.setAdapter(recyclerAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
