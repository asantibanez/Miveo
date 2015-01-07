package com.andressantibanez.vimofit.model.responses;

import com.andressantibanez.vimofit.model.Err;
import com.andressantibanez.vimofit.model.catalogs.Videos;
import com.google.gson.annotations.SerializedName;

public class AlbumsResponses {

    public static class GetWatchLaterResponse extends BaseResponse {
        public Videos videos;
    }

    public static class AddToWatchLaterResponse extends BaseResponse{}
}
