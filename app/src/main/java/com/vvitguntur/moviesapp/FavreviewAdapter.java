package com.vvitguntur.moviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vvitguntur.moviesapp.Models.Reviews;

import java.util.List;

public class FavreviewAdapter extends RecyclerView.Adapter<FavreviewAdapter.ReviewFavHolder> {
    Context context;
    List<Reviews> reviewsList;
    public FavreviewAdapter(Context context, List<Reviews> reviewList) {
        this.reviewsList=reviewList;
        this.context=context;
    }

    @NonNull
    @Override
    public ReviewFavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View textview= LayoutInflater.from(context).inflate(R.layout.recycler_favreview,parent,false);
        return new ReviewFavHolder(textview);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewFavHolder holder, int position) {

        Reviews reviewPojos=reviewsList.get(position);
        holder.favauthor.setText(reviewPojos.getAuthorname());
        holder.favcontent.setText(reviewPojos.getReviewcontent());

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class ReviewFavHolder extends RecyclerView.ViewHolder {
        TextView favauthor;
        TextView favcontent;
        public ReviewFavHolder(View itemView) {
            super(itemView);
            favauthor=itemView.findViewById(R.id.favauthor_tv);
           favcontent=itemView.findViewById(R.id.favreview_tv);
        }
    }
}
