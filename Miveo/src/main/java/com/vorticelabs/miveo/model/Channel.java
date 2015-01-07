package com.vorticelabs.miveo.model;

import android.util.Log;

import com.google.gson.Gson;

public class Channel {
    public static final String TAG = Channel.class.getSimpleName();

    public int id;
    public String name;
    public String description;
    public int isFeatured;
    public int isSponsored;
    public int isSubscribed;
    public int totalVideos;
    public int totalSubscribers;
    public String logoUrl;
    public String badgeUrl;
    public String thumbnailUrl;
    public String url;
    public String privacy;
    public long createdOn;
    public long modifiedOn;

    public String toJson(){
        return new Gson().toJson(this);
    }

    public static Channel loadFromVimofitModel(com.andressantibanez.vimofit.model.Channel data){
        Channel channel = new Channel();

        if(data != null) {
            channel.id = data.id;
            channel.name = data.name;
            channel.description = data.description;
            channel.logoUrl = data.logoUrl;
            channel.thumbnailUrl = data.thumbnail_url;
            channel.totalVideos = data.total_videos;
            channel.totalSubscribers = data.total_subscribers;
        }

        return channel;
    }
}
