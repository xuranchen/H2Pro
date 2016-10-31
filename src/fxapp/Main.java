package fxapp;
import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import model.WaterReport;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by natha on 9/22/2016.
 */
public class Main extends Application{
    /**  the java logger for this class */
    private static final Logger LOGGER =Logger.getLogger("MainFXApplication");

    /** the main container for the application window */
    private Stage mainScreen;

    /** the main layout for the main window */
    private BorderPane rootLayout;

    private User user;

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * return a reference to the main window stage
     * @return reference to main stage
     * */
    public Stage getMainScreen() { return mainScreen;}


    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        loginPrompt(mainScreen);
    }
    public void loginPrompt(Stage mainScreen) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Login.fxml"));
            rootLayout = loader.load();

            // Give the controller access to the main app.
            LoginController controller =  loader.getController();
            controller.setMainApp(this);

            // Set the Main App title
            mainScreen.setTitle("H2Pro");

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainScreen.setScene(scene);
            mainScreen.show();


        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for LoginScreen!!");
            e.printStackTrace();
        }
    }
    public void setHomeScreen() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Home.fxml"));
            rootLayout = loader.load();

            // Give the controller access to the main app.
            HomeController controller =  loader.getController();
            controller.setMainApp(this);

            // Set the Main App title
            mainScreen.setTitle("H2Pro");

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainScreen.setScene(scene);
            mainScreen.show();

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for HomeScreen!!");
            e.printStackTrace();
        }
    }

    public void setRegistrationScreen() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Registration.fxml"));
            GridPane root = loader.load();

            // Give the controller access to the main app.
            RegistrationController controller =  loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout.
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Registration Screen!!");
            e.printStackTrace();
        }

    }

    public void setProfileScreen() {

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Profile.fxml"));
            BorderPane root = loader.load();
            // Give the controller access to the main app.
            ProfileController controller =  loader.getController();
            controller.setMainApp(this);
            controller.callback(user);
            // Show the scene containing the root layout.

            mainScreen.setScene(new Scene(root));
            mainScreen.show();

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Profile Screen!!");
            e.printStackTrace();
        }

    }

    public void launchEditProfileController(User u) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Edit-Profile.fxml"));
            GridPane root = loader.load();
            // Give the controller access to the main app.
            EditProfileController controller =  loader.getController();
            controller.setMainApp(this);
            controller.callback(user);
            // Show the scene containing the root layout.
            mainScreen.setScene(new Scene(root));
            mainScreen.show();

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Edit Profile Screen!!");
            e.printStackTrace();
        }
    }

    public void launchWaterSourceReport() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Water-Source-Report.fxml"));
            GridPane root = loader.load();
            // Give the controller access to the main app.
            WaterReportController controller =  loader.getController();
            controller.setMainApp(this);
            controller.callback(user);
            // Show the scene containing the root layout.
            mainScreen.setScene(new Scene(root));
            mainScreen.show();
        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Edit Water Submission Screen!!");
            e.printStackTrace();
        }
    }
    public void launchViewWaterReport() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/View-Water-Report.fxml"));
            BorderPane root = loader.load();
            // Give the controller access to the main app.
            ViewWaterReportController controller =  loader.getController();
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            mainScreen.setScene(new Scene(root));
            mainScreen.show();
        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for View Water Submission Screen!!");
            e.printStackTrace();
        }
    }
    public void launchWaterAvailability() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Water-Availability-Report.fxml"));
            GridPane root = loader.load();
            // Give the controller access to the main app.
            ViewWaterAvailabilityController controller =  loader.getController();
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            mainScreen.setScene(new Scene(root));
            mainScreen.show();
        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for View Water Availability Screen!!");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}