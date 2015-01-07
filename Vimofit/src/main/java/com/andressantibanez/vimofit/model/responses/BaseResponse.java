package com.andressantibanez.vimofit.model.responses;

import com.andressantibanez.vimofit.model.Err;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("generated_in")
    public float generatedIn;
    public String stat;
    public Err err;

    public String toJson() {
        String json = new Gson().toJson(this);
        return json;
    }

}
