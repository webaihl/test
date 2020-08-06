package com.web.download.impl;


import com.web.download.api.Connection;
import com.web.download.api.ConnectionException;
import com.web.download.api.ConnectionManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConnectionManagerImpl implements ConnectionManager {

    private static final int TIME_OUT = 5_000;

    @Override
    public Connection open(String url) throws ConnectionException {

        URL connUrl;
        HttpURLConnection urlConnection = null;
        try {
            connUrl = new URL(url);
            urlConnection = (HttpURLConnection) connUrl.openConnection();
            urlConnection.setConnectTimeout(TIME_OUT);
            urlConnection.setReadTimeout(TIME_OUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ConnectionImpl(urlConnection);

    }

}
