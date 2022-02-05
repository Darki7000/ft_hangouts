package com.example.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDatabase extends SQLiteOpenHelper {
    public static final int version = 1;
    public  static String dbName="Contact.db";
    public static final String TABLE_NAME ="Contacts";

    public static final String COL1 = "id";
    public static final String COL2 = "name";
    public static final String COL3 = "phone_number";
    public static final String COL4 = "email";
    public static final String COL5 = "address";
    public static final String COL6 = "zip_code";

    private static final String CREATE_TABLE = "create table if not exists "+ TABLE_NAME +
            "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL2 + " TEXT NOT NULL,"
            + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    private Context context;

    public ContactDatabase(Context context) {
        super(context,dbName,null,version);
        context = this.context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public boolean InsertContact(Contact obj)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL2,obj.getContactName());
        cv.put(COL3,obj.getContactPhone());
        cv.put(COL4,obj.getContactEmail());
        cv.put(COL5,obj.getContactAddress());
        cv.put(COL6,obj.getContactZip());

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }
}
