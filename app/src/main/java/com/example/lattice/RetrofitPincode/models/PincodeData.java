package com.example.lattice.RetrofitPincode.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PincodeData {

    @SerializedName("Status")
    String Status;

    @SerializedName("PostOffice")
    public List<PostOffice> postOfficeList = null;

    public List<PostOffice> getPostOfficeList() {
        return postOfficeList;
    }

    public void setPostOfficeList(List<PostOffice> postOfficeList) {
        this.postOfficeList = postOfficeList;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
