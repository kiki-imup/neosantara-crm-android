package com.kikicoding.noesantaracrm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.kikicoding.noesantaracrm.model.Customer;
import com.kikicoding.noesantaracrm.model.Lead;
import com.kikicoding.noesantaracrm.viewmodel.CustomerViewModel;
import com.kikicoding.noesantaracrm.viewmodel.LeadViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnAdd;
    private TextView btnLogout, btnNavProspect;
    private ListView listCustomers;
    private View loginLayout, customerLayout;
    private CustomerViewModel customerViewModel;
    private LeadViewModel leadViewModel;
    private ArrayAdapter<Customer> customerAdapter;
    private ArrayAdapter<Lead> leadAdapter;
    private ArrayList<Customer> customers;
    private ArrayList<Lead> leads;
    private SharedPreferences preferences;
    private String token;
    private boolean isShowingProspects = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        leadViewModel = new ViewModelProvider(this).get(LeadViewModel.class);
        preferences = getSharedPreferences("NeosantaraSession", MODE_PRIVATE);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnAdd = findViewById(R.id.btnAdd);
        btnLogout = findViewById(R.id.btnLogout);
        btnNavProspect = findViewById(R.id.btnNavProspect);
        listCustomers = findViewById(R.id.listCustomers);
        loginLayout = findViewById(R.id.loginLayout);
        customerLayout = findViewById(R.id.customerLayout);

        customers = new ArrayList<>();
        leads = new ArrayList<>();

        customerAdapter = new ArrayAdapter<Customer>(this, R.layout.item_list, R.id.tvTitle, customers) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Customer c = getItem(position);
                if (c != null) {
                    TextView title = view.findViewById(R.id.tvTitle);
                    TextView subtitle = view.findViewById(R.id.tvSubtitle);
                    TextView status = view.findViewById(R.id.tvStatus);

                    title.setText(c.getName());
                    subtitle.setText((c.getEmail() != null ? c.getEmail() : "-") + " | " + (c.getPhone() != null ? c.getPhone() : "-"));
                    status.setText("Pelanggan");
                }
                return view;
            }
        };

        leadAdapter = new ArrayAdapter<Lead>(this, R.layout.item_list, R.id.tvTitle, leads) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Lead l = getItem(position);
                if (l != null) {
                    TextView title = view.findViewById(R.id.tvTitle);
                    TextView subtitle = view.findViewById(R.id.tvSubtitle);
                    TextView status = view.findViewById(R.id.tvStatus);

                    title.setText(l.getCompanyName());
                    subtitle.setText((l.getContactPerson() != null ? l.getContactPerson() : "-") + " | " + (l.getPhone() != null ? l.getPhone() : "-"));
                    status.setText("Status: " + (l.getStatus() != null ? l.getStatus() : "-"));
                }
                return view;
            }
        };

        listCustomers.setAdapter(customerAdapter);

        listCustomers.setOnItemClickListener((parent, view, position, id) -> {
            Object item = parent.getItemAtPosition(position);
            if (item instanceof Customer) {
                Customer c = (Customer) item;
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Detail Pelanggan")
                        .setMessage("Nama: " + c.getName() + "\n\nEmail: " + (c.getEmail() != null ? c.getEmail() : "-") + "\n\nTelepon: " + (c.getPhone() != null ? c.getPhone() : "-"))
                        .setPositiveButton("Tutup", null)
                        .show();
            } else if (item instanceof Lead) {
                Lead l = (Lead) item;
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Detail Prospek")
                        .setMessage("Perusahaan: " + l.getCompanyName() + "\n\nKontak: " + (l.getContactPerson() != null ? l.getContactPerson() : "-") + "\n\nStatus: " + (l.getStatus() != null ? l.getStatus() : "-"))
                        .setPositiveButton("Tutup", null)
                        .show();
            }
        });

        observeViewModel();

        preferences.edit().remove("token").apply();
        token = null;
        showLoginPage();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }
            customerViewModel.login(email, password);
        });

        btnAdd.setOnClickListener(v -> {
            if (isShowingProspects) {
                showAddLeadDialog();
            } else {
                showAddCustomerDialog();
            }
        });

        btnNavProspect.setOnClickListener(v -> {
            if (!isShowingProspects) {
                isShowingProspects = true;
                btnNavProspect.setText("PELANGGAN");
                listCustomers.setAdapter(leadAdapter);
                leadViewModel.loadLeads(token);
            } else {
                isShowingProspects = false;
                btnNavProspect.setText("PROSPEK");
                listCustomers.setAdapter(customerAdapter);
                customerViewModel.loadCustomers(token);
            }
        });

        btnLogout.setOnClickListener(v -> {
            if (token != null) {
                customerViewModel.logout(token);
            }
        });
    }

    private void observeViewModel() {
        customerViewModel.loginResult.observe(this, result -> {
            if (result != null && result.isSuccess()) {
                token = result.getToken();
                preferences.edit().putString("token", token).apply();
                showCustomerPage();
                customerViewModel.loadCustomers(token);
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
            }
        });

        customerViewModel.customerList.observe(this, result -> {
            if (result != null) {
                customers.clear();
                customers.addAll(result);
                customerAdapter.notifyDataSetChanged();
            }
        });

        customerViewModel.message.observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        leadViewModel.leadList.observe(this, result -> {
            if (result != null) {
                leads.clear();
                leads.addAll(result);
                leadAdapter.notifyDataSetChanged();
            }
        });

        leadViewModel.message.observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        customerViewModel.logoutResult.observe(this, result -> {
            if (Boolean.TRUE.equals(result)) {
                preferences.edit().remove("token").apply();
                token = null;
                isShowingProspects = false;
                btnNavProspect.setText("PROSPEK");
                listCustomers.setAdapter(customerAdapter);
                showLoginPage();
                etPassword.setText("");
                customers.clear();
                customerAdapter.notifyDataSetChanged();
                leads.clear();
                leadAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoginPage() {
        loginLayout.setVisibility(View.VISIBLE);
        customerLayout.setVisibility(View.GONE);
    }

    private void showCustomerPage() {
        loginLayout.setVisibility(View.GONE);
        customerLayout.setVisibility(View.VISIBLE);
    }

    private void showAddCustomerDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 10, 30, 10);

        EditText name = new EditText(this);
        name.setHint("Nama Customer");

        EditText email = new EditText(this);
        email.setHint("Email");
        email.setInputType(33);

        EditText phone = new EditText(this);
        phone.setHint("Nomor Telepon");
        phone.setInputType(3);

        EditText address = new EditText(this);
        address.setHint("Alamat");

        layout.addView(name);
        layout.addView(email);
        layout.addView(phone);
        layout.addView(address);

        new android.app.AlertDialog.Builder(this)
                .setTitle("Tambah Customer")
                .setView(layout)
                .setPositiveButton("Simpan", (dialog, which) -> {
                    String customerName = name.getText().toString().trim();
                    String customerEmail = email.getText().toString().trim();

                    if (customerName.isEmpty()) {
                        Toast.makeText(this, "Nama customer wajib diisi", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Customer customer = new Customer(
                            customerName,
                            address.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            customerEmail.isEmpty() ? null : customerEmail,
                            null
                    );

                    customerViewModel.addCustomer(token, customer);
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void showAddLeadDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 10, 30, 10);

        EditText companyName = new EditText(this);
        companyName.setHint("Nama Perusahaan");

        EditText contactPerson = new EditText(this);
        contactPerson.setHint("Nama Kontak");

        EditText phone = new EditText(this);
        phone.setHint("Nomor Telepon");
        phone.setInputType(3);

        EditText email = new EditText(this);
        email.setHint("Email");
        email.setInputType(33);

        EditText status = new EditText(this);
        status.setHint("Status");

        layout.addView(companyName);
        layout.addView(contactPerson);
        layout.addView(phone);
        layout.addView(email);
        layout.addView(status);

        new android.app.AlertDialog.Builder(this)
                .setTitle("Tambah Prospek")
                .setView(layout)
                .setPositiveButton("Simpan", (dialog, which) -> {
                    String cName = companyName.getText().toString().trim();
                    if (cName.isEmpty()) {
                        Toast.makeText(this, "Nama perusahaan wajib diisi", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Lead lead = new Lead(
                            cName,
                            contactPerson.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            email.getText().toString().trim(),
                            status.getText().toString().trim()
                    );

                    leadViewModel.addLead(token, lead);
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}