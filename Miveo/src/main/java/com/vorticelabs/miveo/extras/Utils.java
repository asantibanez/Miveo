package com.vorticelabs.miveo.extras;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

public class Utils {

    public static OAuthConsumer getUnsignedOAuthConsumer(){
        OAuthConsumer consumer = new DefaultOAuthConsumer(
                Constants.VIMEO_CONSUMER_KEY,
                Constants.VIMEO_CONSUMER_SECRET
        );
        return consumer;
    }

    /*
    public static OAuthConsumer getSignedOAuthConsumer(){
        OAuthConsumer consumer = new DefaultOAuthConsumer(
                Constants.API_CONSUMER_KEY,
                Constants.API_CONSUMER_SECRET
        );
        consumer.setTokenWithSecret(
                Constants.API_TOKEN_KEY,
                Constants.API_TOKEN_SECRET
        );
        return consumer;
    }
    */
}
