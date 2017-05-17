package com.example.sabeena.tutorpal.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sabeena.tutorpal.models.Child;
import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaBeeNa on 3/28/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TutorPal.db";

    public static final String STUDENT_TABLE = "student_table";
    public static final String TUITION_TABLE = "tuition_table";
    public static final String DAY_TABLE = "day_table";

    //common clumn names
    public static final String CHILD_ID = "childID";
    public static final String TUITION_ID = "tuitionID";

    //student column names
    //public static final String CHILD_ID = "childID";
    public static final String CHILD_NAME = "name";
    public static final String CHILD_GENDER = "gender";

    //tuition column names
    //public static final String TUITION_ID = "childID";
    //public static final String CHILD_ID = "name";
    public static final String SUBJECT = "subject";
    public static final String TUTOR_NAME = "tutorName";
    public static final String TUTOR_AC_NUMBER = "tutorACNumber";
    public static final String VENUE = "venue";
    public static final String FEE = "fee";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String NOTIFICATION = "notification";


    //day column names
    //public static final String TUITION_ID = "name";
    public static final String DAY_ID = "dayID";
    public static final String DAY = "dayOfTheWeek";
    public static final String START_TIME = "startTimr";
    public static final String END_TIME = "endTime";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 4); //database will be created
        Log.d("Database Operations", "Database Created....");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("delete * from "+STUDENT_TABLE+";");
        db.execSQL("create table " + STUDENT_TABLE + " (childID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, gender TEXT);");//create the studen table
        Log.d("Database Operations", "STUDENT_TABLE Created....");
        //db.execSQL("create table "+ STUDENT_TABLE + " (childID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, gender TEXT);" );//create the studen table


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 5) {
            db.execSQL("alter table " + TUITION_TABLE + " add column notification INTEGER;");//create the tuition table

            Log.d("Database Operations", "Tables Deleted....");
        }

        if (oldVersion < 4) {
            db.execSQL("alter table " + TUITION_TABLE + " add column longitude TEXT;");//create the tuition table
            db.execSQL("alter table " + TUITION_TABLE + " add column latitude TEXT;");

            //Log.d("Database Operations", "TUITION_TABLE Created....");
            //db.execSQL("create table " + DAY_TABLE + " (dayID INTEGER PRIMARY KEY AUTOINCREMENT, tuitionID INTEGER,dayOfTheWeek TEXT, startTimr TEXT, endTime TEXT, FOREIGN KEY(tuitionID) REFERENCES tuition_table(tuitionID));");//create the studen table
            Log.d("Database Operations", "Tables Deleted....");
        }
        if (oldVersion < 3) {
            db.execSQL("delete from " + DAY_TABLE);//create the tuition table
            db.execSQL("delete from " + TUITION_TABLE);
            db.execSQL("delete from " + STUDENT_TABLE);
            //Log.d("Database Operations", "TUITION_TABLE Created....");
            //db.execSQL("create table " + DAY_TABLE + " (dayID INTEGER PRIMARY KEY AUTOINCREMENT, tuitionID INTEGER,dayOfTheWeek TEXT, startTimr TEXT, endTime TEXT, FOREIGN KEY(tuitionID) REFERENCES tuition_table(tuitionID));");//create the studen table
            Log.d("Database Operations", "Tables Deleted....");
        }

        if (oldVersion < 2) {
            db.execSQL("create table " + TUITION_TABLE + " (tuitionID INTEGER PRIMARY KEY AUTOINCREMENT, childID INTEGER, subject TEXT, tutorName TEXT, tutorACNumber TEXT , venue TEXT, fee REAL,FOREIGN KEY(childID) REFERENCES student_table(childID));");//create the tuition table
            Log.d("Database Operations", "TUITION_TABLE Created....");
            db.execSQL("create table " + DAY_TABLE + " (dayID INTEGER PRIMARY KEY AUTOINCREMENT, tuitionID INTEGER,dayOfTheWeek TEXT, startTimr TEXT, endTime TEXT, FOREIGN KEY(tuitionID) REFERENCES tuition_table(tuitionID));");//create the studen table
            Log.d("Database Operations", "Table Created....");
        }
       /* db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TUITION_TABLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + DAY_TABLE + ";");*/
        Log.d("Database Operations", "Database Updated....");
