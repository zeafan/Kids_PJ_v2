package com.app.mohamedgomaa.kids_pj.Qaraan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper_RecordList extends SQLiteOpenHelper {
    public static final String DBNAME = "My_DataRecord.db";
    private Context mContext;
    public DatabaseHelper_RecordList(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS Record_items(id INTEGER PRIMARY KEY AUTOINCREMENT,name_soora TEXT,path TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Record_items");
        onCreate(db);
    }




    public ArrayList<Activity_Regition_Qaraan_Record_item> get_All_Data() {
        ArrayList<Activity_Regition_Qaraan_Record_item> arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Record_items", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            arrayList.add(new Activity_Regition_Qaraan_Record_item(res.getInt(res.getColumnIndex("id")),res.getString(res.getColumnIndex("name_soora")),res.getString(res.getColumnIndex("path"))));
            res.moveToNext();
        }
        res.close();
        return arrayList;
    }
    boolean Insert_Record(String name_soora,String Path)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conv = new ContentValues();
        conv.put("name_soora",name_soora);
        conv.put("path",Path);
        long check = db.insert("Record_items",null,conv);
        if(check==-1)
        {
            return false;
        }
        else return true;
    }

}
