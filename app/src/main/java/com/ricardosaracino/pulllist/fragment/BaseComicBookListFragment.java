package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.adapter.ComicBookListAdapter;
import com.ricardosaracino.pulllist.datasource.DataSourceReader;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseComicBookListFragment extends ListFragment implements AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<List<ComicBook>> {

    protected static final int LOADER_ID = 1;
    private static final int visibleThreshold = 1;
    protected int count = 0;
    protected int offset = 0;
    protected int total = 0;
    protected boolean shown = false;
    protected boolean loading = true;

    protected ComicBookListAdapter comicBookListAdapter;
    private int previousTotalItemCount = 0;
    private ArrayList<ComicBook> list = new ArrayList<>();

    // todo onLoadFinished
    protected int prevOffset = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if(comicBookListAdapter == null) {
            comicBookListAdapter = new ComicBookListAdapter(getActivity());
        }

        onRestoreInstanceState(savedInstanceState);

        setListAdapter(comicBookListAdapter);

        if (!shown) {
            showProgress(true);
            getLoaderManager().restartLoader(LOADER_ID, null, this);   // calls onCreateLoader
        }
    }

    @Override
    public void onLoadFinished(final Loader<List<ComicBook>> loader, List<ComicBook> data) {

        // do before return
        // Search offset = 0 && prevOffset = 0
        if (isResumed()) {
            if (!shown) {
                shown = true; //might have a race condition onActivityCreated
            }

            showProgress(false);

            setEmptyText("No Results");
        }

        DataSourceReader dataSource = ((ComicBookListDataLoader) loader).getDataSource();

        count = dataSource.getResultCount();
        offset = dataSource.getResultOffset();
        total = dataSource.getResultTotal();

        // todo ViewPager is calling onLoadFinished with previous data so set finished if list is full
        if(offset == prevOffset){

            // if init or restart wasn't called we still want to register the listener
            if (comicBookListAdapter.getCount() > 0) {
                getListView().setOnScrollListener(this);
            }

            // Search offset = 0 && prevOffset = 0
            showProgress(false);

            return;
        }

        prevOffset = offset;
        // todo end

        loading = false;

        comicBookListAdapter.addAll(data);

        if (comicBookListAdapter.getCount() > 0) {
            getListView().setOnScrollListener(this);
        }

        list.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ComicBook>> arg0) {
        comicBookListAdapter.clear();
    }

    @Override
    public void onScroll(final AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // http://benjii.me/2010/08/endless-scrolling-listview-in-android/

        if ((offset + count) >= total) {
            return;
        }
        
        if (loading) {
            if (totalItemCount > previousTotalItemCount) {
                loading = false;
                previousTotalItemCount = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

            loading = true;

            showProgress(true);

            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null) { // dependent on setRetainInstance(true);
            // Restore last state

            list = savedInstanceState.getParcelableArrayList("list");

            if(list != null) {

                // make sure not null
                comicBookListAdapter.clear();
                comicBookListAdapter.addAll(list);
                comicBookListAdapter.notifyDataSetChanged();

                int index = savedInstanceState.getInt("visiblePosition");
                this.getListView().setSelectionFromTop(index, 0);

                shown = savedInstanceState.getBoolean("shown");
                loading = savedInstanceState.getBoolean("loading");

                count = savedInstanceState.getInt("count");
                offset = savedInstanceState.getInt("offset");
                total = savedInstanceState.getInt("total");
                previousTotalItemCount = savedInstanceState.getInt("previousTotalItemCount");

                if (comicBookListAdapter.getCount() > 0) {
                    getListView().setOnScrollListener(this);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList("list", list);

        savedInstanceState.putBoolean("shown", shown);
        savedInstanceState.putBoolean("loading", loading);

        savedInstanceState.putInt("count", count);
        savedInstanceState.putInt("offset", offset);
        savedInstanceState.putInt("total", total);
        savedInstanceState.putInt("previousTotalItemCount", previousTotalItemCount);

        if(this.isVisible()) {
            savedInstanceState.putInt("visiblePosition", this.getListView().getFirstVisiblePosition());
        }
    }

    protected void showProgress(boolean visible)
    {
        ProgressBar progressBar = getActivity().findViewById(R.id.progressbar);

        if(progressBar != null){

            if(visible) {
                progressBar.setVisibility(View.VISIBLE);
            }
            else
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}