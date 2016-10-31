package controller;

import fxapp.Main;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.User;

/**
 * Created by natha on 9/22/2016.
 */
public class HomeController extends Controller{
    @FXML
    private void logoutFromApp() {
        mainApplication.loginPrompt(mainApplication.getMainScreen());
    }
    @FXML
    private void handleProfile() {
        mainApplication.setProfileScreen();
    }

    @FXML
    private void launchWaterSourceReport() {
        mainApplication.launchWaterSourceReport();
    }

    @FXML
    private void handleViewReports() {
        mainApplication.launchViewWaterReport();
    }

    @FXML
    private void handleWaterAvailability() {
        mainApplication.launchWaterAvailability();
    }
 }
