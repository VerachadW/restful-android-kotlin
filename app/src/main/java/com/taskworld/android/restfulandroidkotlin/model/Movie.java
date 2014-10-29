package com.taskworld.android.restfulandroidkotlin.model;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */
public class Movie extends RealmObject {

    private int id;
    private String title;
    private float popularity;

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

    public static class ResultList {

        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}
