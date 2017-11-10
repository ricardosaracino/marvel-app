package com.ricardosaracino.pulllist.datasource;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "marvel.db";
    private static final int DATABASE_VERSION = 15;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL(ComicBookFavoriteDataSource.getCreateCommand());
        }
        catch (SQLException e) {
            Log.e("DBHelper",e.getMessage()) ;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ComicBookFavoriteDataSource.TABLE_NAME);
        onCreate(db);
    }
}