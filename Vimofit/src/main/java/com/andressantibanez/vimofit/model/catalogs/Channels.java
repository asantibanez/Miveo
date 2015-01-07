package com.andressantibanez.vimofit.model.catalogs;

import com.andressantibanez.vimofit.model.Channel;
import com.andressantibanez.vimofit.model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Channels {
    @SerializedName("on_this_page")
    public int onThisPage;

    public int page;

    @SerializedName("perpage")
    public int perPage;

    public int total;
    public ArrayList<Channel> channel;
}
