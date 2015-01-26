package com.vorticelabs.miveo.model;

public class Portrait {

    public static final String TAG = Portrait.class.getSimpleName();

    public int height;
    public int width;
    public String url;

    public static Portrait loadFromVimofitModel(com.andressantibanez.vimofit.model.Portrait data) {
        Portrait portrait = new Portrait();
        portrait.height = data.height;
        portrait.width = data.width;
        portrait.url = data._content;
        return portrait;
    }

}
