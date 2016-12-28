package se.atroshi.funtolearnv2.Listener;

import android.util.Log;
import android.view.View;

import se.atroshi.funtolearnv2.Controllers.Navigation;
import se.atroshi.funtolearnv2.Game.Category;

/**
 * Created by Farhad on 01/01/16.
 */
public class CategoryListener implements View.OnClickListener {

    private final String TAG = "CategoryListener";

    private Category category;
    public CategoryListener(Category category){
        this.category = category;
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "Category  " + category.getCategoryName() + " is clicked");
        Navigation.setSelectedCategoryId(category.getCategoryId());
        Navigation.setSelectedCategoryName(category.getCategoryName());
        Navigation.showItemsForWordsPlay();
    }
}
