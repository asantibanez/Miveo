package com.andressantibanez.vimofit.endpoints;

import com.andressantibanez.vimofit.model.Video;
import com.andressantibanez.vimofit.model.responses.VideosResponses;

import oauth.signpost.OAuthConsumer;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

public class VideosEndpoint extends BaseEndpoint {

    private VideosService service;

    public static interface VideosService{

        //GetInfo
        @GET("/v2?format=json&method=vimeo.videos.getInfo")
        public VideosResponses.GetInfoResponse getInfo(
                @Query("video_id") long videoId
        );
    }

    //Factory method
    public static VideosEndpoint build(OAuthConsumer consumer) {
        return new VideosEndpoint(consumer);
    }

    //Constructor
    public VideosEndpoint(OAuthConsumer consumer) {
        super(consumer);
        service = restAdapter.create(VideosService.class);
    }

    //Endpoint methods
    public VideosResponses.GetInfoResponse getInfo(long videoId){
        return service.getInfo(videoId);
    }
}
