package com.ricardosaracino.pulllist.datasource;

public interface  DataSourceReader<T> {

    T read() throws DataSourceException;

    int getResultCount();

    int getResultOffset();

    int getResultTotal();
}