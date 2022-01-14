package com.bignerdranch.android.swapapp;

import android.provider.BaseColumns;

import java.util.UUID;

public final class DBContract {

    public DBContract() {}

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "ShopDetails";

        public static final String IMAGE ="image";
        public static final String TRADE="trade";
        public static final String NAME="name";
        public static final String DESCRIPTION="des";
        public static final String EMAIL="email";
        public static final String DATE="date";

        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
