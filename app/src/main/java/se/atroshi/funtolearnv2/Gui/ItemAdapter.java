package se.atroshi.funtolearnv2.Gui;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.Listener.ItemListener;
import se.atroshi.funtolearnv2.R;

/**
 * Created by Farhad on 01/01/16.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    private final String TAG = "ItemAdapter";
    private List<Item> items;
    private Context context;

    private Item firstItem,secondItem;

    private int counter = 0;

    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.context = context;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_items, parent, false);



        // If its odd
        this.counter = position;
        if(position%2 == 0) {
            if (this.counter < this.items.size() - 1) {
                // First
                firstItem = items.get(this.counter);
                ImageView firstImage = (ImageView) view.findViewById(R.id.firstImage);
                firstImage.setImageBitmap(firstItem.getBitmapResource());

                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;

                firstImage.getLayoutParams().width = width / 2;
                firstImage.requestLayout();

                ItemListener firstListener = new ItemListener(firstItem);
                firstImage.setOnClickListener(firstListener);


                // Second
                secondItem = items.get(this.counter + 1);
                ImageView secondImage = (ImageView) view.findViewById(R.id.secondImage);
                secondImage.setImageBitmap(secondItem.getBitmapResource());
                secondImage.getLayoutParams().width = width/2;
                secondImage.requestLayout();

                ItemListener secondListener = new ItemListener(secondItem);
                secondImage.setOnClickListener(secondListener);

                //Log.i(TAG, "Both img info " + firsItem.getItemName() + " " + secondItem.getItemName());
            }
        }

        // Display Text
        //TextView textView = (TextView) view.findViewById(R.id.textView);
        //textView.setText(item.getCategoryName());



        //imageView.setOnClickListener(new CategoryListener(item));

        return view;
    }



}
