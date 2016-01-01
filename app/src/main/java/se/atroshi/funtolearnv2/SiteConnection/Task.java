package se.atroshi.funtolearnv2.SiteConnection;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import se.atroshi.funtolearnv2.Controller.Controller;
import se.atroshi.funtolearnv2.Database.MySQLiteHelper;
import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.MainActivity;


/**
 * Created by Farhad on 25/12/15.
 */
public class Task extends AsyncTask<String,String,List<Item>> { // < params, process, result>

    private final String TAG = "Task";
    private final String SITE_URL = "http://fun.neodesign.se/";
    private Controller controller;
    private MainActivity mainActivity;

    private MySQLiteHelper db;

    public Task(Controller controller, MainActivity mainActivity){
        this.controller = controller;
        this.mainActivity = mainActivity;

        // DB
        db = new MySQLiteHelper(this.mainActivity);
    }

    /**
     * This method is executed before executing doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this.controller.updateDisplay();
        // Show loading animation icon
        this.controller.showLoadingAnimation();
        //
        this.controller.addTask(this);

    }

    @Override
    protected List<Item> doInBackground(String... params) {
        String content = HttpManager.getDate(params[0]);
        // Get all parsed items
        List<Item> items = ItemJSONParser.parseFeed(content);

        if(items.size() == 0){
            Log.i(TAG, "No update needed");
        }else {

            // Get image for each item
            for (Item item : items) {
                // Download item image and set
                Bitmap bitmap = ImageDownloader.download(this.SITE_URL + item.getImgLink());
                item.setBitmap(bitmap);

                // Save file to internal storage
                ImageDownloader.saveToInternalSorage(this.mainActivity, item);

                // Save items info in database
                this.db.addItem(item);
                // Save category if new
                if(!this.db.categoryExists(item.getCategoryId())){
                    this.db.addCategory(item);
                }

            }
        }

        // This part should be rewritten

        //this.controller.setItems(items);
        //Log.i(tag, content);
        //return this.controller.getItems();

        return null;
    }

    @Override
    protected void onPostExecute(List<Item> result) {
        super.onPostExecute(result);

        // Remove task from task list in the controller
        this.controller.removeTask(this);
        // Remove the loading animation icon
        this.controller.hideLoadingAnimation();

        //this.controller.getItemsFromDB();

        //this.controller.getCategoriesFromDB();

        this.controller.showCategories();

        // GET FROM DB
//        for(Item item: this.db.getItems(MySQLiteHelper.FIND_ALL,null)){
//            //Log.i("FROM DB IN TASK", "ID " + item.getItemId());
//            List<Item> items = this.db.getItems(MySQLiteHelper.FIND_BY_ITEM_ID,item.getItemId());
//            if(items.size() > 0){
//                Log.i(TAG, "ID : " + items.get(0).getItemId() + " " + items.get(0).getCategoryName());
//            }else{
//                Log.i(TAG,"No item found in db");
//            }
//        }

        // Parse data
        //for (int i = 0; i < this.controller.getItems().size(); i++) {
            //this.controller.updateDisplay();
        //}



    }

    @Override
    protected void onProgressUpdate(String... values) {
        //super.onProgressUpdate(values);
        this.controller.updateDisplay();
    }
}
