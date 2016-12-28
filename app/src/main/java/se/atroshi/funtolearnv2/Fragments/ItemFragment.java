package se.atroshi.funtolearnv2.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import se.atroshi.funtolearnv2.Controllers.Navigation;
import se.atroshi.funtolearnv2.Database.Database;
import se.atroshi.funtolearnv2.Game.Item;
import se.atroshi.funtolearnv2.Gui.ItemAdapter;
import se.atroshi.funtolearnv2.R;

/**
 * Created by Farhad on 02/01/16.
 *  this class is used for inflating the item layout file and adding a listener
 */
public class ItemFragment extends ListFragment {

    private View rootView;
    public ItemFragment(){

    }

    /**
     *
     * @param inflater xml layout file that will be inflated
     * @param container view group that will contain the fragment, root element of the mainactivity
     * @param savedInstanceState restore appearance of a fragment.
     * @return View object
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView =  inflater.inflate(R.layout.fragment_categories, container, false);

        List<Item> items = Database.getItems(Database.FIND_BY_CATEGORY_ID, Navigation.getSelectedCategoryId());
        ItemAdapter adapter = new ItemAdapter(getActivity(),R.layout.fragment_items,items);
        setListAdapter(adapter);

        return rootView;
    }
}
