package com.vorticelabs.miveo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andressantibanez.vimofit.endpoints.ChannelsEndpoint;
import com.andressantibanez.vimofit.model.responses.ChannelsResponses;
import com.squareup.picasso.Picasso;
import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.extras.Utils;
import com.vorticelabs.miveo.model.Channel;

public class ChannelInfoFragment extends Fragment
    implements LoaderManager.LoaderCallbacks<Channel>{

    public final static String TAG = ChannelInfoFragment.class.getSimpleName();

    //Arguments
    private static final String CHANNEL_ID = "channel_id";

    //Variables
    private int mId;
    private Channel mChannel;

    //Controls
    private ImageView thumbnail;
    private TextView description;
    private TextView videosCount;
    private TextView subscribersCount;

    //newInstance factory method
    public static ChannelInfoFragment newInstance(int channelId) {
        ChannelInfoFragment fragment = new ChannelInfoFragment();
        Bundle args = new Bundle();
        args.putInt(CHANNEL_ID, channelId);
        fragment.setArguments(args);
        return fragment;
    }

    //Required empty constructor
    public ChannelInfoFragment() {}

    //Lifecycle events
    //onCreateView
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_info, container, false);

        //Get controls
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        description = (TextView) view.findViewById(R.id.description);
        videosCount = (TextView) view.findViewById(R.id.videos_count);
        subscribersCount = (TextView) view.findViewById(R.id.subscribers_count);

        //Handle state
        handleState(savedInstanceState);

        //Init required loader
        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    //State methods
    //onSaveInstanceState
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHANNEL_ID, mId);
    }
    //handleState
    private void handleState(Bundle savedStateInstance) {
        if(savedStateInstance != null) {
            mId = savedStateInstance.getInt(CHANNEL_ID);
        } else {
            mId = getArguments().getInt(CHANNEL_ID);
        }
    }

    //LoaderCallbacks
    //onCreateLoader
    public Loader<Channel> onCreateLoader(int i, Bundle bundle) {
        return new ChannelInfoAsyncTaskLoader(getActivity(), mId);
    }

    //onLoadFinished
    public void onLoadFinished(Loader<Channel> channelLoader, Channel channel) {
        if(channel != null) {
            mChannel = channel;

            //Setup data
            if(mChannel.logoUrl.length() > 0)
                Picasso.with(getActivity()).load(mChannel.logoUrl).into(thumbnail);

            description.setText(mChannel.description);
            videosCount.setText(String.format("%d", mChannel.totalVideos) + " videos");
            subscribersCount.setText(String.format("%d", mChannel.totalSubscribers) + " suscriptores");
            getActivity().setTitle(mChannel.name);
        }
    }

    //onLoaderReset
    public void onLoaderReset(Loader<Channel> channelLoader) {}

    //AsyncTaskLoader
    public static class ChannelInfoAsyncTaskLoader extends AsyncTaskLoader<Channel> {

        private int mId;
        private Channel mChannel;

        public ChannelInfoAsyncTaskLoader(Context context, int channelId) {
            super(context);
            mId = channelId;
        }

        protected void onStartLoading() {
            if(mChannel != null) {
                deliverResult(mChannel);
            } else {
                forceLoad();
            }
        }

        public Channel loadInBackground() {
            ChannelsEndpoint endpoint;
            ChannelsResponses.GetInfoResponse getInfoResponse;

            endpoint = ChannelsEndpoint.build(Utils.getUnsignedOAuthConsumer());
            getInfoResponse = endpoint.getInfo(mId);

            Channel channel = Channel.loadFromVimofitModel(getInfoResponse.channel);
            Log.d(TAG, channel.toJson());
            return channel;
        }

        public void deliverResult(Channel data) {
            if(data != null)
                mChannel = data;

            super.deliverResult(data);
        }
    }
}
