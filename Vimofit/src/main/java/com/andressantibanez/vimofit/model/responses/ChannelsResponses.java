package com.andressantibanez.vimofit.model.responses;

import com.andressantibanez.vimofit.model.Channel;
import com.andressantibanez.vimofit.model.catalogs.Channels;
import com.andressantibanez.vimofit.model.catalogs.Videos;
import com.google.gson.annotations.SerializedName;

public class ChannelsResponses {
    public class GetAllResponse extends BaseResponse {
        public Channels channels;
    }
    public class GetInfoResponse extends BaseResponse {
        public Channel channel;
    }
    public class GetVideosResponse extends BaseResponse {
        public Videos videos;
    }
}
