package com.vorticelabs.miveo.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.andressantibanez.vimofit.endpoints.ChannelsEndpoint;
import com.andressantibanez.vimofit.endpoints.VideosEndpoint;
import com.andressantibanez.vimofit.model.responses.ChannelsResponses;
import com.andressantibanez.vimofit.model.responses.VideosResponses;
import com.vorticelabs.miveo.extras.Utils;
import com.vorticelabs.miveo.model.Video;

import java.util.ArrayList;

public class VideoLoader extends AsyncTaskLoader<VideoLoader.LoaderResponse> {

    public static final String TAG = VideoLoader.class.getSimpleName();

    private int mChannelId;
    private long mVideoId;
    private LoaderResponse mLoaderResponse;

    public VideoLoader(Context context, int channelId, long videoId) {
        super(context);
        mChannelId = channelId;
        mVideoId = videoId;
        mLoaderResponse = null;
    }

    protected void onStartLoading() {
        if (mLoaderResponse != null)
            deliverResult(null);
        else
            forceLoad();
    }

    @Override
    public LoaderResponse loadInBackground() {

        Log.i(TAG, "Loading video info: " + mVideoId + "...");

        //Do request
        VideosResponses.GetInfoResponse getInfoResponse;
        getInfoResponse = VideosEndpoint.build(Utils.getUnsignedOAuthConsumer()).getInfo(mVideoId);

        //Handle response
        LoaderResponse loaderResponse = new LoaderResponse();
        if(getInfoResponse != null) {
            loaderResponse.video = Video.loadFromVimofitModel(getInfoResponse.video.get(0));
        }

        return loaderResponse;
    }

    public void deliverResult(LoaderResponse data) {
        LoaderResponse lastData = new LoaderResponse();

        if(mLoaderResponse != null)
            lastData.video = mLoaderResponse.video;

        if(data != null) {
            lastData.video = data.video;
        }

        mLoaderResponse = lastData;
        super.deliverResult(mLoaderResponse);
    }

    //Response Object for Loader
    public static class LoaderResponse {
        public Video video;

        public LoaderResponse() {
        }
    }
}
