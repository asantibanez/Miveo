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
import com.vorticelabs.miveo.model.Channel;

import java.util.ArrayList;

public class ChannelsListAdapter extends RecyclerView.Adapter<ChannelsListAdapter.ViewHolder> {
    public static final String TAG = ChannelsListAdapter.class.getSimpleName();

    private ArrayList<Channel> mChannels;
    private int itemLayout;

    //Listener for item click events
    private ChannelsAdapterListener mChannelListener;

    public void setChannelsAdapterListener(ChannelsAdapterListener clistener) {
        mChannelListener = clistener;
    }

    public interface ChannelsAdapterListener {
        public void onItemClick(int position);
    }

    public ChannelsListAdapter(ArrayList<Channel> mChannels, int itemLayout) {
        this.itemLayout = itemLayout;
        this.mChannels = mChannels;
    }

    @Override
    public ChannelsListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view, mChannelListener);
    }

    @Override
    public void onBindViewHolder(ChannelsListAdapter.ViewHolder viewHolder, int i) {
        Channel channel = mChannels.get(i);

        if(channel.logoUrl != null && channel.logoUrl.length() > 0)
            Picasso.with(viewHolder.thumbnail.getContext()).load(channel.logoUrl).into(viewHolder.thumbnail);
        else
            viewHolder.thumbnail.setImageBitmap(null);

        viewHolder.title.setText(channel.name);
        viewHolder.subtitle.setText(channel.totalSubscribers + " suscriptores");
    }

    @Override
    public int getItemCount() {
        if (mChannels != null)
            return mChannels.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;
        TextView title;
        TextView subtitle;

        private ChannelsAdapterListener mChannelListener;

        public ViewHolder(View itemView, ChannelsAdapterListener listener) {
            super(itemView);

            mChannelListener = listener;

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.sub_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Element " + getPosition() + " clicked.");
            if (mChannelListener != null) {
                mChannelListener.onItemClick(getPosition());
            }
        }
    }

    public void swapChannels(ArrayList<Channel> channels) {
        mChannels = channels;
        notifyDataSetChanged();
        Log.d(TAG, "Channels swapped!");
    }
}
