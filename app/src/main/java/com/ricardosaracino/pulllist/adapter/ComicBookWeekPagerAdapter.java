package com.ricardosaracino.pulllist.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.ricardosaracino.pulllist.fragment.ComicBookWeekCurrentListFragment;
import com.ricardosaracino.pulllist.fragment.ComicBookWeekNextListFragment;
import com.ricardosaracino.pulllist.fragment.ComicBookWeekPreviousListFragment;

public class ComicBookWeekPagerAdapter extends FragmentPagerAdapter {

    protected Context context;

    public ComicBookWeekPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ComicBookWeekPreviousListFragment();
            case 2:
                return new ComicBookWeekNextListFragment();
        }

        return new ComicBookWeekCurrentListFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}