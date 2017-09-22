package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.adapter.ComicBookListAdapter;
import com.ricardosaracino.pulllist.datasource.MarvelDataSource;
import com.ricardosaracino.pulllist.hydrator.ComicBookListJsonHydrator;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class ComicBookListFragment extends ListFragment implements AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<List<ComicBook>> {

    private static final int LOADER_ID = 1;

    private ComicBookListAdapter comicBookListAdapter;

    private boolean loading = true;
    private boolean shown = false;

    private int count;
    private int limit;
    private int offset;
    private int total;


    private int previousTotal;


    private int visibleThreshold = 5;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        comicBookListAdapter = new ComicBookListAdapter(getActivity());

        setEmptyText("No data, please add from menu.");

        getListView().setOnScrollListener(this);

        setListAdapter(comicBookListAdapter);

        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);   // calls onCreateLoader
    }

    @Override
    public Loader<List<ComicBook>> onCreateLoader(int id, Bundle args) {

        ArrayList params = new ArrayList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("offset", String.valueOf(offset + count)));

        MarvelDataSource marvelDataSource = new MarvelDataSource(new ComicBookListJsonHydrator(), "/v1/public/comics", params);

        return new ComicBookListDataLoader(getActivity(), marvelDataSource);
    }

    @Override
    public void onLoadFinished(final Loader<List<ComicBook>> loader, List<ComicBook> data) {

        loading = false;

        comicBookListAdapter.addAll(data);

        MarvelDataSource marvelDataSource = (MarvelDataSource) ((ComicBookListDataLoader) loader).getDataSource();

        count = marvelDataSource.getResultCount();
        limit = marvelDataSource.getResultLimit();
        offset = marvelDataSource.getResultOffset();
        total = marvelDataSource.getResultTotal();

        if (isResumed()) {
            if (!shown) {
                setListShown(true);
            } else {
                (getActivity().findViewById(R.id.comic_list_progress)).setVisibility(View.INVISIBLE);
            }
            shown = true;

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

            (getActivity().findViewById(R.id.comic_list_progress)).setVisibility(View.VISIBLE);

            getLoaderManager().restartLoader(LOADER_ID, null, this);

            loading = true;


            // https://stackoverflow.com/questions/19320214/adding-items-to-listview-maintaining-scroll-position-and-not-seeing-a-scroll-ju
            final int positionToSave = view.getFirstVisiblePosition();

            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setSelection(positionToSave);
                }
            });

            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (view.getFirstVisiblePosition() == positionToSave) {
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }


}
