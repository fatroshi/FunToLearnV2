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
import se.atroshi.funtolearnv2.Game.User;

/**
 * Created by Farhad on 31/12/15.
 * This class i used for handling sql queries, getting data and setting data from the database file.
 */
public class Database extends SQLiteOpenHelper {

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

    // CATEGORY TABLE
    private static final String TABLE_CATEGORIES          = "categories";
    // CATEGORY TABLE CREATION QUERY
    private static final String TABLE_CREATE_CATEGORIES   =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES +
                    " ( " +
                    COLUMN_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_ID      + " INTEGER NOT NULL , " +
                    COLUMN_CATEGORY_NAME    + " TEXT NOT NULL , " +
                    COLUMN_IMAGE_PATH       + " TEXT NOT NULL  " +
                    " )";


    // CATEGORY TABLE CREATION QUERY
    private static final String COLUMN_NAME        = "name";
    private static final String COLUMN_EMAIL       = "email";
    private static final String DOWNLOAD_UPDATES   = "downloadUpdates";
    // CATEGORY TABLE
    private static final String TABLE_USER          = "user";
    private static final String TABLE_CREATE_USER   =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USER +
                    " ( " +
                    COLUMN_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME     + " TEXT NOT NULL , " +
                    COLUMN_EMAIL    + " TEXT NOT NULL , "  +
                    DOWNLOAD_UPDATES    + " INTEGER NOT NULL  "  +
                    " )";
    public Context context;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        // 1. get reference to writable DB
        db = this.getWritableDatabase();

        // Insure that we always have one user (one record in the db with id = 1)
        //if(!userExists(1)) {
            createUser();
        //}
    }

    /**
     * Create tables for items, categories, and user
     * @param db selected db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.i(TAG, TABLE_CREATE);
        db.execSQL(TABLE_CREATE_ITEMS);
        db.execSQL(TABLE_CREATE_CATEGORIES);
        db.execSQL(TABLE_CREATE_USER);

        // Crete one user, this user will have id 1

        Log.i(TAG, "*** Tables has been created ***");
    }

    /**
     * Managing upgrades, removes older tables and create new ones
     * @param db  selected db
     * @param oldVersion id
     * @param newVersion id
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create fresh books table
        this.onCreate(db);
    }


    /**
     * Create user
     */
    public static void createUser(){
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, "User Name");
        values.put(COLUMN_EMAIL, "User@example.com");
        values.put(DOWNLOAD_UPDATES, 1);

        // 3. insert
        db.insert(TABLE_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
    }

    /**
     * Get the user
     * @return User object
     */
    public static User getUser(){
        String query = "SELECT * FROM " + TABLE_USER + " WHERE id=1" ;

        // Perform query
         Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            // Get data from table row
            int userId = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            int update = Integer.parseInt(cursor.getString(3));

            //Set user
            User user = new User();
            user.setUserId(userId);
            user.setEmail(email);
            user.setName(name);
            // Check update status (In db stored as true = 0, false = 1)
            boolean isUpdate = false;
            if(update == 1){
                isUpdate = true;
            }
            user.setDownloadUpdates(isUpdate);

            return user;
        }

        return null;
    }

    /**
     * Update the user info.
     * @param user object
     */
    public static void updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(DOWNLOAD_UPDATES, user.isDownloadUpdates());

        db.update(TABLE_USER, values, "id="+user.getUserId(), null);

        Log.i(TAG, "User updated");
    }

    /**
     * Add category to the database
     * @param item object
     */
    public void addCategory(Item item){
        if(!categoryExists(item.getCategoryId())) {

            // Save file to internal storage
            ContextWrapper cw = new ContextWrapper(this.context.getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir(item.getCategoryName(), Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = new File(directory, item.getImgName());

            // Get reference to writable DB
            SQLiteDatabase db = this.getWritableDatabase();

            // Create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_ID, item.getCategoryId());
            values.put(COLUMN_CATEGORY_NAME, item.getCategoryName());
            values.put(COLUMN_IMAGE_PATH, mypath.toString());

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
     * @param item object
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

    /**
     * Get all categories in the database
     * @return list of all categories as objects
     */
    public static List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();

        // Sql Query
        String query = "SELECT * FROM " + TABLE_CATEGORIES;
        //Perform query
        Cursor cursor = db.rawQuery(query,null);

        // Get items
        Category category = null;
        while (cursor.moveToNext()){
            // Get data from table row
            int categoryId = Integer.parseInt(cursor.getString(1));
            String categoryName = cursor.getString(2);
            String imgPath = cursor.getString(3);

            // Set item
            category = new Category();
            category.setCategoryId(categoryId);
            category.setCategoryName(categoryName);
            category.setImgPath(imgPath);

            categories.add(category);
        }

        Log.i(TAG, "Total categories: " + categories.size());

        return categories;
    }

    /**
     * Get a category by id
     * @param id of the category
     * @return Category object
     */
    public static Category findCategoryById(int id){
        // Sql query
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + COLUMN_CATEGORY_ID + "=" +id;
        // Perform query
        Cursor cursor = db.rawQuery(query,null);

        Category category = null;
        if(cursor.moveToFirst()){
            // Get data from table row
            int categoryId = Integer.parseInt(cursor.getString(1));
            String categoryName = cursor.getString(2);
            String imgPath = cursor.getString(3);

            // Set item
            category = new Category();
            category.setCategoryId(categoryId);
            category.setCategoryName(categoryName);
            category.setImgPath(imgPath);
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
        //if(cursor.moveToFirst()){
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

                items.add(item);
            }
        //}
        return items;
    }

    /**
     * Check if item exist in database
     * @param id item id
     * @return true if item exists in database
     */
    public static boolean itemExists(int id){
        boolean exists = false;

        List<Item> items = getItems(Database.FIND_BY_ITEM_ID, id);
        if(items.size() > 0){
            exists = true;
        }

        return exists;
    }

    /**
     * Check if the user exists in the database
     * @param id of the user
     * @return true if the user exists
     */
    public static boolean userExists(int id){
        boolean exists = false;

        String query = "SELECT * FROM " + TABLE_USER + " WHERE id=" + id;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() == 1){
            exists = true;
        }
        cursor.close();

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
}
