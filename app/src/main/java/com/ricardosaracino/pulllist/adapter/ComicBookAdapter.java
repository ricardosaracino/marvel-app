package com.ricardosaracino.pulllist.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.cache.BitmapFileCache;
import com.ricardosaracino.pulllist.datasource.BitmapDataSource;
import com.ricardosaracino.pulllist.datasource.ComicBookFavoriteDataSource;
import com.ricardosaracino.pulllist.model.ComicBook;
import com.ricardosaracino.pulllist.task.ComicBookFavoriteAsyncTask;
import com.ricardosaracino.pulllist.task.ComicBookUnFavoriteAsyncTask;
import com.ricardosaracino.pulllist.task.ImageViewAsyncTask;
import com.ricardosaracino.pulllist.viewholder.AbstractImageViewHolder;

public class ComicBookAdapter extends ArrayAdapter<ComicBook> {

    private BitmapFileCache bitmapFileCache;

    private Context context;

    public ComicBookAdapter(Context context) {
        super(context, R.layout.comic_detail);

        this.context = context;

        bitmapFileCache = new BitmapFileCache(context);
    }

    @Override
    public View getView(final int position, View rowView, final ViewGroup parent) {

        ComicDetailHolder taskViewHolder;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.comic_detail, parent, false);

            taskViewHolder = new ComicDetailHolder();

            taskViewHolder.titleView = rowView.findViewById(R.id.title);
            taskViewHolder.descView = rowView.findViewById(R.id.description);
            taskViewHolder.imageView = rowView.findViewById(R.id.image);
            taskViewHolder.position = position;

            rowView.setTag(taskViewHolder);
        } else {
            taskViewHolder = (ComicDetailHolder) rowView.getTag();
        }


        try{
            final ComicBook comicBook = getItem(position);

            FloatingActionButton fabFavorite = rowView.findViewById(R.id.action_favorite);

            ComicBookFavoriteDataSource comicBookFavoriteDataSource = new ComicBookFavoriteDataSource(context);

            if(comicBookFavoriteDataSource.isFavorite(comicBook)){

                fabFavorite.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{ContextCompat.getColor(context, R.color.colorFavorite)}));

                fabFavorite.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // UN-Favorite
                        new ComicBookUnFavoriteAsyncTask(context,comicBook,(FloatingActionButton)v).execute();
                    }
                });
            }
            else
            {
                fabFavorite.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        new ComicBookFavoriteAsyncTask(context,comicBook,(FloatingActionButton)v).execute();
                    }
                });
            }
        }
        catch (Exception e)
        {
        }

        
        taskViewHolder.titleView.setText(getItem(position).getTitle());
        taskViewHolder.descView.setText(getItem(position).getDescription());
        taskViewHolder.imageView.setImageResource(0);
        taskViewHolder.position = position;


        if (getItem(position).getImagePath() != null) {

            //https://developer.marvel.com/documentation/images
            String url = getItem(position).getImagePath() + ".jpg";

            new ImageViewAsyncTask(position, new BitmapDataSource(url), bitmapFileCache, url).execute(taskViewHolder);
        }

        return rowView;
    }

    private static class ComicDetailHolder implements AbstractImageViewHolder {

        TextView titleView;
        TextView descView;
        ImageView imageView;
        int position;

        @Override
        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public int getPosition() {
            return position;
        }
    }
}
