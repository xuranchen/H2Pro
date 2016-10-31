package controller;
import javafx.fxml.FXML;
import fxapp.Main;
import javafx.scene.text.Text;
import model.User;

/**
 * Created by VarunR on 10/2/16.
 */
public class ProfileController extends ControllerWithUser{
    @FXML
    private Text name_label;
    @FXML
    private Text email_label;
    @FXML
    private Text user_label;
    @FXML
    private Text address_label;

    /**
     * Initializes Profile view with the User in session
     * @param user the user currently in session
     */
    @Override
    public void callback(User user) {
        super.callback(user);
        name_label.setText(user.getLast_name() +", "+ user.getFirst_name());
        email_label.setText(user.getEmail());
        user_label.setText(user.getUsername());
        address_label.setText(user.getStreetAddress() + " " + user.getCity()
                + ", " + user.getState());
    }

    /**
     * launches edit profile window
     */
    @FXML
    public void handleEditProfile() {
        mainApplication.launchEditProfileController(user);
    }

    /**
     * goes back to application home
     */
    @FXML
    public void goBack() {
        mainApplication.setHomeScreen();
    }

}
