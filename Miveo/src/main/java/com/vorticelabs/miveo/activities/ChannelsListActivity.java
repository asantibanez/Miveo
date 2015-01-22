package com.vorticelabs.miveo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.fragments.ChannelsListFragment;

public class ChannelsListActivity extends FragmentActivity
    implements ChannelsListFragment.ChannelsListFragmentCallbacks {

    //Fragments
    private ChannelsListFragment mChannelsListFragment;

    //Lifecycle methods
    //onCreate
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels_list);

        if(savedInstanceState == null) {
            mChannelsListFragment = new ChannelsListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mChannelsListFragment)
                    .commit();
        } else {
            mChannelsListFragment = (ChannelsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        }
    }

    //Callbacks
    public void onChannelSelected(int channelId) {
        Intent channelViewActivity = new Intent(this, ChannelViewActivity.class);
        channelViewActivity.putExtra(ChannelViewActivity.CHANNEL_ID, channelId);
        startActivity(channelViewActivity);
    }

}
