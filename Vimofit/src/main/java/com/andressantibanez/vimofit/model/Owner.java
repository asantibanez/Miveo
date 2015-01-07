package com.andressantibanez.vimofit.model;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("display_name") public String displayName;
    public int id;
    /*
    -- Example data --
    "display_name": "Cirkus",
    "id": "942472",
    "is_plus": "1",
    "is_pro": "0",
    "is_staff": "0",
    "profileurl": "http:\/\/vimeo.com\/user942472",
    "realname": "Cirkus",
    "username": "user942472",
    "videosurl": "http:\/\/vimeo.com\/user942472\/videos"
    */
}
