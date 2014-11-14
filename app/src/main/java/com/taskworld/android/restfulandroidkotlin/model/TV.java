package com.taskworld.android.restfulandroidkotlin.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */
public class TV extends RealmObject {

    private int id;
    private String name;
    private float popularity;

    @SerializedName("first_air_date")
    private Date firstAirDate;

    @SerializedName("poster_path")
    private String posterPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public Date getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(Date firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public static class ResultList {

        private List<TV> results;

        public List<TV> getResults() {
            return results;
        }
    }
}
