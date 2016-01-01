package se.atroshi.funtolearnv2.Game;

/**
 * Created by Farhad on 01/01/16.
 */
public class Category {
    private int categoryId;
    private String categoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Category ID: " + getCategoryId();
        info += " Category Name: " + getCategoryName();
        return info;
    }
}
