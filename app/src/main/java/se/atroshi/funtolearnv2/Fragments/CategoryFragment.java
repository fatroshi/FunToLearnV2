package se.atroshi.funtolearnv2.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.atroshi.funtolearnv2.Database.Database;
import se.atroshi.funtolearnv2.Gui.CategoryAdapter;
import se.atroshi.funtolearnv2.R;

/**
 * Created by Farhad on 02/01/16.
 * this class is used for inflating the category layout file and adding a listener
 */
public class CategoryFragment extends ListFragment {

    private ViewGroup rootView;
    public CategoryFragment(){

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
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_categories, container, false);

        CategoryAdapter adapter = new CategoryAdapter(getActivity(),R.layout.items, Database.getAllCategories());
        setListAdapter(adapter);

        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
