package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import com.ricardosaracino.pulllist.datasource.MarvelDataSource;
import com.ricardosaracino.pulllist.hydrator.ComicBookListJsonHydrator;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.List;

/*
    todo remove
 */
public class ComicBookListFragment extends BaseComicBookListFragment {

    @Override
    public Loader<List<ComicBook>> onCreateLoader(int id, Bundle args) {

        MarvelDataSource marvelDataSource = new MarvelDataSource(new ComicBookListJsonHydrator(), "/v1/public/comics");

        marvelDataSource.addIntParam("offset", offset + count);

        marvelDataSource.addIntParam("startYear", 2017);

        marvelDataSource.addStringParam("formatType", "comic");

        marvelDataSource.addStringParam("orderBy", "onsaleDate");

        return new ComicBookListDataLoader(getActivity(), marvelDataSource);
    }
}
