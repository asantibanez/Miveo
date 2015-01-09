package com.vorticelabs.miveo.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.extras.Constants;
import com.vorticelabs.miveo.fragments.ChannelVideosListFragment;
import com.vorticelabs.miveo.fragments.ChannelsListFragment;
import com.vorticelabs.miveo.fragments.NavDrawerFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends FragmentActivity
        implements
        NavDrawerFragment.NavDrawerFragmentCallbacks,
        ChannelsListFragment.ChannelsListFragmentCallbacks,
        ChannelVideosListFragment.ChannelVideosListFragmentCallbacks {

    public static final String TAG = HomeActivity.class.getSimpleName();

    //Variables
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;

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

        /*
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        */
    }

    public void setupNavDrawer(Bundle savedInstanceState) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
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
            case 0:
                mainFragment = ChannelVideosListFragment.newInstance(Constants.VIMEO_STAFF_PICKS_CHANNEL_ID);
                getSupportFragmentManager().beginTransaction()
                        .replace(mainContainer.getId(), mainFragment)
                        .commit();
                break;
            case 1:
                mainFragment = ChannelsListFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(mainContainer.getId(), mainFragment)
                        .commit();
                break;
        }
        drawerLayout.closeDrawer(drawerContainer);
    }

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

    /*
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     *
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         *
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         *
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    */

}
