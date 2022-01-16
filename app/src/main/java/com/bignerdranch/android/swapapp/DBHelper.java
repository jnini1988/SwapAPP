package com.bignerdranch.android.swapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SwapItems";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "ShopDetails";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.FeedEntry.TABLE_NAME + " (" +
                    DBContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    DBContract.FeedEntry.NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.FeedEntry.DESCRIPTION+ TEXT_TYPE + COMMA_SEP +
                    DBContract.FeedEntry.EMAIL + TEXT_TYPE + COMMA_SEP +
                    DBContract.FeedEntry.TRADE + " INTEGER" + COMMA_SEP +
                    DBContract.FeedEntry.DATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.FeedEntry.IMAGE+ " BLOB" +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.FeedEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Item> getListItem() {
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Item> itemList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id=Integer.parseInt(cursor.getString(0));
                String name=cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FeedEntry.NAME));
                String des=cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FeedEntry.DESCRIPTION));
                String email=cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FeedEntry.EMAIL));
                int trade=cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.FeedEntry.TRADE));
                String date=cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FeedEntry.DATE));

                byte[] imageByte=cursor.getBlob(cursor.getColumnIndexOrThrow(DBContract.FeedEntry.IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte , 0, imageByte.length);
                itemList.add(new Item(name,des,email,id,trade,date,bitmap));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    void addItems(Item item) {
        ContentValues values = new ContentValues();
        values.put(DBContract.FeedEntry.NAME, item.getName());
        values.put(DBContract.FeedEntry.DESCRIPTION, item.getDescription());
        values.put(DBContract.FeedEntry.EMAIL,item.getSellerEmail());
        values.put(DBContract.FeedEntry.TRADE,0);
        values.put(DBContract.FeedEntry.DATE,item.getDate());

        byte[] bytes=mapToArray(item.getBitMap());
        values.put(DBContract.FeedEntry.IMAGE,bytes);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    void tradeItem(Item item){
        ContentValues values = new ContentValues();
        values.put(DBContract.FeedEntry.TRADE, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, DBContract.FeedEntry._ID + " = ?", new String[]{String.valueOf(item.getId())});
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

    public static byte[] mapToArray(Bitmap bitmap){
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,output);
        return output.toByteArray();
    }
}

