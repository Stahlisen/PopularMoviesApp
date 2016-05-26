package com.example.android.popularmoviesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fst on 2016-05-26.
 */
public class PosterGridAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Movie> mMovies;
    String LOG_TAG  = this.getClass().getSimpleName();

    public PosterGridAdapter(Context context, ArrayList<Movie> movieList) {
        this.mContext = context;
        this.mMovies = movieList;
        Log.d(LOG_TAG, Integer.toString(movieList.size()));
    }


    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView posterView;
        if (convertView == null) {
            posterView = new ImageView(mContext);
            posterView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            posterView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            posterView.setPadding(8, 8, 8, 8);
        } else {
            posterView = (ImageView) convertView;
        }

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500/" + mMovies.get(position).getImageUrl()).into(posterView);
        return posterView;
    }
}
