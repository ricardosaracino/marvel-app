package com.ricardosaracino.pulllist.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

// My ImageCache implementation
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {
    private static final int DEFAULT_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;

    public BitmapLruCache() {
        this(DEFAULT_CACHE_SIZE);
    }

    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value == null ? 0 : value.getRowBytes() * value.getHeight() / 1024;
    }
}


