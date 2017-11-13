package com.ricardosaracino.pulllist.activity;

import android.os.Bundle;
import android.view.Menu;
import com.ricardosaracino.pulllist.R;

public class ComicBookFavoriteListActivity extends ComicBookBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Favorites");
    }

    @Override
    public void createContentView() {
        setContentView(R.layout.activity_comic_favorite_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true; // no menu
    }
}