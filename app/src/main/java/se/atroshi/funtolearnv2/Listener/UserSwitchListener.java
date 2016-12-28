package se.atroshi.funtolearnv2.Listener;

import android.widget.CompoundButton;

import se.atroshi.funtolearnv2.Database.Database;
import se.atroshi.funtolearnv2.Game.User;

/**
 * Created by Farhad on 04/01/16.
 */
public class UserSwitchListener implements CompoundButton.OnCheckedChangeListener {
    private User user;
    public UserSwitchListener(User user){
        this.user = user;
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.user.setDownloadUpdates(isChecked);
        Database.updateUser(this.user);
    }
}
