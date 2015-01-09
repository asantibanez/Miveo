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
import android.widget.AbsListView;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.adapters.ChannelVideosListAdapter;
import com.vorticelabs.miveo.loaders.ChannelVideosLoader;
import com.vorticelabs.miveo.model.Video;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChannelVideosListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ChannelVideosLoader.LoaderResponse> {

    public static final String TAG = ChannelVideosListFragment.class.getSimpleName();

    //Constants
    public static final int LAYOUT_RESOURCE_ID = R.layout.fragment_channel_videos_list;
    public static final int LOADER_ID = 1;
    public static final String CHANNEL_ID = "channel_id";
    public static final String IS_LOADING_MORE = "is_loading_more";

    //Variables
    private int mChannelId;
    private boolean mIsLoadingMore;
    private ChannelVideosListAdapter mAdapter;
    private ArrayList<Video> mVideos;
    private LinearLayoutManager mLinearLayoutManager;

    //Controls
    @InjectView(R.id.my_recycler_view) public RecyclerView mRecyclerView;

    //Callbacks Listener
    private ChannelVideosListFragmentCallbacks mListener;

    //Mandatory empty constructor
    public ChannelVideosListFragment(){}

    //newInstance factory method
    public static ChannelVideosListFragment newInstance(int channelId){
        ChannelVideosListFragment fragment = new ChannelVideosListFragment();
        Bundle args = new Bundle();
        args.putInt(CHANNEL_ID, channelId);
        fragment.setArguments(args);

        return fragment;
    }

    //Lifecycle methods
    //onCreateView
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(LAYOUT_RESOURCE_ID, container, false);
        ButterKnife.inject(this, fragmentView);

        //Setup init Adapter
        mVideos = new ArrayList<>();
        mAdapter = new ChannelVideosListAdapter(mVideos, R.layout.list_item_channel_video);

        //Handle state
        if(savedInstanceState != null) {
            mChannelId = savedInstanceState.getInt(CHANNEL_ID);
            mIsLoadingMore = savedInstanceState.getBoolean(IS_LOADING_MORE, false);
        } else {
            mChannelId = getArguments().getInt(CHANNEL_ID);
            mIsLoadingMore = false;
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
                if(!mIsLoadingMore && indexToLoadMoreData >= totalItemCount && totalItemCount != 0){
                    mIsLoadingMore = true;
                    getLoaderManager().getLoader(LOADER_ID).forceLoad();
                }
            }
        });
    }

    //onStart: set onScrollListener for End of Page refresh
    @Override
    public void onStart() {
        super.onStart();
        //getListView().setOnScrollListener(this);
    }

    //onAttach: get Callbacks listener if available
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (ChannelVideosListFragmentCallbacks) activity;
        } catch (Exception e) {
            mListener = null;
            e.printStackTrace();
        }
    }

    //onDetach: get rid of Callbacks listener
    public void onDetach() {
        super.onDetach();
        mListener = null;
        ButterKnife.reset(this);
    }

    //onSaveInstanceState: save necessary variables for recreate
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHANNEL_ID, mChannelId);
        outState.putBoolean(IS_LOADING_MORE, mIsLoadingMore);
    }

    //Loader methods
    public Loader<ChannelVideosLoader.LoaderResponse> onCreateLoader(int i, Bundle bundle) {
        return new ChannelVideosLoader(getActivity(), mChannelId);
    }

    public void onLoadFinished(Loader<ChannelVideosLoader.LoaderResponse> loaderResponseLoader, ChannelVideosLoader.LoaderResponse loaderResponse) {
        mVideos = loaderResponse.videos;
        mAdapter.swapVideos(mVideos);
        mIsLoadingMore = false;
    }

    public void onLoaderReset(Loader<ChannelVideosLoader.LoaderResponse> loaderResponseLoader) {
        mAdapter.swapVideos(null);
    }

    //Scroll Listener methods
    public void onScrollStateChanged(AbsListView absListView, int i) {}

    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int indexToLoadMoreData = firstVisibleItem + visibleItemCount + 5;
        if(!mIsLoadingMore && indexToLoadMoreData >= totalItemCount && totalItemCount != 0){
            mIsLoadingMore = true;
            getLoaderManager().getLoader(LOADER_ID).forceLoad();
        }
    }

    //Events
    //onListItemClick: dispatch event to listener
    //public void onListItemClick(ListView l, View v, int position, long id) {
    //    super.onListItemClick(l, v, position, id);
    //    if(mListener != null) {
    //        mListener.onChannelVideoClick(mChannelId, getListAdapter().getItemId(position));
    //    }
    //}

    //Interface for Callbacks
    public interface ChannelVideosListFragmentCallbacks{
        public void onChannelVideoClick(int channelId, long videoId);
    }
}
