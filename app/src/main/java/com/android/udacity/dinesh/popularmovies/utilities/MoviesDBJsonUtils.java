package com.android.udacity.dinesh.popularmovies.utilities;


import android.content.Context;
import com.android.udacity.dinesh.popularmovies.MovieDetails;
import com.android.udacity.dinesh.popularmovies.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


/**
 * Utility functions to handle TheMovieDB popular movies JSON data.
 */

public final class MoviesDBJsonUtils {

    public static JSONArray getMovieResultsFromJson(String movieResults, Context context) throws JSONException {

        JSONArray parsedResults = null;

        JSONObject movieResultsJson = new JSONObject(movieResults);

        if(movieResultsJson.has(context.getResources().getString(R.string.JSON_RESULTS))) {
            parsedResults = movieResultsJson.getJSONArray(context.getResources().getString(R.string.JSON_RESULTS));
        }
        return parsedResults;
    }


    public static ArrayList<MovieDetails> createResultsAsMovieDetailsObjectArrayList(JSONArray movieResultsJson, Context context) throws JSONException{

        ArrayList<MovieDetails> movieDetails= new ArrayList<>();
        ArrayList<String> trailersList= new ArrayList<>();
        trailersList = null;
        //movieDetails = null;

        for(int i=0; i < movieResultsJson.length(); i++) {
            JSONObject movieResultObject =  (JSONObject)movieResultsJson.get(i);
            if(movieResultObject.has(context.getResources().getString(R.string.JSON_ID))) {
                //Extract Trailers list
                //trailersList = getTrailersList(movieResultObject.getInt(context.getResources().getString(R.string.JSON_ID)), context);
                //Log.i(MoviesDBJsonUtils.class.getSimpleName(), "Final extracted trailer list : " + trailersList);

                movieDetails.add(
                    createMovieDetailsObject(
                            movieResultObject.getInt(context.getResources().getString(R.string.JSON_ID)),
                            movieResultObject.getString(context.getResources().getString(R.string.JSON_ORIGINAL_TITLE)),
                            movieResultObject.getString(context.getResources().getString(R.string.JSON_POSTER_PATH)),
                            movieResultObject.getString(context.getResources().getString(R.string.JSON_OVERVIEW)),
                            movieResultObject.getString(context.getResources().getString(R.string.JSON_RELEASE_DATE)),
                            movieResultObject.getDouble(context.getResources().getString(R.string.JSON_VOTE_AVERAGE)),
                            movieResultObject.getDouble(context.getResources().getString(R.string.JSON_POPULARITY)),
                            movieResultObject.getString(context.getResources().getString(R.string.JSON_BACKDROP_PATH)),
                            trailersList)
                );
            }
        }
        return movieDetails;
    }

    public static MovieDetails createMovieDetailsObject (int ID, String originalTitle, String posterPath, String overview, String releaseDate, Double voteAverage, Double popularity, String backdrop_path, ArrayList<String> trailersList) {
        MovieDetails movieDetails = new MovieDetails(ID, originalTitle, posterPath, overview, releaseDate, voteAverage, popularity, backdrop_path, trailersList);
        return movieDetails;
    }

    public static ArrayList<MovieDetails> getResultsAsMovieDetailsObjectArrayList(String movieResults, Context context) throws JSONException{
        JSONArray movieResultsArray = getMovieResultsFromJson(movieResults, context);
        return createResultsAsMovieDetailsObjectArrayList(movieResultsArray, context);
    }

    public static ArrayList<String> getTrailersListAsArrayList(String trailerResults, Context context) throws JSONException{
        ArrayList<String> trailersList = new ArrayList<>();
        JSONArray trailerResultsJson = getMovieResultsFromJson(trailerResults, context);

        for(int i=0; i < trailerResultsJson.length(); i++) {
            JSONObject movieResultObject =  (JSONObject)trailerResultsJson.get(i);
            if(movieResultObject.has(context.getResources().getString(R.string.JSON_ID)) && movieResultObject.getString(context.getResources().getString(R.string.JSON_FIELD_TYPE)).equals(context.getResources().getString(R.string.JSON_FIELD_TRAILER))) {
                trailersList.add(movieResultObject.getString(context.getResources().getString(R.string.JSON_FIELD_KEY)));
            }
        }
        return trailersList;
    }

}
