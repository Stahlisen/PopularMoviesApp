package com.fredrikstahl.watchlisterapp;

import android.util.Log;

import com.fredrikstahl.watchlisterapp.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fst on 2016-05-26.
 */
public class MovieDataParser {
    //Movie parameter names
    private static String TMD_TITLE = "original_title";
    private static String TMD_IMAGE_URL = "poster_path";
    private static String TMD_PLOT = "overview";
    private static String TMD_RATING = "vote_average";
    private static String TMD_RELEASE_DATE = "release_date";

    private static String TMD_MEDIA_TYPE = "media_type";

    //TV-show parameter names
    private static String TMD_SHOW_TITLE = "original_name";
    private static String TMD_SHOW_IMAGE_URL = "poster_path";
    private static String TMD_SHOW_PLOT = "overview";
    private static String TMD_SHOW_RATING = "vote_average";
    private static String TMD_SHOW_RELEASE_DATE = "first_air_date";

    public static String MOVIE_LABEL = "movie";
    public static String TV_LABEL = "tv";

    public static ArrayList<Movie> getMovieData(String movieJsonStr) throws JSONException{
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();

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

    public static ArrayList<Movie> getTvShowData(String movieJsonStr) throws JSONException{
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        String TMD_SHOW_TITLE = "original_name";
        String TMD_SHOW_IMAGE_URL = "poster_path";
        String TMD_SHOW_PLOT = "overview";
        String TMD_SHOW_RATING = "vote_average";
        String TMD_SHOW_RELEASE_DATE = "first_air_date";

        JSONObject response = new JSONObject(movieJsonStr);
        JSONArray results = response.getJSONArray("results");

        for (int i = 0;  i < results.length(); i++) {
            JSONObject movieJsonObj = results.getJSONObject(i);
            Movie movie = new Movie(
                    movieJsonObj.getString(TMD_SHOW_TITLE),
                    movieJsonObj.getString(TMD_SHOW_IMAGE_URL),
                    movieJsonObj.getString(TMD_SHOW_PLOT),
                    movieJsonObj.getString(TMD_SHOW_RATING),
                    movieJsonObj.getString(TMD_SHOW_RELEASE_DATE));
            movieArrayList.add(movie);
        }

        return movieArrayList;
    }

    public static HashMap<String, ArrayList<Movie>>  transformSearchResult(String jsonStr) throws JSONException {
        String TMD_TV_TITLE = "original_name";
        String TMD_MOVIE_TITLE = "original_title";

        JSONObject response = new JSONObject(jsonStr);
        JSONArray results = response.getJSONArray("results");

        ArrayList<Movie> tvShows = new ArrayList<Movie>();
        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject item = results.getJSONObject(i);
            if (item.getString(TMD_MEDIA_TYPE).equals(TV_LABEL)) {
                Movie movie = new Movie(
                        item.getString(TMD_SHOW_TITLE),
                        item.getString(TMD_SHOW_IMAGE_URL),
                        item.getString(TMD_SHOW_PLOT),
                        item.getString(TMD_SHOW_RATING),
                        item.getString(TMD_SHOW_RELEASE_DATE));
                tvShows.add(movie);
            } else if(item.getString(TMD_MEDIA_TYPE).equals(MOVIE_LABEL)) {
                Movie movie = new Movie(
                        item.getString(TMD_TITLE),
                        item.getString(TMD_IMAGE_URL),
                        item.getString(TMD_PLOT),
                        item.getString(TMD_RATING),
                        item.getString(TMD_RELEASE_DATE));
                movies.add(movie);
            } else {
            }
        }

        HashMap<String, ArrayList<Movie>> mediaTypeMap = new HashMap<String, ArrayList<Movie>>();

        mediaTypeMap.put(MOVIE_LABEL, movies);
        mediaTypeMap.put(TV_LABEL, tvShows);

        return mediaTypeMap;
    }
}
