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
import com.vvitguntur.moviesapp.Models.Pojo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.DetailsHolder> {
    Context context;
    List<Pojo> pojoList;

    public RecyclerAdapterMain(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_main,parent,false);
        return new DetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {
        String s="http://image.tmdb.org/t/p/w342"+pojoList.get(position).getPoster();
        Picasso.get().load(s).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return pojoList.size();
    }

    public void setPojo1s(List<Pojo> pojo1s) {
        this.pojoList = pojo1s;
    }

    public class DetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public DetailsHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recycler_poster_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent intent=new Intent(context,DetailsActivity.class);
            intent.putExtra("movie_details",pojoList.get(position));
            context.startActivity(intent);
        }
    }
}
