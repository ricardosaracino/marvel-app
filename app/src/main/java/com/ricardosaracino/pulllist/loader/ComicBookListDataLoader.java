package com.ricardosaracino.pulllist.loader;

import android.content.Context;
import android.util.Log;
import com.ricardosaracino.pulllist.datasource.AbstractDataSource;
import com.ricardosaracino.pulllist.datasource.DataSourceException;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.ArrayList;
import java.util.List;

public class ComicBookListDataLoader extends AbstractDataLoader<List<ComicBook>> {

    private AbstractDataSource<List<ComicBook>> dataSource;

    public ComicBookListDataLoader(Context context, AbstractDataSource dataSource) {
        super(context);

        this.dataSource = dataSource;
    }

    public AbstractDataSource getDataSource() {
        return this.dataSource;
    }


    @Override
    protected List<ComicBook> buildList() {

        try {
            return dataSource.read();
        } catch (DataSourceException e) {
            Log.e("asdfasdf", "asdfasdfasdf");
        }

        return new ArrayList<>();
    }
}