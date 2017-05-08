package com.android.udacity.dinesh.popularmovies;

import java.io.Serializable;

/**
 * Created by dines_000 on 1/27/2017.
 */

public class MovieDetails implements Serializable{
    private int id;
    private String originalTitle;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private Double voteAverage;
    private Double popularity;
    private String backdropPath;

    public MovieDetails(int ID, String originalTitle, String posterPath, String overview, String releaseDate, Double voteAverage, Double popularity, String backdropPath) {
        this.id = ID;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
