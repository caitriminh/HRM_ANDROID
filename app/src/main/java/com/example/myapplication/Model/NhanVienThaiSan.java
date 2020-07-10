package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhanVienThaiSan {
    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("tennv")
    @Expose
    private String tennv;

    @SerializedName("gioitinh")
    @Expose
    private String gioitinh;

    @SerializedName("ngaynghits")
    @Expose
    private String ngaynghits;

    @SerializedName("ngaylamlai")
    @Expose
    private String ngaylamlai;

    @SerializedName("trangthaithaisan")
    @Expose
    private String trangthaithaisan;

    @SerializedName("tenpx")
    @Expose
    private String tenpx;

    @SerializedName("tenchuyen")
    @Expose
    private String tenchuyen;

    @SerializedName("tento")
    @Expose
    private String tento;

    @SerializedName("hinh")
    @Expose
    private String hinh;


    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
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


    public String getNgaynghits() {
        return ngaynghits;
    }

    public void setNgaynghits(String ngaynghits) {
        this.ngaynghits = ngaynghits;
    }

    public String getNgaylamlai() {
        return ngaylamlai;
    }

    public void setNgaylamlai(String ngaylamlai) {
        this.ngaylamlai = ngaylamlai;
    }

    public String getTrangthaithaisan() {
        return trangthaithaisan;
    }

    public void setTrangthaithaisan(String trangthaithaisan) {
        this.trangthaithaisan = trangthaithaisan;
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

    public void setTento(String tento) {
        this.tento = tento;
    }
    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }


}
