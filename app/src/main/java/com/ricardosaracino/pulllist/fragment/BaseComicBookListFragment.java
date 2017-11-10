package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.AbsListView;
import com.ricardosaracino.pulllist.adapter.ComicBookListAdapter;
import com.ricardosaracino.pulllist.datasource.DataSourceReader;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseComicBookListFragment extends ListFragment implements AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<List<ComicBook>> {

    private static final int LOADER_ID = 1;

    private static final int visibleThreshold = 1;
    protected int count = 0;
    protected int offset = 0;
    protected int total = 0;
    private ComicBookListAdapter comicBookListAdapter;
    private boolean shown = false;
    protected boolean loading = true;
    private int previousTotalItemCount = 0;

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

        setEmptyText("No data");

        setListAdapter(comicBookListAdapter);


        if (!shown) {
            setListShown(false);

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
                setListShown(true);
                shown = true;
            }

            //todo
            //(getActivity().findViewById(R.id.comic_list_progress)).setVisibility(View.INVISIBLE);

        } else {
            setListShownNoAnimation(true);
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

            // todo
            //(getActivity().findViewById(R.id.comic_list_progress)).setVisibility(View.VISIBLE);

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
}
