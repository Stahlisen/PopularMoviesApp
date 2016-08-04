package com.example.android.popularmoviesapp;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.android.popularmoviesapp.Model.Movie;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MovieGridFragment extends Fragment {

    ArrayList<Movie> movieList;
    GridView gridView;
    ProgressBar progressBar;
    PosterGridAdapter posterGridAdapter;

    public MovieGridFragment() {
       movieList = new ArrayList<Movie>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_movie_grid, container, false);

         gridView = (GridView) rootView.findViewById(R.id.movieGridView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = posterGridAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("Movie", (Serializable) selectedMovie);
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);

            }
        });
        if (movieList.size() == 0) {
            new fetchMovieData().execute();
        }


        return rootView;
    }

    private class fetchMovieData extends AsyncTask<String, String, ArrayList<Movie>> {
        String LOG_TAG = this.getClass().getSimpleName();
        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            Thread.currentThread();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            ArrayList<Movie> movieList = null;
            String jsonStr = "";
            try {
                String BASE_URL = "http://api.themoviedb.org/3/movie/popular";
                String API_KEY = "2045aa6a6dcedbc160541e810c9dec7f";

                Uri endPointUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter("api_key", API_KEY)
                        .appendQueryParameter("year", "2016")
                        .build();
                Log.d(LOG_TAG, endPointUri.toString());
                URL url = new URL(endPointUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                jsonStr = stringBuffer.toString();

                Log.d(LOG_TAG, jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("PlaceholderFragment", "Error ", e);
                    }
                }

                try {
                    movieList = MovieDataParser.getMovieData(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return movieList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (movies != null) {
                if (movieList.size() > 0) {
                    movieList.clear();
                }
                posterGridAdapter = new PosterGridAdapter(getContext(), movies);
                gridView.setAdapter(posterGridAdapter);
                movieList = movies;
                gridView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }
    }



}
