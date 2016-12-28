package se.atroshi.funtolearnv2.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import se.atroshi.funtolearnv2.Database.Database;
import se.atroshi.funtolearnv2.Game.User;
import se.atroshi.funtolearnv2.Listener.UserEmailListener;
import se.atroshi.funtolearnv2.Listener.UserNameListener;
import se.atroshi.funtolearnv2.Listener.UserSwitchListener;
import se.atroshi.funtolearnv2.R;

/**
 * Created by Farhad on 02/01/16.
 * this class is used for inflating the user settings layout file and adding multiple listeners
 */
public class UserFragment extends Fragment {

    private final String TAG = "UserFragment";

    private EditText name,email;            // Used so that user can enter name and email
    private Switch switchUpdate;            // Used for letting user to decide if future updates are acceptable.
    private View rootView;
    public UserFragment(){

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
        this.rootView = inflater.inflate(R.layout.fragment_userinfo, container, false);

        User user = Database.getUser();
        Log.i(TAG, "User id in database " + user.getUserId());

        this.name = (EditText) rootView.findViewById(R.id.editTextName);
        this.email = (EditText) rootView.findViewById(R.id.editTextEmail);
        this.switchUpdate = (Switch) rootView.findViewById(R.id.switchUpdate);

        this.name.setText(user.getName());
        this.email.setText(user.getEmail());
        this.switchUpdate.setChecked(user.isDownloadUpdates());

        this.name.addTextChangedListener(new UserNameListener(user));
        this.email.addTextChangedListener(new UserEmailListener(user));
        this.switchUpdate.setOnCheckedChangeListener(new UserSwitchListener(user));
        return rootView;
    }
}
