package se.atroshi.funtolearnv2.SiteConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import se.atroshi.funtolearnv2.Database.Database;
import se.atroshi.funtolearnv2.Game.Item;

/**
 * Created by Farhad on 25/12/15.
 * This class is used for parsing and creating Item objects
 */
public class ItemJSONParser {

    public final static String TAG = "ItemJSONParser";

    /**
     * Get item information from the content, create items for each element and returns a
     * list.
     * @param content JSON data
     * @return  List of Item objects
     */
    public static List<Item> parseFeed(String content){
        JSONArray array = null;
        List<Item> items = new ArrayList<>();

        try {
            array = new JSONArray(content);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Item item = new Item();
                // Parse only if the item does not exist in the database
                if(!Database.itemExists(obj.getInt("itemId"))) {
                    item.setCategoryId(obj.getInt("categoryId"));
                    item.setItemId(obj.getInt("itemId"));
                    item.setUploadFolder(obj.getString("uploadFolder"));
                    item.setCategoryName(obj.getString("categoryName"));
                    item.setImgName(obj.getString("imgName"));
                    item.setItemName(obj.getString("itemName"));
                    item.setDate(obj.getString("published"));
                    //Log.i(tag, "http://fun.neodesign.se/" + item.getImgLink());
                    items.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }
}
