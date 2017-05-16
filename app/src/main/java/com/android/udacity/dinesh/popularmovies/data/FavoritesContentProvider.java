package com.android.udacity.dinesh.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.udacity.dinesh.popularmovies.data.FavoriteMoviesContract.FavoriteMoviesEntry;

/**
 * Created by dines_000 on 5/16/2017.
 */

public class FavoritesContentProvider extends ContentProvider {

    public static final int FAVOURITES = 100;
    public static final int FAVOURITES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY, FavoriteMoviesContract.PATH_FAVORITES, FAVOURITES);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY, FavoriteMoviesContract.PATH_FAVORITES + "/#", FAVOURITES_WITH_ID);

        return uriMatcher;
    }

    private FavoritesDbHelper mFavoritesDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavoritesDbHelper = new FavoritesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mFavoritesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch(match) {
            case FAVOURITES:
                long id = db.insert(FavoriteMoviesEntry.TABLE_NAME, null, contentValues);
                if(id > 0) {
                    Log.i(FavoritesContentProvider.class.getSimpleName(), "Successfully inserted into Favorites Table. Content Values : " + contentValues.toString());
                    returnUri = ContentUris.withAppendedId(FavoriteMoviesEntry.CONTENT_URI, id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI : " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
