package com.ricardosaracino.pulllist.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BitmapFileCache implements ImageCache {

    private Context context;

    public BitmapFileCache(Context context) {

        this.context = context;
    }

    @Override
    public Bitmap getBitmap(String key) {

        try {

            UUID keyUuid = UUID.nameUUIDFromBytes(key.getBytes());

            Uri cacheUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(context.getExternalCacheDir(), keyUuid.toString()));

            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), cacheUri);

        } catch (Exception e) {

            return null;
        }
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {

        UUID keyUuid = UUID.nameUUIDFromBytes(key.getBytes());

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File file = new File(context.getExternalCacheDir(), keyUuid.toString());

        try {
            OutputStream outputStream = new FileOutputStream(file);

            outputStream.write(bytes.toByteArray());

            outputStream.close();

        } catch (Exception e) {
            Log.e("asdf", "asdfasdf", e);
        }
    }
}
