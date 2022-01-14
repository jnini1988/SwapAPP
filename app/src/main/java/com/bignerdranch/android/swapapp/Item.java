package com.bignerdranch.android.swapapp;

import android.graphics.Bitmap;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Item {
    private int mId;
    private int mTrade;
    private String mName;
    private String mDescription;
    private String mSellerEmail;
    private String mDate;
    private Bitmap mBitmap;

    //create items in arraylist, items can be modified already
    public Item(String name, String des, String email, int id, int trade, String date, Bitmap bitmap) {
        mId=id;
        mName=name;
        mDescription=des;
        mSellerEmail=email;
        mTrade=trade;
        mDate=date;
        mBitmap=bitmap;
    }

    //create comeplete new items, initilizing
    public Item(String name,String des,String email,Bitmap bitmap) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
        mDate=simpleformat.format(cal.getTime());;
        mName=name;
        mDescription=des;
        mSellerEmail=email;
        mBitmap=bitmap;
    }

    public int getId() { return mId; }

    public String getName() { return mName; }
    public void setName(String name) { mName=name; }

    public String getDescription() { return mDescription; }
    public void setDescription(String des) { mDescription=des; }

    public String getSellerEmail() { return mSellerEmail; }
    public void setSellerEmail(String email) { mSellerEmail=email; }

    public String getDate() {
        return mDate;
    }
    public void setDate(String date) { mDate=date; }

    public int isTrade() { return mTrade; }
    public void setTrade(int trade) { mTrade=trade; }

    public Bitmap getBitMap(){
        return mBitmap;
    }
}

