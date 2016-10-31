package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by natha on 9/22/2016.
 */
public class Model implements Serializable {

    public static ObservableList<String> getAccountTypes() {
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("User");
        typeList.add("Admin");
        typeList.add("Worker");
        typeList.add("Manager");

        return FXCollections.observableArrayList(typeList);
    }
    public static ObservableList<String> getWaterTypes() {
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Bottled");
        typeList.add("Well");
        typeList.add("Stream");
        typeList.add("Lake");
        typeList.add("Spring");
        typeList.add("Other");

        return FXCollections.observableArrayList(typeList);
    }
    public static ObservableList<String> getWaterConditions() {
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Waste");
        typeList.add("Treatable-Clear");
        typeList.add("Treatable-Muddy");
        typeList.add("Potable");

        return FXCollections.observableArrayList(typeList);
    }
}
