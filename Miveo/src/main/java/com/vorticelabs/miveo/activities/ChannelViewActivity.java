package com.vorticelabs.miveo.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;
import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.fragments.ChannelInfoFragment;
import com.vorticelabs.miveo.fragments.ChannelVideosListFragment;
import com.vorticelabs.miveo.model.Channel;

public class ChannelViewActivity extends ActionBarActivity
    implements ChannelVideosListFragment.ChannelVideosListFragmentCallbacks {

    public final static String TAG = ChannelViewActivity.class.getSimpleName();

    //Arguments
    public final static String CHANNEL_ID = "channel_id";

    //Variables
    private int mId;
    private ChannelPagerAdapter mPagerAdapter;
    private Channel mChannel;

    //Controls
    private ViewPager mPager;
    private PagerSlidingTabStrip mTabs;

    //Lifecycle events
    //onCreate
    protected void onCreate(Bundle savedInstanceState) {
        //Setup view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_view);

        //Get controls
        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        //Tabs should expand to consume all width
        mTabs.setShouldExpand(true);

        //Restore instance
        if(savedInstanceState == null) {
            mId = getIntent().getIntExtra(CHANNEL_ID, 0);
        } else {
            mId = savedInstanceState.getInt(CHANNEL_ID);
        }

        //Setup mPager
        mPagerAdapter = new ChannelPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //Setup Tabs
        mTabs.setViewPager(mPager);

        //ActionBar return action
        Toolbar toolbar = (Toolbar)findViewById(R.id.channel_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Canal");
        //getSupportActionBar().setTitle(String.format("%s", mVideo.title));

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP){ // for lollipop
            // your RemoteController class for lollipop is loaded here
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
    }

    //onSaveInstanceState
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHANNEL_ID, mId);
    }

    //Menu methods
    //onCreateOptionsMenu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_channel_view, menu);
        return true;
    }
    //onOptionsItemSelected
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //FragmentPagerAdapter
    public class ChannelPagerAdapter extends FragmentPagerAdapter {

        private final int INFO_FRAGMENT_POSITION = 0;
        private final int VIDEOS_FRAGMENT_POSITION = 1;
        private final int NUMBER_OF_ITEMS = 2;

        public ChannelPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case INFO_FRAGMENT_POSITION:
                    return ChannelInfoFragment.newInstance(mId);
                case VIDEOS_FRAGMENT_POSITION:
                    return ChannelVideosListFragment.newInstance(mId);
            }
            return null;
        }

        public int getCount() {
            return NUMBER_OF_ITEMS;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case INFO_FRAGMENT_POSITION:
                    return getResources().getString(R.string.tab_title_main);
                case VIDEOS_FRAGMENT_POSITION:
                    return getResources().getString(R.string.tab_title_videos);
            }
            return super.getPageTitle(position);
        }
    }

    //Fragment Callbacks
    //ChannelVideosListFragment
    public void onChannelVideoClick(int channelId, long videoId) {
        Intent videoActivityIntent = VideoViewActivity.getStartActivityIntent(this, channelId, videoId);
        startActivity(videoActivityIntent);
    }
}
