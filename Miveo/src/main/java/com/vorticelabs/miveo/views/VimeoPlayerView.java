package com.vorticelabs.miveo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class VimeoPlayerView extends WebView {

    public static final String TAG = VimeoPlayerView.class.getSimpleName();

    private Context mContext;
    private VimeoWebChromeClient mWebChromeClient;
    private View mCustomView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    private FrameLayout mContentView;
    private FrameLayout mBrowserFrameLayout;
    private FrameLayout mLayout;

    public void setVideoId(long videoId) {
        Log.d(TAG, "Setting Video Id");
        loadUrl("http://player.vimeo.com/video/" + videoId + "?autoplay=1");
    }

    //VimeoWebChromeClient class
    private class VimeoWebChromeClient extends WebChromeClient {
        /*
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;


        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            VimeoPlayerView.this.setVisibility(View.GONE);

            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
        }

        public void onHideCustomView() {
            if (mCustomView == null)
                return;

            mCustomView.setVisibility(View.GONE);

            mCustomViewContainer.removeView(mCustomView);
            mCustomView = null;
            mCustomViewContainer.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();

            VimeoPlayerView.this.setVisibility(View.VISIBLE);
            VimeoPlayerView.this.goBack();
        }

        public View getVideoLoadingProgressView() {
            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                mVideoProgressView = inflater.inflate(R.layout.vimeo_player_loading_progress, null);
            }
            return mVideoProgressView;
        }

        public void onReceivedTitle(WebView view, String title) {
            ((Activity) mContext).setTitle(title);
        }

        public void onProgressChanged(WebView view, int newProgress) {
            ((Activity) mContext).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress*100);
        }

        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
        */
    }


    private void init(Context context) {
        mContext = context;
        //Activity a = (Activity) context;
        //mLayout = new FrameLayout(context);

        //Inflate layout
        //mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(a).inflate(R.layout.vimeo_player, null);
        //mContentView = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.main_content);
        //mCustomViewContainer = (FrameLayout) mBrowserFrameLayout.findViewById(R.id.fullscreen_custom_content);

        //Cover screen with Browser layout
        FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        //mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);

        // Configure Settings
        WebSettings s = getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);

        //Set WebChromeClient
        //mWebChromeClient = new VimeoWebChromeClient();
        setWebChromeClient(mWebChromeClient);

        //Set WebViewClient
        setWebViewClient(new WebViewClient());

        //Enable scrollbars
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //Add Content View to WebView
        //mContentView.addView(this);
    }

    public VimeoPlayerView(Context context) {
        super(context);
        init(context);
    }

    public VimeoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VimeoPlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /*
    public FrameLayout getLayout() {
        return mLayout;
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((mCustomView == null) && canGoBack()){
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    */

}
