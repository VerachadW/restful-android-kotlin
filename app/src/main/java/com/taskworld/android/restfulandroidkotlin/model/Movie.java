package com.taskworld.android.restfulandroidkotlin.model;

import java.util.ArrayList;

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */
public class Movie {

    private int id;
    private String title;
    private float popularityScore;

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

    public float getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(float popularityScore) {
        this.popularityScore = popularityScore;
    }

    public static class List extends ArrayList<Movie> {

    }
}
