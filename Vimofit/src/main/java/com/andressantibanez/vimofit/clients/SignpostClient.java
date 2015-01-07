
package com.andressantibanez.vimofit.clients;

import java.io.IOException;
import java.net.HttpURLConnection;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpRequest;
import retrofit.client.OkClient;
import retrofit.client.Request;

public class SignpostClient extends OkClient {

    public OAuthConsumer mConsumer;

    public SignpostClient(OAuthConsumer consumer) {
        super();
        mConsumer = consumer;
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {
        HttpURLConnection connection = super.openConnection(request);
        try {
            HttpRequest signedReq = mConsumer.sign(connection);

        } catch (OAuthMessageSignerException e) {
            //Fail to sign, ignore
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            //Fail to sign, ignore
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            //Fail to sign, ignore
            e.printStackTrace();
        }
        return connection;
    }
}
