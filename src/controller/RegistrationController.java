package controller;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import fxapp.Main;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;
import model.User;

import java.io.*;

/**
 * Created by VarunR on 9/29/16.
 */
public class RegistrationController extends ControllerWithUser{
    /**
     * reference back to mainApplication.
     */
    @FXML
    private TextField register_user;
    @FXML
    private TextField register_password;
    @FXML
    private TextField confirm_password;
    @FXML
    private TextField register_email;
    @FXML
    private ComboBox register_type;
    @FXML
    private TextField register_fname;
    @FXML
    private TextField register_lname;
    @FXML
    private TextField register_address;
    @FXML
    private TextField register_city;
    @FXML
    private TextField register_state;
    @FXML
    private TextField register_zip;
    @FXML
    private Button close_button;

    private String[] invalidChars = {"\'", "\"", "&", "\\", ":"};
    private GeocodingService geocoder;

    private void promptErrorAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    private void promptHelpAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * called by javafx on contruction, sets up view
     */
    @FXML
    public void initialize() {
        register_type.setItems(Model.getAccountTypes());
    }
        /**
         * ?Helper method to facilitate registration
         * @return status code -
         * 0 - successful registration
         * 1 - invalid characters, retry
         * 2 - user exists, retry
         * 3 - registration failed (unknown)
         */
    private int registerUser() {
        String user = register_user.getText();
        String pass = register_password.getText();
        for (String invalidChar : invalidChars) {
            if (user.contains(invalidChar) || pass.contains(invalidChar)) {
                promptErrorAlert("Credentials cannot contain characters:"+ invalidChar
                        , "Contains invalid character!");

                return 1;
            }
        }
        try {
            //check if user exists in system
            if (verifyRegistered(user)) {
                return 2;
            } else {
                FileWriter fw = new FileWriter("res/cred.dat",true);
                BufferedWriter writer = new BufferedWriter(fw);
                writer.write(user + ":" + pass + "\n");
                writer.close();
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            promptErrorAlert("Unable to register user!", "Please try again later!");
            return 3;
        }
    }

    /**
     * checks to see whether user is already registered or not
     * @param aUser User to look up in credentials
     * @return boolean whether registered
     */
    private boolean verifyRegistered(String aUser) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/cred.dat"));
            String currentCredentials;
            while ((currentCredentials = reader.readLine()) != null) {
                String[] userPass = currentCredentials.split(":");
                if (userPass[0].equals(aUser)) {
                    return true;
                }
            }
            reader.close();
        } catch(Exception e) {
            promptErrorAlert("System Error!", "Unable to authenticate user!");
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * handles process of registering user based on textfields
     */
    @FXML
    public void handleRegistration() {
        int comboBoxChoice = register_type.getSelectionModel().getSelectedIndex();
        //verify filled (type, First name, Last name, email)
        if (!formValid()) {
            return;
        } else if (comboBoxChoice == -1) {
            promptErrorAlert("Select Type", "Please select a user type");
            return;
        }
        // handle credential addition
        int errorCode = registerUser();

        //need work on this error switch
        switch (errorCode) {
            //successful registration
            case 0: promptHelpAlert("Success", "Successfully Registered!");
                    closeWindow();
                    break;
            case 1: clearFields();
                    break;
            case 2: promptErrorAlert("User already registered", "Sign in or choose different username.");
                    return;
            case 3: return;
        }
        //handle profile userdata population

        User newUser = User.constructUser(
                register_user.getText(),
                register_email.getText(),
                register_fname.getText(),
                register_lname.getText(),
                register_address.getText(),
                register_city.getText(),
                register_state.getText(),
                register_zip.getText(),
                register_type.getSelectionModel().getSelectedItem().toString());

        newUser.register();
        mainApplication.setUser(newUser);

    }

    /**
     * Checks to see whether textfields in form are filled
     * implement more robust verification in the future
     * @return
     */
    private boolean formValid() {
        TextField[] form_meta = {register_user, register_email, register_fname, register_lname, register_password, confirm_password,
            register_address, register_city, register_state, register_zip};
        for (TextField t : form_meta) {
            if (t ==  null || t.getText().equals("")) {
                promptErrorAlert("Incomplete form", "Please fill required fields!");
                return false;
            }
        }
        if (!register_password.getText().equals(confirm_password.getText())) {
            return false;
        }
        return true;
    }

    /**
     * Clears the form's fields (used in event of invalid entry)
     */
    private void clearFields() {
        TextField[] form_meta = {register_user, register_email, register_fname, register_lname, register_password, confirm_password,
                register_address, register_city, register_state, register_zip};
        for (TextField t : form_meta) {
            t.clear();
        }
    }

    /**
     * closes the registration window
     */
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    /**
     * Helper method to append string to file
     * @param location file location (relative path from root)
     * @param data string being entered into file
     * @return
     */
    private static boolean appendToFile(String location, String data) {
        try {
            FileWriter fw = new FileWriter(location, true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(data + "\n");
            writer.close();
            return true;
        } catch (Exception e) {
            //do stuff

            System.out.println(e.getMessage());
            return false;
        }
    }

}
