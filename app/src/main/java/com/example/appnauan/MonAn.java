package com.example.appnauan;

import java.util.ArrayList;

public class MonAn {
    private int MaMonAn;
    private String TenMonAn;
    private String DSNguyenLieu;
    private String CongThuc;
    private byte[] HinhAnh;

    public MonAn(int maMonAn, String tenMonAn, String DSNguyenLieu, String congThuc, byte[] hinhAnh) {
        MaMonAn = maMonAn;
        TenMonAn = tenMonAn;
        this.DSNguyenLieu = DSNguyenLieu;
        CongThuc = congThuc;
        HinhAnh = hinhAnh;
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

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
