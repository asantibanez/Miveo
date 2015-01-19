package com.vorticelabs.miveo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.adapters.NavDrawerAdapter;

public class NavDrawerFragment extends Fragment {
    public final static String TAG = NavDrawerFragment.class.getSimpleName();

    //Variables
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

//    String optionsArray[] = getActivity().getResources().getStringArray(R.array.nav_drawer_options);
//    ArrayList<String> TITLES = new ArrayList<>(Arrays.asList(optionsArray));

    String TITLES[] = {"Staff Picks", "Canales Destacados", "Favoritos", "Ver Luego"};
    int ICONS[] = {R.drawable.ic_canales_destacados,
            R.drawable.ic_videos_destacados,
            R.drawable.ic_videos_favoritos,
            R.drawable.ic_videos_verluego};

    String NAME = "Omar Tosca";
    int COVER = R.drawable.default_menu_backdrop;
    int PROFILE = R.drawable.unknown_user;

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

        // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        mAdapter = new NavDrawerAdapter(TITLES, ICONS, COVER,PROFILE, NAME);

        return v;
    }

    //onActivityCreated
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Assigning the RecyclerView Object to the xml View
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.navdrawer_recyclerview);
        // Letting the system know that the list objects are of fixed size
        mRecyclerView.setHasFixedSize(true);
        // Setting the adapter to RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // Creating a layout Manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
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
