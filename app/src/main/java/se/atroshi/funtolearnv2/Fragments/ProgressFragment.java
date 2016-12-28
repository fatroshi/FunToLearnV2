package se.atroshi.funtolearnv2.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.atroshi.funtolearnv2.R;

/**
 * Created by Farhad on 02/01/16.
 * this class is used for inflating the progress layout file
 */
public class ProgressFragment extends Fragment {

    private View rootView;
    public ProgressFragment(){

    }
    /**
     *
     * @param inflater xml layout file that will be inflated
     * @param container view group that will contain the fragment, root element of the mainactivity
     * @param savedInstanceState restore appearance of a fragment.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_progress, container, false);

        return rootView;
    }
}
