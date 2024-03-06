package com.example.cardbag.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtils {
    public static final String DATABASE_NAME = "Information";//数据库名
    public static final String DATABASE_TABLE = "Note";  //表名
    public static final int DATABASE_VERION = 1;          //数据库版本
    //数据库表中的列名
    public static final String HOMEPAGE_ID = "id";
    public static final String HOMEPAGE_CARD_NAME = "name";
    public static final String HOMEPAGE_CARD_ID = "card_id";
    public static final String HOMEPAGE_CARD_NOTE = "note";
    public static final String HOMEPAGE_CARD_TIME = "card_time";
    public static final String HOMEPAGE_CARD_TYPE = "card_type";
    public static final String HOMEPAGE_CARD_IMAGE = "card_image";
    public static final String HOMEPAGE_TIME = "edit_time";


    //获取当前日期
    public static final String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
