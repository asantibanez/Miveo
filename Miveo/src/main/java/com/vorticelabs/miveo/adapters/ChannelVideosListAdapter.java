package com.vorticelabs.miveo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.model.Video;

import java.util.ArrayList;

public class ChannelVideosListAdapter extends RecyclerView.Adapter<ChannelVideosListAdapter.ViewHolder> {
    public static final String TAG = ChannelVideosListAdapter.class.getSimpleName();

    private ArrayList<Video> mVideos;
    private LayoutInflater mInflater;

    public ChannelVideosListAdapter(Context context){
        super();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View convertView = mInflater.inflate(R.layout.fragment_channel_videos_list, parent, false);
        return new ViewHolder(convertView);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Video video = mVideos.get(i);
        Picasso.with(mInflater.getContext()).load(video.thumbnailUrl).into(viewHolder.thumbnail);
        viewHolder.title.setText(video.title);
        viewHolder.subTitle.setText("por " + video.uploadedBy);
    }
    @Override
    public int getItemCount() {
        if(mVideos != null)
            return mVideos.size();
        else
            return 0;
    }
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title;
        public TextView subTitle;

        public ViewHolder (View convertView) {
            super(convertView);
            thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            title = (TextView) convertView.findViewById(R.id.title);
            subTitle = (TextView) convertView.findViewById(R.id.sub_title);
        }
    }

    public void swapVideos(ArrayList<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
        Log.d(TAG, "Videos swapped!");
    }
}
