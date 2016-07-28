package srairs.com.user_story.Articles;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import srairs.com.user_story.Constants;

/**
 * Created by Andrii on 22.04.2016.
 */
class ArticlesDBHelper extends SQLiteOpenHelper {

    public ArticlesDBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(Constants.MyLog, "--- onCreate database ---");
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "email text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}