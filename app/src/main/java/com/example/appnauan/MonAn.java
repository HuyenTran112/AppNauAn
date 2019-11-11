package com.example.appnauan;

import java.util.ArrayList;

public class MonAn {
    private int MaMonAn;
    private String TenMonAn;
    private String DSNguyenLieu;
    private String CongThuc;
    private String HinhAnh;
    private String MaLoaiMonAn;
    private String MaNguoiDung;

    public MonAn(int maMonAn, String tenMonAn, String DSNguyenLieu, String congThuc, String hinhAnh, String maLoaiMonAn, String maNguoiDung) {
        MaMonAn = maMonAn;
        TenMonAn = tenMonAn;
        this.DSNguyenLieu = DSNguyenLieu;
        CongThuc = congThuc;
        HinhAnh = hinhAnh;
        MaLoaiMonAn = maLoaiMonAn;
        MaNguoiDung = maNguoiDung;
    }

    public int getMaMonAn() {
        return MaMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        MaMonAn = maMonAn;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public String getDSNguyenLieu() {
        return DSNguyenLieu;
    }

    public void setDSNguyenLieu(String DSNguyenLieu) {
        this.DSNguyenLieu = DSNguyenLieu;
    }

    public String getCongThuc() {
        return CongThuc;
    }

    public void setCongThuc(String congThuc) {
        CongThuc = congThuc;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getMaLoaiMonAn() {
        return MaLoaiMonAn;
    }

    public void setMaLoaiMonAn(String maLoaiMonAn) {
        MaLoaiMonAn = maLoaiMonAn;
    }

    public String getMaNguoiDung() {
        return MaNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        MaNguoiDung = maNguoiDung;
    }
}
