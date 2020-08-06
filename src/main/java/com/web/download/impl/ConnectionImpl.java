package com.web.download.impl;

import com.web.download.api.Connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


public class ConnectionImpl implements Connection {

    private HttpURLConnection conn;

    public ConnectionImpl(HttpURLConnection conn) {
        this.conn = conn;
    }

    @Override
    public byte[] read(int startPos, int endPos) throws IOException {
        this.conn.setRequestProperty("Range", String.format("bytes=%d-%d", startPos, endPos));
        this.conn.connect();

        try (InputStream is = this.conn.getInputStream()) {
            return getBytesFromInputStream(is);
        } finally {
            this.close();
        }

    }

    //InputStream --> byte
    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[0xFFFF];
            for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
                os.write(buffer, 0, len);
            }
            return os.toByteArray();
        }
    }

    @Override
    public int getContentLength() {
        int length = this.conn.getContentLength();
        this.close();
        return length;
    }

    @Override
    public void close() {
        this.conn.disconnect();
    }

}
