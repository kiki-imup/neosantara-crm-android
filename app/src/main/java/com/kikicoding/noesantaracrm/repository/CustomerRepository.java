package com.kikicoding.noesantaracrm.repository;

import com.kikicoding.noesantaracrm.api.ApiClient;
import com.kikicoding.noesantaracrm.api.ApiService;
import com.kikicoding.noesantaracrm.model.ApiResponse;
import com.kikicoding.noesantaracrm.model.Customer;
import com.kikicoding.noesantaracrm.model.Lead;
import com.kikicoding.noesantaracrm.model.LoginRequest;
import com.kikicoding.noesantaracrm.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository {

    private final ApiService apiService;

    public CustomerRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public interface ResultCallback<T> {
        void onSuccess(T result);
        void onError(String message);
    }

    public void login(String email, String password, ResultCallback<LoginResponse> callback) {
        LoginRequest request = new LoginRequest(email, password);
        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Login gagal. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getCustomers(String token, ResultCallback<List<Customer>> callback) {
        apiService.getCustomers("Bearer " + token).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Gagal ambil customer. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void addCustomer(String token, Customer customer, ResultCallback<Customer> callback) {
        apiService.addCustomer("Bearer " + token, customer).enqueue(new Callback<ApiResponse<Customer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Customer>> call, Response<ApiResponse<Customer>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Gagal tambah customer. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Customer>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getLeads(String token, ResultCallback<List<Lead>> callback) {
        apiService.getLeads("Bearer " + token).enqueue(new Callback<List<Lead>>() {
            @Override
            public void onResponse(Call<List<Lead>> call, Response<List<Lead>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Gagal ambil prospek. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Lead>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void addLead(String token, Lead lead, ResultCallback<Lead> callback) {
        apiService.addLead("Bearer " + token, lead).enqueue(new Callback<ApiResponse<Lead>>() {
            @Override
            public void onResponse(Call<ApiResponse<Lead>> call, Response<ApiResponse<Lead>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Gagal tambah prospek. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Lead>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void logout(String token, ResultCallback<String> callback) {
        apiService.logout("Bearer " + token).enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Logout berhasil");
                } else {
                    callback.onError("Logout gagal. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}