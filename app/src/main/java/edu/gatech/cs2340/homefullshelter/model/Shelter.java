package edu.gatech.cs2340.homefullshelter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gkarnati3 on 2/24/18.
 */

public class Shelter implements Parcelable {
    private int key;
    private String name;
    private String capacity;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String notes;
    private String number;
    private String checkedOut;

    /**
     * A non-arg constructor that will initialize a null (empty) shelter
     */
    public Shelter() {
        //for the firebase stuff.
            this(-1, "", "0", "", 0, 0, "", "", "", "0");
    }

    /**
     * An arg constructor that will initialize a shelter with the following parameters with a
     * default initialization for checkedOut
     * @param k the key of the shelter
     * @param n the name of the shelter
     * @param c the capacity of the shelter
     * @param r the restrictions of the shelter
     * @param lo the longitude of the shelter
     * @param la the latitude of the shelter
     * @param a the address of the shelter
     * @param no the notes of the shelter
     * @param nu the number of the shelter
     */
    public Shelter(int k, String n, String c, String r, double lo, double la, String a, String no,
                   String nu) {
        this(k, n, c, r, lo, la, a, no, nu, "0");
    }

    /**
     * An arg constructor that will initialize a shelter with the following parameters
     * @param k the key of the shelter
     * @param n the name of the shelter
     * @param c the capacity of the shelter
     * @param r the restrictions of the shelter
     * @param lo the longitude of the shelter
     * @param la the latitude of the shelter
     * @param a the address of the shelter
     * @param no the notes of the shelter
     * @param nu the number of the shelter
     * @param co the checked out of the shelter
     */
    public Shelter(int k, String n, String c, String r, double lo, double la, String a, String no,
                   String nu, String co) {
        key = k;
        name = n;
        capacity = c;
        restrictions = r;
        longitude = lo;
        latitude = la;
        address = a;
        notes = no;
        number = nu;
        checkedOut = co;
    }

    /**
     *
     * @return a string version of the shelter containing the name and the key of the shelter
     */
    public String toString() {
        return name + " " + key;
    }

    /**
     *
     * @return the key of the shelter
     */
    public int getKey() { return key; }

    /**
     *
     * @return the name of the shelter
     */
    public String getName() { return name; }

    /**
     *
     * @return the capacity of the shelter
     */
    public String getCapacity() { return capacity; }

    /**
     *
     * @return the restrictions of the shelter
     */
    public String getRestrictions() { return restrictions; }

    /**
     *
     * @return the longitude of the shelter
     */
    public double getLongitude() { return longitude; }

    /**
     *
     * @return the latitude of the shelter
     */
    public double getLatitude() { return latitude; }

    /**
     *
     * @return the address of the shelter
     */
    public String getAddress() { return address; }

    /**
     *
     * @return the notes of the shelter
     */
    public String getNotes() { return notes; }

    /**
     *
     * @return the number of the shelter
     */
    public String getNumber() { return number; }

    /**
     *
     * @return the checked out of the shelter
     */
    public String getCheckedOut() { return checkedOut; }

    /**
     * setting the key of the shelter to a new key
     * @param key the new key
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * setting the name of the shelter to a new name
     * @param name the new name
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * setting the capacity of the shelter to a new capacity
     * @param capacity the new capacity
     */
    public void setCapacity(String capacity) {
        try {
            int newCapacity = Integer.parseInt(capacity);
            if (newCapacity >= 0) {
                this.capacity =  Integer.toString(newCapacity);
            } else {
                throw new IllegalArgumentException("capacity must be nonnegative");
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("capacity must contain a number");
        }

    }

    /**
     * setting the restrictions of the shelter to new restrictions
     * @param restrictions the new restrictions
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * setting the longitude of the shelter to a new longitude
     * @param longitude the new longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * setting the latitude of the shelter to a new latitude
     * @param latitude the new latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * setting the address of the shelter to a new address
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * setting the notes of the shelter to new notes
     * @param notes the new notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * setting the number of the shelter to a new number
     * @param number the new number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * setting the checked out of the shelter to a new checked out
     * @param checkedOut the new checked out
     */
    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Shelter)) {
            return false;
        }

        Shelter comp = (Shelter) other;
        return this.key == comp.key;
    }

    @Override
    public int hashCode() {
        return key;
    }

    // All stuff to make it parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.key);
        out.writeString(this.name);
        out.writeString(this.capacity);
        out.writeString(this.restrictions);
        out.writeDouble(this.latitude);
        out.writeDouble(this.longitude);
        out.writeString(this.address);
        out.writeString(this.notes);
        out.writeString(this.number);
        out.writeString(this.checkedOut);

    }

    public static final Parcelable.Creator<Shelter> CREATOR
            = new Parcelable.Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

    /**
     * putting shelter data into parcel
     * @param in the parcel that has data
     */
    private Shelter(Parcel in) {
        this.key = in.readInt();
        this.name = in.readString();
        this.capacity = in.readString();
        this.restrictions = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.address = in.readString();
        this.notes = in.readString();
        this.number = in.readString();
        this.checkedOut = in.readString();
    }
}
