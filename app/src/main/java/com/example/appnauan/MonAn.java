package com.example.appnauan;

import java.io.Serializable;
import java.util.ArrayList;

public class MonAn implements Serializable {
    private int MaMonAn;
    private String TenMonAn;
    private String DSNguyenLieu;
    private String CongThuc;
    private String HinhAnh;
    private int MaLoaiMonAn;
    private int MaNguoiDung;

    public MonAn(int maMonAn, String tenMonAn, String DSNguyenLieu, String congThuc, String hinhAnh, int maLoaiMonAn, int maNguoiDung) {
        MaMonAn = maMonAn;
        TenMonAn = tenMonAn;
        this.DSNguyenLieu = DSNguyenLieu;
        CongThuc = congThuc;
        HinhAnh = hinhAnh;
        MaLoaiMonAn = maLoaiMonAn;
        MaNguoiDung = maNguoiDung;
    }

    public MonAn() {

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

    public int getMaLoaiMonAn() {
        return MaLoaiMonAn;
    }

    public void setMaLoaiMonAn(int maLoaiMonAn) {
        MaLoaiMonAn = maLoaiMonAn;
    }

    public int getMaNguoiDung() {
        return MaNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        MaNguoiDung = maNguoiDung;
    }
}
