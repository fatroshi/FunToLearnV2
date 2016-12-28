package se.atroshi.funtolearnv2.Listener;

import android.util.Log;
import android.view.View;

import se.atroshi.funtolearnv2.Controllers.Navigation;
import se.atroshi.funtolearnv2.Game.Item;

/**
 * Created by Farhad on 01/01/16.
 */
public class ItemListener implements View.OnClickListener {

    private final String TAG = "ItemListener";

    private Item item;
    public ItemListener(Item item){
        this.item = item;

    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "Item  " + item.getItemName() + " is clicked");

        // Speak when item is clicked
        Navigation.getSpeaker().speak(item.getItemName());
        Navigation.getGame().checkAnswer(item.getItemId());
    }
}
