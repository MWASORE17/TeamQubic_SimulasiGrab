package com.qubic.grabsimulation.api.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ferr on 08/05/17.
 */

// Model for location
public class Locations implements Parcelable {
    private int id;
    private double distance, lng, lat;
    private String name, address;
    private boolean favorite;

    private int startId = 1;
    public static ArrayList<Locations> listLocations = new ArrayList<>();
    // Constructor
    public Locations() {}
    public Locations(String name, String address, double distance, double lat, double lng) {
        this.id = startId;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.lng = lng;
        this.lat = lat;
        startId++;
    }

    // CREATE DATAS
    public static ArrayList<Locations> createListLocations() {
        ArrayList<Locations> lists = new ArrayList<>();
        String[] names = {"Mikroskil", "Sun Plaza", "Centre Point", "Thamrin Plaza", "Merdeka Walk", "JW Marriot"};
        String[] addresses = {"Jl. Thamrin no 101", "Jl. Test no 1", "Jl. Test no 2", "Jl. Thamrin no 1", "Jl. Merdeka", "Jl. Test no.3"};
        Double[] distances = {0.85, 2.34, 1.55, 0.97, 4.55, 6.35};
        Double[] lngs = {0.85, 2.34, 1.55, 0.97, 4.55, 6.35};
        Double[] lats = {0.85, 2.34, 1.55, 0.97, 4.55, 6.35};
        for (int i = 0; i < names.length; i++) {
            lists.add(new Locations(names[i], addresses[i], distances[i], lngs[i], lats[i]));
        }
        return lists;
    }

    // SETTER AND GETTER
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public double getLat() { return lat; }

    public double getLng() { return lng; }

    public void addNewLocation(Locations location) { listLocations.add(location); }

    //Parcelable
    protected Locations(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        distance = in.readDouble();
        favorite = in.readInt() != 0;
        lat = in.readDouble();
        lng = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeDouble(this.distance);
        dest.writeInt((this.favorite ? 1 : 0));
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Locations> CREATOR = new Parcelable.Creator<Locations>() {
        @Override
        public Locations createFromParcel(Parcel in) {
            return new Locations(in);
        }

        @Override
        public Locations[] newArray(int size) {
            return new Locations[size];
        }
    };
}
