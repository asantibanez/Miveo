package com.vorticelabs.miveo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.fragments.VideoPlaybackFragment;

public class VideoPlaybackActivity extends ActionBarActivity {

    public static final String TAG = VideoPlaybackActivity.class.getSimpleName();

    //Available Args
    public static final String VIDEO_ID = "video_id";

    //Variables
    private long mVideoId;

    //Fragment
    VideoPlaybackFragment mPlaybackFragment;

    //Factory method
    public static Intent getStartActivityIntent(Context context, long videoId) {
        Intent startActivityIntent = new Intent(context, VideoPlaybackActivity.class);
        startActivityIntent.putExtra(VIDEO_ID, videoId);
        return startActivityIntent;
    }

    //Lifecycle methods
    //onCreate
    protected void onCreate(Bundle savedInstanceState) {
        //Setup view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        //Handle state
        if(savedInstanceState != null) {
            mVideoId = savedInstanceState.getLong(VIDEO_ID);
        } else {
            mVideoId = getIntent().getLongExtra(VIDEO_ID, 0);
        }

        //Handle fragment
        FragmentManager fm = getSupportFragmentManager();
        mPlaybackFragment = (VideoPlaybackFragment) fm.findFragmentById(R.id.fragment_container);
        if(mPlaybackFragment == null) {
            mPlaybackFragment = VideoPlaybackFragment.newInstance(mVideoId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mPlaybackFragment)
                    .commit();
        }

        //Hide ActionBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.video_playback_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
    }

    //onSaveInstanceState
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(VIDEO_ID, mVideoId);
    }

    //onStop
    protected void onStop() {
        super.onStop();
        mPlaybackFragment.stopPlayback();
        finish();
    }
}
