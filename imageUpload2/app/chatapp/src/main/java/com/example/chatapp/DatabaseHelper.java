package com.example.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "Student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DISEASE";
    public static final String COL_4 = "SYMPTOMS";
    public static final String COL_5 = "DATEOFADMIT";
    public static final String COL_6 = "DATEOFTREATMENT";
    public static final String COL_7 = "TIMEOFTREATMENT";
    public static final String COL_8 = "PULSERATE";
    public static final String COL_9 = "HEARTBEATRATE";
    public static final String COL_10 = "TREATMENTGIVEN";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,DISEASE TEXT,SYMPTOMS TEXT,DATEOFADMIT INTEGER,DATEOFTREATMENT INTEGER,TIMEOFTREATMENT INTEGER,PULSERATE INTEGER,HEARTBEATRATE INTEGER,TREATMENTGIVEN TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData ( String name,String disease,String symptoms,String dateofadmit,String dateoftreatment,String timeoftreatment,String pulserate,String heartbeatrate,String treatmentgiven){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,name);
        contentValues.put(COL_3,disease);
        contentValues.put(COL_4,symptoms);
        contentValues.put(COL_5,dateofadmit);
        contentValues.put(COL_6,dateoftreatment);
        contentValues.put(COL_7,timeoftreatment);
        contentValues.put(COL_8,pulserate);
        contentValues.put(COL_9,heartbeatrate);
        contentValues.put(COL_10,treatmentgiven);
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

    public boolean UpdateData( String id,String name,String disease,String symptoms,String dateofadmit,String dateoftreatment,String timeoftreatment,String pulserate,String heartbeatrate,String treatmentgiven){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,disease);
        contentValues.put(COL_4,symptoms);
        contentValues.put(COL_5,dateofadmit);
        contentValues.put(COL_6,dateoftreatment);
        contentValues.put(COL_7,timeoftreatment);
        contentValues.put(COL_8,pulserate);
        contentValues.put(COL_9,heartbeatrate);
        contentValues.put(COL_10,treatmentgiven);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[] { id });
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID =  ?",new String[]  {id});
    }
}
