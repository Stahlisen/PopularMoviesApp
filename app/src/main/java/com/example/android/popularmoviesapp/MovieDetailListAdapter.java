package com.example.android.popularmoviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fst on 2016-05-30.
 */
public class MovieDetailListAdapter extends RecyclerView.Adapter<MovieDetailListAdapter.MyViewHolder> {
    String LOG_TAG  = this.getClass().getSimpleName();
    Context mContext;
    HashMap<String, String> mMovieDetails;
    ArrayList<String> mKeys;

    private static final int TYPE_TOP = 0;
    private static final int TYPE_SECOND = 1;

    public MovieDetailListAdapter(Context context, HashMap<String, String> mMovieDetails) {
        this.mContext = context;
        this.mMovieDetails = mMovieDetails;
        this.mKeys = new ArrayList<String>(mMovieDetails.keySet());
        Log.d(LOG_TAG, Integer.toString(mMovieDetails.values().size()));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(mContext).inflate(R.layout.detail_movie_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.contentText.setText(mMovieDetails.get(mKeys.get(position)));
        holder.headerText.setText(mKeys.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mMovieDetails.size();
    }

   public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView headerText;
        TextView contentText;

        public MyViewHolder(View v) {
            super(v);
            headerText = (TextView) v.findViewById(R.id.list_item_head);
            contentText = (TextView) v.findViewById(R.id.list_item_content);
        }
    }
}
