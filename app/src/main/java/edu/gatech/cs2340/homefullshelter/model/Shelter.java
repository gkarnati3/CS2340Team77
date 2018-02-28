package edu.gatech.cs2340.homefullshelter.model;

/**
 * Created by gkarnati3 on 2/24/18.
 */

public class Shelter {
    private int key;
    private String shelter;
    private String capacity;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String notes;
    private String number;

    public Shelter(int k, String s, String c, String r, double lo, double la, String a, String no, String nu) {
        key = k;
        shelter = s;
        capacity = c;
        restrictions = r;
        longitude = lo;
        latitude = la;
        address = a;
        notes = no;
        number = nu;
    }

    public String toString() {
        return shelter + " " + key;
    }

    public int getKey() { return key; }
    public String getShelter() { return shelter; }
    public String getCapacity() { return capacity; }
    public String getRestrictions() { return restrictions; }
    public double getLongitude() { return longitude; }
    public double getLatitude() { return latitude; }
    public String getAddress() { return address; }
    public String getNotes() { return notes; }
    public String getNumber() { return number; }
}
