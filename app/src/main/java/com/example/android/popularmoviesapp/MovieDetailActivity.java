package com.example.android.popularmoviesapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesapp.API.MovieTrailerRequest;
import com.example.android.popularmoviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.HashMap;

public class MovieDetailActivity extends AppCompatActivity implements IFetchMovieTrailerDataReponse{

    Movie detailMovie;
    String movieTrailerVideoId;
    fetchMovieTrailerData fetchTask = new fetchMovieTrailerData();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fetchTask.delegate = this;

        setContentView(R.layout.activity_movie_detail);

        detailMovie = (Movie) getIntent().getSerializableExtra("Movie");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String movieTitleString = detailMovie.getTitle();
        toolbar.setTitle(movieTitleString);

        ImageView imgView = (ImageView) findViewById(R.id.toolbarImage);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + detailMovie.getImageUrl()).into(imgView);

        TextView floatRatingButtonText = (TextView) findViewById(R.id.floatRating_text);
        Double ratingDouble = Double.parseDouble(detailMovie.getRating());
        Double roundedRatingDouble = new BigDecimal(ratingDouble).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        floatRatingButtonText.setText(roundedRatingDouble.toString());

        ImageView playButton = (ImageView) findViewById(R.id.secondImage);
        int color = Color.parseColor("#FF4081");
        playButton.setColorFilter(color);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (movieTrailerVideoId == null) {
                    fetchTask.execute();
                } else {
                    runMovieTrailerByVideoId();
                }



            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle movieBundle = new Bundle();
        movieBundle.putSerializable("movie", detailMovie);
        MovieDetailFragment mdf = new MovieDetailFragment();
        mdf.setArguments(movieBundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mdf)
                .commit();

    }

    @Override
    public void fetchTaskFinished(String result) {
        movieTrailerVideoId = result;
        runMovieTrailerByVideoId();
    }

    public void runMovieTrailerByVideoId() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+movieTrailerVideoId));
        intent.putExtra("force_fullscreen",true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
    }

    public static class MovieDetailFragment extends Fragment {
        String LOG_TAG = this.getClass().getSimpleName();

        public MovieDetailFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);
            Movie movie = (Movie) getArguments().getSerializable("movie");

            RecyclerView movieDetailListView = (RecyclerView) rootView.findViewById(R.id.movieDetailListView);
            //ListView movieDetailListView = (ListView) rootView.findViewById(R.id.movieDetailListView);
            HashMap<String, String> movieMap = new HashMap<String, String>();
            movieMap.put("Plot", movie.getPlot());
            movieMap.put("Rating", movie.getRating());
            movieMap.put("Release date",movie.getReleaseDate());

            MovieDetailListAdapter listAdapter = new MovieDetailListAdapter(getActivity(), movieMap);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            movieDetailListView.setLayoutManager(layoutManager);
            //movieDetailListView.setHasFixedSize(true);

            RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getResources().getDrawable(R.drawable.divider));
            movieDetailListView.addItemDecoration(dividerItemDecoration);
            movieDetailListView.setAdapter(listAdapter);




            return rootView;
        }
    }

    private class fetchMovieTrailerData extends AsyncTask<String, String, Void> {
        public IFetchMovieTrailerDataReponse delegate = null;
        String mVideoId;

        @Override
        protected Void doInBackground(String... params) {
            MovieTrailerRequest mtr = new MovieTrailerRequest();
            String videoId =  mtr.getMovieTrailerVideoId(detailMovie.getTitle());
            this.mVideoId = videoId;

            return null;
        }

        @Override
        protected void onPostExecute(Void Void) {
            delegate.fetchTaskFinished(mVideoId);
        }
    }

}
