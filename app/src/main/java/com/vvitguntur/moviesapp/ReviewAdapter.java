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

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    List<Reviews> reviewPojoList1;
    Context context;
    ReviewAdapter(Context context,List<Reviews> reviewPojoList1){
        this.context = context;
        this.reviewPojoList1=reviewPojoList1;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View textview= LayoutInflater.from(context).inflate(R.layout.recycler_review,parent,false);
        return new ReviewHolder(textview);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewHolder holder, int position) {
        Reviews reviewPojos=reviewPojoList1.get(position);
        holder.textViewauthor.setText(reviewPojos.getAuthorname());
        holder.textViewcontent.setText(reviewPojos.getReviewcontent());
    }

    @Override
    public int getItemCount() {
        return reviewPojoList1.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        TextView textViewauthor;
        TextView textViewcontent;
        public ReviewHolder(View itemView) {
            super(itemView);
            textViewauthor=itemView.findViewById(R.id.author_tv);
            textViewcontent=itemView.findViewById(R.id.review_tv);

        }
    }
}
