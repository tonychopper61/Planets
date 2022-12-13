package com.example.planets;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {

    private int ID;
    private String Name;
    private String Distance;
    private String Image;

    protected Mask(Parcel in) {
        ID = in.readInt();
        Name = in.readString();
        Distance = in.readString();
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Name);
        dest.writeString(Distance);
        dest.writeString(Image);
    }



    public Mask(int ID, String name, String distance, String image)
    {
        this.ID = ID;
        Name = name;
        Distance = distance;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}