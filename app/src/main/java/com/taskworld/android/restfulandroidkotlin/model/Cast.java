package com.taskworld.android.restfulandroidkotlin.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */
public class Cast {

    private String character;

    private String name;

    @SerializedName("profile_path")
    private String profilePath;

    public static class CastList {
        private List<Cast> cast;

        public List<Cast> getCasts() {
            return cast;
        }
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
