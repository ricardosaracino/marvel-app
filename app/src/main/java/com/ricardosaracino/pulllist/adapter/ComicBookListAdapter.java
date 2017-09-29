package com.ricardosaracino.pulllist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.activity.ComicBookActivity;
import com.ricardosaracino.pulllist.cache.BitmapLruCache;
import com.ricardosaracino.pulllist.datasource.BitmapDataSource;
import com.ricardosaracino.pulllist.model.ComicBook;
import com.ricardosaracino.pulllist.util.AbstractImageViewHolder;
import com.ricardosaracino.pulllist.util.AbstractViewHolder;
import com.ricardosaracino.pulllist.util.ImageViewAsyncTask;

import java.util.UUID;

public class ComicBookListAdapter extends ArrayAdapter<ComicBook> {

    private Context context;

    private BitmapLruCache bitmapLruCache;
    private Bitmap bitmapPlaceholder;

    public ComicBookListAdapter(Context context) {
        super(context, R.layout.comic_list_row);

        this.context = context;

        bitmapLruCache = new BitmapLruCache();

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmapPlaceholder = Bitmap.createBitmap(100, 100, conf);
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {

        ComicListRowHolder taskViewHolder;

        UUID uuid = UUID.nameUUIDFromBytes((ComicListRowHolder.class.toString()+getItem(position).getId()).getBytes());

        if (rowView == null) {

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.comic_list_row, parent, false);

            taskViewHolder = new ComicListRowHolder();

            taskViewHolder.titleView = rowView.findViewById(R.id.title);
            taskViewHolder.descView = rowView.findViewById(R.id.description);
            taskViewHolder.imageView = rowView.findViewById(R.id.image);

            rowView.setTag(taskViewHolder);

        } else {

            taskViewHolder = (ComicListRowHolder) rowView.getTag();

            if(uuid.equals(taskViewHolder.getUUID())) {

                // if the tag has the same uuid as the item just return the current row view
                // todo why the fuck do i need to do this??
                return  rowView;
            }
        }


        taskViewHolder.titleView.setText(getItem(position).getTitle());
        taskViewHolder.descView.setText(getItem(position).getDescription());

        taskViewHolder.imageView.setImageBitmap(bitmapPlaceholder);
        taskViewHolder.position = position;

        taskViewHolder.uuid = uuid;



        if (getItem(position).getImagePath() != null) {

            //https://developer.marvel.com/documentation/images
            String url = getItem(position).getImagePath() + "/standard_medium.jpg";

            new ImageViewAsyncTask(position, new BitmapDataSource(url), bitmapLruCache, getItem(position).getImagePath() + "/standard_medium.jpg").execute(taskViewHolder);
        }

        rowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ComicBook comicBook = getItem(position);

                    Intent intent = new Intent(context, ComicBookActivity.class);

                    Bundle b = new Bundle();

                    b.putInt("comic_id", comicBook.getId());

                    intent.putExtras(b);

                    context.startActivity(intent);

                    return true;
                }

                return false;
            }
        });

        return rowView;
    }

    private static class ComicListRowHolder implements AbstractImageViewHolder, AbstractViewHolder {

        TextView titleView;
        TextView descView;

        ImageView imageView;
        int position;

        UUID uuid;

        @Override
        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public int getPosition() {
            return position;
        }

        @Override
        public UUID getUUID() {
            return uuid;
        }
    }
}
