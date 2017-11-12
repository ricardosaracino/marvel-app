package com.ricardosaracino.pulllist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;
import com.ricardosaracino.pulllist.R;

/*
todo remove
 */
public abstract class ComicBookBaseActivity extends BaseActivity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
