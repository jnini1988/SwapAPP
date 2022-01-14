package com.bignerdranch.android.swapapp;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {

        //To set the name of the Table
        public static final String TABLE_NAME = "UserDetails";

        //To set the name of the columns you need to add in your table
        //name, id and salary of your employees

        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
