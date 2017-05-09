package com.android.udacity.dinesh.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.udacity.dinesh.popularmovies.utilities.MoviesDBJsonUtils;
import com.android.udacity.dinesh.popularmovies.utilities.NetworkUtils;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieDBAdapter.MovieDBAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private MovieDBAdapter mMovieDBAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private MovieDataQueryTask movieDataQueryTask;

    public Context context;

    private static final String SORTBY_OPTION_KEY = "sortby_option_key";
    private int selectedSortByOptionID = -1;
    MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_PopularMovies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieDBAdapter = new MovieDBAdapter(this);

        mRecyclerView.setAdapter(mMovieDBAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        showMovieDataView();

        movieDataQueryTask = new MovieDataQueryTask();

        if(savedInstanceState != null) {
            selectedSortByOptionID = savedInstanceState.getInt(SORTBY_OPTION_KEY);
        }

        switch (selectedSortByOptionID) {
            case R.id.sort_by_popularMovies :
                movieDataQueryTask.execute(getString(R.string.SORT_BY_POPULAR));
                break;

            case R.id.sort_by_rating :
                movieDataQueryTask.execute(getString(R.string.SORT_BY_RATING));
                break;

            default:
                movieDataQueryTask.execute(getString(R.string.SORT_BY_POPULAR));
                break;
        }

    }
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(MovieDetails movieDetails) {
        context = MainActivity.this;
        Class destinationActivity = MovieDetailsActivity.class;
        Intent movieDetailsIntent = new Intent(context, destinationActivity);

        movieDetailsIntent.putExtra(Intent.EXTRA_TEXT, movieDetails);
        startActivity(movieDetailsIntent);
    }

    public class MovieDataQueryTask extends AsyncTask <String, Void, ArrayList<MovieDetails>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
            context = MainActivity.this;
        }

        @Override
        protected ArrayList<MovieDetails> doInBackground(String... query) {
            String querySortString = query[0];
            URL theMovieDBRequestURL = NetworkUtils.buildUrl(querySortString, context);
            String movieResults = null;
            try {
                movieResults = NetworkUtils.getResponseFromHttpUrl(theMovieDBRequestURL);

                ArrayList<MovieDetails> movieDetailsResultsArray = MoviesDBJsonUtils.getResultsAsMovieDetailsObjectArrayList(movieResults, context);

                return movieDetailsResultsArray;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieDetails> movieDetailsResultsArray) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(movieDetailsResultsArray != null){
                showMovieDataView();
                mMovieDBAdapter.setMovieListData(movieDetailsResultsArray);
            }
            else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_movies, menu);

        switch (selectedSortByOptionID) {
            case R.id.sort_by_popularMovies :
                menuItem = (MenuItem) menu.findItem(R.id.sort_by_popularMovies);
                menuItem.setChecked(true);
                break;

            case R.id.sort_by_rating :
                menuItem = (MenuItem) menu.findItem(R.id.sort_by_rating);
                menuItem.setChecked(true);
                break;

            default:
                menuItem = (MenuItem) menu.findItem(R.id.sort_by_popularMovies);
                menuItem.setChecked(true);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //MenuPopupWindow.MenuDropDownListView menuDropDownListView = new MenuPopupWindow.MenuDropDownListView(this, true);
        //menuDropDownListView.getCheckedItemIds();

        switch (item.getItemId()) {
            case R.id.sort_by_popularMovies:
                movieDataQueryTask = new MovieDataQueryTask();
                movieDataQueryTask.execute(getString(R.string.SORT_BY_POPULAR));
                item.setChecked(true);
                selectedSortByOptionID = item.getItemId();
                return true;
            case R.id.sort_by_rating:
                movieDataQueryTask = new MovieDataQueryTask();
                movieDataQueryTask.execute(getString(R.string.SORT_BY_RATING));
                item.setChecked(true);
                selectedSortByOptionID = item.getItemId();
                return true;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORTBY_OPTION_KEY, selectedSortByOptionID);
    }
}
