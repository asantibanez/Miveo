
package com.andressantibanez.vimofit.endpoints;

import com.andressantibanez.vimofit.model.responses.ChannelsResponses;

import oauth.signpost.OAuthConsumer;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

public class ChannelsEndpoint extends BaseEndpoint{

    private ChannelsService service;

    public static class SortEnum{
        public final static String DEFAULT = "default";
        public final static String MOST_SUBSCRIBED = "most_subscribed";
    }

    private static interface ChannelsService {

        //GetAll
        @GET("/v2?format=json&method=vimeo.channels.getAll")
        public ChannelsResponses.GetAllResponse getAll(
                @Query("sort") String sort,
                @Query("page") int page,
                @Query("per_page") int perPage
        );

        //GetVideos
        @GET("/v2?format=json&method=vimeo.channels.getVideos")
        public ChannelsResponses.GetVideosResponse getVideos(
                @Query("channel_id") int channelId,
                @Query("page") int page,
                @Query("per_page") int perPage,
                @Query("summary_response") int summaryResponse
        );

        //GetInfo
        @GET("/v2?format=json&method=vimeo.channels.getInfo")
        public ChannelsResponses.GetInfoResponse getInfo(
                @Query("channel_id") int channelId
        );

    }

    //Factory method
    public static ChannelsEndpoint build(OAuthConsumer consumer){
        return new ChannelsEndpoint(consumer);
    }

    //Constructor
    public ChannelsEndpoint(OAuthConsumer consumer) {
        super(consumer);
        service = restAdapter.create(ChannelsService.class);
    }

    //Endpoint methods
    public ChannelsResponses.GetAllResponse getAll(String sort, int page, int perPage){
        return service.getAll(sort, page, perPage);
    }

    public ChannelsResponses.GetVideosResponse getVideos(int channelId, int page, int perPage, int summaryResponse){
        return service.getVideos(channelId, page, perPage, summaryResponse);
    }

    public ChannelsResponses.GetInfoResponse getInfo(int channelId){
        return service.getInfo(channelId);
    }
}
