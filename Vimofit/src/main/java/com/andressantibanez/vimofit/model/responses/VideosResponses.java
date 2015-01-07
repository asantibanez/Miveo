package com.andressantibanez.vimofit.model.responses;

import com.andressantibanez.vimofit.model.Video;

import java.util.ArrayList;

public class VideosResponses {
    public class GetInfoResponse extends BaseResponse {
        public ArrayList<Video> video;
    }
}
