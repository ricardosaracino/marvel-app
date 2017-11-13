package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.SearchView;
import com.ricardosaracino.pulllist.R;
import com.ricardosaracino.pulllist.adapter.ComicBookListAdapter;
import com.ricardosaracino.pulllist.datasource.MarvelDataSource;
import com.ricardosaracino.pulllist.hydrator.ComicBookListJsonHydrator;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.List;

public class ComicBookSearchListFragment extends BaseComicBookListFragment implements SearchView.OnQueryTextListener {

    private String query;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        shown = true;
        
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<List<ComicBook>> onCreateLoader(int id, Bundle args) {

        MarvelDataSource marvelDataSource = new MarvelDataSource(new ComicBookListJsonHydrator(), "/v1/public/comics");

        marvelDataSource.addIntParam("offset", offset + count);

        marvelDataSource.addStringParam("format", "comic");

        marvelDataSource.addStringParam("titleStartsWith", query);

        marvelDataSource.addStringParam("orderBy", "-onsaleDate");

        return new ComicBookListDataLoader(getActivity(), marvelDataSource);
    }

    @Override
    public void onStart() {
        super.onStart();

        SearchView searchView = getActivity().findViewById(R.id.comic_search);

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        query = s;

        showProgress(true);

        loading = true;

        getLoaderManager().restartLoader(LOADER_ID, null, this);

        SearchView searchView = getActivity().findViewById(R.id.comic_search);

        searchView.clearFocus();


        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
