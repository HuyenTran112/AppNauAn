package com.example.appnauan;

import java.io.Serializable;

public class LoaiMonAn implements Serializable {
    private int MaLoaiMonAn;
    private String TenLoaiMonAn;
    public int HinhAnh;

    public int getHinhAnh() {
        return HinhAnh;
    }

    public LoaiMonAn(int maLoaiMonAn, String tenLoaiMonAn, int hinhAnh) {
        MaLoaiMonAn = maLoaiMonAn;
        TenLoaiMonAn = tenLoaiMonAn;
        HinhAnh = hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public LoaiMonAn(int maLoaiMonAn, String tenLoaiMonAn) {
        MaLoaiMonAn = maLoaiMonAn;
        TenLoaiMonAn = tenLoaiMonAn;
    }

    public int getMaLoaiMonAn() {
        return MaLoaiMonAn;
    }

    public void setMaLoaiMonAn(int maLoaiMonAn) {
        MaLoaiMonAn = maLoaiMonAn;
    }

    public String getTenLoaiMonAn() {
        return TenLoaiMonAn;
    }

    public void setTenLoaiMonAn(String tenLoaiMonAn) {
        TenLoaiMonAn = tenLoaiMonAn;
    }
    public String getTenLoai(int maLoaiMonAn)
    {
        return TenLoaiMonAn;
    }
}
