package com.example.android.popularmoviesapp.Model;

import java.io.Serializable;

/**
 * Created by fst on 2016-05-26.
 */
public class Movie  implements Serializable{
    String title;
    String imageUrl;
    String plot;
    String rating;
    String releaseDate;

    public Movie(String title, String imageUrl, String plot, String rating, String releaseDate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlot() {
        return plot;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
