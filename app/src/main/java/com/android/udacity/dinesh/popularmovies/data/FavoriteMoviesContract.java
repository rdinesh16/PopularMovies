package com.android.udacity.dinesh.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dines_000 on 5/16/2017.
 */

public class FavoriteMoviesContract {


    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.android.udacity.dinesh.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_FAVORITES = "favorites";


    public static final class FavoriteMoviesEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_FAVORITES)
                .build();

        // FavouriteMovies table and column names
        public static final String TABLE_NAME = "favorites";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the coumns below
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";

    }
}
