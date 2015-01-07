package com.vorticelabs.miveo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.views.VimeoPlayerView;

public class VideoPlaybackFragment extends Fragment {

    public static final String TAG = VideoPlaybackFragment.class.getSimpleName();

    //Available Args
    public static final String VIDEO_ID = "video_id";

    //Variables
    private long mVideoId;

    //Controls
    private VimeoPlayerView mPlayer;

    //Factory method
    public static VideoPlaybackFragment newInstance(long videoId) {
        VideoPlaybackFragment fragment = new VideoPlaybackFragment();
        Bundle args = new Bundle();
        args.putLong(VIDEO_ID, videoId);
        fragment.setArguments(args);
        return fragment;
    }

    //Required empty constructor
    public VideoPlaybackFragment() {}

    //Lifecycle methods
    //onCreate
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    //onCreateView
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Setup view
        View view = inflater.inflate(R.layout.fragment_video_playback, container, false);
        mPlayer = (VimeoPlayerView) view.findViewById(R.id.player);

        //Get VideoId
        if(savedInstanceState != null) {
            mVideoId = savedInstanceState.getLong(VIDEO_ID);
        } else {
            mVideoId = getArguments().getLong(VIDEO_ID);
        }

        //Set VideoId to Player
        mPlayer.setVideoId(mVideoId);

        return view;
    }

    //onSaveInstanceState
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(VIDEO_ID, mVideoId);
    }

    public void stopPlayback() {
        mPlayer.loadUrl("about:blank");
    }
}
