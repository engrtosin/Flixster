package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieTrailerActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    Context context;
    Movie movie;
    int radius = 30;

    public static final String TAG = "DetailsAdapter";

    public DetailsAdapter(Context context, Movie movie) {
        this.context = context;
        this.movie = movie;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder");
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RatingBar rbVoteAverage;
        ImageView ivPlayBtn;
        Switch swOverview;
        TextView tvOverviewTitle;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            rbVoteAverage = itemView.findViewById(R.id.rbVoteAverage);
            ivPlayBtn = itemView.findViewById(R.id.ivPlayBtn);
            swOverview = itemView.findViewById(R.id.swOverview);
            tvOverviewTitle = itemView.findViewById(R.id.tvOverviewTitle);
            swOverview.setChecked(true);
            tvOverviewTitle.setVisibility(View.VISIBLE);
            tvOverview.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load("http://via.placeholder.com/300.png")
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(radius, 0))
                    .into(ivPoster);

            Glide.with(context)
                    .load(R.drawable.play_button_overlay)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(30, 0))
                    .into(ivPlayBtn);

//            itemView.setOnClickListener(this);
//            ivPoster.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (movie.getMovieKey() != null) {
//                        Intent intent = new Intent(context, MovieTrailerActivity.class);
//                        intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie.getMovieKey()));
//                        Log.i(TAG, "onClickImage");
//                        context.startActivity(intent);
//                    }
//                }
//            });
            ivPlayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (movie.getMovieKey() != null) {
                        Intent intent = new Intent(context, MovieTrailerActivity.class);
                        intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie.getMovieKey()));
                        Log.i(TAG, "onClickImage");
                        context.startActivity(intent);
                    }
                }
            });
            swOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvOverview.getVisibility() == View.VISIBLE) {
                        tvOverview.setVisibility(View.INVISIBLE);
                        tvOverviewTitle.setVisibility(View.INVISIBLE);
                    }
                    else if (tvOverview.getVisibility() == View.INVISIBLE) {
                        tvOverview.setVisibility(View.VISIBLE);
                        tvOverviewTitle.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            float voteAverage = movie.getVoteAverage().floatValue();
            rbVoteAverage.setRating(voteAverage/2.0f);

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

            imageUrl = movie.getBackdropPath();

            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, 0)).into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            if (movie.getMovieKey() != null) {
                Intent intent = new Intent(context, MovieTrailerActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie.getMovieKey()));
                Log.i(TAG, "onClickImage");
                context.startActivity(intent);
            }
        }
    }
}
