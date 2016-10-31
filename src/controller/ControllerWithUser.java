package controller;

import model.User;

/**
 * Created by Xuran on 10/24/2016.
 */
public abstract class ControllerWithUser extends Controller{
    protected User user;
    public void callback(User u) {
        this.user = u;
    }
}
