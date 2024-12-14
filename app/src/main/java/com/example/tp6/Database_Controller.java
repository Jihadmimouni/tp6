package com.example.tp6;

// StudentDatabaseHelper.java
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database_Controller extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_MARK = "mark";

    public Database_Controller(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_MARK + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addStudent(String name, String lastname, double mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LASTNAME, lastname);
        values.put(COLUMN_MARK, mark);

        // Step 1: Find the next available ID (either a deleted ID or the highest existing ID + 1)
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC", null);
        int newId = 1; // Default to 1 if no rows exist

        // Check if we have any gaps in IDs (e.g., deleted IDs)
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int currentId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            if (currentId != newId) {
                // If we find a gap, use this ID
                break;
            }
            newId++;
        }

        cursor.close();

        values.put(COLUMN_ID, newId);
        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            Log.e("DatabaseError", "Error adding student: Insert failed.");
            return false;
        } else {
            Log.d("DatabaseSuccess", "Student added with ID: " + result);
        }

        return true;
    }



    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 1: Delete the student with the given ID
        boolean isDeleted = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;

        if (isDeleted) {
            // Step 2: Reorder IDs to ensure they remain sequential
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC", null);
            int newId = 1;

            while (cursor.moveToNext()) {
                @SuppressLint("Range") int currentId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));

                // Update the ID if it's not already the new sequential ID
                if (currentId != newId) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID, newId);
                    db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(currentId)});
                }
                newId++;
            }

            cursor.close();
        }

        db.close();
        return isDeleted;
    }

    public boolean updateStudent(int id, double newMark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MARK, newMark);

        return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }
}

