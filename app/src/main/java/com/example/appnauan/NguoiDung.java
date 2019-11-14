package com.example.appnauan;

public class NguoiDung {
    private int MaNguoiDung;
    private String Email;
    private String TenHienThi;
    private String MatKhau;
    private int MaLoaiNguoiDung;
    private String HinhAnh;

    public NguoiDung() {

    }

    public int getMaNguoiDung() {
        return MaNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        MaNguoiDung = maNguoiDung;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTenHienThi() {
        return TenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        TenHienThi = tenHienThi;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public int getMaLoaiNguoiDung() {
        return MaLoaiNguoiDung;
    }

    public void setMaLoaiNguoiDung(int maLoaiNguoiDung) {
        MaLoaiNguoiDung = maLoaiNguoiDung;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public NguoiDung(int maNguoiDung, String email, String tenHienThi, String matKhau, int maLoaiNguoiDung, String hinhAnh) {
        MaNguoiDung = maNguoiDung;
        Email = email;
        TenHienThi = tenHienThi;
        MatKhau = matKhau;
        MaLoaiNguoiDung = maLoaiNguoiDung;
        HinhAnh = hinhAnh;
    }
}
