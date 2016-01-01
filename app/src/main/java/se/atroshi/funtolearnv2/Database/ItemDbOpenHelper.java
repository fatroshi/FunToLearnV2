package se.atroshi.funtolearnv2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Farhad on 31/12/15.
 */
public class ItemDbOpenHelper extends SQLiteOpenHelper {


    private static final String TAG                     = "EXPLORECA";
    private static final String DATABASE_NAME           = "fun.db";
    private static final int DATABASE_VERSION           = 1;

    private static final String TABLE_ITEMS             = "items";

    private static final String COLUMN_ID               = "itemID";
    private static final String COLUMN_CATEGORY_ID      = "categoryID";
    private static final String COLUMN_CATEGORY_NAME    = "categoryName";
    private static final String COLUMN_ITEM_ID          = "itemId";
    private static final String COLUMN_ITEM_NAME        = "itemName";
    private static final String COLUMN_IMAGE_PATH       = "imagePath";
    private static final String COLUMN_PUBLISHED        = "published";

    private static final String TABLE_CREATE   =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS +
                    " ( " +
                        COLUMN_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CATEGORY_ID      + " INTEGER NOT NULL , " +
                        COLUMN_CATEGORY_NAME    + " TEXT NOT NULL , " +
                        COLUMN_ITEM_ID          + " INTEGER NOT NULL , " +
                        COLUMN_ITEM_NAME        + " TEXT NOT NULL , " +
                        COLUMN_IMAGE_PATH       + " TEXT NOT NULL , " +
                        COLUMN_PUBLISHED        + " Text NOT NULL "   +
                    " )";

    public ItemDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, TABLE_CREATE);
        //db.execSQL(TABLE_CREATE);
        Log.i(TAG,"*** Table has been created ***");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXIST " + TABLE_ITEMS);
        //this.onCreate(db);
    }
}
