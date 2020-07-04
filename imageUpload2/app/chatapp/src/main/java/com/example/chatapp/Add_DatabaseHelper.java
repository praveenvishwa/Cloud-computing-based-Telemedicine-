package com.example.chatapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Add_DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Stud.db";
    public static final String TABLE_NAME = "Stud_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "AGE";
    public static final String COL_4 = "GENDER";
    public static final String COL_5 = "PULSERATE";
    public static final String COL_6 = "WEIGHT";
    public static final String COL_7 = "HEIGHT";



    public Add_DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,AGE INTEGER,GENDER TEXT,PULSERATE INTEGER,WEIGHT INTEGER,HEIGHT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData ( String name,String age,String gender,String pulserate,String weight,String height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,name);
        contentValues.put(COL_3,age);
        contentValues.put(COL_4,gender);
        contentValues.put(COL_5,pulserate);
        contentValues.put(COL_6,weight);
        contentValues.put(COL_7,height);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return  false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean UpdateData( String id,String name,String age,String gender,String pulserate,String weight,String height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,age);
        contentValues.put(COL_4,gender);
        contentValues.put(COL_5,pulserate);
        contentValues.put(COL_6,weight);
        contentValues.put(COL_7,height);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[] { id });
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID =  ?",new String[]  {id});
    }
}

