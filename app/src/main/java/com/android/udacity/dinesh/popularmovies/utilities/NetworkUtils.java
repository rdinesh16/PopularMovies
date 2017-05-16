package com.android.udacity.dinesh.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.udacity.dinesh.popularmovies.R;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dines_000 on 1/27/2017.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static URL buildUrl(String query, Context context) {
        Uri buildUri = Uri.parse(context.getResources().getString(R.string.THE_MOVIE_DATABASE_API))
                .buildUpon()
                .appendPath(context.getResources().getString(R.string.MOVIE))
                .appendPath(query)
                .appendQueryParameter(context.getResources().getString(R.string.API_KEY_PARAM), context.getResources().getString(R.string.API_KEY))
                .build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static Uri buildImageUrl(String posterPath, Context context) {
        Uri buildUri = Uri.parse(context.getResources().getString(R.string.BASE_IMAGE_URL))
                .buildUpon()
                .appendPath(context.getResources().getString(R.string.IMAGE_SIZE_DEFAULT))
                .appendPath(posterPath.substring(1))
                .build();
        return buildUri;
    }

    public static URL buildTrailersUrl(int movieID, Context context) {
        Uri buildUri = Uri.parse(context.getResources().getString(R.string.THE_MOVIE_DATABASE_API))
                .buildUpon()
                .appendPath(context.getResources().getString(R.string.MOVIE))
                .appendPath((Integer.toString(movieID)))
                .appendPath(context.getResources().getString(R.string.VIDEOS))
                .appendQueryParameter(context.getResources().getString(R.string.API_KEY_PARAM), context.getResources().getString(R.string.API_KEY))
                .build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
