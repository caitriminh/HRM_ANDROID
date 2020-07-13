package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheDoBaoHiem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("manv")
    @Expose
    private String manv;
    @SerializedName("tennv")
    @Expose
    private String tennv;
    @SerializedName("ngaynhap")
    @Expose
    private String ngaynhap;
    @SerializedName("noikham")
    @Expose
    private String noikham;
    @SerializedName("tungay")
    @Expose
    private String tungay;
    @SerializedName("denngay")
    @Expose
    private String denngay;
    @SerializedName("sotien")
    @Expose
    private String sotien;
    @SerializedName("dathanhtoan")
    @Expose
    private String dathanhtoan;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;

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

    public String getNoikham() {
        return noikham;
    }

    public void setNoikham(String noikham) {
        this.noikham = noikham;
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

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }

    public String getDathanhtoan() {
        return dathanhtoan;
    }

    public void setDathanhtoan(String dathanhtoan) {
        this.dathanhtoan = dathanhtoan;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

}

