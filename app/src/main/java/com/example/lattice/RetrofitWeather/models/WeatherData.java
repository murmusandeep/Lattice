package com.example.lattice.RetrofitWeather.models;

import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("location")
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @SerializedName("current")
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
