package com.forkthecode.capstone.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rohanarora on 19/10/16.
 */

public class News implements Serializable {

    @SerializedName("id")
    private long serverId;

    private String title;

    private String content;

    private String url;

    @SerializedName("cover_image_url")
    private String coverImageUrl;

    @SerializedName("timestamp")
    private long timeStamp;

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public News(){


    }


}
