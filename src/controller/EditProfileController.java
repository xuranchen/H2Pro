package controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import fxapp.Main;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;
import model.User;
import java.io.*;

/**
 * nrabinovich3
 */
public class EditProfileController extends ControllerWithUser{


    @FXML
    private TextField fNameChange;
    @FXML
    private TextField lNameChange;
    @FXML
    private TextField emailChange;
    @FXML
    private TextField cityChange;
    @FXML
    private TextField stateChange;
    @FXML
    private TextField zipChange;
    @FXML
    private TextField streetChange;

    private String[] invalidChars = {"\'", "\"", "&", "\\", ":"};

    /**
     * Initializes EditProfile view with the User in session
     * @param u the user currently in session
     */
    @Override
    public void callback(User u) {
        super.callback(u);
        fNameChange.setText(user.getFirst_name());
        lNameChange.setText(user.getLast_name());
        cityChange.setText(user.getCity());
        stateChange.setText(user.getState());
        zipChange.setText(user.getZipCode());
        streetChange.setText(user.getStreetAddress());
        emailChange.setText(user.getEmail());
    }

    /**
     * Goes back to profile viewing screen
     */
    @FXML
    private void goBack() {
        mainApplication.setProfileScreen();
    }

    /**
     * Edits the profile
     */
    @FXML
    private void handleEditProfile() {
        user.setFirst_name(fNameChange.getText());
        user.setLast_name(lNameChange.getText());
        user.setEmail(emailChange.getText());
        user.setStreetAddress(streetChange.getText());
        user.setCity(cityChange.getText());
        user.setState(stateChange.getText());
        user.setZipCode(zipChange.getText());
        updateRecords();
        mainApplication.setProfileScreen();
    }

    /**
     * Updates userdata given new entries
     */
    private void updateRecords() {
        user.updateUser();
    }

}
