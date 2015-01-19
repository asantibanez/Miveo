package com.vorticelabs.miveo.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.extras.Constants;
import com.vorticelabs.miveo.fragments.ChannelVideosListFragment;
import com.vorticelabs.miveo.fragments.ChannelsListFragment;
import com.vorticelabs.miveo.fragments.NavDrawerFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends ActionBarActivity
        implements
        NavDrawerFragment.NavDrawerFragmentCallbacks,
        ChannelsListFragment.ChannelsListFragmentCallbacks,
        ChannelVideosListFragment.ChannelVideosListFragmentCallbacks {

    public static final String TAG = HomeActivity.class.getSimpleName();

    //Variables
    ActionBarDrawerToggle mDrawerToggle;

    //Fragments
    Fragment mainFragment;
    Fragment drawerFragment;

    //Controls
    @InjectView(R.id.drawer_layout) public DrawerLayout drawerLayout;
    @InjectView(R.id.main_container) public FrameLayout mainContainer;
    @InjectView(R.id.drawer_container) public FrameLayout drawerContainer;

    //Lifecycle methods
    //onCreate
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        setupViews(savedInstanceState);
        setupNavDrawer(savedInstanceState);
    }

    public void setupNavDrawer(Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary_dark));

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setupViews(Bundle savedInstanceState) {
        //Instance views first time
        if(savedInstanceState == null) {
            mainFragment = ChannelVideosListFragment.newInstance(Constants.VIMEO_STAFF_PICKS_CHANNEL_ID);
            drawerFragment = NavDrawerFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(mainContainer.getId(), mainFragment)
                    .replace(drawerContainer.getId(), drawerFragment)
                    .commit();
        }else {
            //Get references to displayed fragments
            mainFragment = getSupportFragmentManager().findFragmentById(mainContainer.getId());
            drawerFragment = getSupportFragmentManager().findFragmentById(drawerContainer.getId());
        }
    }

    public void onNavDrawerOptionSelected(int position) {
        switch (position) {
            case 1:
                mainFragment = ChannelVideosListFragment.newInstance(Constants.VIMEO_STAFF_PICKS_CHANNEL_ID);
                getSupportActionBar().setTitle(R.string.title_section1);
                getSupportFragmentManager().beginTransaction()
                        .replace(mainContainer.getId(), mainFragment)
                        .commit();
                break;
            case 2:
                mainFragment = ChannelsListFragment.newInstance();
                getSupportActionBar().setTitle(R.string.title_section2);
                getSupportFragmentManager().beginTransaction()
                        .replace(mainContainer.getId(), mainFragment)
                        .commit();
                break;
        }
        drawerLayout.closeDrawer(drawerContainer);
    }

    //Menu methods
    //onCreateOptionsMenu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    //onOptionsItemSelected
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Fragment Callbacks
    //ChannelsListFragment Callbacks
    public void onChannelSelected(int channelId) {
        Intent channelViewActivity = new Intent(this, ChannelViewActivity.class);
        channelViewActivity.putExtra(ChannelViewActivity.CHANNEL_ID, channelId);
        startActivity(channelViewActivity);
    }

    //ChannelVideosListFragment
    public void onChannelVideoClick(int channelId, long videoId) {
        Intent videoActivityIntent = VideoViewActivity.getStartActivityIntent(this, channelId, videoId);
        startActivity(videoActivityIntent);
    }
}
