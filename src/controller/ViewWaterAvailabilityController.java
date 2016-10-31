package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import model.WaterReport;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Xuran on 10/19/2016.
 */
public class ViewWaterAvailabilityController extends ControllerWithUser implements Initializable, MapComponentInitializedListener {
    @FXML
    private Button backButton;
    @FXML
    private TextField updateLocationText;
    @FXML
    private GoogleMapView googleMapView;
    private GoogleMap map;
    private GeocodingService geocoder;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        googleMapView.addMapInializedListener(this);
    }
    @Override
    public void mapInitialized() {
        user = mainApplication.getUser();
        String UserAddress = user.getStreetAddress() + " " + user.getCity() + ", " + user.getState();
        new GeocodingService().geocode(UserAddress , (GeocodingResult[] geocodingResults, GeocoderStatus geocoderStatus) -> {
            System.out.println(geocoderStatus);
            user.setLon(geocodingResults[0].getGeometry().getLocation().getLongitude());
            user.setLat(geocodingResults[0].getGeometry().getLocation().getLatitude());
            restOfInitialize();
        });


    }

    /**
     * called after geocode request, creates markers
     */
    private void restOfInitialize() {
        MapOptions options = new MapOptions();
        LatLong center = new LatLong(user.getLat(), user.getLon());
        options.center(center)
                .zoom(15)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);


        map = googleMapView.createMap(options);
        for (WaterReport w : WaterReportController.readReportFromFile()) {
            double[] pos = new double[2];
            new GeocodingService().geocode(w.getLocation(), (GeocodingResult[] geocodingResults, GeocoderStatus geocoderStatus) -> {
                pos[1] = (geocodingResults[0].getGeometry().getLocation().getLongitude());
                pos[0] = (geocodingResults[0].getGeometry().getLocation().getLatitude());
                System.out.println(pos[0] + pos[1]);
                MarkerOptions markerOptions = new MarkerOptions();
                LatLong loc = new LatLong(pos[0], pos[1]);
                markerOptions.position(loc)
                        .visible(Boolean.TRUE);
                Marker marker = new Marker(markerOptions);
                map.addUIEventHandler(marker,
                        UIEventType.click,
                        (JSObject obj) -> {
                            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                            infoWindowOptions.content("Type: " + w.getType().getName() + "<br>Quality: " + w.getQuality().getName());

                            InfoWindow window = new InfoWindow(infoWindowOptions);
                            window.open(map, marker);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    window.close();
                                }
                            }, 10000);
                        });

                map.addMarker(marker);
            });
        }
        System.out.println("reach");

    }

    /**
     * called when back button is pressed
     * returns to home screen
     */
    @FXML
    private void onBackCall() {
        mainApplication.setHomeScreen();
    }

    /**
     * called when update button is pressed
     * updates center of map to location entered
     */
    @FXML
    private void onUpdate() {
        geocoder = new GeocodingService();

        geocoder.geocode(updateLocationText.getText(), (GeocodingResult[] geocodingResults, GeocoderStatus geocoderStatus) -> {
            map.setCenter(geocodingResults[0].getGeometry().getLocation());

        });

    }

}
