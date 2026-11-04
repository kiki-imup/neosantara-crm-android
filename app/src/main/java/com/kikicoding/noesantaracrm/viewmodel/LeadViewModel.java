package com.kikicoding.noesantaracrm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kikicoding.noesantaracrm.model.Lead;
import com.kikicoding.noesantaracrm.repository.CustomerRepository;
import java.util.List;

public class LeadViewModel extends ViewModel {

    private final CustomerRepository repository = new CustomerRepository();

    public MutableLiveData<List<Lead>> leadList = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();

    public void loadLeads(String token) {
        repository.getLeads(token, new CustomerRepository.ResultCallback<List<Lead>>() {
            @Override
            public void onSuccess(List<Lead> result) {
                leadList.postValue(result);
            }
            @Override
            public void onError(String error) {
                message.postValue(error);
            }
        });
    }

    public void addLead(String token, Lead lead) {
        repository.addLead(token, lead, new CustomerRepository.ResultCallback<Lead>() {
            @Override
            public void onSuccess(Lead result) {
                message.postValue("Prospek berhasil ditambahkan");
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                    loadLeads(token);
                }, 500);
            }
            @Override
            public void onError(String error) {
                message.postValue(error);
            }
        });
    }
}