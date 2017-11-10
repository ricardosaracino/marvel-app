package com.ricardosaracino.pulllist.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import com.ricardosaracino.pulllist.datasource.ComicBookFavoriteDataSource;
import com.ricardosaracino.pulllist.loader.ComicBookListDataLoader;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.List;

public class ComicBookFavoriteListFragment extends BaseComicBookListFragment {

    @Override
    public Loader<List<ComicBook>> onCreateLoader(int id, Bundle args) {

        ComicBookFavoriteDataSource comicBookFavoriteDataSource = new ComicBookFavoriteDataSource(getActivity());

        return new ComicBookListDataLoader(getActivity(), comicBookFavoriteDataSource);
    }
}
