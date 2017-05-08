package com.android.udacity.dinesh.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.udacity.dinesh.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dines_000 on 1/27/2017.
 */

public class MovieDBAdapter extends RecyclerView.Adapter <MovieDBAdapter.MovieDBAdapterViewHolder> {

    //TODO : Remove this later if Array List Works
    //private String[] mMoviePosterPath;

    private ArrayList<MovieDetails> mMovieDetailsResultsArray;
    Context current_Context;
    final private MovieDBAdapterOnClickHandler mOnClickHandler;

    public interface MovieDBAdapterOnClickHandler {
        public void onListItemClick(MovieDetails movieDetails);
    }
    public MovieDBAdapter(MovieDBAdapterOnClickHandler mOnClickHandler) {
        this.mOnClickHandler = mOnClickHandler;
    }


    public void setMovieListData(ArrayList<MovieDetails> movieDetailsResultsArray) {
        this.mMovieDetailsResultsArray = movieDetailsResultsArray;
        notifyDataSetChanged();
    }


    @Override
    public MovieDBAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        this.current_Context = context;
        int layoutIdForListItem = R.layout.movie_poster;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        MovieDBAdapterViewHolder movieDBAdapterViewHolder = new MovieDBAdapterViewHolder(view);

        return movieDBAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieDBAdapterViewHolder holder, int position) {
        //holder.mMovieTitleView.setText(mMoviePosterPath[position]);
        MovieDetails movie = mMovieDetailsResultsArray.get(position);
        String posterPath = movie.getPosterPath();
        //Log.i(this.getClass().getSimpleName(), "onBindViewHolder : " + posterPath);
        Uri PosterImageUri = NetworkUtils.buildImageUrl(posterPath, this.current_Context);
        Picasso.with(current_Context).load(PosterImageUri).into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if(mMovieDetailsResultsArray != null)
            return mMovieDetailsResultsArray.size();
        return 0;
    }

    public class MovieDBAdapterViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        //public final TextView mMovieTitleView;
        public final ImageView mMovieImageView;

        public MovieDBAdapterViewHolder(View itemView) {
            super(itemView);
            //mMovieTitleView = (TextView) itemView.findViewById(R.id.movie_title_view);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.movie_image_view);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickHandler.onListItemClick(mMovieDetailsResultsArray.get(position));
        }
    }
}
