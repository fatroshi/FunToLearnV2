package se.atroshi.funtolearnv2.Game;

/**
 * Created by Farhad on 03/01/16.
 * This class is used for creating the user
 */
public class User {


    private int userId;                             // User id
    private String name;                            // User Name
    private String email;                           // User email
    private boolean downloadUpdates;

    /**
     * Set download updates as true
     */
    public User(){
        this.downloadUpdates = true;
    }

    /**
     * Get the id for the user
     * @return id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set id for the user
     * @param userId that will be set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the name of the user
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name for the user
     * @param name that will be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the email address for the user
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email address for the user
     * @param email address that will be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Check if the user wants to update the application in the future.
     * @return
     */
    public boolean isDownloadUpdates() {
        return downloadUpdates;
    }

    /**
     * Set downloadUpdates
     * @param downloadUpdates true/false
     */
    public void setDownloadUpdates(boolean downloadUpdates) {
        this.downloadUpdates = downloadUpdates;
    }
}
