package com.vorticelabs.miveo.activities;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.adapters.NavDrawerAdapter;
import com.vorticelabs.miveo.fragments.Fragment1;
import com.vorticelabs.miveo.fragments.LigaFragment;
import com.vorticelabs.miveo.fragments.PartidoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements  ListView.OnItemClickListener {

    public List<String> items = new ArrayList<String>();

    private ListView drawer_list;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle drawer_toggle;

    private static final String OPENED_KEY = "OPENED_KEY";
    private SharedPreferences prefs = null;
    private Boolean opened = null;

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items.add("Staff Picks");
        items.add("Canales Destacados");
        items.add("Favoritos");
        items.add("Ver Luego");
        items.add("Enviar comentarios");
        items.add("Ajustes");

        drawer_list = (ListView) findViewById(R.id.left_drawer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavDrawerAdapter drawer_adapter = new NavDrawerAdapter(this, items);

        drawer_list.setAdapter(drawer_adapter);
        drawer_list.setOnItemClickListener(this);
        if (savedInstanceState == null) {
            selectItem(0);
        }

        drawer_toggle = new ActionBarDrawerToggle(this, drawer_layout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        drawer_layout.setDrawerListener(drawer_toggle);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                prefs = getPreferences(MODE_PRIVATE);
                opened = prefs.getBoolean(OPENED_KEY, false);
                if(opened == false)
                {
                    drawer_layout.openDrawer(drawer_list);
                }
            }
        }).start();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawer_toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawer_toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawer_layout.isDrawerOpen(drawer_list);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawer_toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()){
            case R.id.action_search:
                mSearchView.setIconified(false);
                return true;
        }
        return false;
    }

    private void selectItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new LigaFragment();
                break;
            case 1:
                frag = new PartidoFragment();
                break;
            case 2:
                frag = new Fragment1();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, frag)
                .commit();
        drawer_list.setItemChecked(position, true);
        setTitle(drawer_list.getItemAtPosition(position).toString());
        drawer_layout.closeDrawer(drawer_list);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        selectItem(position);
    }
}
