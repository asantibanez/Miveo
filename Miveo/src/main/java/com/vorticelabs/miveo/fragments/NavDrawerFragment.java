package com.vorticelabs.miveo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.adapters.NavDrawerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class NavDrawerFragment extends ListFragment {
    public final static String TAG = NavDrawerFragment.class.getSimpleName();

    //Variables
    private NavDrawerAdapter mAdapter;

    //Listener
    private NavDrawerFragmentCallbacks mListener;

    //Factory method
    public static NavDrawerFragment newInstance() {
        NavDrawerFragment fragment = new NavDrawerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //Required empty constructor
    public NavDrawerFragment() {}

    //Lifecycle methods
    //onCreateView
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        //Setup adapter
        String optionsArray[] = getActivity().getResources().getStringArray(R.array.nav_drawer_options);
        ArrayList<String> options = new ArrayList<String>(Arrays.asList(optionsArray));
        mAdapter = new NavDrawerAdapter(getActivity(), NavDrawerAdapter.IGNORE_ITEM_VIEW_TYPE, options);
        setListAdapter(mAdapter);

        return v;
    }
    //onAttach: Activity must implement interface
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NavDrawerFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    //onDetach: throw away listener
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onListItemClick(ListView listView, View vie, int position, long id) {
        if(mListener != null) {
            mListener.onNavDrawerOptionSelected(position);
        }
    }

    //Interface for interactions. Activity must implement methods.
    public interface NavDrawerFragmentCallbacks {
        public void onNavDrawerOptionSelected(int position);
    }

}
