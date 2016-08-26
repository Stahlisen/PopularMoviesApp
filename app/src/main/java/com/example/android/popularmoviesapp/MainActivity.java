package com.example.android.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmoviesapp.Model.Movie;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    HashMap<String, ArrayList<Movie>> showsdata;
    ProgressBar progressBar;
    ViewPager viewPager;
    TabLayout tabLayout;
    FetchMovieData fmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        /*
        if (savedInstanceState ==  null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieGridFragment())
                    .commit();
        }
        */
        fmd = new FetchMovieData();
        fmd.execute();

    }

    public void dataFetchingDone() {
        viewPager.setAdapter(new MovieGridFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));
        tabLayout.setupWithViewPager(viewPager);
    }

    public ArrayList<Movie> getShowsData(String type) {
        if (showsdata != null) {
            return showsdata.get(type);
        } else {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class FetchMovieData extends AsyncTask<String, String, HashMap<String, ArrayList<Movie>>> {
        String LOG_TAG = this.getClass().getSimpleName();
        @Override
        protected HashMap<String, ArrayList<Movie>> doInBackground(String... params) {
            Log.d("MovieGridFragment", "Starting callout");
            Thread.currentThread();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            HashMap<String, ArrayList<Movie>> fullMovieMap = new HashMap<String, ArrayList<Movie>>();
            fullMovieMap.put("movie", null);
            fullMovieMap.put("tv", null);

            for (String key : fullMovieMap.keySet()) {


                ArrayList<Movie> movieList = null;
                String jsonStr = "";
                try {
                    String BASE_URL = "http://api.themoviedb.org/3/" + key + "/popular";
                    String API_KEY = "2045aa6a6dcedbc160541e810c9dec7f";

                    Uri endPointUri = Uri.parse(BASE_URL).buildUpon()
                            .appendQueryParameter("api_key", API_KEY)
                            .appendQueryParameter("year", "2016")
                            .build();
                    Log.d(LOG_TAG, "endpointuri = " + endPointUri.toString());
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
                        if (key == "movie") {
                            fullMovieMap.put(key, MovieDataParser.getMovieData(jsonStr));
                        } else if (key == "tv") {
                            fullMovieMap.put(key, MovieDataParser.getTvShowData(jsonStr));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return fullMovieMap;
        }

        @Override
        protected void onPostExecute(HashMap<String, ArrayList<Movie>> shows) {
            if (showsdata != null) {
                if (showsdata.values().size() > 0) {
                    showsdata.clear();
                }
            }
            showsdata = shows;

            progressBar.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            dataFetchingDone();
        }
    }
}


