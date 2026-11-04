# Neosantara CRM Android (REST Client)

Aplikasi Android ini dibuat untuk memenuhi Ujian Tengah Semester (UTS) mata kuliah P. Mobile Lanjutan. Aplikasi ini berfungsi sebagai REST Client yang terhubung dengan REST API Laravel (Neosantara ERP).

Aplikasi ini mengimplementasikan arsitektur **MVVM (Model-View-ViewModel)** secara bersih menggunakan bahasa pemrograman **Java**, serta menggunakan **Retrofit** untuk komunikasi data (GET dan POST) dan **LiveData** untuk pembaruan UI.

## Fitur Utama
1. **Autentikasi**: Login untuk mendapatkan Token akses dan Logout untuk menghapus sesi.
2. **Manajemen Pelanggan (Customer)**: Menampilkan daftar pelanggan (GET) dan menambahkan pelanggan baru (POST).
3. **Manajemen Prospek (Leads)**: Menampilkan daftar prospek (GET) dan menambahkan prospek baru (POST).

## Dokumentasi Aplikasi (Screenshot)

### 1. Halaman Login
Halaman ini digunakan pengguna untuk memasukkan email dan password. Jika berhasil, API Laravel akan mengirimkan token yang akan disimpan di `SharedPreferences`.
<img width="490" height="911" alt="LOGIN" src="https://github.com/user-attachments/assets/94d3c979-abe9-4fe7-bd59-1a51f28cf0c7" />

### 2. Dashboard Customer (GET Data)
Halaman ini menampilkan daftar pelanggan yang diambil dari API Laravel menggunakan Retrofit, lalu ditampilkan ke dalam `ListView` menggunakan `ArrayAdapter`.
<img width="463" height="912" alt="DASHBOARD CUSTUMER" src="https://github.com/user-attachments/assets/da1ac77d-9542-4dd2-ae41-9727d445fdcb" />

### 3. Detail Customer (Opsional)
Menampilkan detail informasi pelanggan saat salah satu item pada ListView diklik.
<img width="510" height="915" alt="LIHAT DATA" src="https://github.com/user-attachments/assets/32ce31fe-8cf8-47c7-bd4b-27f86a2fd578" />

### 4. Form Tambah Data (POST Data)
Form ini digunakan untuk mengirim data pelanggan atau prospek baru ke database Laravel melalui API.
<img width="452" height="922" alt="TAMBAH DATA" src="https://github.com/user-attachments/assets/019a3b8a-147a-4d8e-82eb-0d94e0b9329b" />

### 5. Dashboard Prospek (GET Data)
Halaman ini menampilkan daftar calon pelanggan (Prospek/Leads) yang juga diambil dari API. Fitur ini membuktikan aplikasi dapat mengelola lebih dari satu entitas data.
<img width="476" height="925" alt="DASHBOARD PROSPEK" src="https://github.com/user-attachments/assets/9ae697c3-6b42-45c9-9af7-e09d95ced14e" />

### 6. Fitur Logout
Fitur ini akan memanggil API logout untuk menghapus token di server dan menghapus token di perangkat, lalu mengembalikan pengguna ke halaman Login.
<img width="500" height="907" alt="LOGOUT" src="https://github.com/user-attachments/assets/0c528ea1-b6bc-4f32-87d8-4fd3925d0dc9" />
