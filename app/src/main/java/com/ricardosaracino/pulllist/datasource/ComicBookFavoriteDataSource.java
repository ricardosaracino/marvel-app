package com.ricardosaracino.pulllist.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ricardosaracino.pulllist.hydrator.ComicBookCursorHydrator;
import com.ricardosaracino.pulllist.model.ComicBook;

import java.util.ArrayList;
import java.util.List;

public class ComicBookFavoriteDataSource implements DataSourceReader<List<ComicBook>>, DataSourceWriter<ComicBook> {

    private Context context;

    public static final String TABLE_NAME = "comic_book_favorite";


    int resultCount = 0;

    int resultOffset = 0;

    int resultTotal = 0;


    public ComicBookFavoriteDataSource(Context c) {

        context = c;
    }

    @Override
    public boolean write(ComicBook comicBook) throws DataSourceException {

        if (comicBook == null) {

            return false;
        }

        long id = -1;

        SQLiteDatabase database = new DbHelper(context).getWritableDatabase();

        try {
            // insert+delete
            id = database.replaceOrThrow(TABLE_NAME, null, generateContentValuesFromObject(comicBook));
        } catch (SQLException e) {
            Log.e("ComicBookFav", e.getMessage());
        } finally {
            database.close();
        }

        return id != -1;
    }


    @Override
    public List<ComicBook> read() throws DataSourceException {

        ComicBookCursorHydrator comicBookCursorHydrator = new ComicBookCursorHydrator();

        List<ComicBook> comicBookList = new ArrayList<>();

        SQLiteDatabase database = new DbHelper(context).getReadableDatabase();

        try {

            Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    comicBookList.add(comicBookCursorHydrator.create(cursor));
                } while (cursor.moveToNext());
            }

            resultCount = cursor.getCount();

            resultOffset = comicBookList.size();

            resultTotal = cursor.getCount();

        } catch (SQLException e) {
            Log.e("ComicBookFav", e.getMessage());
        } finally {
            database.close();
        }

        return comicBookList;
    }


    public int getResultOffset() {
        return resultOffset;
    }

    public int getResultCount() {
        return resultCount;
    }

    public int getResultTotal() {
        return resultTotal;
    }

    public boolean delete(ComicBook comicBook) throws DataSourceException {

        if (comicBook == null) {

            return false;
        }

        long id = -1;

        SQLiteDatabase database = new DbHelper(context).getWritableDatabase();

        try {
            id = database.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(comicBook.getId())});
        } catch (SQLException e) {
            Log.e("ComicBookFav", e.getMessage());
        } finally {
            database.close();
        }

        return id != -1;
    }


    public boolean isFavorite(ComicBook comicBook) throws DataSourceException {

        if (comicBook == null) {

            return false;
        }

        boolean found = false;
        
        SQLiteDatabase database = new DbHelper(context).getReadableDatabase();

        try {
            Cursor cursor = database.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(comicBook.getId())}, null, null, null);

            found = cursor.getCount() > 0;

            cursor.close();

        } catch (SQLException e) {
            Log.e("ComicBookFav", e.getMessage());
        }  finally {
            database.close();
        }

        return found;
    }

    private ContentValues generateContentValuesFromObject(ComicBook comicBook) {
        if (comicBook == null) {
            return null;
        }

        ContentValues values = new ContentValues();

        values.put("id", comicBook.getId());
        values.put("digitalId", comicBook.getDigitalId());
        values.put("title", comicBook.getTitle());
        values.put("issueNumber", comicBook.getIssueNumber());
        values.put("variantDescription", comicBook.getVariantDescription());
        values.put("description", comicBook.getDescription());
        values.put("format", comicBook.getFormat());
        values.put("imagePath", comicBook.getImagePath());

        return values;
    }

    public static String getCreateCommand() {
        return "create table " + TABLE_NAME + "("
                //+ "_id integer primary key autoincrement, "
                + "id integer primary key,"
                + "digitalId integer not null,"
                + "title text not null,"
                + "issueNumber integer not null,"
                + "variantDescription text not null,"
                + "description text not null,"
                + "format text not null,"
                + "imagePath text not null"
                + ");";
    }
}