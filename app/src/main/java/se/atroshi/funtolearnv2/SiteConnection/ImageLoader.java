package se.atroshi.funtolearnv2.SiteConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;

import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.Gui.ItemAndView;
import se.atroshi.funtolearnv2.R;

/**
 * Created by Farhad on 29/12/15.
 */
public class ImageLoader extends AsyncTask<ItemAndView,Void,ItemAndView> {
    private final String tag = "ImageLoader";

    private LruCache<Integer,Bitmap> imgCache;

    public ImageLoader(LruCache<Integer,Bitmap> imgCache){
        this.imgCache = imgCache;
    }

    /**
     * Get the image and set
     *
     * @param params ItemAndView, Holder for View, Item and bitmap
     * @return ItemAndView
     */
    @Override
    protected ItemAndView doInBackground(ItemAndView... params) {

        ItemAndView container = params[0];
        Item item = container.getItem();

            String imgUrl = "http://fun.neodesign.se/" + item.getImgLink();
            //Log.i(tag, imgUrl);

            InputStream in = null;
            try {
                in = (InputStream) new URL(imgUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                item.setBitmap(bitmap);
                in.close();

                // Store bitmap in container
                container.setBitmap(bitmap);
                return container;
            }catch (Exception e){
                e.printStackTrace();
                Log.i(tag,"We have a problem!!!" + imgUrl);
            }

        return null;
    }

    @Override
    protected void onPostExecute(ItemAndView result) {
        // Display image
        ImageView imageView = (ImageView) result.getView().findViewById(R.id.imageView);
        imageView.setImageBitmap(result.getBitmap());
        //result.getItem().setBitmap(result.getBitmap());
        // Add the bitmap to the imageCache
        // LruCache will manage the memory for us.
        this.imgCache.put(result.getItem().getItemId(), result.getBitmap());
    }
}
