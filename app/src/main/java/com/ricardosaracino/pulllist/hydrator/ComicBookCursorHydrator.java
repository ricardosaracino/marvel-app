package com.ricardosaracino.pulllist.hydrator;

import android.database.Cursor;
import com.ricardosaracino.pulllist.model.ComicBook;

public class ComicBookCursorHydrator extends AbstractHydrator<ComicBook, Cursor> {

    public ComicBook create(Cursor cursor){

        ComicBook comicBook = new ComicBook();

        comicBook.setId(cursor.getInt(cursor.getColumnIndex("id")));

        comicBook.setDigitalId(cursor.getInt(cursor.getColumnIndex("digitalId")));

        comicBook.setTitle(cursor.getString(cursor.getColumnIndex("title")));

        comicBook.setIssueNumber(cursor.getInt(cursor.getColumnIndex("issueNumber")));

        comicBook.setVariantDescription(cursor.getString(cursor.getColumnIndex("variantDescription")));

        comicBook.setDescription(cursor.getString(cursor.getColumnIndex("description")));

        comicBook.setFormat(cursor.getString(cursor.getColumnIndex("format")));

        comicBook.setImagePath(cursor.getString(cursor.getColumnIndex("imagePath")));

        return comicBook;
    }
}
