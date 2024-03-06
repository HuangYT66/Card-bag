package com.example.cardbag.Scan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "task.db";
    public static final int DATABASE_VERSION = 15;

    public static class LoyaltyCardDbGroups {
        public static final String ID = "_id";
        public static final String ORDER = "orderId";
    }

    public static class LoyaltyCardDbIds {
        public static final String ID = "_id";
        public static final String STORE = "store";
        public static final String EXPIRY = "expiry";
        public static final String BALANCE = "balance";
        public static final String BALANCE_TYPE = "balancetype";
        public static final String NOTE = "note";
        public static final String HEADER_COLOR = "headercolor";
        public static final String CARD_ID = "cardid";
        public static final String BARCODE_ID = "barcodeid";
        public static final String BARCODE_TYPE = "barcodetype";
        public static final String STAR_STATUS = "starstatus";
        public static final String LAST_USED = "lastused";
        public static final String ZOOM_LEVEL = "zoomlevel";
        public static final String ARCHIVE_STATUS = "archive";
    }




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }







}
