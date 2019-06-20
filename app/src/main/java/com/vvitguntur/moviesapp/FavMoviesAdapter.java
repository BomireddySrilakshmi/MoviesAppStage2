package com.vvitguntur.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vvitguntur.moviesapp.DataBase.FavMovies;

import java.util.ArrayList;
import java.util.List;

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.FavHolder>{
    Context context;
    List<FavMovies> result;
    public FavMoviesAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public FavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fav_movies_adapter,parent,false);
        return new FavHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavHolder holder, int position) {
        FavMovies pj=result.get(position);
        String s="http://image.tmdb.org/t/p/w342"+pj.getPoster_path();
        Picasso.get().load(s)
                .into(holder.imageViewfav);
        holder.imageViewfav.setTag(position);

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setResult(List<FavMovies> result) {
        this.result = result;
    }

    public class FavHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageViewfav;
        public FavHolder(View itemView) {
            super(itemView);
            imageViewfav=itemView.findViewById(R.id.posterimage);
            imageViewfav.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent intent=new Intent(context,FavMoviesActivity.class);
            intent.putExtra("favobject",result.get(position));
            context.startActivity(intent);

        }
    }
}
