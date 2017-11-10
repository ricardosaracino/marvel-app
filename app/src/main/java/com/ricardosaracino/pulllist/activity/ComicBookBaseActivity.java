package com.ricardosaracino.pulllist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import com.ricardosaracino.pulllist.R;

public abstract class ComicBookBaseActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    protected static final int TAB_LIST = 0;
    protected static final int TAB_SEARCH = 1;
    protected static final int TAB_FAVORITE = 2;
    protected TabLayout tabLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTabLayoutView();
    }

    private void createTabLayoutView() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        if (tabLayout != null) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_list_hd), TAB_LIST, this instanceof ComicBookListActivity);
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search_hd), TAB_SEARCH, this instanceof ComicBookSearchListActivity);
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite_hd), TAB_FAVORITE, this instanceof ComicBookFavoriteListActivity);

            tabLayout.addOnTabSelectedListener(this);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case TAB_LIST:
                startActivity(new Intent(ComicBookBaseActivity.this, ComicBookListActivity.class));
                break;

            case TAB_SEARCH:
                startActivity(new Intent(ComicBookBaseActivity.this, ComicBookSearchListActivity.class));
                break;

            case TAB_FAVORITE:
                this.startActivity(new Intent(this, ComicBookFavoriteListActivity.class));
                break;
        }
    }
}
