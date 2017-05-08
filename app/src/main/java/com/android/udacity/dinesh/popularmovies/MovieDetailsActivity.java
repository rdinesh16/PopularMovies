package com.android.udacity.dinesh.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.udacity.dinesh.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

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

        Intent receivedIntent = getIntent();

        if(receivedIntent.hasExtra(Intent.EXTRA_TEXT)) {
            MovieDetails movieDetails = (MovieDetails) receivedIntent.getSerializableExtra(Intent.EXTRA_TEXT);
            if(movieDetails != null){
                Uri backDropImageUri = NetworkUtils.buildImageUrl(movieDetails.getPosterPath(), MovieDetailsActivity.this);
                Picasso.with(this).load(backDropImageUri).into(backdropImageView);
                titleTextView.setText(movieDetails.getOriginalTitle().toUpperCase());
                releaseDateTextView.setText(movieDetails.getReleaseDate());
                voteAverageTextView.setText(Double.toString(movieDetails.getVoteAverage()));
                popularityTextView.setText(Double.toString(movieDetails.getPopularity()));
                synopsisTextView.setText(movieDetails.getOverview());
            }
        }
    }
}
