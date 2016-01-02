package se.atroshi.funtolearnv2.Controller;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.atroshi.funtolearnv2.Database.MySQLiteHelper;
import se.atroshi.funtolearnv2.Fragment.CategoryFragment;
import se.atroshi.funtolearnv2.Fragment.ItemFragment;
import se.atroshi.funtolearnv2.Fragment.MathFragment;
import se.atroshi.funtolearnv2.Fragment.StartFragment;
import se.atroshi.funtolearnv2.Game.Category;
import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.Gui.CategoryAdapter;
import se.atroshi.funtolearnv2.Gui.ItemAdapter;
import se.atroshi.funtolearnv2.MainActivity;
import se.atroshi.funtolearnv2.R;
import se.atroshi.funtolearnv2.SiteConnection.Task;

/**
 * Created by Farhad on 25/12/15.
 */
public class Controller extends ListActivity{

    private final String TAG = "Controller";
    private MainActivity mainActivity;
    private ListView list;
    private ListView categoryListView;

    private Task task;
    private ProgressBar pb;

    private List<Task> tasks;
    private List<Item> items;

    private MySQLiteHelper database;

    private FragmentManager fragmentManager;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        //DB
        this.database = new MySQLiteHelper(this.mainActivity);
        this.tasks = new ArrayList<>();                                     // Holder for all tasks

        // Animation for loading
        this.pb = (ProgressBar) this.mainActivity.findViewById(R.id.progressBar);
        this.pb.setVisibility(View.INVISIBLE);

        // Fragment Manager
        fragmentManager = this.mainActivity.getFragmentManager();
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

    public void showStartView(){
        fragmentManager.beginTransaction().replace(R.id.myContainer, new StartFragment()).commit();
    }

    public void showWordsPlayView(){
        //
        this.fragmentManager.beginTransaction().replace(R.id.myContainer, new CategoryFragment()).commit();
        //
        this.showCategories();
    }

    public void showMathView(){
        this.fragmentManager.beginTransaction().replace(R.id.myContainer, new MathFragment()).commit();
    }

    public void requestData(String uri){

        // Serial
        //this.task.execute("Parma 1", "Param 2", "Param 3");
        // Parallel
        //this.task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Param 1", "Param 2", "Param 3");
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
//        ItemAdapter adapter = new ItemAdapter(this.mainActivity,R.layout.items, this.items);
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

    public List<Item> getItems(){
        return this.items;
    }

    public void getItemsFromDB(){
        setItems(MySQLiteHelper.getItems(MySQLiteHelper.FIND_ALL, null));

        if(this.items.size() > 0) {
            for (Item item : this.items) {
                Log.i(TAG, item.toString());
            }
        }
    }

    public List<Category> getCategoriesFromDB(){
        List<Category> categories = this.database.getAllCategories();
        if (categories.size() > 0){
            for (Category category: categories){
                Log.i(TAG, category.toString());
            }
        }

        return categories;
    }

}
