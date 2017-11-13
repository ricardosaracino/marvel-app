package com.ricardosaracino.pulllist.hydrator;

import android.text.Html;
import com.ricardosaracino.pulllist.model.ComicBook;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComicBookJsonHydrator extends AbstractHydrator<ComicBook, JSONObject> {

    public ComicBook create(JSONObject jsonObject) throws Exception {

        ComicBook comicBook = new ComicBook();

        comicBook.setId(jsonObject.getInt("id"));

        comicBook.setDigitalId(jsonObject.getInt("digitalId"));

        comicBook.setTitle(jsonObject.getString("title"));

        comicBook.setIssueNumber(jsonObject.getInt("issueNumber"));

        comicBook.setVariantDescription(jsonObject.getString("variantDescription"));

        comicBook.setDescription(jsonObject.isNull("description") ? "" : Html.fromHtml(jsonObject.getString("description")).toString());

        JSONArray images = jsonObject.getJSONArray("images");

        if (images.length() > 0) {

            JSONObject image = (JSONObject) images.get(0);

            comicBook.setImagePath(image.getString("path"));
        }

        return comicBook;
    }
}
