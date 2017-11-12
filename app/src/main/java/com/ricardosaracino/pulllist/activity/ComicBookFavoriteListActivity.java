package com.ricardosaracino.pulllist.activity;

import android.view.Menu;
import com.ricardosaracino.pulllist.R;

public class ComicBookFavoriteListActivity extends BaseActivity {

    @Override
    public void createContentView() {
        setContentView(R.layout.activity_comic_favorite_list);

        setTitle("Comics");
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}