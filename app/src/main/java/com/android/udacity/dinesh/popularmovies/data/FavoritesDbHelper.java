package com.android.udacity.dinesh.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.udacity.dinesh.popularmovies.data.FavoriteMoviesContract.FavoriteMoviesEntry;

/**
 * Created by dines_000 on 5/16/2017.
 */

public class FavoritesDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "favouritesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;

    //Constructor
    FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + FavoriteMoviesEntry.TABLE_NAME + " (" +
                FavoriteMoviesEntry.COLUMN_MOVIE_ID    + " INTEGER PRIMARY KEY, " +
                FavoriteMoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_POPULARITY + " TEXT NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_OVERVIEW    + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
