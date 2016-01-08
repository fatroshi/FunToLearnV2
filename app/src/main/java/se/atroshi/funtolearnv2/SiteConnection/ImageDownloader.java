package se.atroshi.funtolearnv2.SiteConnection;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.MainActivity;

/**
 * Created by Farhad on 31/12/15.
 * This class is used for download the images and storing to the internal storage
 */
public class ImageDownloader {

    static final String TAG = "ImageDownloader";

    /**
     * Download the image
     * @param imgUrl path to the image
     * @return Bitmap object
     */
    static Bitmap download(String imgUrl) {
        InputStream in = null;
        try {
            in = (InputStream) new URL(imgUrl).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            in.close();
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
            Log.i(TAG, "We have a problem!!!" + imgUrl);
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Stores the image to the internal storage
     * @param mainActivity main thread
     * @param item object that contains image information.
     * @return
     */
    public static String saveToInternalStorage(MainActivity mainActivity, Item item){
        ContextWrapper cw = new ContextWrapper(mainActivity.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(item.getCategoryName(), Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,item.getImgName());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            item.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
