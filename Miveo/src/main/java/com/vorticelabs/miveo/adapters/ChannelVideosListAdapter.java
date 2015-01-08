package com.vorticelabs.miveo.adapters;

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
    private int itemLayout;

    public ChannelVideosListAdapter(ArrayList<Video> mVideos, int itemLayout){
        this.mVideos = mVideos;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(convertView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        Video video = mVideos.get(i);
        Picasso.with(viewHolder.thumbnail.getContext()).load(video.thumbnailUrl).into(viewHolder.thumbnail);
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

//    private ArrayList<Video> mVideos;
//    private LayoutInflater mInflater;
//
//    public ChannelVideosListAdapter(Context context){
//        super();
//        mInflater = LayoutInflater.from(context);
//    }
//
//    public void swapVideos(ArrayList<Video> videos) {
//        mVideos = videos;
//        notifyDataSetChanged();
//        Log.d(TAG, "Videos swapped!");
//    }
//
//    public int getCount() {
//        if(mVideos != null)
//            return mVideos.size();
//        else
//            return 0;
//    }
//
//    public Video getItem(int i) {
//        if(mVideos != null && mVideos.size() > 0)
//            return mVideos.get(i);
//
//        return null;
//    }
//
//    public long getItemId(int i) {
//        if(mVideos != null && mVideos.size() > 0)
//            return mVideos.get(i).id;
//
//        return 0;
//    }
//
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        Video video = getItem(i);
//
//        ViewHolder viewHolder;
//        if(convertView == null) {
//            convertView = mInflater.inflate(R.layout.list_item_channel_video, viewGroup, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
//            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
//            viewHolder.subTitle = (TextView) convertView.findViewById(R.id.sub_title);
//            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        //Set data
//        Picasso.with(mInflater.getContext()).load(video.thumbnailUrl).into(viewHolder.thumbnail);
//        viewHolder.title.setText(video.title);
//        viewHolder.subTitle.setText("por " + video.uploadedBy);
//
//        return convertView;
//    }
//
//    static class ViewHolder{
//        ImageView thumbnail;
//        TextView title;
//        TextView subTitle;
//    }
}
