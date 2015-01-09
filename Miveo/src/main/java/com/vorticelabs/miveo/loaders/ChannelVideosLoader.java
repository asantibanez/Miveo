package com.vorticelabs.miveo.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.andressantibanez.vimofit.endpoints.ChannelsEndpoint;
import com.andressantibanez.vimofit.model.responses.ChannelsResponses;
import com.vorticelabs.miveo.extras.Utils;
import com.vorticelabs.miveo.model.Video;

import java.util.ArrayList;

public class ChannelVideosLoader extends AsyncTaskLoader<ChannelVideosLoader.LoaderResponse> {

    public static final String TAG = ChannelVideosLoader.class.getSimpleName();

    private final int mChannelId;
    private int mCurrentPage;
    private int mItemsPerPage;
    private LoaderResponse mLoaderResponse;

    public ChannelVideosLoader(Context context, int channelId) {
        super(context);
        mChannelId = channelId;
        mCurrentPage = 1;
        mItemsPerPage = 20;
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

        Log.i(TAG, "Loading page " + mCurrentPage + "...");

        //Download channels using Vimofit
        ChannelsResponses.GetVideosResponse getVideosResponse;
        getVideosResponse = ChannelsEndpoint.build(Utils.getUnsignedOAuthConsumer()).getVideos(
                mChannelId,
                mCurrentPage,
                mItemsPerPage,
                1
        );

        //Handle getVideosResponse
        LoaderResponse loaderResponse = new LoaderResponse();
        if(getVideosResponse != null){
            mCurrentPage += 1;
            loaderResponse = new LoaderResponse();

            ArrayList<com.andressantibanez.vimofit.model.Video> videosArray = getVideosResponse.videos.video;
            for (int i = 0; i < videosArray.size(); i++) {
                loaderResponse.videos.add(Video.loadFromVimofitModel(videosArray.get(i)));
            }

        }

        return loaderResponse;
    }

    public void deliverResult(LoaderResponse data) {
        LoaderResponse lastData = new LoaderResponse();

        if(mLoaderResponse != null)
            lastData.videos = new ArrayList<Video>(mLoaderResponse.videos);

        if(data!=null){
            for (int i = 0; i < data.videos.size(); i++) {
                lastData.videos.add(data.videos.get(i));
            }
        }
        mLoaderResponse = lastData;
        super.deliverResult(mLoaderResponse);
    }

    //Response Object for Loader
    public static class LoaderResponse {
        public ArrayList<Video> videos;

        public LoaderResponse() {
            videos = new ArrayList<Video>();
        }
    }
}
