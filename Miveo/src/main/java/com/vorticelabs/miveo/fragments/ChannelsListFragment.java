package com.vorticelabs.miveo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.adapters.ChannelsListAdapter;
import com.vorticelabs.miveo.loaders.ChannelsLoader;

public class ChannelsListFragment extends ListFragment
    implements
        LoaderManager.LoaderCallbacks<ChannelsLoader.LoaderResponse>,
        AbsListView.OnScrollListener{

    public final static String TAG = ChannelsListFragment.class.getSimpleName();

    //Constants
    public final static String IS_LOADING_MORE = "is_loading_more";

    //Variables
    private ChannelsListFragmentCallbacks mListener;
    private ChannelsListAdapter mAdapter;
    private boolean mIsLoadingMore;

    //newInstance factory method
    public static ChannelsListFragment newInstance() {
        ChannelsListFragment fragment = new ChannelsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //Mandatory empty constructor
    public ChannelsListFragment() {}

    //Lifecycle methods
    //onCreateView: instance views, set adapter
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_channels_list, container, false);

        //Handle instance state
        if(savedInstanceState != null){
            mIsLoadingMore = savedInstanceState.getBoolean(IS_LOADING_MORE);
        }

        //Set adapter
        mAdapter = new ChannelsListAdapter(getActivity());
        setListAdapter(mAdapter);

        //Init loader
        getLoaderManager().initLoader(0, null, this);

        return fragmentView;
    }

    //onStart: set OnScrollListener
    public void onStart() {
        super.onStart();
        getListView().setOnScrollListener(this);
    }

    //onSaveInstanceState
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_LOADING_MORE, mIsLoadingMore);
    }

    //onListItemClick
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.onChannelSelected(mAdapter.getItem(position).id);
    }

    //Listener methods
    //onAttach
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (ChannelsListFragmentCallbacks) activity;
    }
    //onDetach
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Loader methods
    //onCreateLoader
    public Loader<ChannelsLoader.LoaderResponse> onCreateLoader(int i, Bundle bundle) {
        return new ChannelsLoader(getActivity());
    }
    //onLoadFinished
    public void onLoadFinished(Loader<ChannelsLoader.LoaderResponse> loaderResponseLoader, ChannelsLoader.LoaderResponse loaderResponse) {
        mAdapter.swapVideos(loaderResponse.channels);
        mIsLoadingMore = false;
    }
    //onLoaderReset
    public void onLoaderReset(Loader<ChannelsLoader.LoaderResponse> loaderResponseLoader) {
        mAdapter.swapVideos(null);
    }

    //Scroll Listener methods
    //onScrollStateChanged
    public void onScrollStateChanged(AbsListView absListView, int i) {}
    //onScroll
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int indexToLoadMoreData = firstVisibleItem + visibleItemCount + 5;
        if(!mIsLoadingMore && indexToLoadMoreData >= totalItemCount && totalItemCount != 0){
            mIsLoadingMore = true;
            getLoaderManager().getLoader(0).forceLoad();
        }
    }

    //Interface for event handling
    public interface ChannelsListFragmentCallbacks {
        public void onChannelSelected(int channelId);
    }
}
