package com.ricardosaracino.pulllist.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.ricardosaracino.pulllist.cache.ImageCache;
import com.ricardosaracino.pulllist.datasource.DataSourceReader;
import com.ricardosaracino.pulllist.viewholder.AbstractImageViewHolder;


//https://developer.android.com/training/improving-layouts/smooth-scrolling.html#AsyncTask
//https://www.itworld.com/article/2705632/development/how-to-make-smooth-scrolling-listviews-in-android.html
public class ImageViewAsyncTask extends AsyncTask<AbstractImageViewHolder, Void, Bitmap> {

    private final DataSourceReader<Bitmap> dataSource;

    private final String cacheKey;

    private final ImageCache bitmapLruCache;

    private final int position;

    private AbstractImageViewHolder viewHolder;


    public ImageViewAsyncTask(int position, DataSourceReader<Bitmap> dataSource, ImageCache bitmapLruCache, String cacheKey) {

        this.position = position;

        this.dataSource = dataSource;

        this.bitmapLruCache = bitmapLruCache;

        this.cacheKey = cacheKey;
    }

    @Override
    protected Bitmap doInBackground(AbstractImageViewHolder... params) {

        if (isCancelled()) {
            return null;
        }

        viewHolder = params[0];

        try {

            Bitmap bitmap = null;

            if (bitmapLruCache != null) {

                bitmap = bitmapLruCache.getBitmap(cacheKey);
            }

            if (bitmap == null) {

                bitmap = dataSource.read();

                if (bitmapLruCache != null) {

                    bitmapLruCache.putBitmap(cacheKey, bitmap);
                }
            }

            return bitmap;

        } catch (Exception e) {
            // Log?
            Log.e("asdf", "asdfasdf", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);

        if (isCancelled()) {
            return;
        }

        if (bitmap != null) {

            ImageView imageView = viewHolder.getImageView();

            if (imageView != null && imageView.getVisibility() == View.VISIBLE && position == viewHolder.getPosition()) {

                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
