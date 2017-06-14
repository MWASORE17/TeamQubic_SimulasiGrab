package com.qubic.grabsimulation.api.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennyho on 5/17/17.
 */

public class Track implements Parcelable {
    private static List<Track> tracks = new ArrayList<>();
    private static int idCount = 1;

    private int id;
    private Locations origin;
    private Locations destination;
    private List<String> polylines = new ArrayList<>();
    private double distance;

    public Track() { }

    public Track(Locations origin, Locations destination, List<String> polylines, double distance) {
        this.origin = origin;
        this.destination = destination;
        this.polylines = polylines;
        this.distance = distance;
        this.id = idCount;
        idCount++;
    }

    public int getId() {
        return this.id;
    }

    public Locations getOrigin() {
        return this.origin;
    }

    public void setOrigin(Locations origin) {
        this.origin = origin;
    }

    public Locations getDestination() {
        return this.destination;
    }

    public void setDestination(Locations destination) {
        this.destination = destination;
    }

    public List<String> getPolylines() {
        return this.polylines;
    }

    public void setPolylines(List<String> polylines) {
        this.polylines = polylines;
    }

    public void addPolyline(String polyline) {
        this.polylines.add(polyline);
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public static void addNewTrack(Track track) {
        tracks.add(track);
    }

    public static List<Track> getTrackList() {
        return tracks;
    }

    protected Track(Parcel in) {
        id = in.readInt();
        origin = in.readParcelable(Locations.class.getClassLoader());
        destination = in.readParcelable(Locations.class.getClassLoader());
        polylines = in.readArrayList(String.class.getClassLoader());
        distance = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.origin, flags);
        dest.writeParcelable(this.destination, flags);
        dest.writeList(this.polylines);
        dest.writeDouble(this.distance);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
