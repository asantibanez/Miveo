package com.vorticelabs.miveo.model;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Video {
    public static final String TAG = Video.class.getSimpleName();

    public int id;
    public String title;
    public String description;
    public String thumbnailUrl;
    public String uploadedBy;

    public Owner owner = new Owner();
    public ArrayList<Tag> tags = new ArrayList<>();

    public static Video loadFromVimofitModel(com.andressantibanez.vimofit.model.Video data){
        Video video = new Video();
        video.id = data.id;
        video.title = data.title;
        video.description = data.description;
        video.uploadedBy = data.owner.displayName;

        //Owner
        video.owner = Owner.loadFromVimofitModel(data.owner);

        //Get good thumbnail
        if(data.thumbnails.thumbnail.size() > 0) {
            for (int i = 0; i < data.thumbnails.thumbnail.size(); i++) {
                video.thumbnailUrl = data.thumbnails.thumbnail.get(i)._content;
                if(data.thumbnails.thumbnail.get(i).width >= 300)
                    break;
            }
        }

        //Tags
        if(data.tags != null && data.tags.tag.size() > 0) {
            for (int i = 0; i < data.tags.tag.size(); i++) {
                video.tags.add(Tag.loadFromVimofitModel(data.tags.tag.get(i)));
            }
        }

        return video;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
