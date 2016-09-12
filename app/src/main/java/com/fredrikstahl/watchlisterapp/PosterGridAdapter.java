package com.fredrikstahl.watchlisterapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fredrikstahl.watchlisterapp.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by fst on 2016-05-26.
 */
public class PosterGridAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Movie> mMovies;
    String LOG_TAG  = this.getClass().getSimpleName();
    TextView posterTextView;
    FrameLayout posterTextViewPlaceHolder;

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

        LayoutInflater inflater = ((StartActivity)mContext).getLayoutInflater();

        View gridView;
        ImageView posterImageView;
        //TextView posterTextView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_poster_view, null);


        } else {
            gridView = (View) convertView;
        }

        posterImageView = (ImageView) gridView.findViewById(R.id.posterImage);
        //posterImageView = new ImageView(mContext);
        //posterImageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        posterImageView.setPadding(8, 8, 8, 8);
        //posterTextViewPlaceHolder = (FrameLayout) gridView.findViewById(R.id.posterTextPlaceHolder);
        //posterTextView = (TextView) gridView.findViewById(R.id.posterText);
        //posterTextView.setText(mMovies.get(position).getTitle());

        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w500/" + mMovies.get(position).getImageUrl())
                .into(posterImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //posterTextViewPlaceHolder.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });

        //Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500/" + mMovies.get(position).getImageUrl()).into(posterImageView);

        return gridView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
