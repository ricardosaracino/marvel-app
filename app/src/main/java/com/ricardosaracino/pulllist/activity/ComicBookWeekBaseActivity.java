package com.ricardosaracino.pulllist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.ricardosaracino.pulllist.R;

public abstract class ComicBookWeekBaseActivity extends ComicBookBaseActivity implements TabLayout.OnTabSelectedListener {

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

            tabLayout.addTab(tabLayout.newTab().setText("Previous"), TAB_PREVIOUS, this instanceof ComicBookWeekPreviousListActivity);
            tabLayout.addTab(tabLayout.newTab().setText("Current"), TAB_CURRENT, this instanceof ComicBookWeekCurrentListActivity);
            tabLayout.addTab(tabLayout.newTab().setText("Next"), TAB_NEXT, this instanceof ComicBookWeekNextListActivity);

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
        //https://stackoverflow.com/questions/5151591/android-left-to-right-slide-animation

        switch (tab.getPosition()) {

            case TAB_PREVIOUS:
                startActivityForResult(new Intent(ComicBookWeekBaseActivity.this, ComicBookWeekPreviousListActivity.class), 500);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case TAB_CURRENT:
                startActivityForResult(new Intent(ComicBookWeekBaseActivity.this, ComicBookWeekCurrentListActivity.class), 500);
                if(this instanceof ComicBookWeekNextListActivity){
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                else {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;

            case TAB_NEXT:
                startActivityForResult(new Intent(ComicBookWeekBaseActivity.this, ComicBookWeekNextListActivity.class), 500);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}