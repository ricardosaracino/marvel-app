package com.ricardosaracino.pulllist.loader;

import android.content.Context;
import com.ricardosaracino.pulllist.datasource.AbstractDataSource;
import com.ricardosaracino.pulllist.model.PullList;

import java.util.List;

public class PullListDataLoader extends AbstractDataLoader<List<PullList>> {

    private AbstractDataSource<PullList> dataSource;

    public PullListDataLoader(Context context, AbstractDataSource dataSource) {
        super(context);

        this.dataSource = dataSource;
    }

    @Override
    protected List<PullList> buildList() {

        return dataSource.read();
    }
}