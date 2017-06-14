package com.qubic.grabsimulation.api.model.entity;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dennyho on 5/9/17.
 */

public class PaymentMethod implements Parcelable {
    private static int idCount = 1;

    private int id;
    private String name;
    private String imageName;

    //blank constructor
    public PaymentMethod() {

    }

    //constructor
    public PaymentMethod(String name, String imageName) {
        this.id = idCount;
        this.name = name;
        this.imageName = imageName;
        idCount++;
    }

    //getters and setters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() { return this.imageName; }

    public void setImageName(String image) { this.imageName = imageName; }

    protected PaymentMethod(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PaymentMethod> CREATOR = new Parcelable.Creator<PaymentMethod>() {
        @Override
        public PaymentMethod createFromParcel(Parcel in) {
            return new PaymentMethod(in);
        }

        @Override
        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };
}
