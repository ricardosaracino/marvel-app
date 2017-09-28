package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
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

import java.util.List;


public class ComicBookListFragment extends ListFragment implements AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<List<ComicBook>> {

    private static final int LOADER_ID = 1;

    private ComicBookListAdapter comicBookListAdapter;

    private boolean loading = true;
    private boolean shown = false;

    private int count;
    private int offset = 0;

    private int previousTotal;

    private int visibleThreshold = 5;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        comicBookListAdapter = new ComicBookListAdapter(getActivity());

        setEmptyText("No data");


        setListAdapter(comicBookListAdapter);

        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);   // calls onCreateLoader
    }

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

        MarvelDataSource marvelDataSource = (MarvelDataSource) ((ComicBookListDataLoader) loader).getDataSource();

        count = marvelDataSource.getResultCount();
        offset = marvelDataSource.getResultOffset();


        if (count > 0) {
            getListView().setOnScrollListener(this);
        }


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
    public void onDestroy() {
        super.onDestroy();
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
}
