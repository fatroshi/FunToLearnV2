package se.atroshi.funtolearnv2.Controller;

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
 */
public class Controller extends ListActivity{

    private final String TAG = "Controller";

    private MainActivity mainActivity;
    private Task task;
    private ProgressBar pb;
    private List<Task> tasks;
    private Database database;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        //DB
        this.database = new Database(this.mainActivity);
        this.tasks = new ArrayList<>();                                     // Holder for all tasks
        // Animation for loading
        this.pb = (ProgressBar) this.mainActivity.findViewById(R.id.progressBar);
        this.pb.setVisibility(View.INVISIBLE);


    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) this.mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return  false;
        }
    }



    public boolean update(){
        User user = Database.getUser();
        return user.isDownloadUpdates();
    }


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

    public void showLoadingAnimation(){
        if(this.tasks.size() == 0) {
            // Show loading animation icon
            this.pb.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoadingAnimation(){
        if(this.tasks.size() == 0) {
            // Show loading animation icon
            this.pb.setVisibility(View.INVISIBLE);
        }
    }

    public void showCategories(){
//        // Category listview
//        this.categoryListView = (ListView) this.mainActivity.findViewById(R.id.list);
//
//        // Adapter
//        CategoryAdapter adapter = new CategoryAdapter(this.mainActivity,R.layout.items, getCategoriesFromDB());
//
//        if(this.categoryListView != null){
//            this.categoryListView.setAdapter(adapter);
//
//        }else {
//            Log.i(TAG,"categoryListView is NULL");
//        }

    }

    public void updateDisplay(){

    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public void removeTask(Task task){
        this.tasks.remove(task);
    }

    /**
     * Shows messages in the app
     * @param msg
     */
    private void showToast(String msg) {
        Toast toast = Toast.makeText(mainActivity, msg, Toast.LENGTH_SHORT);
    }

}
