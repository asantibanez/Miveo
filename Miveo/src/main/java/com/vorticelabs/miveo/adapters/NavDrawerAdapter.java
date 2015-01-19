package com.vorticelabs.miveo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vorticelabs.miveo.R;

public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {
    public final static String TAG = NavDrawerAdapter.class.getSimpleName();

    //Available item types
    private static final int HEADER_VIEW_TYPE = 0;
    private static final int OPTION_VIEW_TYPE = 1;

    //Available view resources
    private static final int HEADER_VIEW_RESOURCE_ID = R.layout.nav_drawer_header;
    private static final int OPTION_VIEW_RESOURCE_ID = R.layout.nav_drawer_list_item_option;

    //Variables
    private String mTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

    private int cover;        //String Resource for header View Name
    private int profile;        //int Resource for header view profile picture
    private String name;
    private String info; //String Resource for header view email

    Context context;

    //Constructor
    public NavDrawerAdapter(String Titles[], int Icons[], int Cover, int Profile, String Name, String Info, Context passedContext) {
        mTitles = Titles;
        mIcons = Icons;
        cover = Cover;
        profile = Profile;
        name = Name;
        info = Info;
        this.context = passedContext;
    }

    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderId;

        TextView title;
        ImageView icon;
        ImageView profile;
        ImageView cover;
        TextView name;
        TextView info;
        Context contxt;

        public ViewHolder(View itemView, int ViewType, Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            contxt = c;

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if (ViewType == HEADER_VIEW_TYPE) {
                cover = (ImageView) itemView.findViewById(R.id.userCover);
                profile = (ImageView) itemView.findViewById(R.id.userPhoto);
                name = (TextView) itemView.findViewById(R.id.userName);
                info = (TextView) itemView.findViewById(R.id.userInfo);
                holderId = 0; // Setting holder id = 0 as the object being populated are of type header view
            } else {
                icon = (ImageView) itemView.findViewById(R.id.option_icon);
                title = (TextView) itemView.findViewById(R.id.option_title);
                holderId = 1;
            }
        }
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Element " + getPosition() + " clicked.");
        }
    }

        //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
        //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
        // if the viewType is TYPE_HEADER
        // and pass it to the view holder

    @Override
    public NavDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == OPTION_VIEW_TYPE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(OPTION_VIEW_RESOURCE_ID, parent, false); //Inflating the layout
            ViewHolder vhItem = new ViewHolder(v,viewType, context); //Creating ViewHolder and passing the object of type view
            return vhItem; // Returning the created object
            //inflate your layout and pass it to view holder

        } else if (viewType == HEADER_VIEW_TYPE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(HEADER_VIEW_RESOURCE_ID,parent,false); //Inflating the layout
            ViewHolder vhHeader = new ViewHolder(v,viewType, context); //Creating ViewHolder and passing the object of type view
            return vhHeader; //returning the object created
        }
        return null;
    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(NavDrawerAdapter.ViewHolder holder, int position) {
        if(holder.holderId == 1) {
            // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            //Title
            holder.title.setText(mTitles[position -1]);
            //Icon
            holder.icon.setImageResource(mIcons[position -1]);
        }
        else{
            //Cover
            holder.cover.setImageResource(R.drawable.default_menu_backdrop);
            //User photo
            holder.profile.setImageResource(R.drawable.user);
            //User name
            holder.name.setText(R.string.username);
            //User info
            holder.info.setText("omartozk@gmail.com");
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        // the number of items in the list will be +1 the titles including the header view.
        return mTitles.length+1;
    }


    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return HEADER_VIEW_TYPE;

        return OPTION_VIEW_TYPE;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}