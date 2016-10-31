package controller;

import fxapp.Main;

/**
 * Created by Xuran on 10/24/2016.
 */
public abstract class Controller {
    protected Main mainApplication;
    /**
     * Sets the main application to enable access both ways
     * @param main the main application
     */
    public void setMainApp(Main main) {
        mainApplication = main;
    }
}
