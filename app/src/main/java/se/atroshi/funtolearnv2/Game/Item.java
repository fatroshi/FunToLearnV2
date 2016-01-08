package se.atroshi.funtolearnv2.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Farhad on 25/12/15.
 */
public class Item {

    private final String SITE_URL = "http://fun.neodesign.se/";                     // Link to the server, where all resources are.
    private int categoryId;                                                         // Id of the category
    private String uploadFolder;                                                    //
    private String categoryName;                                                    // Name of the category
    private String imgName;                                                         // Image name
    private String itemName;                                                        // Item name
    private String imgPath;                                                         // Image path

    private int itemId;                                                             // Item id
    private Bitmap bitmap;                                                          // Holder for the image
    private Date date;                                                              // Upload date


    /**
     * Get the id of the category
     * @return id
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Set the category id
     * @param categoryId id
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
     * Set the category name
     * @param categoryName will be set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Get name of the image
     * @return name
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * Set name of the image
     * @param imgName
     */
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    /**
     * Get name of the item
     * @return name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Set name for the item
     * @param itemName will be set as name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Get name for upload folder
     * @return name
     */
    public String getUploadFolder() {
        return uploadFolder;
    }

    /**
     * Set the name for the upload folder
     * @param uploadFolder name that will be set.
     */
    public void setUploadFolder(String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }

    /**
     * Get path to the image on the server
     * @return
     */
    public String getImgLink(){
        return this.uploadFolder + "/" + this.getCategoryId() + "/" + this.imgName;
    }

    /**
     * Set path for the image on the server
     * @param imgLink
     */
    public void setImgPath(String imgLink){
        this.imgPath = imgLink;
    }

    /**
     * Get id
     * @return id of the item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Set id
     * @param itemId that will be set for the item.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Get the image
     * @return Bitmap object
     */
    public Bitmap getBitmap() {
        //getBitmapResource();
        return bitmap;
    }

    /**
     * Set image
     * @param bitmap image that will be set.
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Get image from the internal storage if it exists.
     * @return image if it exist else null
     */
    public Bitmap getBitmapResource(){
        // Set bitmap
        File imageFile = new File(imgPath);
        if(imageFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            this.setBitmap(bitmap);
            return this.bitmap;
        }

        return null;
    }

    /**
     * Get Date when the item was published on the website
     * @return Date object
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set date
     * @param strDate when the object was published.
     */
    public void setDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inputDate = null;
        try {
            inputDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = inputDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Upload folder: " + this.uploadFolder             + "\n");
        sb.append("Category id: "   + this.categoryId               + "\n");
        sb.append("Category name: " + this.categoryName             + "\n");
        sb.append("Image name: "    + this.imgName                  + "\n");
        sb.append("Image link: "    + this.getImgLink()             + "\n");
        sb.append("Item Id: "       + this.itemId                   + "\n");
        sb.append("Item name: "     + this.itemName                 + "\n\n");
        //sb.append("Published: "     + this.getDate().toString()     + "\n\n");

        return sb.toString();
    }
}
