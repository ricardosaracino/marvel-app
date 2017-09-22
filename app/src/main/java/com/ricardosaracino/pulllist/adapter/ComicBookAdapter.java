package com.ricardosaracino.pulllist.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class ComicBookAdapter extends ArrayAdapter<ComicBook> {

    private Bitmap myBitmap;

    public ComicBookAdapter(Context context) {
        super(context, R.layout.comic_detail);
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {


        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.comic_detail, parent, false);
        }

        TextView labelView = rowView.findViewById(R.id.label);
        TextView valueView = rowView.findViewById(R.id.value);

        labelView.setText(getItem(position).getTitle());
        valueView.setText(getItem(position).getDescription());


        //https://developer.marvel.com/documentation/images
        final String src = getItem(position).getImagePath() + ".jpg";

        final ImageView imageView = rowView.findViewById(R.id.image);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    java.net.URL url = new java.net.URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);

                } catch (Exception e) {
                    // log error
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                imageView.setImageBitmap(myBitmap);
            }
        }.execute();

        return rowView;
    }
}
