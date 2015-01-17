package com.vorticelabs.miveo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.adapters.ChannelsListAdapter;
import com.vorticelabs.miveo.loaders.ChannelsLoader;
import com.vorticelabs.miveo.model.Channel;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChannelsListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ChannelsLoader.LoaderResponse>,
        ChannelsListAdapter.ChannelsAdapterListener
        {

    public final static String TAG = ChannelsListFragment.class.getSimpleName();

    //Constants
    public static final int LAYOUT_RESOURCE_ID = R.layout.fragment_channels_list;
    public final static String IS_LOADING_MORE = "is_loading_more";
    public static final int LOADER_ID = 1;

    //Variables
    private boolean mIsLoadingMore;
    private ChannelsListAdapter mAdapter;
    private ArrayList<Channel> mChannels;
    private LinearLayoutManager mLinearLayoutManager;

    //Controls
    @InjectView(R.id.channel_recyclerview) public RecyclerView mRecyclerView;

    //Callbacks Listener
    private ChannelsListFragmentCallbacks mChannelListener;

    //Mandatory empty constructor
    public ChannelsListFragment(){}

    //newInstance factory method
    public static ChannelsListFragment newInstance() {
        ChannelsListFragment fragment = new ChannelsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    //Lifecycle methods
    //onCreateView: instance views, set adapter
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(LAYOUT_RESOURCE_ID, container, false);
        ButterKnife.inject(this, fragmentView);

        //Setup init Adapter
        mChannels = new ArrayList<>();
        mAdapter = new ChannelsListAdapter(mChannels, R.layout.list_item_channel);
        mAdapter.setChannelsAdapterListener(this);

        //Handle instance state
        if(savedInstanceState != null){
            mIsLoadingMore = savedInstanceState.getBoolean(IS_LOADING_MORE);
        }

        //Init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);

        return fragmentView;
    }

    //onActivityCreated
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //LayoutManager for RecyclerView
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        //Setup RecyclerView for Videos list
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int indexToLoadMoreData = firstVisibleItem + visibleItemCount + 5;
                if (!mIsLoadingMore && indexToLoadMoreData >= totalItemCount && totalItemCount != 0) {
                    mIsLoadingMore = true;
                    getLoaderManager().getLoader(LOADER_ID).forceLoad();
                }
            }
        });
    }

    //onSaveInstanceState
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_LOADING_MORE, mIsLoadingMore);
    }

    //Listener methods
    //onAttach
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mChannelListener = (ChannelsListFragmentCallbacks) activity;
    }
    //onDetach
    public void onDetach() {
        super.onDetach();
        mChannelListener = null;
        ButterKnife.reset(this);
    }

    //Loader methods
    //onCreateLoader
    public Loader<ChannelsLoader.LoaderResponse> onCreateLoader(int i, Bundle bundle) {
        return new ChannelsLoader(getActivity());
    }
    //onLoadFinished
    public void onLoadFinished(Loader<ChannelsLoader.LoaderResponse> loaderResponseLoader, ChannelsLoader.LoaderResponse loaderResponse) {
        mChannels = loaderResponse.channels;
        mAdapter.swapChannels(mChannels);
        mIsLoadingMore = false;
    }
    //onLoaderReset
    public void onLoaderReset(Loader<ChannelsLoader.LoaderResponse> loaderResponseLoader) {
        mAdapter.swapChannels(null);
    }


    @Override
    public void onItemClick(int position) {
            mChannelListener.onChannelSelected(mChannels.get(position).id);
    }

    //Interface for event handling
    public interface ChannelsListFragmentCallbacks {
        public void onChannelSelected(int channelId);
    }
}
