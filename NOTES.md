# Aplikasi Biodata Mahasiswa (Java Swing)

Aplikasi desktop sederhana untuk menginput dan menampilkan biodata mahasiswa (NIM, Nama, Program Studi), dibuat dengan **Java Swing**.

Tema UI/UX: **Futuristic, Cozy, Simple** — dark theme dengan aksen warna cyan & ungu, kartu bersudut melengkung, tombol gradient dengan efek hover, input field dengan animasi border saat fokus, dan panel output dengan efek reveal per baris.

## Fitur

- Input: NIM, Nama, Program Studi
- Tombol **Tampilkan** → menampilkan biodata di panel Output dengan animasi
- Tombol **Reset** → mengosongkan input dan output
- Validasi sederhana: peringatan jika ada field yang masih kosong
- Didesain custom (rounded input, gradient button, badge ikon vektor) tanpa memakai library/gambar dari luar

## Requirement

- **JDK (Java Development Kit) 8 ke atas** — cek dengan `java -version` dan `javac -version` di terminal
- **Tidak ada library eksternal tambahan.** Semua komponen (Swing/AWT) sudah bawaan JDK.

## Cara Menjalankan

### Opsi 1 — Command Line

```bash
javac BiodataMahasiswaApp.java
java BiodataMahasiswaApp
```

### Opsi 2 — IDE (NetBeans / IntelliJ / Eclipse / VS Code)

1. Buat project Java baru.
2. Masukkan file `BiodataMahasiswaApp.java` ke folder `src`.
3. Run file tersebut (klik kanan → Run, atau tombol Run di IDE).

## Struktur Kode

Satu file `BiodataMahasiswaApp.java` berisi:

- `BiodataMahasiswaApp` — frame utama & logika Tampilkan/Reset
- `GradientPanel` — panel background gradient
- `RoundedPanel` — kartu dengan sudut melengkung
- `RoundedField` — text field melengkung dengan animasi border saat fokus
- `GradientButton` — tombol gradient dengan efek hover/press
- `IconBadge` — badge ikon vektor (ID card, orang, topi wisuda) digambar manual, tanpa emoji/gambar eksternal

## Screenshot

_Tambahkan screenshot hasil running program di sini setelah dijalankan._
