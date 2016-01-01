package se.atroshi.funtolearnv2.Gui;

import android.graphics.Bitmap;
import android.view.View;

import se.atroshi.funtolearnv2.Game.Item;

/**
 * Created by Farhad on 29/12/15.
 */
public class ItemAndView {
    private Item item;
    private View view;
    private Bitmap bitmap;


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
