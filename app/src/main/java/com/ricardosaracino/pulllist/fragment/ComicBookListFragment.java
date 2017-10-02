package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AbsListView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.adapter.ComicBookListAdapter;
import com.ricardosaracino.pulllist.datasource.MarvelDataSource;
import com.ricardosaracino.pulllist.hydrator.ComicBookListJsonHydrator;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.ArrayList;
import java.util.List;


public class ComicBookListFragment extends ListFragment implements AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<List<ComicBook>> {

    private static final int LOADER_ID = 1;

    private static final int visibleThreshold = 1;


    private ComicBookListAdapter comicBookListAdapter;

    private boolean shown = false;
    private boolean loading = true;

    private int count = 0;
    private int offset = 0;

    private int previousTotal = 0;


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

    // loader

    @Override
    public Loader<List<ComicBook>> onCreateLoader(int id, Bundle args) {

        MarvelDataSource marvelDataSource = new MarvelDataSource(new ComicBookListJsonHydrator(), "/v1/public/comics");

        marvelDataSource.addIntParam("offset", offset + count);

        marvelDataSource.addIntParam("startYear", 2017);

        marvelDataSource.addStringParam("orderBy", "onsaleDate");

        return new ComicBookListDataLoader(getActivity(), marvelDataSource);
    }

    @Override
    public void onLoadFinished(final Loader<List<ComicBook>> loader, List<ComicBook> data) {

        loading = false;

        comicBookListAdapter.addAll(data);

        if (comicBookListAdapter.getCount() > 0) {
            getListView().setOnScrollListener(this);
        }

        MarvelDataSource marvelDataSource = (MarvelDataSource) ((ComicBookListDataLoader) loader).getDataSource();

        list.addAll(data);
        count = marvelDataSource.getResultCount();
        offset = marvelDataSource.getResultOffset();

        if (isResumed()) {
            if (!shown) {
                setListShown(true);
                shown = true;
            }

            (getActivity().findViewById(R.id.comic_list_progress)).setVisibility(View.INVISIBLE);

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

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

            loading = true;

            (getActivity().findViewById(R.id.comic_list_progress)).setVisibility(View.VISIBLE);

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
            previousTotal = savedInstanceState.getInt("previousTotal");

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
        outState.putInt("previousTotal", previousTotal);

        super.onSaveInstanceState(outState);
    }
}
