package com.example.lattice.RetrofitPincode.models;

import com.google.gson.annotations.SerializedName;

public class PostOffice {

    @SerializedName("District")
    String District;

    @SerializedName("State")
    String State;

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
