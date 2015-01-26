package com.vorticelabs.miveo.model;

public class Tag {

    public int id;
    public String name;
    public String url;

    public static Tag loadFromVimofitModel(com.andressantibanez.vimofit.model.Tag data) {
        Tag tag = new Tag();
        tag.id = data.id;
        tag.name = data.normalized;
        tag.url = data.url;
        return tag;
    }
}
