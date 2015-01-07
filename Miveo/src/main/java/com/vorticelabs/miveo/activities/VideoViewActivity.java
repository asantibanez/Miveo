package com.vorticelabs.miveo.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.vorticelabs.miveo.R;
import com.vorticelabs.miveo.loaders.VideoLoader;
import com.vorticelabs.miveo.model.Video;

import org.w3c.dom.Text;

public class VideoViewActivity extends FragmentActivity
    implements LoaderManager.LoaderCallbacks<VideoLoader.LoaderResponse> {

    public static final String TAG = VideoViewActivity.class.getSimpleName();

    //Available Args
    public static final String CHANNEL_ID = "channel_id";
    public static final String VIDEO_ID = "video_id";

    //Variables
    private int mChannelId;
    private long mVideoId;

    //Controls
    private ImageView mThumbnail;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mUploadInfo;

    //Factory method for intent resolution
    public static Intent getStartActivityIntent(Context context, int channelId, long videoId) {
        Intent activityIntent = new Intent(context, VideoViewActivity.class);
        activityIntent.putExtra(CHANNEL_ID, channelId);
        activityIntent.putExtra(VIDEO_ID, videoId);
        return activityIntent;
    }

    //Lifecycle methods
    //onCreate
    protected void onCreate(Bundle savedInstanceState) {
        //Setup view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        //Get controls
        mThumbnail = (ImageView) findViewById(R.id.thumbnail);
        mTitle = (TextView) findViewById(R.id.title);
        mDescription = (TextView) findViewById(R.id.description);
        mUploadInfo = (TextView) findViewById(R.id.upload_info);

        //Handle state
        if(savedInstanceState != null) {
            mChannelId = savedInstanceState.getInt(CHANNEL_ID);
            mVideoId = savedInstanceState.getLong(VIDEO_ID);
        } else {
            mChannelId = getIntent().getIntExtra(CHANNEL_ID, 0);
            mVideoId = getIntent().getLongExtra(VIDEO_ID, 0);
        }

        //ActionBar Up Navigation
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Init loader
        getSupportLoaderManager().initLoader(0, null, this);
    }

    //onSaveInstanceState
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHANNEL_ID, mChannelId);
        outState.putLong(VIDEO_ID, mVideoId);
    }

    //Menu methods
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video_view, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Loader Callbacks
    public Loader<VideoLoader.LoaderResponse> onCreateLoader(int id, Bundle args) {
        return new VideoLoader(this, mChannelId, mVideoId);
    }

    public void onLoadFinished(Loader<VideoLoader.LoaderResponse> loader, VideoLoader.LoaderResponse data) {
        if(data == null || data.video == null)
            return;

        Video video = data.video;
        if(video.thumbnailUrl.length() > 0) {
            Picasso.with(this).load(video.thumbnailUrl).into(mThumbnail);
            mThumbnail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent startActivityIntent = VideoPlaybackActivity.getStartActivityIntent(
                            VideoViewActivity.this,
                            mVideoId
                    );
                    VideoViewActivity.this.startActivity(startActivityIntent);
                }
            });
        }

        mTitle.setText(video.title);
        mDescription.setText(video.description);
        mUploadInfo.setText(video.uploadedBy);
    }

    public void onLoaderReset(Loader<VideoLoader.LoaderResponse> loader) {

    }
}
