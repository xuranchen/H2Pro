package model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReportEntry {
    private SimpleIntegerProperty reportNumber = new SimpleIntegerProperty();
    private SimpleStringProperty user = new SimpleStringProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty location = new SimpleStringProperty();

    public ReportEntry() {
    }

    public static ReportEntry WaterReportToEntry(WaterReport wr) {
        System.out.println(wr);
        ReportEntry re = new ReportEntry();
        re.reportNumber.set(wr.getReportNumber());
        re.user.set(wr.getUser());

        re.date.set(formatDate(wr.getDateOfReport().toString())[0]);

        re.time.set(formatDate(wr.getDateOfReport().toString())[1]);
        re.location.set(wr.getLocation());
        return re;
    }
    //plz excuse my bootleg
    private static String[] formatDate(String aDate) {
        String[] splitDate = aDate.split(" ");
        String datePortion = "";
        String timePortion = "";
        for (int i = 0; i < splitDate.length; i++) {
            if (i < 3 || i == splitDate.length - 1) {
                datePortion += splitDate[i] + " ";
            } else {
                timePortion += splitDate[i] + " ";
            }
        }
        String[] ret = {datePortion, timePortion};
        return ret;
    }

    /**
     * getters and setters for the Entry Object
     *
     */

    public int getReportNumber() {
        return reportNumber.get();
    }

    public SimpleIntegerProperty reportNumberProperty() {
        return reportNumber;
    }

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    /**
     * format report toString with necessary elements
     * @return a formatted string
     */
    public String toString() {
        String str = "";
        str += reportNumber.get() + ", ";
        str += user.get() + ", ";
        str += time.get() + ", ";
        str += date.get() + ", ";
        str += location.get();
        return str;
    }
}