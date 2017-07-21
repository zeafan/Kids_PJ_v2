package com.app.mohamedgomaa.kids_pj.Qaraan;
 import android.content.Context;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;
 import android.os.Environment;
 import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "My_DataBase.db";
    public static final String DBLOCATION = Environment.getDataDirectory()+"/data/com.app.mohamedgomaa.kids_pj/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }


    public ArrayList<Activity_Regition_Qaraan_items> get_All_Data() {
        ArrayList<Activity_Regition_Qaraan_items> arrayList = new ArrayList();
        openDatabase();
        Cursor res = mDatabase.rawQuery("select * from Item_Qaraan", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            arrayList.add(new Activity_Regition_Qaraan_items(res.getInt(res.getColumnIndex("id")),res.getInt(res.getColumnIndex("id_img")),res.getInt(res.getColumnIndex("num_ayaa")),res.getInt(res.getColumnIndex("num_views")),res.getInt(res.getColumnIndex("soora_saved")),res.getString(res.getColumnIndex("name_soora"))));

            res.moveToNext();
        }
        res.close();
        closeDatabase();
        return arrayList;
    }
    public ArrayList<Activity_Regition_Qaraan_items_tafseer> get_All_Data_tafseer(int Soora_num) {
        ArrayList<Activity_Regition_Qaraan_items_tafseer> arrayList = new ArrayList();
        openDatabase();
        Cursor res = mDatabase.rawQuery("select * from tafseer where verses_id='"+Soora_num+"'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            arrayList.add(new Activity_Regition_Qaraan_items_tafseer(res.getInt(res.getColumnIndex("ayah_id")),res.getString(res.getColumnIndex("title")),res.getString(res.getColumnIndex("content"))));
            res.moveToNext();
        }
        res.close();
        closeDatabase();
        return arrayList;
    }
    public String get_Tafseer(int ayah_id,int id_soora) {
        openDatabase();
        Cursor res = mDatabase.rawQuery("select * from tafseer where ayah_id='"+ayah_id+"' and verses_id='"+id_soora+"'", null);
     if(res!=null){
        res.moveToFirst();
        String Result="";
        while (!res.isAfterLast()) {
            Result+=res.getString(res.getColumnIndex("title")) +":" +res.getString(res.getColumnIndex("content"))+"\n";
            res.moveToNext();
        }
        res.close();
        closeDatabase();
        return Result;
     }
     return null;
    }


    void updata_numView(int num_view,int id) {
        if(num_view>3) {
            return;
        }
        openDatabase();
        String strSQL = "UPDATE Item_Qaraan SET num_views= '" + num_view + "' WHERE id = '" + id + "';";
        mDatabase.execSQL(strSQL);
        closeDatabase();
    }
    void updata_Saved(int Soora_saved,int id) {
        if(Soora_saved==1||Soora_saved==0)
        {
            openDatabase();
        String strSQL = "UPDATE Item_Qaraan SET soora_saved= '" + Soora_saved + "' WHERE id = '" + id + "';";
        mDatabase.execSQL(strSQL);
            closeDatabase();
        }
    }
}
