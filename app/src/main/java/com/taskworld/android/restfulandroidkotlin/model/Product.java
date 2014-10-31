package com.taskworld.android.restfulandroidkotlin.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Kittinun Vantasin on 10/19/14.
 */
public class Product extends RealmObject {

    @Ignore
    public static final String TABLE_NAME = "Product";

    public enum Field {
        name,
        price
    }

    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
