package com.ricardosaracino.pulllist.cache;

import android.graphics.Bitmap;

// https://stackoverflow.com/questions/18369123/using-lrucache-to-store-bitmap-in-memory
// From com.android.volley.toolbox.ImageLoader
public interface ImageCache {
    public Bitmap getBitmap(String url);

    public void putBitmap(String url, Bitmap bitmap);
}