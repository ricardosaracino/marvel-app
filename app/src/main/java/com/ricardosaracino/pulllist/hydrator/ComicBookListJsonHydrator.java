package com.ricardosaracino.pulllist.hydrator;

import com.ricardosaracino.pulllist.model.ComicBook;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ComicBookListJsonHydrator extends AbstractHydrator<List<ComicBook>, JSONArray> {

    public List<ComicBook> create(JSONArray results) throws Exception {

        List<ComicBook> comicBookList = new ArrayList<>();

        ComicBookJsonHydrator comicBookJsonHydrator = new ComicBookJsonHydrator();

        for (int i = 0; i < results.length(); i++) {

            comicBookList.add(comicBookJsonHydrator.create(results.getJSONObject(i)));
        }

        return comicBookList;
    }
}
