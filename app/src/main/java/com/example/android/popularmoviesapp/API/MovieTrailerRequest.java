package com.example.android.popularmoviesapp.API;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by fst on 2016-06-02.
 */
public class MovieTrailerRequest {


    public String getMovieTrailerVideoId(String movieTitle) {
        String LOG_TAG = "MovieTrailerRequest.getMovieTrailerVideoId";

        HttpURLConnection urlConnection;
        BufferedReader reader;

        String resultJsonStr = "";

        try {
        String BASE_URL = "https://www.googleapis.com/youtube/v3/search";

        String API_PART_PARAM_NAME = "part";
        String API_PART_PARAM_VALUE = "id";
        String API_CHANNEL_ID_PARAM_NAME = "channelId";
        String API_CHANNEL_ID_PARAM_VALUE = "UCi8e0iOVk1fEOogdfu4YgfA";
        String API_ORDER_PARAM_NAME = "order";
        String API_ORDER_PARAM_VALUE= "relevance";
        String API_Q_PARAM_NAME = "q";
        String API_Q_PARAM_VALUE = movieTitle + "official trailer";
        String API_KEY_PARAM_NAME = "key";
        String API_KEY_PARAM_VALUE = "AIzaSyCDxALvRvBD41l4pdpIxYzfQMv0ngUKKPQ";

        Uri endPointAddress = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_PART_PARAM_NAME, API_PART_PARAM_VALUE)
                .appendQueryParameter(API_CHANNEL_ID_PARAM_NAME, API_CHANNEL_ID_PARAM_VALUE)
                .appendQueryParameter(API_ORDER_PARAM_NAME, API_ORDER_PARAM_VALUE)
                .appendQueryParameter(API_Q_PARAM_NAME, API_Q_PARAM_VALUE)
                .appendQueryParameter(API_KEY_PARAM_NAME, API_KEY_PARAM_VALUE)
                .build();

            URL url = new URL(endPointAddress.toString());
            Log.d(LOG_TAG, url.toString());

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

            resultJsonStr = stringBuffer.toString();
            Log.d(LOG_TAG, resultJsonStr);



        } catch (IOException e) {
            e.printStackTrace();
        }


        return parseVideoIdFromJson(resultJsonStr);

    }

    private String parseVideoIdFromJson(String jsonStr){

        return "gtTfd6tISfw";

    }

}
