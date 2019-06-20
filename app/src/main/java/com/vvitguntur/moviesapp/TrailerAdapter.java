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

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {
    List<Trailers> youtubelinkkeys;
    Context context;

    public TrailerAdapter(Context context, List<Trailers> youtubelinkkeys) {
        this.context = context;
        this.youtubelinkkeys = youtubelinkkeys;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View textView2 = LayoutInflater.from(context)
                .inflate(R.layout.trailer_adapter, parent, false);
        return new TrailerHolder(textView2);

    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        Trailers linkkey=youtubelinkkeys.get(position);
        holder.linkkeyset.append(linkkey.getName());
    }

    @Override
    public int getItemCount() {
        return youtubelinkkeys.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView linkkeyset;

        public TrailerHolder(View itemView2) {
            super(itemView2);
            linkkeyset = itemView2.findViewById(R.id.trailer_tv);
            linkkeyset.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+youtubelinkkeys.get(position).getKey()));
            context.startActivity(intent);

        }
    }
}
