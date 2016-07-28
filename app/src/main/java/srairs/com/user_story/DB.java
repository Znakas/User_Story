package srairs.com.user_story;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.Toast;

/**
 * Created by Andry on 26.07.2016.
 */
public class DB {
    private SQLiteDatabase db;

    private DBHelper dbHelper;

    private final Context mCtx;


    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void openDB() {
        dbHelper = new DBHelper(mCtx, Constants.DB_TABLE, null, Constants.DB_VERSION);
        db = dbHelper.getWritableDatabase();
        Log.d(Constants.MyLog, "Db opened!");

    }

    public void closeDB() {
        if (dbHelper != null) dbHelper.close();
        Log.d(Constants.MyLog, "Db closed!");

    }

    public Cursor getAllItems() {
        return db.query(Constants.DB_TABLE, null, null, null, null, null, null);
    }

    public void addItem(String title, String article) {
        ContentValues cv = new ContentValues();
        Log.d(Constants.MyLog, "--- Insert in mytable: ---");

        cv.put(Constants.COLUMN_TITLE, title);
        cv.put(Constants.COLUMN_ARTICLE, article);

        long rowID = db.insert(Constants.DB_TABLE, null, cv);
        Log.d(Constants.MyLog, "Item inserted, ID = " + rowID);

    }

    /////
    public void updateItem(String title, String article, long myId) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.TITLE, title);
        cv.put(Constants.ARTICLE, article);

        String id = String.valueOf(myId);
        String where = Constants.COLUMN_ID + "=" + id;

        db.update(Constants.DB_TABLE, cv, where, null);
    }
/////

    public void deleteItem(long id) {
        Log.d(Constants.MyLog, "Delete info.id 11111 = " + id);
        db.delete(Constants.DB_TABLE, Constants.COLUMN_ID + "=" + id, null);
        Log.d(Constants.MyLog, "Deleted!");
    }

    public void deleteAllItems() {
        int clearCount = db.delete(Constants.DB_TABLE, null, null);
        Log.d(Constants.MyLog, "All items deleted!");

    }

    public String getItemTitle(long id) {
//        String title;
//        ContentValues cv = new ContentValues();
//        cv.get(Constants.COLUMN_TITLE);
//        cv.get(Constants.COLUMN_ARTICLE);
//
//        db.getAttachedDbs().get(id);
//
//        db.query(Constants.DB_TABLE, null, null, null, null, null);
//        return
//    }
//    public String getItemArticle(){
        return null;
    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(Constants.MyLog, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table " + Constants.DB_TABLE + " ("
                    + Constants.COLUMN_ID + " integer primary key autoincrement,"
                    + Constants.COLUMN_TITLE + " text,"
                    + Constants.COLUMN_ARTICLE + " text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
