package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

    private int previousTotalItemCount = 0;
    private static final int visibleThreshold = 1;

    protected int count = 0;
    protected int offset = 0;
    protected int total = 0;
    protected boolean shown = false;
    protected boolean loading = true;

    protected ComicBookListAdapter comicBookListAdapter;

    private ArrayList<ComicBook> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        comicBookListAdapter = new ComicBookListAdapter(getActivity());

        onRestoreInstanceState(savedInstanceState);

        setListAdapter(comicBookListAdapter);

        if (!shown) {

            //setListShown(false);
            showProgress(true);

            getLoaderManager().initLoader(LOADER_ID, null, this);   // calls onCreateLoader
        }
    }


    @Override
    public void onLoadFinished(final Loader<List<ComicBook>> loader, List<ComicBook> data) {

        loading = false;

        comicBookListAdapter.addAll(data);

        if (comicBookListAdapter.getCount() > 0) {
            getListView().setOnScrollListener(this);
        }

        DataSourceReader dataSource = ((ComicBookListDataLoader) loader).getDataSource();

        list.addAll(data);
        count = dataSource.getResultCount();
        offset = dataSource.getResultOffset();
        total = dataSource.getResultTotal();

        if (isResumed()) {
            if (!shown) {
                //setListShown(true);
                shown = true;
            }

            showProgress(false);

            setEmptyText("No Results");

        } else {
            //setListShownNoAnimation(true);
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("list", list);

        outState.putInt("visiblePosition", this.getListView().getFirstVisiblePosition());

        outState.putBoolean("shown", shown);
        outState.putBoolean("loading", loading);

        outState.putInt("count", count);
        outState.putInt("offset", offset);
        outState.putInt("total", total);
        outState.putInt("previousTotalItemCount", previousTotalItemCount);

        super.onSaveInstanceState(outState);
    }


    public void showProgress(boolean visible)
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
