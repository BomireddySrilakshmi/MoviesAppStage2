package com.vvitguntur.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vvitguntur.moviesapp.Models.Trailers;

import java.util.List;

public class FavTrailerAdapter extends RecyclerView.Adapter<FavTrailerAdapter.FavTrailerHolder> {
    Context context;
    List<Trailers> favtrailersList;
    public FavTrailerAdapter(Context context, List<Trailers> trailersfav) {
        this.favtrailersList=trailersfav;
        this.context=context;
    }

    @NonNull
    @Override
    public FavTrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View textView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_favtrailer, parent, false);
        return new FavTrailerHolder(textView);

    }

    @Override
    public void onBindViewHolder(@NonNull FavTrailerHolder holder, int position) {
        Trailers fine=favtrailersList.get(position);
        holder.youtubelinksfav.append( fine.getName());
        holder.youtubelinksfav.setTag(position);

    }

    @Override
    public int getItemCount() {
        return favtrailersList.size();
    }

    public class FavTrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView youtubelinksfav;
        public FavTrailerHolder(View itemView) {
            super(itemView);
            youtubelinksfav=itemView.findViewById(R.id.favtrailer_tv);
            youtubelinksfav.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+favtrailersList.get(position).getKey()));
            context.startActivity(intent);

        }
    }
}
