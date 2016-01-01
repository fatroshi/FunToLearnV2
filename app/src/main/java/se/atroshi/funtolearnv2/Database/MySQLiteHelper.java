package se.atroshi.funtolearnv2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.atroshi.funtolearnv2.Game.Category;
import se.atroshi.funtolearnv2.Game.Item;

/**
 * Created by Farhad on 31/12/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static SQLiteDatabase db;

    private static final String TAG                     = "EXPLORECA";
    //Operations constants
    public static final int FIND_BY_CATEGORY_ID         = 1;
    public static final int FIND_BY_ITEM_ID             = 2;
    public static final int FIND_ALL                    = 3;
    // DATABASE
    private static final String DATABASE_NAME           = "fun.db";
    private static final int DATABASE_VERSION           = 1;
    // TABLE
    private static final String TABLE_ITEMS             = "items";
    // COLUMNS ITEM TABLE
    private static final String COLUMN_ID               = "id";
    private static final String COLUMN_CATEGORY_ID      = "categoryID";
    private static final String COLUMN_CATEGORY_NAME    = "categoryName";
    private static final String COLUMN_ITEM_ID          = "itemId";
    private static final String COLUMN_ITEM_NAME        = "itemName";
    private static final String COLUMN_IMG_NAME         = "imageName";
    private static final String COLUMN_IMAGE_PATH       = "imagePath";
    private static final String COLUMN_PUBLISHED        = "published";
    // ITEMS TABLE CREATION QUERY
    private static final String TABLE_CREATE_ITEMS   =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS +
                    " ( " +
                    COLUMN_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_ID      + " INTEGER NOT NULL , " +
                    COLUMN_CATEGORY_NAME    + " TEXT NOT NULL , " +
                    COLUMN_ITEM_ID          + " INTEGER NOT NULL , " +
                    COLUMN_ITEM_NAME        + " TEXT NOT NULL , " +
                    COLUMN_IMG_NAME         + " TEXT NOT NULL , " +
                    COLUMN_IMAGE_PATH       + " TEXT NOT NULL , " +
                    COLUMN_PUBLISHED        + " Text NOT NULL "   +
                    " )";

    // TABLE
    private static final String TABLE_CATEGORIES          = "categories";
    private static final String TABLE_CREATE_CATEGORIES   =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES +
                    " ( " +
                    COLUMN_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_ID      + " INTEGER NOT NULL , " +
                    COLUMN_CATEGORY_NAME    + " TEXT NOT NULL  " +
                    " )";

    public Context context;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        // 1. get reference to writable DB
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.i(TAG, TABLE_CREATE);
        db.execSQL(TABLE_CREATE_ITEMS);
        db.execSQL(TABLE_CREATE_CATEGORIES);
        Log.i(TAG,"*** Tables has been created ***");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);

        // create fresh books table
        this.onCreate(db);
    }


    public void addCategory(Item item){
        if(!categoryExists(item.getCategoryId())) {

            // 1. get reference to writable DB
            SQLiteDatabase db = this.getWritableDatabase();

            // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_ID, item.getCategoryId());
            values.put(COLUMN_CATEGORY_NAME, item.getCategoryName());

            // 3. insert
            db.insert(TABLE_CATEGORIES, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values

            // 4. close
            //db.close();
        }
    }

    /**
     * Add item if it does not exist in database
     * @param item
     */
    public void addItem(Item item){
        if(!this.itemExists(item.getItemId())) {

            // Save file to internal storage
            ContextWrapper cw = new ContextWrapper(this.context.getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir(item.getCategoryName(), Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = new File(directory, item.getImgName());

            // 1. get reference to writable DB
            SQLiteDatabase db = this.getWritableDatabase();

            // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_ID, item.getCategoryId());
            values.put(COLUMN_CATEGORY_NAME, item.getCategoryName());
            values.put(COLUMN_ITEM_ID, item.getItemId());
            values.put(COLUMN_ITEM_NAME, item.getItemName());
            values.put(COLUMN_IMG_NAME, item.getImgName());
            values.put(COLUMN_IMAGE_PATH, mypath.toString());           // INTERNAL STORAGE place
            values.put(COLUMN_PUBLISHED, item.getDate().toString());

            // 3. insert
            db.insert(TABLE_ITEMS, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values

            // 4. close
            //db.close();
        }
    }

    public static List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();

        // Sql Query
        String query = "SELECT * FROM " + TABLE_CATEGORIES;
        //Perform query
        Cursor cursor = db.rawQuery(query,null);

        // Get items
        Category category = null;
        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                // Get data from table row
                int categoryId = Integer.parseInt(cursor.getString(1));
                String categoryName = cursor.getString(2);

                // Set item
                category = new Category();
                category.setCategoryId(categoryId);
                category.setCategoryName(categoryName);

                categories.add(category);
            }
        }

        return categories;
    }

    public static Category findCategoryById(int id){


        // Sql query
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + COLUMN_CATEGORY_ID + "=" +id;
        // Perform query
        //Perform query
        Cursor cursor = db.rawQuery(query,null);

        Category category = null;
        if(cursor.moveToFirst()){
            // Get data from table row
            int categoryId = Integer.parseInt(cursor.getString(1));
            String categoryName = cursor.getString(2);

            // Set item
            category = new Category();
            category.setCategoryId(categoryId);
            category.setCategoryName(categoryName);
        }

        return category;
    }


    /**
     * Get items
     * @param operation find by item id, find by category id, find all
     * @param id item id
     * @return list of found items
     */
    public static List<Item> getItems(int operation, Integer id){
        List<Item> items = new ArrayList<>();

        // Sql Query
        String query = null;

        if(operation == FIND_ALL){                                                                  // Get all items
            query = "Select * From " + TABLE_ITEMS;
        }else if (operation == FIND_BY_CATEGORY_ID){                                                // Get all items for category
            query = "Select * From " + TABLE_ITEMS + " WHERE " + COLUMN_CATEGORY_ID + "=" + id;
        }else if(operation == FIND_BY_ITEM_ID){                                                     // Get item by item id
            query = "Select * From " + TABLE_ITEMS + " WHERE " + COLUMN_ITEM_ID + "=" + id;
        }
        //Perform query
        Cursor cursor = db.rawQuery(query,null);

        // Get items
        Item item = null;
        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                // Get data from table row
                int categoryId = Integer.parseInt(cursor.getString(1));
                String categoryName = cursor.getString(2);
                int itemId = Integer.parseInt(cursor.getString(3));
                String itemName = cursor.getString(4);
                String imgName = cursor.getString(5);
                String imgPath = cursor.getString(6);

                // Set item
                item = new Item();
                item.setCategoryId(categoryId);
                item.setCategoryName(categoryName);
                item.setItemId(itemId);
                item.setItemName(itemName);
                item.setImgName(imgName);
                item.setImgPath(imgPath);
                // Set bitmap
//                File imageFile = new File(imgPath);
//                if(imageFile.exists()){
//                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
//                    item.setBitmap(bitmap);
//                }
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Check if item exist in database
     * @param id item id
     * @return true if item exists in database
     */
    public static boolean itemExists(int id){
        boolean exists = false;

        List<Item> items = getItems(MySQLiteHelper.FIND_BY_ITEM_ID, id);
        if(items.size() > 0){
            exists = true;
        }

        return exists;
    }

    /**
     * Check if item exist in database
     * @param id item id
     * @return true if item exists in database
     */
    public  boolean categoryExists(int id){
        boolean exists = false;

        if(findCategoryById(id) != null){
            exists = true;
        }
        return exists;
    }

    /**
     * Create a function that checks if the item already exist in db, call this method before creating item after parsing JSON
     * this will also affect downloading images.. which is good.
     *
     * This can perhaps handel the app update.
     *
     * 1. On app start
     *  - if we are connected to internet
     *  - check if we need to download
     *  - show items if exist from previous downloads
     *
     */

//    public static List<Item> getCategories(){
//        List<Item> items = getItems(FIND_ALL)
//    }
}
