package ravi.androidapp.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "My_movies.db";
    private static final String TABLE_NAME = "Favorites";
    private static final String COL0 = "id";
    private static final String COL1 = "title";
    private static final String COL2 = "poster";
    private static final String COL3 = "backdrop";
    private static final String COL4 = "date";
    private static final String COL5 = "overview";
    private static final String COL6 = "link";
    private static final String COL7 = "crew";
    private static final String COL8 = "isFavorite";

    private static final int DATABASE_VERSION = 1;

    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Defining table name and columns
        String SQL_CREATE_CAL_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + COL0 + " TEXT PRIMARY KEY NOT NULL , "+ COL1 + " TEXT  , "+ COL2 + " TEXT , " + COL3 + " TEXT , "+
                COL4 + " TEXT , " + COL5 + " TEXT , "+ COL6 + " TEXT , " + COL7 + " TEXT , " + COL8 + " INTEGER );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_CAL_TABLE);
    }
    boolean insertMovie(String id, String title, String poster, String backdrop, String date, String overview, String link, String crew) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL0, id);
        contentValues.put(COL1, title);
        contentValues.put(COL2, poster);
        contentValues.put(COL3, backdrop);
        contentValues.put(COL4, date);
        contentValues.put(COL5, overview);
        contentValues.put(COL6, link);
        contentValues.put(COL7, crew);
        contentValues.put(COL8, 1);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    int updateMovie(String id, Integer isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL8, isFavorite);
        return db.update(TABLE_NAME,contentValues,"id = ?",new String[]{String.valueOf(id)});
    }


    Cursor isFavorite(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME,new String[]{COL8},"id = ?",new String[]{id},null,null,null);
    }

    Cursor getAllMovies(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME,null,null,null,null,null,null,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
