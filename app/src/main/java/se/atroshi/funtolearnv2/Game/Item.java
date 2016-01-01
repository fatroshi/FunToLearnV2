package se.atroshi.funtolearnv2.Game;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Farhad on 25/12/15.
 */
public class Item {

    private final String SITE_URL = "http://fun.neodesign.se/";
    private int categoryId;
    private String uploadFolder;
    private String categoryName;
    private String imgName;
    private String itemName;
    private String imgPath;

    private int itemId;
    private Bitmap bitmap;
    private Date date;

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

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUploadFolder() {
        return uploadFolder;
    }

    public void setUploadFolder(String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }

    public String getImgLink(){
        return this.uploadFolder + "/" + this.getCategoryId() + "/" + this.imgName;
    }

    public void setImgPath(String imgLink){
        this.imgPath = imgLink;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Date getDate() {
        return date;
    }

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
