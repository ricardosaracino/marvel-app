package com.ricardosaracino.pulllist.activity;

import android.view.Menu;
import com.ricardosaracino.pulllist.R;

public class ComicBookSearchListActivity extends ComicBookBaseActivity {

    @Override
    public void createContentView() {
        setContentView(R.layout.activity_comic_search_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true; // no menu
    }
}