package com.example.lattice.RetrofitWeather.models;

import com.google.gson.annotations.SerializedName;

public class Current {

    @SerializedName("temp_c")
    String temp_c;

    @SerializedName("temp_f")
    String temp_f;

    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public String getTemp_f() {
        return temp_f;
    }

    public void setTemp_f(String temp_f) {
        this.temp_f = temp_f;
    }
}
