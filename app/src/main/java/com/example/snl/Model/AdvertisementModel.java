package com.example.snl.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertisementModel {

    static String memberId;
    static String title;
    static String description;
    static String price;
    static String category;
    static String state;

    public static String getMemberId() {
        return memberId;
    }

    public static void setMemberId(String memberId) {
        AdvertisementModel.memberId = memberId;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        AdvertisementModel.title = title;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        AdvertisementModel.description = description;
    }

    public static String getPrice() {
        return price;
    }

    public static void setPrice(String price) {
        AdvertisementModel.price = price;
    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        AdvertisementModel.category = category;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        AdvertisementModel.state = state;
    }
}
