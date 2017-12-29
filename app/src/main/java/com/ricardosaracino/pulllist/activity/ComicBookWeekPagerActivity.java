package com.ricardosaracino.pulllist.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.adapter.ComicBookWeekPagerAdapter;

/**
 * http://codetheory.in/android-swipe-views-with-tabs/
 */
public class ComicBookWeekPagerActivity extends ComicBookBaseActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener, View.OnScrollChangeListener {

    protected TabLayout tabLayout;

    protected ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createTabLayoutView();

        createViewPager();
    }


    @Override
    public void createContentView() {

        setContentView(R.layout.activity_comic_week_view_pager);
    }


    private void createViewPager() {
        
        ComicBookWeekPagerAdapter customPagerAdapter = new ComicBookWeekPagerAdapter(getSupportFragmentManager(), this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(customPagerAdapter);

        
        // When swiping between different sections, select the corresponding tab
        viewPager.addOnPageChangeListener(this);

        viewPager.setOnScrollChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {

        try {

            if (tabLayout != null) {
                tabLayout.getTabAt(position).select();
            }
        }
        catch (NullPointerException e)
        {
           ;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        
    }

    private void createTabLayoutView() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        if (tabLayout != null) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            tabLayout.addTab(tabLayout.newTab().setText("Previous"));
            tabLayout.addTab(tabLayout.newTab().setText("Current"));
            tabLayout.addTab(tabLayout.newTab().setText("Next"));

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

        int position = tab.getPosition();

        // http://bighow.org/42247412-Android_ViewPager___differentiating_between_new_query_and_new_data_for_setCurrentItem___.html
        viewPager.setCurrentItem(position);
    }
}