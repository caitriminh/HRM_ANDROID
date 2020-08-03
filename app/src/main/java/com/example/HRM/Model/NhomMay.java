package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhomMay {

    @SerializedName("manhom")
    @Expose
    private String manhom;
    @SerializedName("nhommay")
    @Expose
    private String nhommay;
    @SerializedName("sudung")
    @Expose
    private Integer sudung;
    @SerializedName("thoigian")
    @Expose
    private String thoigian;
    @SerializedName("nguoitd")
    @Expose
    private String nguoitd;
    @SerializedName("nguoitd2")
    @Expose
    private Object nguoitd2;
    @SerializedName("thoigian2")
    @Expose
    private Object thoigian2;

    public String getManhom() {
        return manhom;
    }

    public void setManhom(String manhom) {
        this.manhom = manhom;
    }

    public String getNhommay() {
        return nhommay;
    }

    public void setNhommay(String nhommay) {
        this.nhommay = nhommay;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getNguoitd() {
        return nguoitd;
    }

    public void setNguoitd(String nguoitd) {
        this.nguoitd = nguoitd;
    }

    public Object getNguoitd2() {
        return nguoitd2;
    }

    public void setNguoitd2(Object nguoitd2) {
        this.nguoitd2 = nguoitd2;
    }

    public Object getThoigian2() {
        return thoigian2;
    }

    public void setThoigian2(Object thoigian2) {
        this.thoigian2 = thoigian2;
    }

}

