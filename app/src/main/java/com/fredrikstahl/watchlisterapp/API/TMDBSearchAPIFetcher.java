package com.fredrikstahl.watchlisterapp.API;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.fredrikstahl.watchlisterapp.Model.Movie;
import com.fredrikstahl.watchlisterapp.MovieDataParser;
import com.fredrikstahl.watchlisterapp.Search.SearchResultActivity;
import com.fredrikstahl.watchlisterapp.Search.SearchResultTabPagerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fredrikstahl on 13/04/17.
 */

public class TMDBSearchAPIFetcher extends AsyncTask<String, String, HashMap<String, ArrayList<Movie>>> {

    SearchResultTabPagerFragment caller;
    public TMDBSearchAPIFetcher(SearchResultTabPagerFragment caller) {
        this.caller = caller;
    }

    @Override
    protected HashMap<String, ArrayList<Movie>> doInBackground(String... strings) {
        Log.d("TMDBSearchAPIFetcher", "string = " + strings.toString());
        Thread.currentThread();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, ArrayList<Movie>> searchResultItemMap = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;

        try {
            String BASE_URL = "http://api.themoviedb.org/3/search/multi";
            String API_KEY = "2045aa6a6dcedbc160541e810c9dec7f";

            Uri endPointUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("query", strings[0])
                    .build();
            URL url = new URL(endPointUri.toString());

            Log.d("TMDBSearchAPIFetcher", "URL = " + url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

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
            Log.d("TMDBSearchAPIFetcher", "jsonStr = " + jsonStr);
        } catch(Exception e) {

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
                searchResultItemMap = MovieDataParser.transformSearchResult(jsonStr);
                Log.d("TMDBSearchAPIFetcher", "RECIEVED MOVIES");
                for (Movie m : searchResultItemMap.get(MovieDataParser.MOVIE_LABEL)) {
                    Log.d("TMDBSearchAPIFetcher", m.getTitle());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return searchResultItemMap;
    }

    @Override
    protected void onPostExecute(HashMap<String, ArrayList<Movie>> result) {
        caller.onSearchAPIFetcherCompleted(result);
    }
}
