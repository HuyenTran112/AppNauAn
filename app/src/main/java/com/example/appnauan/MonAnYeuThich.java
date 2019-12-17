package com.example.appnauan;

public class MonAnYeuThich {
    private int MaMonAn;
    private int MaNguoiDung;

    public MonAnYeuThich(int maMonAn, int maNguoiDung) {
        MaMonAn = maMonAn;
        MaNguoiDung = maNguoiDung;
    }

    public int getMaMonAn() {
        return MaMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        MaMonAn = maMonAn;
    }

    public int getMaNguoiDung() {
        return MaNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        MaNguoiDung = maNguoiDung;
    }
}
