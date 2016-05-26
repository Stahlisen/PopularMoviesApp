package com.example.android.popularmoviesapp;

import com.example.android.popularmoviesapp.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fst on 2016-05-26.
 */
public class MovieDataParser {

    public static ArrayList<Movie> getMovieData(String movieJsonStr) throws JSONException{
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        String TMD_TITLE = "original_title";
        String TMD_IMAGE_URL = "poster_path";
        String TMD_PLOT = "overview";
        String TMD_RATING = "vote_average";
        String TMD_RELEASE_DATE = "release_date";

        JSONObject response = new JSONObject(movieJsonStr);
        JSONArray results = response.getJSONArray("results");

        for (int i = 0;  i < results.length(); i++) {
            JSONObject movieJsonObj = results.getJSONObject(i);
            Movie movie = new Movie(
                    movieJsonObj.getString(TMD_TITLE),
                    movieJsonObj.getString(TMD_IMAGE_URL),
                    movieJsonObj.getString(TMD_PLOT),
                    movieJsonObj.getString(TMD_RATING),
                    movieJsonObj.getString(TMD_RELEASE_DATE));
            movieArrayList.add(movie);
        }

        return movieArrayList;
    }
}
