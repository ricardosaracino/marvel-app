package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.ricardosaracino.pulllist.adapter.ComicBookAdapter;
import com.ricardosaracino.pulllist.adapter.ComicBookListAdapter;
import com.ricardosaracino.pulllist.datasource.MarvelDataSource;
import com.ricardosaracino.pulllist.hydrator.ComicBookListJsonHydrator;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ComicBookFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<ComicBook>> {

    private static final int LOADER_ID = 1;

    private ComicBookAdapter comicBookAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        comicBookAdapter = new ComicBookAdapter(getActivity());

        setEmptyText("No data, please add from menu.");

        setListAdapter(comicBookAdapter);

        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);   // calls onCreateLoader
    }

    @Override
    public Loader<List<ComicBook>> onCreateLoader(int id, Bundle args) {

        int comicId = getActivity().getIntent().getExtras().getInt("comic_id");

        MarvelDataSource marvelDataSource = new MarvelDataSource(new ComicBookListJsonHydrator(), "/v1/public/comics/"+comicId);

        return new ComicBookListDataLoader(getActivity(), marvelDataSource);
    }

    @Override
    public void onLoadFinished(final Loader<List<ComicBook>> loader, List<ComicBook> data) {

        comicBookAdapter.clear();

        comicBookAdapter.addAll(data);

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ComicBook>> arg0) {
        comicBookAdapter.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
