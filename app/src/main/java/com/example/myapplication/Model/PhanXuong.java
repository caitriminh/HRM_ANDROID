package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhanXuong {

    @SerializedName("mapx")
    @Expose
    private String mapx;
    @SerializedName("tenpx")
    @Expose
    private String tenpx;
    @SerializedName("sudung")
    @Expose
    private Integer sudung;
    @SerializedName("ngaythanhlap")
    @Expose
    private Object ngaythanhlap;
    @SerializedName("nguoidaidien")
    @Expose
    private String nguoidaidien;
    @SerializedName("sodt")
    @Expose
    private Object sodt;
    @SerializedName("sofax")
    @Expose
    private Object sofax;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;
    @SerializedName("nguoitd")
    @Expose
    private String nguoitd;
    @SerializedName("thoigian")
    @Expose
    private String thoigian;
    @SerializedName("nguoitd2")
    @Expose
    private String nguoitd2;
    @SerializedName("thoigian2")
    @Expose
    private String thoigian2;

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getTenpx() {
        return tenpx;
    }

    public void setTenpx(String tenpx) {
        this.tenpx = tenpx;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public Object getNgaythanhlap() {
        return ngaythanhlap;
    }

    public void setNgaythanhlap(Object ngaythanhlap) {
        this.ngaythanhlap = ngaythanhlap;
    }

    public String getNguoidaidien() {
        return nguoidaidien;
    }

    public void setNguoidaidien(String nguoidaidien) {
        this.nguoidaidien = nguoidaidien;
    }

    public Object getSodt() {
        return sodt;
    }

    public void setSodt(Object sodt) {
        this.sodt = sodt;
    }

    public Object getSofax() {
        return sofax;
    }

    public void setSofax(Object sofax) {
        this.sofax = sofax;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getNguoitd() {
        return nguoitd;
    }

    public void setNguoitd(String nguoitd) {
        this.nguoitd = nguoitd;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getNguoitd2() {
        return nguoitd2;
    }

    public void setNguoitd2(String nguoitd2) {
        this.nguoitd2 = nguoitd2;
    }

    public String getThoigian2() {
        return thoigian2;
    }

    public void setThoigian2(String thoigian2) {
        this.thoigian2 = thoigian2;
    }

}

