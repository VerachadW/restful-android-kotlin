package com.taskworld.android.restfulandroidkotlin.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by VerachadW on 11/14/14.
 */
public class FavoriteList extends RealmObject {

    private String id;
    private String name;
    private String description;
    private RealmList<Movie> items;

    @SerializedName("poster_path")
    private String posterPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public static class RequestBody{
        public static Map<String, String> generatePostBody(FavoriteList list) {
            HashMap<String, String> body = new HashMap<String, String>();
            body.put("name", list.name);
            body.put("description", list.description);
            return body;
        }
    }

}
