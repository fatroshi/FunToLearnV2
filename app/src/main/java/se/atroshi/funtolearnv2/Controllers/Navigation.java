package se.atroshi.funtolearnv2.Controllers;

import android.app.FragmentManager;

import se.atroshi.funtolearnv2.Fragments.CategoryFragment;
import se.atroshi.funtolearnv2.Fragments.ItemFragment;
import se.atroshi.funtolearnv2.Fragments.MathFragment;
import se.atroshi.funtolearnv2.Fragments.ProgressFragment;
import se.atroshi.funtolearnv2.Fragments.StartFragment;
import se.atroshi.funtolearnv2.Fragments.UserFragment;
import se.atroshi.funtolearnv2.Game.Game;
import se.atroshi.funtolearnv2.Game.Speaker;
import se.atroshi.funtolearnv2.MainActivity;
import se.atroshi.funtolearnv2.R;

/**
 * Created by Farhad on 02/01/16.
 * This class is used for navigation between different fragments, works as a controller for Game,
 * and Speaker class.
 *
 * This class has the purpose of a single tone class, in this case the MainActivity class.
 */
public class Navigation {

    public static String TAG = "Navigation";

    private static MainActivity mainActivity;                    // Main thread
    private static int selectedCategoryId;                       // holder for current selected category (ID)
    private static String selectedCategory;                      // holder for current selected category (NAME)
    private static FragmentManager fragmentManager;              // Used for changing fragment layouts
    private static Speaker speaker;                              // Used for activation the speaker and speech
    private static Game game;                                   // Game logic

    public Navigation(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        // Fragment Manager
        fragmentManager = this.mainActivity.getFragmentManager();

        // Init speaker
        speaker = new Speaker(mainActivity) ;

        // So that we can change the title
        mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(true); //optional

        // Game
        this.game = new Game();
    }

    /**
     * Get the main thread
     * @return MainActivity object
     */
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    /**
     * Set main thread
     * @param mainActivity the main thread object
     */
    public static void setMainActivity(MainActivity mainActivity) {
        Navigation.mainActivity = mainActivity;
    }

    /**
     * Returns the id of the selected category
     * @return id
     */
    public static int getSelectedCategoryId() {
        return selectedCategoryId;
    }

    /**
     * Set id for the selected category
     * @param selectedCategoryId
     */
    public static void setSelectedCategoryId(int selectedCategoryId) {
        Navigation.selectedCategoryId = selectedCategoryId;
    }

    /**
     * Get name of the selected category
     * @return name
     */
    public static String getSelectedCategoryName() {
        return selectedCategory;
    }

    /**
     * Set name for the selected category
     * @param selectedCategory
     */
    public static void setSelectedCategoryName(String selectedCategory) {
        Navigation.selectedCategory = selectedCategory;
    }

    /**
     * Show the start fragment/page of the app
     */
    public static void showStartView(){
        fragmentManager.beginTransaction().replace(R.id.myContainer, new StartFragment()).commit();
        setActionBarTitle("FunToLearn");

        setSelectedCategoryName(null);
    }

    /**
     * Show categories for the "Learn new words" fragment/page
     */
    public static void showWordsPlayView(){
        fragmentManager.beginTransaction().replace(R.id.myContainer, new CategoryFragment()).commit();
        setActionBarTitle("Learn new words");
        setSelectedCategoryName(null);
    }

    /**
     * Show the user settings page
     */
    public static void showUserInfoView(){
        fragmentManager.beginTransaction().replace(R.id.myContainer, new UserFragment()).commit();
        setActionBarTitle("User Settings");
        setSelectedCategoryName(null);
    }

    /**
     * Show fragment/page of the items in the selected category
     */
    public static void showItemsForWordsPlay(){
        fragmentManager.beginTransaction().replace(R.id.myContainer, new ItemFragment()).commit();
        setActionBarTitle(selectedCategory);
        game.play();
    }

    /**
     * Show fragment/page for the mathematics.
     */
    public static void showMathView(){
        fragmentManager.beginTransaction().replace(R.id.myContainer, new MathFragment()).commit();
        setActionBarTitle("Learn Mathematics");
        setSelectedCategoryName(null);
    }

    /**
     * Show progressbar
     */
    public static void showProgressView(){
        fragmentManager.beginTransaction().replace(R.id.myContainer, new ProgressFragment()).commit();
        setActionBarTitle("Achievements");
        setSelectedCategoryName(null);
    }

    /**
     * Change the title for the action bar
     * @param title
     */
    private static void setActionBarTitle(String title){
        mainActivity.getSupportActionBar().setTitle(title);
    }

    /**
     * Get the speaker
     * @return speaker object
     */
    public static Speaker getSpeaker() {
        return speaker;
    }

    /**
     * Set speaker
     * @param speaker speaker
     */
    public static void setSpeaker(Speaker speaker) {
        Navigation.speaker = speaker;
    }

    /**
     * Get current game
     * @return
     */
    public static Game getGame() {
        return game;
    }

    /**
     * Set game
     * @param game object
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
