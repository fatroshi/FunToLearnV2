package se.atroshi.funtolearnv2.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by Farhad on 01/01/16.
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private String imgPath;
    private Bitmap bitmap;

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imagePath) {
        this.imgPath = imagePath;
    }

    public Bitmap getBitmap() {
        getBitmapResourse();
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmapResourse(){
        // Set bitmap
        File imageFile = new File(this.imgPath);
        if(imageFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            this.setBitmap(bitmap);
            return this.bitmap;
        }
        return null;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Category ID: " + getCategoryId();
        info += " Category Name: " + getCategoryName();
        return info;
    }
}
