package com.ricardosaracino.pulllist.hydrator;

public abstract class AbstractHydrator<T, E> {

    public abstract T create(E raw) throws Exception;
}
