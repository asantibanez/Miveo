package com.vorticelabs.miveo.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.model.Channel;
import com.vorticelabs.miveo.model.Video;

import java.util.ArrayList;

public class ChannelsListAdapter extends BaseAdapter {
    public static final String TAG = ChannelsListAdapter.class.getSimpleName();

    private ArrayList<Channel> mChannels;
    private LayoutInflater mInflater;

    public ChannelsListAdapter(Context context){
        super();
        mInflater = LayoutInflater.from(context);
    }

    public void swapVideos(ArrayList<Channel> channels) {
        mChannels = channels;
        notifyDataSetChanged();
        Log.d(TAG, "Channels swapped!");
    }

    public int getCount() {
        if(mChannels != null)
            return mChannels.size();
        else
            return 0;
    }

    public Channel getItem(int i) {
        if(mChannels != null && mChannels.size() > 0)
            return mChannels.get(i);

        return null;
    }

    public long getItemId(int i) {
        if(mChannels != null && mChannels.size() > 0)
            return mChannels.get(i).id;

        return 0;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Channel channel = getItem(i);

        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_channel, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.subTitle = (TextView) convertView.findViewById(R.id.sub_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Set data
        if(channel.logoUrl != null && channel.logoUrl.length() > 0)
            Picasso.with(mInflater.getContext()).load(channel.logoUrl).into(viewHolder.thumbnail);
        else
            viewHolder.thumbnail.setImageBitmap(null);

        viewHolder.title.setText(channel.name);
        viewHolder.subTitle.setText(channel.totalVideos + " videos");

        return convertView;
    }

    static class ViewHolder{
        ImageView thumbnail;
        TextView title;
        TextView subTitle;
    }
}
