package com.vorticelabs.miveo.model;

import java.util.ArrayList;

public class Owner {

    public static final String TAG = Owner.class.getSimpleName();

    public int id;
    public String displayName;
    public ArrayList<Portrait> portraits = new ArrayList<>();

    public static Owner loadFromVimofitModel(com.andressantibanez.vimofit.model.Owner data) {

        Owner owner = new Owner();
        owner.id = data.id;
        owner.displayName = data.displayName;

        if(data.portraits != null && data.portraits.portrait.size() > 0) {
            for (int i = 0; i < data.portraits.portrait.size(); i++) {
                owner.portraits.add(Portrait.loadFromVimofitModel(data.portraits.portrait.get(i)));
            }
        }

        return owner;
    }

}
