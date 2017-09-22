package com.ricardosaracino.pulllist.datasource;

public abstract class AbstractDataSource<T> {

    public abstract T read() throws DataSourceException;
}