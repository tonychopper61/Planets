package com.example.planets;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {

    private int ID;
    private String Country;
    private String Population;
    private String Image;

    protected Mask(Parcel in) {
        ID = in.readInt();
        Country = in.readString();
        Population = in.readString();
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
        dest.writeString(Country);
        dest.writeString(Population);
        dest.writeString(Image);
    }



    public Mask(int ID, String country, String population, String image)
    {
        this.ID = ID;
        Country = country;
        Population = population;
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
        return Country;
    }

    public void setName(String country) {
        Country = country;
    }

    public String getDistance() {
        return Population;
    }

    public void setDistance(String population) {
        Population = population;
    }
}