package com.fredrikstahl.watchlisterapp;

/**
 * Created by fredrikstahl on 23/8/2016.
 */
public class MovieDataStore {
    private static MovieDataStore instance;

    private int data;

    private MovieDataStore() {

    }

    public static synchronized MovieDataStore getInstance() {
        if (instance == null) {
            instance = new MovieDataStore();
        }
        return instance;
    }
}
