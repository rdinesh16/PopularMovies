package com.android.udacity.dinesh.popularmovies;

import java.io.Serializable;
import java.util.ArrayList;

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
    private ArrayList<String> trailersList;

    public MovieDetails(int ID, String originalTitle, String posterPath, String overview, String releaseDate, Double voteAverage, Double popularity, String backdropPath, ArrayList<String> trailersList) {
        this.id = ID;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.backdropPath = backdropPath;
        this.trailersList = trailersList;
    }

    public int getId() {
        return id;
    }

    String getOriginalTitle() {
        return originalTitle;
    }

    String getPosterPath() {
        return posterPath;
    }

    String getOverview() {
        return overview;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    Double getVoteAverage() {
        return voteAverage;
    }

    Double getPopularity() {
        return popularity;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public ArrayList<String> getTrailersList() { return trailersList; }
}
