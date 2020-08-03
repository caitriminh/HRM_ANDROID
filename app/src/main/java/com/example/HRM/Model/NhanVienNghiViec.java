package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhanVienNghiViec {
    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("manv2")
    @Expose
    private String manv2;

    @SerializedName("tennv")
    @Expose
    private String tennv;

    @SerializedName("gioitinh")
    @Expose
    private String gioitinh;

    @SerializedName("ngaysinh")
    @Expose
    private String ngaysinh;

    @SerializedName("ngaynghi")
    @Expose
    private String ngaynghi;

    @SerializedName("tenpx")
    @Expose
    private String tenpx;

    @SerializedName("tenchuyen")
    @Expose
    private String tenchuyen;

    @SerializedName("tento")
    @Expose
    private String tento;

    @SerializedName("lydo")
    @Expose
    private String lydo;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("duyet")
    @Expose
    private String duyet;

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getManv2() {
        return manv2;
    }

    public void setManv2(String manv2) {
        this.manv2 = manv2;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getNgaynghi() {
        return ngaynghi;
    }

    public void setNgaynghi(String ngaynghi) {
        this.ngaynghi = ngaynghi;
    }

    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    public String getTenpx() {
        return tenpx;
    }

    public void setTenpx(String tenpx) {
        this.tenpx = tenpx;
    }

    public String getTenchuyen() {
        return tenchuyen;
    }

    public void setTenchuyen(String tenchuyen) {
        this.tenchuyen = tenchuyen;
    }

    public String getTento() {
        return tento;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getDuyet() {
        return duyet;
    }

    public void setDuyet(String duyet) {
        this.duyet = duyet;
    }


}
