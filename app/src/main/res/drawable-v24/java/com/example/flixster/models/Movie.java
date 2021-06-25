package com.example.flixster.models;

import android.content.Context;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@Parcel
public class Movie {

    public static final String TAG = "Movie";
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double voteAverage;
    Integer id;
    String YOUTUBE_URL;
    String movieKey;

    public Movie() {}

    public Movie(JSONObject jsonObject, Context context) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
        YOUTUBE_URL = String.format("https://api.themoviedb.org/3/movie/"+String.valueOf(id)+"/videos?api_key=%s",context.getString(R.string.tmdb_api_key));

    }

    public void getMovieKeyfromJson() throws JSONException {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(YOUTUBE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG,"onSuccess response");
                JSONArray results = null;
                try {
                    results = json.jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < results.length(); j++) {
                    try {
                        JSONObject jsonObject = results.getJSONObject(j);
                        Log.i(TAG,"Got a " + jsonObject.getString("site") + "site.");
                        if (jsonObject.getString("site").equals("YouTube")) {
                            movieKey = jsonObject.getString("key");
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "onFailure response", throwable);
                Log.e(TAG, YOUTUBE_URL, throwable);
            }
        });
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray,Context context) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i), context));
            movies.get(i).getMovieKeyfromJson();
        }
        return movies;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getMovieKey() {
        return movieKey;
    }
}
