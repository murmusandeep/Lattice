package com.example.lattice.RetrofitPincode.service;

import com.example.lattice.RetrofitPincode.models.PincodeData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PincodeApiInterface {

    @GET("pincode/{path}")
    Call<List<PincodeData>> getPincodeData(@Path("path") String pincode);
}
