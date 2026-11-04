package com.kikicoding.noesantaracrm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kikicoding.noesantaracrm.model.Customer;
import com.kikicoding.noesantaracrm.model.LoginResponse;
import com.kikicoding.noesantaracrm.repository.CustomerRepository;

import java.util.List;

public class CustomerViewModel extends ViewModel {

    private final CustomerRepository repository = new CustomerRepository();

    public MutableLiveData<LoginResponse> loginResult = new MutableLiveData<>();
    public MutableLiveData<List<Customer>> customerList = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Boolean> logoutResult = new MutableLiveData<>();

    public void login(String email, String password) {
        repository.login(email, password, new CustomerRepository.ResultCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse result) {
                loginResult.postValue(result);
            }

            @Override
            public void onError(String error) {
                message.postValue(error);
            }
        });
    }

    public void loadCustomers(String token) {
        repository.getCustomers(token, new CustomerRepository.ResultCallback<List<Customer>>() {
            @Override
            public void onSuccess(List<Customer> result) {
                customerList.postValue(result);
            }

            @Override
            public void onError(String error) {
                message.postValue(error);
            }
        });
    }

    public void addCustomer(String token, Customer customer) {
        repository.addCustomer(token, customer, new CustomerRepository.ResultCallback<Customer>() {
            @Override
            public void onSuccess(Customer result) {
                message.postValue("Customer berhasil ditambahkan");

                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                    loadCustomers(token);
                }, 500);
            }

            @Override
            public void onError(String error) {
                message.postValue(error);
            }
        });
    }

    public void logout(String token) {
        repository.logout(token, new CustomerRepository.ResultCallback<String>() {
            @Override
            public void onSuccess(String result) {
                logoutResult.postValue(true);
            }

            @Override
            public void onError(String error) {
                message.postValue(error);
                logoutResult.postValue(false);
            }
        });
    }
}