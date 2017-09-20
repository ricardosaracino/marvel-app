package com.ricardosaracino.pulllist.datasource;

import java.util.List;

public abstract class AbstractDataSource<T> {

    public abstract List<T> read();
}