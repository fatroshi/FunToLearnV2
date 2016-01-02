package se.atroshi.funtolearnv2.Gui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import se.atroshi.funtolearnv2.Game.Category;
import se.atroshi.funtolearnv2.Listener.CategoryListener;
import se.atroshi.funtolearnv2.R;


/**
 * Created by Farhad on 01/01/16.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    private final String TAG = "CategoryAdapter";
    private List<Category> categories;
    private Context context;
    private LruCache<Integer,Bitmap> imageCache;


    public CategoryAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.categories = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.items, parent, false);
        Category category = categories.get(position);

        // Display Text
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(category.getCategoryName());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageBitmap(category.getBitmap());

        imageView.setOnClickListener(new CategoryListener(category));

        Log.i(TAG,"CategoryListener added");

        return view;
    }
}
