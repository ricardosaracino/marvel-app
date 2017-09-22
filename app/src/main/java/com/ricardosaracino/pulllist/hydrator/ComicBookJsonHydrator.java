package com.ricardosaracino.pulllist.hydrator;

import com.ricardosaracino.pulllist.model.ComicBook;
import org.json.JSONObject;

public class ComicBookJsonHydrator extends AbstractHydrator<ComicBook, JSONObject> {

    public ComicBook create(JSONObject jsonObject) throws Exception {

        ComicBook comicBook = new ComicBook();

        comicBook.setId(jsonObject.getInt("id"));

        comicBook.setDigitalId(jsonObject.getInt("digitalId"));

        comicBook.setTitle(jsonObject.getString("title"));

        comicBook.setIssueNumber(jsonObject.getInt("issueNumber"));

        comicBook.setVariantDescription(jsonObject.getString("variantDescription"));

        comicBook.setDescription(jsonObject.isNull("description") ? "" : jsonObject.getString("description"));

        return comicBook;
    }
}
