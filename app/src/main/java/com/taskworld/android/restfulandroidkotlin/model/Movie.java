package com.taskworld.android.restfulandroidkotlin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */
public class Movie extends RealmObject {

    private int id;
    private String title;
    private String overview;
    private float popularity;

    @SerializedName("poster_path")
    private String posterPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public static class ResultList {
        private List<Movie> results;

        public void setResults(List<Movie> list) {
            results = new ArrayList<Movie>(list);
        }

        public List<Movie> getResults() {
            return results;
        }
    }

}
