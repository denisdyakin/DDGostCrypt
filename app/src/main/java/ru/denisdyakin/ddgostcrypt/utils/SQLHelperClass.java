package ru.denisdyakin.ddgostcrypt.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Denis on 05.05.2015.
 */
public class SQLHelperClass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DDGost";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_FIRST = "Password_table";
    public static final String ID = "ID";
    public static final String PASSHASH = "PassHash";

    public static final String TABLE_SECOND = "Files_table";
    public static final String FILE_NAME = "File_name";

    public static final String TABLE_THIRD = "Passwords_table";
    public static final String WEB_SITE = "Web_site";
    public static final String LOGIN = "Login";
    public static final String PASSWORD = "Password";

    public static final String TABLE_FOURTH = "Notes_table";
    public static final String TITLE = "Title";
    public static final String TEXT = "Text";

    public SQLHelperClass(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_FIRST + " ( "
                        + ID + " integer primary key, "
                        + PASSHASH + " varchar(256) not null "
                        + " );"
        );

        db.execSQL("CREATE TABLE " + TABLE_SECOND + " ( "
                        + ID + " integer primary key autoincrement, "
                        + FILE_NAME + " varchar(256) not null "
                        + " );"
        );

        db.execSQL(
            "CREATE TABLE " + TABLE_THIRD + " ( "
                + ID + " integer primary key autoincrement, "
                + WEB_SITE + " text not null, "
                + LOGIN + " text not null, "
                + PASSWORD + " text not null "
                + " );"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_FOURTH + " ( "
                        + ID + " integer primary key autoincrement, "
                + TITLE + " text not null, "
                + TEXT + " text "
                + " );"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
