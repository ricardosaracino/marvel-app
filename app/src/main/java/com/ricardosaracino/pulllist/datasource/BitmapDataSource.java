package com.ricardosaracino.pulllist.datasource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class BitmapDataSource implements DataSourceReader<Bitmap> {

    private String url;

    public BitmapDataSource(String url) {
        this.url = url;
    }

    @Override
    public Bitmap read() throws DataSourceException {

        HttpURLConnection urlConnection = null;
        Bitmap bitmap = null;

        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HTTP_OK) {
                throw new HttpStatusException("Error Status Code", statusCode);
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            throw new DataSourceException(e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return bitmap;
    }

    @Override
    public int getResultCount() {
        return 0;
    }

    @Override
    public int getResultOffset() {
        return 0;
    }

    @Override
    public int getResultTotal() {
        return 0;
    }
}
