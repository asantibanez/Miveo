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

    public void swapVideos(ArrayList<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
        Log.d(TAG, "Videos swapped!");
    }

    @Override
    public int getItemCount() {
        if(mVideos != null)
            return mVideos.size();
        else
            return 0;
    }

    public Video getItem(int i) {
        if(mVideos != null && mVideos.size() > 0)
            return mVideos.get(i);

        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View convertView = mInflater.inflate(R.layout.fragment_channel_videos_list, viewGroup, false);
        return ViewHolder.newInstance(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Video video = getItem(i);
        Picasso.with(mInflater.getContext()).load(video.thumbnailUrl).into(viewHolder.thumbnail);
        viewHolder.title.setText(video.title);
        viewHolder.subTitle.setText("por " + video.uploadedBy);
    }

    public long getItemId(int i) {
        if(mVideos != null && mVideos.size() > 0)
            return mVideos.get(i).id;

        return 0;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        public final View convertView;
        private final ImageView thumbnail;
        private final TextView subTitle;
        private final TextView title;

        public static ViewHolder newInstance(View convertView) {

            ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView subTitle = (TextView) convertView.findViewById(R.id.sub_title);
            return new ViewHolder(convertView, thumbnail, title, subTitle);
        }

        private ViewHolder(View convertView, ImageView thumbnail, TextView title, TextView subTitle) {
            super(convertView);
            this.convertView = convertView;
            this.thumbnail = thumbnail;
            this.title = title;
            this.subTitle = subTitle;
        }
    }
}
