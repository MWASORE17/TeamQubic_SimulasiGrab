package com.qubic.grabsimulation.api.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dennyho on 5/1/17.
 */

public class TopUpType implements Parcelable {
    private static int idCount = 1;

    private int id;
    private String type;
    private String name;

    //blank constructor
    public TopUpType() {

    }

    //constructor
    public TopUpType(String type, String name) {
        this.type = type;
        this.name = name;
        this.id = idCount;
        idCount++;
    }

    //getters and setters
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected TopUpType(Parcel in) {
        type = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TopUpType> CREATOR = new Parcelable.Creator<TopUpType>() {
        @Override
        public TopUpType createFromParcel(Parcel in) {
            return new TopUpType(in);
        }

        @Override
        public TopUpType[] newArray(int size) {
            return new TopUpType[size];
        }
    };
}
