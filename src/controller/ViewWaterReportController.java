package controller;

import fxapp.Main;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ReportEntry;
import model.User;
import model.WaterReport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by VarunR on 10/16/16.
 */
public class ViewWaterReportController extends Controller{

    @FXML
    private TableView<ReportEntry> tableView;
    @FXML
    private TableColumn tableDate;
    @FXML
    private TableColumn tableLocation;

    @FXML
    private TableColumn tableReportID;
    @FXML
    private TableColumn tableTime;
    @FXML
    private TableColumn tableUser;

    @FXML
    public void initialize() {
        tableView.getItems().addAll(readReportFromFile());

        tableDate.setCellValueFactory(
                new PropertyValueFactory("date")
        );
        tableLocation.setCellValueFactory(
                new PropertyValueFactory("location")
        );
        tableReportID.setCellValueFactory(
                new PropertyValueFactory("reportNumber")
        );
        tableTime.setCellValueFactory(
                new PropertyValueFactory("time")
        );
        tableUser.setCellValueFactory(
                new PropertyValueFactory("user")
        );

    }


    private ObservableList<ReportEntry> readReportFromFile() {
        ArrayList<WaterReport> list = new ArrayList<>();
        ObjectInputStream ois = null;
        FileInputStream stream = null;
        try  {
            stream = new FileInputStream("res/Reports.ser");
            ois = new ObjectInputStream(stream);
            list = (ArrayList<WaterReport>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<ReportEntry> returnList = new ArrayList<>();
        for (WaterReport item : list) {
            if (item != null) {
                returnList.add(ReportEntry.WaterReportToEntry(item));
            }
        }
        for (ReportEntry item : returnList) {
            if (item!= null) {
                System.out.println(item);
            }
        }
        return FXCollections.observableList(returnList);
    }

    /**
     * Goes back to main screen
     */
    @FXML
    private void goBack() {
        mainApplication.setHomeScreen();
    }

}


