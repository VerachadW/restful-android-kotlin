package com.taskworld.android.restfulandroidkotlin.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kittinun Vantasin on 11/13/14.
 */
public class Image {

    @SerializedName("file_path")
    private String filePath;

    @SerializedName("vote_count")
    private int voteCount;

    public static class PosterList {

        @SerializedName("posters")
        private List<Image> results;

        public List<Image> getResults() {
            return results;
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
