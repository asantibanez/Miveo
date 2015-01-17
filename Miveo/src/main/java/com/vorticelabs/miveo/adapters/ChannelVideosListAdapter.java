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

    //Listener for item click events
    private ChannelVideosAdapterListener mListener;
    public void setChannelVideosAdapterListener(ChannelVideosAdapterListener listener) {
        mListener = listener;
    }

    public interface ChannelVideosAdapterListener {
        public void onItemClick(int position);
    }

    public ChannelVideosListAdapter(ArrayList<Video> mVideos, int itemLayout){
        this.itemLayout = itemLayout;
        this.mVideos = mVideos;
    }

    @Override
    public ChannelVideosListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ChannelVideosListAdapter.ViewHolder viewHolder, int i) {
        Video video = mVideos.get(i);

        Picasso.with(viewHolder.thumbnail.getContext()).load(video.thumbnailUrl).into(viewHolder.thumbnail);
        viewHolder.title.setText(video.title);
        viewHolder.subtitle.setText("por " + video.uploadedBy);
    }

    @Override
    public int getItemCount() {
        if(mVideos != null)
            return mVideos.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thumbnail;
        TextView title;
        TextView subtitle;

        private ChannelVideosAdapterListener mListener;

        public ViewHolder(View itemView, ChannelVideosAdapterListener listener) {
            super(itemView);

            mListener = listener;

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.sub_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Element " + getPosition() + " clicked.");
            if(mListener != null)
                mListener.onItemClick(getPosition());
        }
    }

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
