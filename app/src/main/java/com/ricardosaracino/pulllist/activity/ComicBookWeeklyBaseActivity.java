package com.ricardosaracino.pulllist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import com.ricardosaracino.pulllist.R;

public abstract class ComicBookWeeklyBaseActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    protected static final int TAB_PREVIOUS = 0;
    protected static final int TAB_CURRENT = 1;
    protected static final int TAB_NEXT = 2;
    protected TabLayout tabLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTabLayoutView();
    }

    private void createTabLayoutView() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        if (tabLayout != null) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_list_hd), TAB_PREVIOUS, this instanceof this);
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search_hd), TAB_CURRENT, this instanceof this);
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite_hd), TAB_NEXT, this instanceof this);

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
            case TAB_PREVIOUS:
                startActivity(new Intent(ComicBookWeeklyBaseActivity.this, ComicBookListActivity.class));
                break;

            case TAB_CURRENT:
                startActivity(new Intent(ComicBookWeeklyBaseActivity.this, ComicBookSearchListActivity.class));
                break;

            case TAB_NEXT:
                this.startActivity(new Intent(ComicBookWeeklyBaseActivity.this, ComicBookFavoriteListActivity.class));
                break;
        }
    }
}
