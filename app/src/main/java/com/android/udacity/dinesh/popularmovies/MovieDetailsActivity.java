package com.android.udacity.dinesh.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.udacity.dinesh.popularmovies.data.FavoriteMoviesContract.FavoriteMoviesEntry;
import com.android.udacity.dinesh.popularmovies.utilities.MoviesDBJsonUtils;
import com.android.udacity.dinesh.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<String>>{

    private static final int MOVIE_TRAILER_LIST_LOADER = 17657;

    private static final String MOVIE_TRAILER_LIST_QUERY_URL_EXTRA = "trailerListQuery";

    private TextView trailersListTextView;

    MovieDetails movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView titleTextView = (TextView) findViewById(R.id.movie_title);
        ImageView backdropImageView = (ImageView) findViewById(R.id.movie_backdrop);
        TextView releaseDateTextView = (TextView)findViewById(R.id.movie_release_date);
        TextView voteAverageTextView = (TextView)findViewById(R.id.movie_vote_average);
        TextView popularityTextView = (TextView)findViewById(R.id.movie_popularity);
        TextView synopsisTextView = (TextView)findViewById(R.id.movie_synopsis);
        //trailersListTextView = (TextView)findViewById(R.id.movie_trailer_list);

        Intent receivedIntent = getIntent();

        if(receivedIntent.hasExtra(Intent.EXTRA_TEXT)) {
            movieDetails = (MovieDetails) receivedIntent.getSerializableExtra(Intent.EXTRA_TEXT);
            if(movieDetails != null){
                Uri backDropImageUri = NetworkUtils.buildImageUrl(movieDetails.getPosterPath(), MovieDetailsActivity.this);
                Picasso.with(this).load(backDropImageUri).into(backdropImageView);
                titleTextView.setText(movieDetails.getOriginalTitle().toUpperCase());
                releaseDateTextView.setText(movieDetails.getReleaseDate());
                voteAverageTextView.setText(Double.toString(movieDetails.getVoteAverage()));
                popularityTextView.setText(Double.toString(movieDetails.getPopularity()));
                synopsisTextView.setText(movieDetails.getOverview());
                //trailersListTextView.setText(movieDetails.getTrailersList().toString());

                //setTrailers(movieDetails.getId());
                makeTrailerListSearchQuery(movieDetails.getId());
            }
        }

        getSupportLoaderManager().initLoader(MOVIE_TRAILER_LIST_LOADER, null, this);
    }


    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<String>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args == null) {
                    return;
                }
                // TODO Enable ProgressBar here in OnStartLoading Function
                forceLoad();
            }

            @Override
            public ArrayList<String> loadInBackground() {
                String trailerListQueryUrlString = args.getString(MOVIE_TRAILER_LIST_QUERY_URL_EXTRA);
                if(trailerListQueryUrlString == null || TextUtils.isEmpty(trailerListQueryUrlString))
                    return null;

                String trailerListSearchQueryResults = null;

                //String trailerResultsFromURL = null;
                try {
                    URL trailerListQueryUrl = new URL(trailerListQueryUrlString);
                    trailerListSearchQueryResults = NetworkUtils.getResponseFromHttpUrl(trailerListQueryUrl);
                    return MoviesDBJsonUtils.getTrailersListAsArrayList(trailerListSearchQueryResults, getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> data) {
        // TODO Make progress bar invisible. (If implemented)
        if(data != null && data.size() != 0) {
            trailersListTextView = (TextView)findViewById(R.id.movie_trailer_list);
            trailersListTextView.setText(data.toString());
        } else {
            // TODO Show the user that No Trailers available or ShowErrorMessage
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {

    }

    private void makeTrailerListSearchQuery(int id) {
        URL trailersURL = NetworkUtils.buildTrailersUrl(id, this);

        Bundle trailerListQueryBundle = new Bundle();
        trailerListQueryBundle.putString(MOVIE_TRAILER_LIST_QUERY_URL_EXTRA, trailersURL.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ArrayList<String>> trailerListQueryLoader = loaderManager.getLoader(MOVIE_TRAILER_LIST_LOADER);

        if(trailerListQueryLoader == null) {
            loaderManager.initLoader(MOVIE_TRAILER_LIST_LOADER, trailerListQueryBundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_TRAILER_LIST_LOADER, trailerListQueryBundle, this);
        }
    }

    public void onClickFavorites(View view) {
        if(movieDetails == null) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteMoviesEntry.COLUMN_MOVIE_ID, movieDetails.getId());
        contentValues.put(FavoriteMoviesEntry.COLUMN_ORIGINAL_TITLE, movieDetails.getOriginalTitle());
        contentValues.put(FavoriteMoviesEntry.COLUMN_VOTE_AVERAGE, movieDetails.getVoteAverage());
        contentValues.put(FavoriteMoviesEntry.COLUMN_POPULARITY, movieDetails.getPopularity());
        contentValues.put(FavoriteMoviesEntry.COLUMN_RELEASE_DATE, movieDetails.getReleaseDate());
        contentValues.put(FavoriteMoviesEntry.COLUMN_OVERVIEW, movieDetails.getOverview());

        Uri uri = getContentResolver().insert(FavoriteMoviesEntry.CONTENT_URI, contentValues);

        if(uri != null){
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
