package com.vorticelabs.miveo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vorticelabs.miveo.R;

import java.util.ArrayList;

public class NavDrawerAdapter extends ArrayAdapter<String> {
    public final static String TAG = NavDrawerAdapter.class.getSimpleName();

    //Available item types
    private final int OPTION_VIEW_TYPE = 0;
    private final int SUB_OPTION_VIEW_TYPE = 1;
    private final int MAX_VIEW_TYPE = 2;

    //Available view resources
    private final int OPTION_VIEW_RESOURCE_ID = R.layout.list_item_nav_drawer_option;
    private final int SUB_OPTION_VIEW_RESOURCE_ID = R.layout.list_item_nav_drawer_sub_option;

    //Variables
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mOptions;

    //Constructor
    public NavDrawerAdapter(Context context, int resource, ArrayList<String> options) {
        super(context, resource, options);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mOptions = options;
    }

    //ViewTypes methods
    public int getViewTypeCount() {
        return MAX_VIEW_TYPE;
    }
    public int getItemViewType(int position) {
        if(position > 3)
            return SUB_OPTION_VIEW_TYPE;
        else
            return OPTION_VIEW_TYPE;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        int viewType = getItemViewType(position);

        switch (viewType) {
            //Option View
            case OPTION_VIEW_TYPE:
                if (convertView == null) {
                    convertView = mInflater.inflate(OPTION_VIEW_RESOURCE_ID, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.icon = (ImageView) convertView.findViewById(R.id.option_icon);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.option_title);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                //Assign Data
                //Title
                viewHolder.title.setText(getItem(position));
                //Icon
                switch (position) {
                    case 0:
                        viewHolder.icon.setImageResource(R.drawable.ic_canales_destacados);
                        break;
                    case 1:
                        viewHolder.icon.setImageResource(R.drawable.ic_videos_destacados);
                        break;
                    case 2:
                        viewHolder.icon.setImageResource(R.drawable.ic_videos_favoritos);
                        break;
                    case 3:
                        viewHolder.icon.setImageResource(R.drawable.ic_videos_verluego);
                        break;
                }

            //SubOption View
            case SUB_OPTION_VIEW_TYPE:

                if (convertView == null) {
                    convertView = mInflater.inflate(SUB_OPTION_VIEW_RESOURCE_ID, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.title = (TextView) convertView.findViewById(R.id.option_title);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                //Assign Data
                //Title
                viewHolder.title.setText(getItem(position));
                //Icon
//                switch (position) {
//                    case 4:
//                        viewHolder.icon.setImageResource(R.drawable.ic_menu_feedback);
//                        break;
//                    case 5:
//                        viewHolder.icon.setImageResource(R.drawable.ic_menu_settings);
//                        break;
//                }
        }

        return convertView;
    }

    //ViewHolder class
    final static class ViewHolder {
        TextView title;
        ImageView icon;
    }
}