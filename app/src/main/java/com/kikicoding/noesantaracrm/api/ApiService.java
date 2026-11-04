package com.kikicoding.noesantaracrm.api;

import com.kikicoding.noesantaracrm.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<LoginResponse> login(
            @Body LoginRequest request
    );

    @POST("logout")
    Call<ApiResponse<Object>> logout(
            @Header("Authorization") String token
    );

    @GET("customers")
    Call<List<Customer>> getCustomers(
            @Header("Authorization") String token
    );

    @POST("customers")
    Call<ApiResponse<Customer>> addCustomer(
            @Header("Authorization") String token,
            @Body Customer customer
    );

    @GET("leads")
    Call<List<Lead>> getLeads(
            @Header("Authorization") String token
    );

    @POST("leads")
    Call<ApiResponse<Lead>> addLead(
            @Header("Authorization") String token,
            @Body Lead lead
    );
} 