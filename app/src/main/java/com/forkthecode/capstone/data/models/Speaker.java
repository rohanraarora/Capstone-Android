package com.forkthecode.capstone.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rohanarora on 20/10/16.
 *
 */

public class Speaker implements Serializable{

    @SerializedName("id")
    private long serverId;

    private long eventId;

    private String name;

    private String bio;

    @SerializedName("profile_url")
    private String profileURL;

    @SerializedName("profile_pic_url")
    private String profilePicURL;

    public Speaker(){

    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }
}
