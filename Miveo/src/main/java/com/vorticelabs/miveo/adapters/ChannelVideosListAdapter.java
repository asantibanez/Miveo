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

    ArrayList<Video> mVideos;
    int itemLayout;

    public ChannelVideosListAdapter(ArrayList<Video> mVideos, int itemLayout){
        this.itemLayout = itemLayout;
        this.mVideos = mVideos;
    }

    @Override
    public ChannelVideosListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChannelVideosListAdapter.ViewHolder viewHolder, int i) {
        Video video = mVideos.get(i);

        Picasso.with(viewHolder.thumbnail.getContext()).load(video.thumbnailUrl).into(viewHolder.thumbnail);
        viewHolder.title.setText(video.title);
        viewHolder.subtitle.setText(video.uploadedBy);
    }

    @Override
    public int getItemCount() {
        if(mVideos != null)
            return mVideos.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView title;
        TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.sub_title);

        }
    }

//    private ArrayList<Video> mVideos;
//    private int itemLayout;
//    private Context mContext;
//
//
//    public ChannelVideosListAdapter(ArrayList<Video> mVideos, int itemLayout){
//        this.mVideos = mVideos;
//        this.itemLayout = itemLayout;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//
//        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
//        return new ViewHolder(convertView);
//
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
//
//        Video video = mVideos.get(i);
//        Picasso.with(mContext).load(video.thumbnailUrl).into(viewHolder.thumbnail);
//        viewHolder.title.setText(video.title);
//        viewHolder.subTitle.setText("por " + video.uploadedBy);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if(mVideos != null)
//            return mVideos.size();
//        else
//            return 0;
//    }
//
//    public static final class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView thumbnail;
//        public TextView title;
//        public TextView subTitle;
//
//        public ViewHolder (View convertView) {
//            super(convertView);
//            thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
//            title = (TextView) convertView.findViewById(R.id.title);
//            subTitle = (TextView) convertView.findViewById(R.id.sub_title);
//        }
//    }

    public void swapVideos(ArrayList<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
        Log.d(TAG, "Videos swapped!");
    }

    //Old code

//    private ArrayList<Video> mVideos;
//    private LayoutInflater mInflater;
//
//    public ChannelVideosListAdapter(Context context){
//        super();
//        mInflater = LayoutInflater.from(context);
//    }
//
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
