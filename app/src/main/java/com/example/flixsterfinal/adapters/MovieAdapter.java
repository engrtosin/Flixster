package com.example.flixsterfinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixsterfinal.MovieDetailsActivity;
import com.example.flixsterfinal.R;
import com.example.flixsterfinal.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;
    int radius = 30;

    public static final String TAG = "MovieAdapter";

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder");
        // Get the movie at the position
        Movie movie = movies.get(position);
        // Bind the movie to the view holder
        holder.bind(movie);
    }

    // Return the number of movies
    @Override
    public int getItemCount() {

        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            Glide.with(context)
                    .load("http://via.placeholder.com/300.png")
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(radius, 0))
                    .into(ivPoster);

            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            Log.i("MovieAdapter",movie.getTitle());
            tvTitle.setText(movie.getTitle());

            String[] textArray = movie.getOverview().split(" ");
            if (textArray.length > 60) {
                textArray = Arrays.copyOfRange(textArray,0,60);
                textArray[59] = "...";
            }
            Log.d(TAG,"length of text array: " + String.valueOf(textArray.length));
            tvOverview.setText(String.join(" ",textArray));

            String imageUrl;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context)
                        .load("http://via.placeholder.com/300.png")
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .centerCrop()
                        .transform(new RoundedCornersTransformation(radius, 0))
                        .into(ivPoster);
                imageUrl = movie.getBackdropPath();
            }
            else {
                Glide.with(context)
                        .load("http://via.placeholder.com/300.png")
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .centerCrop()
                        .transform(new RoundedCornersTransformation(radius, 0))
                        .into(ivPoster);
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, 0)).into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                // create intent
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show activity
                Log.i(TAG,"OnCick");
                context.startActivity(intent);
            }
        }
    }
}