//        onCreate(db);
    }

    public boolean insertChild(Child c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();//to pass the values to the database
        contentValues.put(CHILD_NAME, c.getName());
        contentValues.put(CHILD_GENDER, c.getGender());
        long result = db.insert(STUDENT_TABLE, null, contentValues);
        //incase of an error, returns -1, else returns row id the newly inserted row

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertClass(TuitionClass t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();//to pass the values to the database

        contentValues.put(CHILD_ID, t.getChildID());
        contentValues.put(SUBJECT, t.getSubject());
        contentValues.put(TUTOR_NAME, t.getTutorName());
        contentValues.put(TUTOR_AC_NUMBER, t.getTutorACNumber());
        contentValues.put(VENUE, t.getLocation());
        contentValues.put(FEE, t.getTuitionFee());
        contentValues.put(LONGITUDE, t.getLongitude());
        contentValues.put(LATITUDE, t.getLatitude());
        contentValues.put(NOTIFICATION, t.isNotification());


        long result = db.insert(TUITION_TABLE, null, contentValues);
        //incase of an error, returns -1, else returns row id the newly inserted row

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean insertDay(int lastTuitionID, Day d) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();//to pass the values to the database

        contentValues.put(TUITION_ID, lastTuitionID);

        String selectedDay = "Monday";

        if (d.getDayOfTheWeek().equals(Day.DayOfTheWeek.MONDAY))
            selectedDay = "Monday";
        else if (d.getDayOfTheWeek().equals(Day.DayOfTheWeek.TUESDAY))
            selectedDay = "Tuesday";
        else if (d.getDayOfTheWeek().equals(Day.DayOfTheWeek.WEDNESDAY))
            selectedDay = "Wednesday";
        else if (d.getDayOfTheWeek().equals(Day.DayOfTheWeek.THURSDAY))
            selectedDay = "Thursday";
        else if (d.getDayOfTheWeek().equals(Day.DayOfTheWeek.FRIDAY))
            selectedDay = "Friday";
        else if (d.getDayOfTheWeek().equals(Day.DayOfTheWeek.SATURDAY))
            selectedDay = "Saturday";
        else if (d.getDayOfTheWeek().equals(Day.DayOfTheWeek.SUNDAY))
            selectedDay = "Sunday";


        contentValues.put(DAY, selectedDay);
        contentValues.put(START_TIME, d.getStarTime());
        contentValues.put(END_TIME, d.getEndTime());

        long result = db.insert(DAY_TABLE, null, contentValues);
        //incase of an error, returns -1, else returns row id the newly inserted row

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public int getLastChildID() {
        SQLiteDatabase db = getReadableDatabase();

        //returns the last index of the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " ORDER BY childID DESC LIMIT 1; ", null);

        //return false if the cursor is empty.
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public int getLastTuitionID() {
        SQLiteDatabase db = getReadableDatabase();

        //returns the last index of the table
        Cursor cursor = db.rawQuery("SELECT * FROM " + TUITION_TABLE + " ORDER BY tuitionID DESC LIMIT 1; ", null);

        //return false if the cursor is empty.
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    //to get the child when childID is given
    public Child getChild(int childID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " WHERE childID = " + childID + ";", null);

        //return false if the cursor is empty.
        cursor.moveToFirst();
        Child c = new Child(cursor.getString(1), cursor.getString(2));
        return c;
    }

    public Cursor getAllChildren(SQLiteDatabase db) {
        Cursor result = db.rawQuery("select * from " + STUDENT_TABLE + ";", null);
        return result;
    }

    public Cursor getAllTuition(SQLiteDatabase db, int childID) {
        //Cursor result = db.rawQuery("select * from " + TUITION_TABLE + ";", null);
        Cursor result = db.rawQuery("select * from " + TUITION_TABLE + " where childID = " + childID + ";", null);
        //Cursor.getCount();
        return result;
    }

    public Cursor getTuitionsOForheDay(SQLiteDatabase db, String dayOfTheWeek) {
        Cursor result = db.rawQuery("select * from " + DAY_TABLE + " where dayOfTheWeek = " + dayOfTheWeek + ";", null);
        //Cursor.getCount();
        return result;
    }

    //
    public ArrayList<Integer> getAllTuitionOfTheDay(SQLiteDatabase db, String dayOfTheWeek) {
        //Cursor result = db.rawQuery("select * from " + TUITION_TABLE + ";", null);
        Cursor result = db.rawQuery("select * from " + DAY_TABLE + " where dayOfTheWeek =  '" + dayOfTheWeek + "';", null);
        //Cursor.getCount();
        ArrayList<Integer> tuitionIDs = new ArrayList<>();
        if (result.getCount() > 0) {
            result.moveToFirst();
            do {
                tuitionIDs.add(result.getInt(1));
            } while (result.moveToNext());
        }
        return tuitionIDs;
    }

    public Cursor getTuition(SQLiteDatabase db, int tuitionID) {
        //Cursor result = db.rawQuery("select * from " + TUITION_TABLE + ";", null);
        Cursor result = db.rawQuery("select * from " + TUITION_TABLE + " where tuitionID = " + tuitionID + ";", null);
        //Cursor.getCount();
        return result;
    }

    public Cursor getTuitionDay(SQLiteDatabase db, int tuitionID) {
        //Cursor result = db.rawQuery("select * from " + TUITION_TABLE + ";", null);
        Cursor result = db.rawQuery("select * from " + DAY_TABLE + " where tuitionID = " + tuitionID + ";", null);
        //Cursor.getCount();
        return result;
    }
}