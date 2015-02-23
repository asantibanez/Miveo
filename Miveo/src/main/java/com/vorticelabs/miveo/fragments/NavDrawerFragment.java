package com.vorticelabs.miveo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.adapters.NavDrawerAdapter;

import butterknife.ButterKnife;

public class NavDrawerFragment extends Fragment implements NavDrawerAdapter.NavDrawerListener, NavDrawerAdapter.Callback {

    public final static String TAG = NavDrawerFragment.class.getSimpleName();

    //Variables
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private int mCurrentSelectedPosition = 0;

    RecyclerView mRecyclerView;
    NavDrawerAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

//    String optionsArray[] = getActivity().getResources().getStringArray(R.array.nav_drawer_options);
//    ArrayList<String> TITLES = new ArrayList<>(Arrays.asList(optionsArray));

    String TITLES[] = {"Staff Picks", "Canales Destacados", "Favoritos", "Ver Luego"};
    String SUBTITLES[] = {"Opciones","Acerca de"};
    int ICONS[] = {R.drawable.ic_canales_destacados,
            R.drawable.ic_videos_destacados,
            R.drawable.ic_videos_favoritos,
            R.drawable.ic_videos_verluego};

    String NAME = "Iniciar sesión";
    String INFO = "";
    int COVER = R.drawable.default_menu_backdrop;
    int PROFILE = R.drawable.unknown_user;

    //Listener
    private NavDrawerFragmentCallbacks mNavDrawerListener;

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

        return v;
    }

    //OnViewCreated
    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        mAdapter = new NavDrawerAdapter(TITLES, ICONS, SUBTITLES, COVER,PROFILE, NAME, INFO, getActivity(), this);
        mAdapter.setNavDrawerListener(this);

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
            mNavDrawerListener = (NavDrawerFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    //onDetach: throw away listener
    public void onDetach() {
        super.onDetach();
        mNavDrawerListener = null;
    }

    public void onItemClick(int position) {
        if(mNavDrawerListener != null){
            mNavDrawerListener.onNavDrawerOptionSelected(position);}

        selectItem(mAdapter.getCorrectPosition(position));
    }

    @Override
    public int getSelectedPosition() {
        return mCurrentSelectedPosition;
    }

    //Interface for interactions. Activity must implement methods.
    public interface NavDrawerFragmentCallbacks {
        public void onNavDrawerOptionSelected(int position);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }
    /**
     * Called when a list item is selected.
     *
     * Updates the state of the list, closes the drawer, and fowards the event to the parent activity to handle.
     *
     * @param position position of the item in the list
     */
    public void selectItem(int position) {
        mCurrentSelectedPosition = position;

        mAdapter.notifyDataSetChanged();
    }

}
