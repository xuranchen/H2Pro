package controller;

import com.lynden.gmapsfx.javascript.object.LatLong;
import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Model;
import model.ReportEntry;
import model.User;
import model.WaterReport;

import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Created by Xuran on 10/11/2016.
 */
public class WaterReportController extends ControllerWithUser{
    /**
     * Current date
     */
    private Date currentDate;
    @FXML
    private Button SubmitButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Label DateField;
    @FXML
    private Label TimeField;
    @FXML
    private Label ReportNumberField;
    @FXML
    private Label ReporterField;
    @FXML
    private TextField LocationField;
    @FXML
    private ComboBox WaterTypeField;
    @FXML
    private ComboBox WaterConditionField;

    /**
     * called automatically after load
     */
    @FXML
    public void initialize() {
        try {

        } catch (Exception e) {

        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        currentDate = new Date();
        DateField.setText("Date: " + dateFormat.format(currentDate).substring(0, 10));
        TimeField.setText("Time: " + dateFormat.format(currentDate).substring(10));
        WaterConditionField.setItems(Model.getWaterConditions());
        WaterTypeField.setItems(Model.getWaterTypes());


        ReportNumberField.setText("Report #: " + readReportFromFile().size());


    }

    /**
     * returns to the home page
     */
    @FXML
    public void onCancel() {
        mainApplication.setHomeScreen();
    }
    /**
     * submits the water report
     */
    @FXML
    public void onSubmit() {
        if (isValid()) {
            try {
                ArrayList<WaterReport> reportList = readReportFromFile();
                FileOutputStream fileOut = new FileOutputStream("res/Reports.ser");
                reportList.add(new WaterReport(Integer.parseInt(ReportNumberField.getText().substring(10)),
                        ReporterField.getText().substring(10),
                        WaterReport.Type.getEnumFromName(Model.getWaterTypes()
                                .get(WaterTypeField.getSelectionModel().getSelectedIndex())),
                        WaterReport.Quality.getEnumFromName(Model.getWaterConditions()
                                .get(WaterConditionField.getSelectionModel().getSelectedIndex())),
                        LocationField.getText(),
                        currentDate));
                System.out.println(reportList.size());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(reportList);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("reports.ser cannot be accessed");
            }


            /*
            String[] dataList = {ReportNumberField.getText().substring(10),
                ReporterField.getText().substring(10),
                DateField.getText().substring(6),
                TimeField.getText().substring(6),
                LocationField.getText(),
                Model.getWaterTypes().get(WaterTypeField.getSelectionModel().getSelectedIndex()),
                Model.getWaterConditions().get(WaterConditionField.getSelectionModel().getSelectedIndex())};
            try {
                BufferedWriter numWriter = new BufferedWriter(new FileWriter("res/AppData.dat", false));
                BufferedWriter writer = new BufferedWriter(new FileWriter("res/Reports.dat", true));
                for (String item: dataList) {
                    writer.write(item);
                    writer.write(",");
                }
                writer.write("\n");
                writer.close();
                System.out.println(Integer.parseInt(dataList[0]));
                numWriter.write("Report Number: ");
                numWriter.write(Integer.toString(Integer.parseInt(dataList[0]) + 1));
                numWriter.close();
            } catch(IOException e) {
                System.out.println("Reports not found");
            }*/
            mainApplication.setHomeScreen();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("submission failed");
            alert.setHeaderText("One or more fields are incomplete");
            alert.showAndWait();
        }
    }

    /**
     * checks to see if the form is fully filled
     * and all fields are valid
     * @return true if form si valid, false otherwise
     */
    private boolean isValid() {
        if (LocationField.getText() == null
                || WaterTypeField.getSelectionModel().getSelectedIndex() == -1
                || WaterConditionField.getSelectionModel().getSelectedIndex() == -1) {
            return false;
        }
        return true;
    }

    /**
     * allows access to current user
     * @param user the current user
     */
    public void callback(User user) {
        super.callback(user);
        ReporterField.setText("Reporter: " + user.getFirst_name() + " " + user.getLast_name());
    }

    public static ArrayList<WaterReport> readReportFromFile() {
        ArrayList<WaterReport> returnList = new ArrayList<>();
        ObjectInputStream ois = null;
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("res/Reports.ser");
            ois = new ObjectInputStream(stream);
            returnList = (ArrayList<WaterReport>) ois.readObject();
            ois.close();
        } catch (EOFException e) {
            return returnList;
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
        for (WaterReport item : returnList) {
            System.out.println(item);
        }
        return returnList;
    }




}
