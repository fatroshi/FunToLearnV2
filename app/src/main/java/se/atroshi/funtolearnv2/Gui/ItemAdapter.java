package se.atroshi.funtolearnv2.Gui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.R;
import se.atroshi.funtolearnv2.SiteConnection.ImageLoader;

/**
 * Created by Farhad on 26/12/15.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private final String tag  = "ItemAdapter";
    private Context context;
    private List<Item> items;
    private LruCache<Integer,Bitmap> imageCache;

    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

        this.context = context;
        this.items = objects;

        // From android doc
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()) / 1024;   // Total memory
        final int cacheSize = maxMemory / 8;                                    // Amount of memory that we want to use of the available memory
        this.imageCache = new LruCache<>(cacheSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.items, parent, false);
        Item item = items.get(position);

        // Display Text
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(item.getItemName() + "\nPublished:" + item.getDate().toString());
        /*
        If we have a bitmap in memory display it else download the bitmap.
         */
        Bitmap bitmap = imageCache.get(item.getItemId());
        if(bitmap != null){
            // Display image
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageBitmap(item.getBitmap());
        }else{
            ItemAndView container = new ItemAndView();
            container.setItem(item);
            container.setView(view);

            // AsyncTask for downloading
            ImageLoader loader = new ImageLoader(this.imageCache);
            loader.execute(container);
        }
        return view;
    }
}
