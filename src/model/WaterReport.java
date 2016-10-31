package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xuran on 10/7/2016.
 */
public class WaterReport implements Serializable {
    private int reportNumber;
    private String user;
    private Type type;
    private Quality quality;
    private String location;
    private Date dateOfReport;

    private static final long serialVersionUID = 7408947847435218863L;

    /**
     * creates a water report from give parameters
     */
    public WaterReport(int reportNumber, String user, Type type,
                       Quality quality, String location, Date dateOfReport) {
        this.reportNumber = reportNumber;
        this.user = user;
        this.type = type;
        this.quality = quality;
        this.location = location;
        this.dateOfReport = dateOfReport;
    }

    /*
    getters and setters
     */
    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateOfReport() {
        return dateOfReport;
    }

    public void setDateOfReport(Date dateOfReport) {
        this.dateOfReport = dateOfReport;
    }

    public enum Type {
        BOTTLED("Bottled"),
        WELL("Well"),
        STREAM("Stream"),
        LAKE("Lake"),
        SPRING("Spring"),
        OTHER("Other");

        private String name;

        private Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Type getEnumFromName(String name) {
            for (Type item : Type.values()) {
                if (name == item.getName()) {
                    return item;
                }
            }
            return null;
        }
    }

    public enum Quality {
        WASTE("Waste"),
        TREATABLE_CLEAR("Treatable-Clear"),
        TREATABLE_MUDDY("Treatable-Muddy"),
        POTABLE("Potable");

        private String name;

        private Quality(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Quality getEnumFromName(String name) {
            for (Quality item : Quality.values()) {
                if (name.equals(item.getName())) {
                    return item;
                }
            }
            return null;
        }
    }

    public String toString() {
        String retString = "";
        retString += reportNumber+", ";
        retString += user+", ";
        retString += type+", ";
        retString += quality+", ";
        retString += location+", ";
        retString += dateOfReport;
        return retString;
    }
}
