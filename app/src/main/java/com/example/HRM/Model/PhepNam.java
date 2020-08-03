package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhepNam {
    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("manv2")
    @Expose
    private String manv2;

    @SerializedName("tennv")
    @Expose
    private String tennv;

    @SerializedName("thamnien")
    @Expose
    private String thamnien;

    @SerializedName("phepnam")
    @Expose
    private String phepnam;

    @SerializedName("nghiphepnam")
    @Expose
    private String nghiphepnam;

    @SerializedName("pnconlai")
    @Expose
    private String pnconlai;

    @SerializedName("tenpx")
    @Expose
    private String tenpx;

    @SerializedName("tenchuyen")
    @Expose
    private String tenchuyen;


    @SerializedName("hinh")
    @Expose
    private String hinh;


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

    public String getThamnien() {
        return thamnien;
    }

    public void setThamnien(String thamnien) {
        this.thamnien = thamnien;
    }

    public String getPhepnam() {
        return phepnam;
    }

    public void setPhepnam(String phepnam) {
        this.phepnam = phepnam;
    }

    public String getNghiphepnam() {
        return nghiphepnam;
    }

    public void setNghiphepnam(String nghiphepnam) {
        this.nghiphepnam = nghiphepnam;
    }

    public String getPnconlai() {
        return pnconlai;
    }

    public void setPnconlai(String pnconlai) {
        this.pnconlai = pnconlai;
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


    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }


}
