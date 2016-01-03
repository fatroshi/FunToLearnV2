package se.atroshi.funtolearnv2.Controller;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import se.atroshi.funtolearnv2.Database.MySQLiteHelper;
import se.atroshi.funtolearnv2.Game.Item;
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
    private List<Item> items;
    private MySQLiteHelper database;


    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        //DB
        this.database = new MySQLiteHelper(this.mainActivity);
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



    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void requestData(String uri){

     if(this.isOnline()){
            this.task = new Task(this, this.mainActivity);
            this.task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
        }else{
            // USE TOAST
            this.updateDisplay();
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

//        //Textview
//        list = (ListView) this.mainActivity.findViewById(R.id.list);
//        //list.setMovementMethod(new ScrollingMovementMethod());
//
//        //this.list.append(message + "\n");
//        ItemAdapter_REMOVE adapter = new ItemAdapter_REMOVE(this.mainActivity,R.layout.items, this.items);
//        //        setListAdapter(adapter);
//        this.list.setAdapter(adapter);
//        for (Item item: this.items){
//            if(item.getBitmap() != null){
//                Log.i("Controller", "Has bitmap, id " + item.getImgLink());
//            }
//        }
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

    public void setItems(List<Item> items){
        this.items = items;
    }

}
