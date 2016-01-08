package se.atroshi.funtolearnv2.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by Farhad on 01/01/16.
 * This class is used for creating categories.
 */
public class Category {
    private int categoryId;                                     // Id of the category
    private String categoryName;                                // Name of the cateogry
    private String imgPath;                                     // Path to an image
    private Bitmap bitmap;                                      // Image


    /**
     * Get the id
     * @return id of the category
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Set id
     * @param categoryId the id that will be set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get the category name
     * @return name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set category name
     * @param categoryName the name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Get image path
     * @return path to the image
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Set path to the image
     * @param imagePath the path
     */
    public void setImgPath(String imagePath) {
        this.imgPath = imagePath;
    }

    /**
     * Get the bitmap (image)
     * @return image
     */
    public Bitmap getBitmap() {
        getBitmapResourse();
        return bitmap;
    }

    /**
     * Set the bitmap(image)
     * @param bitmap image
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Get the saved image from internal storage
     * @return bitmap object (image)
     */
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

    /**
     * Display info about this object
     * @return
     */
    @Override
    public String toString() {
        String info = "";
        info += "Category ID: " + getCategoryId();
        info += " Category Name: " + getCategoryName();
        return info;
    }
}
