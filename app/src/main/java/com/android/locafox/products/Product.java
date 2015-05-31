package com.android.locafox.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

/**
 * Created by Erno on 5/31/2015.
 */
public class Product implements Parcelable, JsonDeserializer<Product>{

    private String name;
    private String desc;
    private String imageUrl;
    private int availability;

    public Product(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.desc);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.availability);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            Product p = new Product();
            p.setName(in.readString());
            p.setDesc(in.readString());
            p.setImageUrl(in.readString());
            p.setAvailability(in.readInt());

            return p;
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Product p = new Product();

        return p;
    }
}
