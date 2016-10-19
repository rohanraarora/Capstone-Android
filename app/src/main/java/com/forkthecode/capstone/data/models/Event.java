package com.forkthecode.capstone.data.models;

import com.google.gson.annotations.SerializedName;

import java.security.PublicKey;

/**
 * Created by rohanarora on 19/10/16.
 */

public class Event {

    @SerializedName("id")
    private long serverId;

    private String title;

    private String description;

    @SerializedName("from_timestamp")
    private long fromTimestamp;

    @SerializedName("to_timestamp")
    private long toTimestamp;

    @SerializedName("ticket_url")
    private String titcketURL;

    @SerializedName("ticket_price")
    private int ticketPrice;

    @SerializedName("venue_title")
    private String venueTitle;

    @SerializedName("venue_lat")
    private double venueLat;

    @SerializedName("venue_long")
    private double venueLong;

    @SerializedName("cover_image_url")
    private String coverImageURL;

    public Event(){

    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFromTimestamp() {
        return fromTimestamp;
    }

    public void setFromTimestamp(long fromTimestamp) {
        this.fromTimestamp = fromTimestamp;
    }

    public long getToTimestamp() {
        return toTimestamp;
    }

    public void setToTimestamp(long toTimestamp) {
        this.toTimestamp = toTimestamp;
    }

    public String getTitcketURL() {
        return titcketURL;
    }

    public void setTitcketURL(String titcketURL) {
        this.titcketURL = titcketURL;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getVenueTitle() {
        return venueTitle;
    }

    public void setVenueTitle(String venueTitle) {
        this.venueTitle = venueTitle;
    }

    public double getVenueLat() {
        return venueLat;
    }

    public void setVenueLat(double venueLat) {
        this.venueLat = venueLat;
    }

    public double getVenueLong() {
        return venueLong;
    }

    public void setVenueLong(double venueLong) {
        this.venueLong = venueLong;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public boolean isPastEvent(){
        return System.currentTimeMillis() >= toTimestamp * 1000L;
    }


}
