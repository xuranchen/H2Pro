package model;


import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lynden.gmapsfx.javascript.object.LatLong;


/**
 * Created by VarunR on 10/1/16.
 * Should contain information that would go on profile
 */
public class User implements Serializable{
    private String email;
    private String first_name;
    private String last_name;
    private String username;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String type;
    private Double lat;
    private Double lon;


    protected static final long serialVersionUID = 22L;

    /**
     * setter for username
     * @param username the passed in username to set
     */
    public void setUsername(String username) {
        this.username = username;

    }

    /**
     * setter for street address
     * @param streetAddress the passed in street address to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * setter for City of residence
     * @param city passed in city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * setter for State of residence
     * @param state the passed in state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * setter for zipcode of residence
     * @param zipCode passed in zipcode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * setter for type (manager, worker etc)
     * @param type the passed in type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for street address
     * @return street address associated with that user
     */
    public String getStreetAddress() {

        return streetAddress;
    }

    /**
     * getter for City
     * @return the city associated with that user
     */
    public String getCity() {
        return city;
    }

    /**
     * getter for State
     * @return the state associated with that user
     */
    public String getState() {
        return state;
    }

    /**
     * getter for Zipcode
     * @return the zipcode associated with that user
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * getter for level of authorization
     * @return the authorization level of the user
     */
    public String getType() {
        return type;
    }

    public User() {

    }

    /**
     * initialize a new user with only username
     * @param username the requested username
     */
    public User(String username) {
        this(username, null, null, null);
    }


    /**
     * create a new user with username, email, and full name
     * @param username requested username
     * @param email new registration user's email address
     * @param first_name new registration user's first name
     * @param last_name new registration user's last name
     */
    public User(String username, String email, String first_name, String last_name) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
    }



    // Getters and setter methods for every specified field in the constructor

    public LatLong getLatLong() {
        return new LatLong(lat, lon);
    }
    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    /**
     * finds user within directory and returns
     * @param username the username to search for
     * @return the User if found otherwise null
     */
    public static User findUser(String username) {
        ArrayList<User> userList = readUserData();
        for (User u : userList) {
            if (u.username.equals(username)) {
                System.out.println("found user");
                return u;
            }
        }
        return null;
    }
//aUsername,asdf@email.com,John,Doe,password,1234 street str,Atlanta,GA,12345,Admin

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * creates user object from a string array of its attributes
     * @param args string array of User attributes
     * @return a user object composite of string array attributes
     */
    public static User constructUser(String ... args) {
        User user = new User();
        String type = args[8];
        if (type.equals("Admin")) {
            user = new Administrator();
        } else if (type.equals("Manager")) {
            user = new Manager();
        } else if (type.equals("Worker")) {
            user = new Worker();
        }
        if (args.length == 9) {
            user.username = args[0];
            user.email = args[1];
            user.first_name = args[2];
            user.last_name = args[3];
            user.streetAddress = args[4];
            user.city = args[5];
            user.state = args[6];
            user.zipCode = args[7];
            user.type = args[8];

        } else if (args.length == 11) {
            user.username = args[0];
            user.email = args[1];
            user.first_name = args[2];
            user.last_name = args[3];
            user.streetAddress = args[4];
            user.city = args[5];
            user.state = args[6];
            user.zipCode = args[7];
            user.type = args[8];
            user.lat= Double.parseDouble(args[9]);
            user.lon = Double.parseDouble(args[10]);
        }
        return user;
    }

    /**
     * formats userdata in a file-loadable string format
     * @return formatted string
     */
    public String getUserDataText() {
        String[] user_meta = {username, email, first_name, last_name, streetAddress, city, state, zipCode, type};
        String returnString = "";
        for (String s : user_meta) {
            returnString += s + ",";
        }

        return returnString.substring(0,returnString.length() - 1);
    }

    public boolean register() {
        try {
            ArrayList<User> reportList = readUserData();
            FileOutputStream fileOut = new FileOutputStream("res/UserData.ser");
            reportList.add(this);
            System.out.println(reportList.size());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(reportList);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("UserData.ser cannot be accessed");
            return false;
        }
    }




    private static ArrayList<User> readUserData() {
        ArrayList<User> returnList = new ArrayList<>();
        ObjectInputStream ois = null;
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("res/UserData.ser");
            ois = new ObjectInputStream(stream);
            returnList = (ArrayList<User>) ois.readObject();
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
        for (User item : returnList) {
            System.out.println(item);
        }
        return returnList;
    }

    public boolean updateUser() {
        try {
            ArrayList<User> reportList = readUserData();
            FileOutputStream fileOut = new FileOutputStream("res/UserData.ser");
            User delUser = null;
            for (User user : reportList) {
                if (user.username.equals(this.first_name)) {
                    delUser = user;
                    break;
                }
            }
            reportList.remove(delUser);
            reportList.add(this);
            System.out.println(reportList.size());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(reportList);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("UserData.ser cannot be accessed");
            return false;
        }
    }

    /**
     * Searches for valid credentials in file. User and password fields
     * assumed to be filled when called.
     * @return boolean whether credentials are correct
     */
    public static boolean verifyUser(String aUser, String aPass) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/cred.dat"));
            String currentCredentials;
            while ((currentCredentials = reader.readLine()) != null) {
                String[] userPass = currentCredentials.split(":");
                //invalid entry in cred.dat -- ignore
                if (userPass.length != 2) continue;
                if (userPass[0].equals(aUser)) {
                    if (userPass[1].equals(aPass)) {
                        reader.close();
                        return true;
                    } else {
                        reader.close();
                        return false;
                    }
                }
            }
            reader.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
