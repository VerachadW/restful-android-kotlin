package com.taskworld.android.restfulandroidkotlin.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by VerachadW on 11/14/14.
 */
public class PlayList extends RealmObject{

    private String name;
    private String description;
    private RealmList<Movie> items;

    @SerializedName("poster_path")
    private String posterPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<Movie> getItems() {
        return items;
    }

    public void setItems(RealmList<Movie> items) {
        this.items = items;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
