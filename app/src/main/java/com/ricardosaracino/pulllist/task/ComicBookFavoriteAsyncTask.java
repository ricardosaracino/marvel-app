package com.ricardosaracino.pulllist.task;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.datasource.ComicBookFavoriteDataSource;
import com.ricardosaracino.pulllist.model.ComicBook;

public class ComicBookFavoriteAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;

    private ComicBookFavoriteDataSource comicBookFavoriteDataSource;

    private FloatingActionButton floatingActionButton;

    private ComicBook comicBook;

    public ComicBookFavoriteAsyncTask(Context context, ComicBook comicBook, FloatingActionButton floatingActionButton) {
        this.context = context;
        this.floatingActionButton = floatingActionButton;
        this.comicBook = comicBook;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        comicBookFavoriteDataSource = new ComicBookFavoriteDataSource(context);
    }

    @Override
    protected Boolean doInBackground(Void... params) {


        try{
            return comicBookFavoriteDataSource.write(comicBook);
        }
        catch (Exception e)
        {
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        if(success) {
            floatingActionButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{ContextCompat.getColor(context, R.color.colorFavorite)}));

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // UN-Favorite
                    new ComicBookUnFavoriteAsyncTask(context,comicBook,(FloatingActionButton)v).execute();
                }
            });
        }
    }
}
