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

//    //Menu methods
//    //onCreateOptionsMenu
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//    //onOptionsItemSelected
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //Callbacks
    public void onChannelSelected(int channelId) {
        Intent channelViewActivity = new Intent(this, ChannelViewActivity.class);
        channelViewActivity.putExtra(ChannelViewActivity.CHANNEL_ID, channelId);
        startActivity(channelViewActivity);
    }

}
