package se.atroshi.funtolearnv2.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import se.atroshi.funtolearnv2.Controllers.Navigation;
import se.atroshi.funtolearnv2.Database.Database;

/**
 * Created by Farhad on 03/01/16.
 */
public class Game {

    private User user;
    private List<Item> items;
    private List<Integer> indexes;
    private String QUESTION_PHRASE = "Where is the ";
    private Item item;

    public Game(){
        user = new User();
        user.setName("Your Name...");
        user.setEmail("yourEmail@example.com");
        user.setDownloadUpdates(true);

        this.indexes = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public void play(){
        if(Navigation.getSelectedCategoryName() != null){
            // Category selected
            this.indexes.clear();
            this.items = Database.getItems(Database.FIND_BY_CATEGORY_ID, Navigation.getSelectedCategoryId());
            // Tell player to find item
            this.findIndex();
        }
    }

    public void checkAnswer(int itemId){
        if(itemId == this.item.getItemId()){
            // Clicked on the correct item
            Navigation.getSpeaker().speak("Bravo that was correct");

            // Check if all items are found by the user
            if(allFound()){
                // User has found all items
                Navigation.getSpeaker().speak("You have found all " + Navigation.getSelectedCategoryName() + "s, well done");
                this.indexes.clear();
            }else {
                // Tell to find the next item
                findIndex();
            }
        }
    }

    private boolean allFound(){
        boolean allFound = false;
        if(this.indexes.size() == this.items.size()){
            allFound = true;
        }
        return  allFound;
    }

    private void findIndex(){
        int index = randInt(0,this.items.size()-1);
        // Check if we have used this index before
        while(inArray(index,indexes)){
            index = randInt(0,this.items.size()-1);
        }
        // Add, so this will not be asked for again in the game
        indexes.add(index);

        Navigation.getSpeaker().speak("Find the " + this.items.get(index).getItemName());
        this.item = this.items.get(index);

    }

    public boolean inArray(Integer integer, List<Integer> list){
        boolean exists = false;

        for(Integer index: list){
            if(index == integer){
                exists = true;
                break;
            }
        }
        return exists;
    }

    public int randInt(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Integer> correctAnswer) {
        this.indexes = correctAnswer;
    }

    public String getQUESTION_PHRASE() {
        return QUESTION_PHRASE;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
