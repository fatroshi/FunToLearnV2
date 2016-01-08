package se.atroshi.funtolearnv2.SiteConnection;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import se.atroshi.funtolearnv2.Controllers.Controller;
import se.atroshi.funtolearnv2.Controllers.Navigation;
import se.atroshi.funtolearnv2.Database.Database;
import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.MainActivity;
import se.atroshi.funtolearnv2.R;


/**
 * Created by Farhad on 25/12/15.
 * This class is used for downloading content from the website, storing the item info in
 * the database and storing images to the internal storage.
 */
public class Task extends AsyncTask<String,String,List<Item>> { // < params, process, result>

    private final String TAG = "Task";
    private final String SITE_URL = "http://fun.neodesign.se/";
    private Controller controller;
    private MainActivity mainActivity;
    private TextView tvDownloading;
    private Database db;

    public Task(Controller controller, MainActivity mainActivity){
        this.controller = controller;
        this.mainActivity = mainActivity;
        // DB
        db = new Database(this.mainActivity);
        //
        this.tvDownloading = (TextView) this.mainActivity.findViewById(R.id.textViewDownloading);
    }

    /**
     * This method is executed before executing doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Show loading animation icon
        this.controller.showLoadingAnimation();
        //
        this.controller.addTask(this);

    }

    /**
     * Download resources if needed and save item/category info in the database.
     * @param params
     * @return
     */
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
        return null;
    }

    /**
     * When task is done redirect user to the category fragment/page
     * @param result null
     */
    @Override
    protected void onPostExecute(List<Item> result) {
        super.onPostExecute(result);

        // Remove task from task list in the controller
        this.controller.removeTask(this);
        // Remove the loading animation icon
        this.controller.hideLoadingAnimation();
        // Remove text
        this.tvDownloading.setVisibility(View.INVISIBLE);
        // Send to words page
        Navigation.showWordsPlayView();

    }

    @Override
    protected void onProgressUpdate(String... values) {
        //super.onProgressUpdate(values);
        //this.controller.updateDisplay();
    }
}
