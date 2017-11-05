package com.example.asd.android;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

// helper
class FeedReaderDbHelper extends SQLiteOpenHelper {
    // define table properties
    private static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "entry";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }

    // sql
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE" + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY, " +
            FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_SUBTITLE + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    // header properties
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ReedReader.db";

    // query constants
    private String[] projection = {
            FeedEntry._ID,
            FeedEntry.COLUMN_NAME_TITLE,
            FeedEntry.COLUMN_NAME_SUBTITLE
    };

    // implements helper abstract class
    FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * 插入记录
     */
    long insert(String title, String subtitle) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);
        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * 查询记录
     */
    Cursor queryByTitle(String[] title) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                projection,
                selection,
                title,
                null,
                null,
                sortOrder
        );
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * 删除记录
     */
    int delete(String[] title) {
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        return getWritableDatabase().delete(FeedEntry.TABLE_NAME, selection, title);
    }

    /**
     * 更新记录
     */
    int update(String title, String oldTile) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);

        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] args = {oldTile};
        return db.update(FeedEntry.TABLE_NAME, values, selection, args);
    }
}

public class AppSQLite {

    private FeedReaderDbHelper readHelper;

    AppSQLite(Context context) {
        readHelper = new FeedReaderDbHelper(context);
    }

    public boolean insertFeed(String title, String subtitle) {
        return readHelper.insert(title, subtitle) != -1;
    }

    public String queryFeedByTitle(String[] title) {
        return readHelper.queryByTitle(title).getString(1);
    }

}
