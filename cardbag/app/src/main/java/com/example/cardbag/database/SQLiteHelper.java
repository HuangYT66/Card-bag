package com.example.cardbag.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.cardbag.bean.HomepageBean;
import com.example.cardbag.utils.DBUtils;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase sqLiteDatabase;
    //创建数据库
    public SQLiteHelper(Context context){
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERION);
        sqLiteDatabase = this.getWritableDatabase();
    }
    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table "+DBUtils.DATABASE_TABLE+"("+DBUtils.HOMEPAGE_ID+
    public void onCreate() {
        sqLiteDatabase.execSQL("create table "+DBUtils.DATABASE_TABLE+"("+DBUtils.HOMEPAGE_ID+
                " integer primary key autoincrement,"+
                DBUtils.HOMEPAGE_CARD_NAME  +" text,"+
                DBUtils.HOMEPAGE_CARD_ID+ " text," +
                DBUtils.HOMEPAGE_CARD_NOTE + " text," +
                DBUtils.HOMEPAGE_CARD_TIME+ " text," +
                DBUtils.HOMEPAGE_CARD_TYPE + " text," +
                DBUtils.HOMEPAGE_CARD_IMAGE + " text," +
                DBUtils.HOMEPAGE_TIME+ " text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    //添加数据
    public boolean insertData(String userName,String userID,String userNote,String userTime,String dueTime,String userType,byte[] userImage){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.HOMEPAGE_CARD_NAME,userName);
        contentValues.put(DBUtils.HOMEPAGE_CARD_ID,userID);
        contentValues.put(DBUtils.HOMEPAGE_CARD_NOTE,userNote);
        contentValues.put(DBUtils.HOMEPAGE_CARD_TIME,dueTime);
        contentValues.put(DBUtils.HOMEPAGE_TIME,userTime);
        contentValues.put(DBUtils.HOMEPAGE_CARD_TYPE,userType);
        contentValues.put(DBUtils.HOMEPAGE_CARD_IMAGE, userImage);
        return
                sqLiteDatabase.insert(DBUtils.DATABASE_TABLE,null,contentValues)>0;
    }
    //删除数据
    public boolean deleteData(String id){
        String sql=DBUtils.HOMEPAGE_ID+"=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return
                sqLiteDatabase.delete(DBUtils.DATABASE_TABLE,sql,contentValuesArray)>0;
    }
    //修改数据
    public boolean updateData(String id,String userName,String userID,String userNote,String userTime,String dueTime,String userType,byte[] userImage){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.HOMEPAGE_CARD_NAME,userName);
        contentValues.put(DBUtils.HOMEPAGE_CARD_ID,userID);
        contentValues.put(DBUtils.HOMEPAGE_CARD_NOTE,userNote);
        contentValues.put(DBUtils.HOMEPAGE_TIME,userTime);
        contentValues.put(DBUtils.HOMEPAGE_CARD_TIME,dueTime);
        contentValues.put(DBUtils.HOMEPAGE_CARD_TYPE,userType);
        contentValues.put(DBUtils.HOMEPAGE_CARD_IMAGE, userImage);

        String sql=DBUtils.HOMEPAGE_ID+"=?";
        String[] strings=new String[]{id};
        return
                sqLiteDatabase.update(DBUtils.DATABASE_TABLE,contentValues,sql,strings)>0;
    }
    //查询数据
    public List<HomepageBean> query(String order,String[] column,String select){
        List<HomepageBean> list=new ArrayList<HomepageBean>();
        Cursor cursor=sqLiteDatabase.query(DBUtils.DATABASE_TABLE,column,select,null,
                null,null,order+" desc");

        if (cursor!=null){
            while (cursor.moveToNext()){
                HomepageBean noteInfo=new HomepageBean();
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt
                        (cursor.getColumnIndex(DBUtils.HOMEPAGE_ID)));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex
                        (DBUtils.HOMEPAGE_CARD_NAME));
                @SuppressLint("Range") String card_id = cursor.getString(cursor.getColumnIndex
                        (DBUtils.HOMEPAGE_CARD_ID));
                @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex
                        (DBUtils.HOMEPAGE_CARD_NOTE));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex
                        (DBUtils.HOMEPAGE_TIME));
                @SuppressLint("Range") String card_time = cursor.getString(cursor.getColumnIndex
                        (DBUtils.HOMEPAGE_CARD_TIME));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex
                        (DBUtils.HOMEPAGE_CARD_TYPE));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex
                        (DBUtils.HOMEPAGE_TIME));
                noteInfo.setId(id);
                noteInfo.setHomepageName(name);
                noteInfo.setHomepageCardID(card_id);
                noteInfo.setHomepageNote(note);
                noteInfo.setHomepageTime(time);
                noteInfo.setHomepageCardTime(card_time);
                noteInfo.setHomepageCardType(type);
                noteInfo.setHomepageCardImage(image);
                list.add(noteInfo);
            }
            cursor.close();
        }
        return list;
    }

    @SuppressLint("Range")
    public static byte[] readImage(String id){
        String selection = "id=?";
        Cursor cur=sqLiteDatabase.query(DBUtils.DATABASE_TABLE,null,selection,new String[]{ id },
                null,null,null);
        byte[] imgData=null;
        if(cur.moveToNext()){
            //将Blob数据转化为字节数组
            imgData=cur.getBlob(cur.getColumnIndex(DBUtils.HOMEPAGE_CARD_IMAGE));
        }
        return imgData;
    }
}
