package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NghiPhep {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("manv2")
    @Expose
    private String manv2;

    @SerializedName("tennv")
    @Expose
    private String tennv;
    @SerializedName("ngaynhap")
    @Expose
    private String ngaynhap;
    @SerializedName("ngaynghi")
    @Expose
    private String ngaynghi;
    @SerializedName("songay")
    @Expose
    private String songay;
    @SerializedName("maloainghiphep")
    @Expose
    private String maloainghiphep;
    @SerializedName("lydo")
    @Expose
    private String lydo;
    @SerializedName("tenpx")
    @Expose
    private String tenpx;
    @SerializedName("tenchuyen")
    @Expose
    private String tenchuyen;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    @SerializedName("nguoiduyet")
    @Expose
    private String nguoiduyet;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("tungay")
    @Expose
    private String tungay;

    @SerializedName("denngay")
    @Expose
    private String denngay;

    @SerializedName("status_quanly")
    @Expose
    private String status_quanly;

    @SerializedName("status_nhansu")
    @Expose
    private String status_nhansu;

    @SerializedName("tongso")
    @Expose
    private String tongso;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getNgaynghi() {
        return ngaynghi;
    }

    public void setNgaynghi(String ngaynghi) {
        this.ngaynghi = ngaynghi;
    }

    public String getSongay() {
        return songay;
    }

    public void setSongay(String songay) {
        this.songay = songay;
    }

    public String getMaloainghiphep() {
        return maloainghiphep;
    }

    public void setMaloainghiphep(String maloainghiphep) {
        this.maloainghiphep = maloainghiphep;
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

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getNguoiduyet() {
        return nguoiduyet;
    }

    public void setNguoiduyet(String nguoiduyet) {
        this.nguoiduyet = nguoiduyet;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTungay() {
        return tungay;
    }

    public void setTungay(String tungay) {
        this.tungay = tungay;
    }

    public String getDenngay() {
        return denngay;
    }

    public void setDenngay(String denngay) {
        this.denngay = denngay;
    }

    public String getStatus_quanly() {
        return status_quanly;
    }

    public void setStatus_quanly(String status_quanly) {
        this.status_quanly = status_quanly;
    }

    public String getStatus_nhansu() {
        return status_nhansu;
    }

    public void setStatus_nhansu(String status_nhansu) {
        this.status_nhansu = status_nhansu;
    }

    public String getTongso() {
        return tongso;
    }

    public void setTongso(String tongso) {
        this.tongso = tongso;
    }


}
