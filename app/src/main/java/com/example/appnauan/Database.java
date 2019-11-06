package com.example.appnauan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void QueryData(String sql)
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);

    }

    public void Insert_MonAn(String ten, String nguyenlieu,String congthuc, byte[]hinhanh)
    {
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO MonAn VALUES(null,?,?,?,?)";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        //định dạng cho các ?
        statement.bindString(1,ten);
        statement.bindString(2,nguyenlieu);
        statement.bindString(3,congthuc);
        statement.bindBlob(4,hinhanh);
        //Thực thi insert
        statement.executeInsert();
    }
    public void Insert_User(String tenhienthi, String mail, String matkhau, String loaiuser, byte[] hinhanh)
    {
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO User VALUES(null,?,?,?,?,?)";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        //định dạng cho các ?
        statement.bindString(1,tenhienthi);
        statement.bindString(2,mail);
        statement.bindString(3,matkhau);
        statement.bindString(4,loaiuser);
        statement.bindBlob(5,hinhanh);
        //Thực thi insert
        statement.executeInsert();
    }
    public Cursor GetData(String sql)
    {
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
