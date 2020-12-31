package com.wtechweb.v2_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_CELL = "_cell";

    private final String DATABASE_NAME = "ContactsDB";
    private final String DATABASE_TABLE = "ContactsTable";
    private final int DATABASE_VERSION = 2;

    private DBHelper ourHelper;
    private final Context outContext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB(Context context)
    {
        outContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            /*
            CREATE TABLE ContactsTable(
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            person_name TEXT NOT NULL,
            _cell TEXT NOT NULL);
             */
            String sqlQuery = "CREATE TABLE "+DATABASE_TABLE+"("
                    +KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_NAME+" TEXT NOT NULL,"
                    +KEY_CELL+" TEXT NOT NULL);";

            sqLiteDatabase.execSQL(sqlQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public ContactsDB open()
    {
        ourHelper = new DBHelper(outContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long insertData(String name, String cell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_CELL, cell);
       return ourDatabase.insert(DATABASE_TABLE,null, cv);
    }

    public String getData()
    {
        String []columns = new String[]{KEY_ROWID, KEY_NAME, KEY_CELL};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null,null,null,null,null);

        int idColumn = c.getColumnIndex(KEY_ROWID);
        int nameColumn = c.getColumnIndex(KEY_NAME);
        int cellColumn = c.getColumnIndex(KEY_CELL);
        String text="";
        int i=1;
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            text = text+i+" : "+c.getString(nameColumn)+" "+c.getString(cellColumn)+"\n";
            i++;
        }

        return text;
    }

    public long updateEntry(String rowId, String newName, String newCell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, newName);
        cv.put(KEY_CELL, newCell);
        return ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID+"=?", new String[]{rowId});
    }

    public long deleteEntry(String rowId)
    {
        return ourDatabase.delete(DATABASE_TABLE, KEY_ROWID+"=?",new String[]{rowId});
    }
}