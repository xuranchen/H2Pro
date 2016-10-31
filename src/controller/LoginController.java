package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import fxapp.Main;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.*;

/**
 * Created by natha on 9/22/2016.
 */
public class LoginController extends Controller{

    @FXML
    private TextField user_field;
    @FXML
    private TextField pass_field;

    // Refer to cred.dat for valid credentials!
    // example user: "varun" pass: "apples"

    /**
     * Attempt login
     */
    @FXML
    private void handleLogin() {
        if (User.verifyUser(user_field.getText(), pass_field.getText())) {
            //Successful login
            User user = User.findUser(user_field.getText());
            mainApplication.setUser(user);
            //System.out.println(user);
            mainApplication.setHomeScreen();

        } else {
            //Rejected login
            promptErrorAlert("Incorrect Login Information", "Please try again!");
            user_field.clear();
            pass_field.clear();
        }
    }


    /**
     * helper method to facilitate prompting error message
     * @param title text of error title
     * @param header text of error header
     */
    private void promptErrorAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * Goes to registration screen
     */
    @FXML
    public void register() {
        mainApplication.setRegistrationScreen();
    }
}