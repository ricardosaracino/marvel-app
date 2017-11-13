package com.ricardosaracino.pulllist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.ricardosaracino.pulllist.R;

/*
todo remove
 */
public abstract class ComicBookBaseActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Comics");
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (this instanceof ComicBookWeekBaseActivity) {
            getMenuInflater().inflate(R.menu.menu_comic_week, menu);
        } else if (this instanceof ComicBookSearchListActivity) {
            getMenuInflater().inflate(R.menu.menu_comic_search, menu);
        } else if (this instanceof ComicBookFavoriteListActivity) {
            getMenuInflater().inflate(R.menu.menu_comic_favorite, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.comic_search:
                this.startActivity(new Intent(ComicBookBaseActivity.this, ComicBookSearchListActivity.class));
                return true;

            case R.id.comic_favorite:
                this.startActivity(new Intent(ComicBookBaseActivity.this, ComicBookFavoriteListActivity.class));
                return true;

            case R.id.comic_week_current:
                this.startActivity(new Intent(ComicBookBaseActivity.this, ComicBookWeekCurrentListActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
