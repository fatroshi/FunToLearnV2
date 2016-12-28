package se.atroshi.funtolearnv2.Controllers;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.atroshi.funtolearnv2.Database.Database;
import se.atroshi.funtolearnv2.Game.User;
import se.atroshi.funtolearnv2.MainActivity;
import se.atroshi.funtolearnv2.R;
import se.atroshi.funtolearnv2.SiteConnection.Task;

/**
 * Created by Farhad on 25/12/15.
 * This class is used for controlling background tasks on the device.
 */
public class Controller extends ListActivity{

    private final String TAG = "Controller";

    private MainActivity mainActivity;                                  // Main thread
    private Task task;                                                  // Current task
    private ProgressBar pb;                                             // Used for showing progress for the user
    private List<Task> tasks;                                           // Holder for all tasks
    private Database db;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        //DB
        db = new Database(this.mainActivity);

        this.tasks = new ArrayList<>();
        // Animation for loading
        this.pb = (ProgressBar) this.mainActivity.findViewById(R.id.progressBar);
        this.pb.setVisibility(View.INVISIBLE);


    }

    /**
     * Check if the device has internet connectivity access
     * @return true if device is connected to the internet
     */
    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) this.mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return  false;
        }
    }

    /**
     * Check if the app needs to download resources
     * @return true if new files are on the server
     */
    public boolean update(){
        User user = Database.getUser();
        return user.isDownloadUpdates();
    }

    /**
     * Get data from the server
     */
    public void requestData(){
        String uri = "http://fun.neodesign.se/app.json";
        // Check if the user wants to update
        if(update()) {
            if (this.isOnline()) {
                this.task = new Task(this, this.mainActivity);
                this.task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
            } else {
                // USE TOAST
                this.showToast("No Internet Connection");
                TextView tvDownloading = (TextView) this.mainActivity.findViewById(R.id.textViewDownloading);
                tvDownloading.setVisibility(View.GONE);
            }
        }else{
            TextView tvDownloading = (TextView) this.mainActivity.findViewById(R.id.textViewDownloading);
            tvDownloading.setVisibility(View.GONE);
        }
    }

    /**
     * Show progressbar
     */
    public void showLoadingAnimation(){
        if(this.tasks.size() == 0) {
            // Show loading animation icon
            this.pb.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hide progressbar
     */
    public void hideLoadingAnimation(){
        if(this.tasks.size() == 0) {
            // Show loading animation icon
            this.pb.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Add current task to the list, used for determine when to
     * use the progressbar.
     * @param task
     */
    public void addTask(Task task){
        this.tasks.add(task);
    }

    /**
     * Removes task from the list
     * @param task current task
     */
    public void removeTask(Task task){
        this.tasks.remove(task);
    }

    /**
     * Shows messages in the app
     * @param msg that will be displayed on the device
     */
    private void showToast(String msg) {
        Toast toast = Toast.makeText(mainActivity, msg, Toast.LENGTH_SHORT);
    }

}
