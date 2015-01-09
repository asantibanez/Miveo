
package com.andressantibanez.vimofit.endpoints;

import com.andressantibanez.vimofit.clients.SignpostClient;

import oauth.signpost.OAuthConsumer;
import retrofit.RestAdapter;

public abstract class BaseEndpoint {

    public static final String BASE_ENDPOINT = "http://vimeo.com/api/rest";

    protected OAuthConsumer consumer;
    protected RestAdapter restAdapter;
    protected RestAdapter.LogLevel logLevel;

    public BaseEndpoint(final OAuthConsumer consumer) {
        this.consumer = consumer;
        this.logLevel = RestAdapter.LogLevel.FULL;

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_ENDPOINT)
                .setClient(new SignpostClient(this.consumer))
                .setLogLevel(this.logLevel)
                .build();
    }

    public void setLogLevel(RestAdapter.LogLevel logLevel) {
        this.logLevel = logLevel;
        restAdapter.setLogLevel(this.logLevel);
    }

}
