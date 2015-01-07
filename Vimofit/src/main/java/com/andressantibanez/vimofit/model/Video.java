package com.andressantibanez.vimofit.model;

import com.andressantibanez.vimofit.model.catalogs.Thumbnails;

public class Video {
    public String embed_privacy;
    public int id;
    public int is_hd;
    public int is_transcoding;
    public String privacy;
    public String title;
    public String description;
    public String upload_date;
    public String modified_date;
    public int number_of_plays;
    public int number_of_likes;
    public int number_of_comments;
    public Owner owner;
    public Thumbnails thumbnails;
}
