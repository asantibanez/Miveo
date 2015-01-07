package com.andressantibanez.vimofit.model;

import com.google.gson.annotations.SerializedName;

public class Channel {
    public int id;
    public int is_featured;
    public int is_sponsored;
    public int is_subscribed;
    public String name;
    public String description;
    public String created_on;
    public String modified_on;
    public int total_videos;
    public int total_subscribers;

    @SerializedName("logo_url")
    public String logoUrl;

    public String badge_url;
    public String thumbnail_url;
    public String url[];
    public String privacy;
}
