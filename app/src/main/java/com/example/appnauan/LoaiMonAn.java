package com.example.appnauan;

public class LoaiMonAn {
    private int MaLoaiMonAn;
    private String TenLoaiMonAn;

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
