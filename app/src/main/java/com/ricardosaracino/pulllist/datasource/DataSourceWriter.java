package com.ricardosaracino.pulllist.datasource;

public interface DataSourceWriter<T> {

    boolean write(T entity) throws DataSourceException;
}
