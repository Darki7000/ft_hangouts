package com.example.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ContactDatabase extends SQLiteOpenHelper {
    public static final int version = 4;
    public  static String dbName = "Contact.db";
    public static final String TABLE_NAME_CONTACT = "Contacts";
    public static final String TABLE_NAME_MESSAGE = "Messages";

    public static final String COL1 = "id";
    public static final String COL2 = "name";
    public static final String COL3 = "phone_number";
    public static final String COL4 = "email";
    public static final String COL5 = "address";
    public static final String COL6 = "zip_code";

    public static final String COL7 = "contact_id";
    public static final String COL8 = "message";
    public static final String COL9 = "is_from_me";

    private static final String CREATE_TABLE_CONTACT = "create table if not exists "+ TABLE_NAME_CONTACT +
            "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL2 + " TEXT NOT NULL,"
            + COL3 + " INTEGER, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT);";
    private static final String CREATE_TABLE_MESSAGE = "create table if not exists "+ TABLE_NAME_MESSAGE +
            "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL7 + " INTEGER,"
            + COL8 + " TEXT, " + COL9 + " BIT);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public ContactDatabase(Context context) {
        super(context,dbName,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_CONTACT);
        } catch (Exception e) {}

        try {
            db.execSQL(CREATE_TABLE_MESSAGE);
        } catch (Exception e) {}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + TABLE_NAME_CONTACT);
        db.execSQL(DROP_TABLE + TABLE_NAME_MESSAGE);
        onCreate(db);
    }

    public boolean insertContact(Contact obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL2,obj.getName());
        cv.put(COL3,obj.getPhone());
        cv.put(COL4,obj.getEmail());
        cv.put(COL5,obj.getAddress());
        cv.put(COL6,obj.getZip());

        long result = db.insert(TABLE_NAME_CONTACT,null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Contact> listContacts(){
        String sql = "select * from " + TABLE_NAME_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contact> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String email = cursor.getString(3);
                String address = cursor.getString(4);
                String zip = cursor.getString(5);
                storeContacts.add(new Contact(id, name, phone, email, address, zip));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_CONTACT, COL1	+ "	= ?", new String[] { String.valueOf(id)});
    }

    public void updateContact(Contact obj){
        ContentValues values = new ContentValues();
        values.put(COL2,obj.getName());
        values.put(COL3,obj.getPhone());
        values.put(COL4,obj.getEmail());
        values.put(COL5,obj.getAddress());
        values.put(COL6,obj.getZip());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME_CONTACT, values, COL1	+ "	= ?", new String[] { String.valueOf(obj.getId())});
    }

    public boolean insertMessage(Message obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL7,obj.getContactId());
        cv.put(COL8,obj.getMessage());
        cv.put(COL9,obj.getIsFromMe());

        long result = db.insert(TABLE_NAME_MESSAGE,null, cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Message> listMessages(int contactId){
        String sql = "select * from " + TABLE_NAME_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Message> storeMessage = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                if(contactId == Integer.parseInt(cursor.getString(1))) {
                    int id = Integer.parseInt(cursor.getString(0));
                    String message = cursor.getString(2);
                    boolean isFromMe = false;
                    if(Integer.parseInt(cursor.getString(3)) == 1) {
                        isFromMe = true;
                    }
                    storeMessage.add(new Message(id, contactId, message, isFromMe));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeMessage;
    }
}

