package com.vorticelabs.miveo.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.andressantibanez.vimofit.endpoints.ChannelsEndpoint;
import com.andressantibanez.vimofit.model.responses.ChannelsResponses;
import com.vorticelabs.miveo.extras.Utils;
import com.vorticelabs.miveo.model.Channel;

import java.util.ArrayList;

public class ChannelsLoader extends AsyncTaskLoader<ChannelsLoader.LoaderResponse> {

    public static final String TAG = ChannelsLoader.class.getSimpleName();

    private int mCurrentPage;
    private int mItemsPerPage;
    private LoaderResponse mLoaderResponse;

    public ChannelsLoader(Context context) {
        super(context);
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
        ChannelsResponses.GetAllResponse getAllResponse;
        getAllResponse = ChannelsEndpoint.build(Utils.getUnsignedOAuthConsumer()).getAll(
                ChannelsEndpoint.SortEnum.MOST_SUBSCRIBED,
                mCurrentPage,
                mItemsPerPage
        );

        //Handle getVideosResponse
        LoaderResponse loaderResponse = new LoaderResponse();
        if(getAllResponse != null){
            mCurrentPage += 1;
            loaderResponse = new LoaderResponse();

            ArrayList<com.andressantibanez.vimofit.model.Channel> channelsArray = getAllResponse.channels.channel;
            for (int i = 0; i < channelsArray.size(); i++) {
                Channel channel = Channel.loadFromVimofitModel(channelsArray.get(i));
                if(channel.logoUrl != null) {
                    loaderResponse.channels.add(channel);
                }
            }
        }

        return loaderResponse;
    }

    public void deliverResult(LoaderResponse data) {
        LoaderResponse lastData = new LoaderResponse();

        if(mLoaderResponse != null)
            lastData.channels = new ArrayList<Channel>(mLoaderResponse.channels);

        if(data!=null){
            for (int i = 0; i < data.channels.size(); i++) {
                lastData.channels.add(data.channels.get(i));
            }
        }

        mLoaderResponse = lastData;
        super.deliverResult(mLoaderResponse);
    }

    //Response Object for Loader
    public static class LoaderResponse {
        public ArrayList<Channel> channels;

        public LoaderResponse() {
            channels = new ArrayList<Channel>();
        }
    }
}
