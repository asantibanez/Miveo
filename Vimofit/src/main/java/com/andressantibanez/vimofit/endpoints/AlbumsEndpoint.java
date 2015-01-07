package com.andressantibanez.vimofit.endpoints;

import com.andressantibanez.vimofit.model.responses.AlbumsResponses;

import oauth.signpost.OAuthConsumer;
import retrofit.http.GET;
import retrofit.http.Query;

public class AlbumsEndpoint extends BaseEndpoint {

    private AlbumsService service;

    private static interface AlbumsService{

        //getWatchLater
        @GET("/v2?format=json&method=vimeo.albums.getWatchLater")
        public AlbumsResponses.GetWatchLaterResponse getWatchLater(
                @Query("page") int page,
                @Query("per_page") int perPage,
                @Query("summary_response") int summaryResponse
        );

        //addToWatchLater
        @GET("/v2?format=json&method=vimeo.albums.addToWatchLater")
        public AlbumsResponses.AddToWatchLaterResponse addToWatchLater(
                @Query("video_id") int videoId
        );

    }

    public AlbumsEndpoint(OAuthConsumer consumer) {
        super(consumer);
        service = restAdapter.create(AlbumsService.class);
    }

    public AlbumsResponses.GetWatchLaterResponse getWatchLater(int page, int perPage, int summaryResponse){
        return service.getWatchLater(page, perPage, summaryResponse);
    }
}
