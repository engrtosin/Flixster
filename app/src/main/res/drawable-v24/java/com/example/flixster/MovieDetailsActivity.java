package com.example.flixster;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flixsterfinal.adapters.DetailsAdapter;
import com.example.flixsterfinal.databinding.ActivityMovieDetailsBinding;
import com.example.flixsterfinal.models.Movie;
import com.example.flixsterfinal.R;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;
    DetailsAdapter adapter;
    int radius = 30;

    public static final String TAG = "MovieDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        // assign text views and rating bars
//        tvTitle = findViewById(R.id.tvTitle);
//        tvOverview = findViewById(R.id.tvOverview);
//        rbVoteAverage = findViewById(R.id.rbVoteAverage);
//        ivPoster = findViewById(R.id.ivPoster);
        setContentView(R.layout.activity_movie_details);
        RecyclerView rvDetails = findViewById(R.id.rvDetails);

        // unwrap movie from parcel
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, String.format("Showing details for %s", movie.getTitle()));
        adapter = new DetailsAdapter(this,movie);
//        binding.rvDetails.setAdapter(adapter);
        rvDetails.setAdapter(adapter);
//        Log.d(TAG,binding.rvDetails.getAdapter().toString());
        Log.d(TAG,rvDetails.getAdapter().toString());

//        binding.rvDetails.setLayoutManager(new LinearLayoutManager(this));
        rvDetails.setLayoutManager(new LinearLayoutManager(this));

        // get image
//        String imageUrl;
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Glide.with(this)
//                    .load("http://via.placeholder.com/300.png")
//                    .placeholder(R.drawable.flicks_backdrop_placeholder)
//                    .centerCrop()
//                    .transform(new RoundedCornersTransformation(radius, 0))
//                    .into(binding.ivPoster);
//            imageUrl = movie.getBackdropPath();
//        }
//        else {
//            Glide.with(this)
//                    .load("http://via.placeholder.com/300.png")
//                    .placeholder(R.drawable.flicks_movie_placeholder)
//                    .centerCrop()
//                    .transform(new RoundedCornersTransformation(radius, 0))
//                    .into(binding.ivPoster);
//            imageUrl = movie.getPosterPath();
//        }
//        // post image
//        Glide.with(this).load(imageUrl).centerCrop().transform(new RoundedCornersTransformation(radius, 0)).into(ivPoster);
//
//        // convert vote average to 0 to 5 and set
//        float voteAverage = movie.getVoteAverage().floatValue();
//        binding.rbVoteAverage.setRating(voteAverage/2.0f);
    }
}