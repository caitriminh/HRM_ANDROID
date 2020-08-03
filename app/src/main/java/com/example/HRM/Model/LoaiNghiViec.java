package com.example.HRM.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoaiNghiViec {

    @SerializedName("maloainghiviec")
    @Expose
    private String maloainghiviec;
    @SerializedName("nghiviec")
    @Expose
    private String nghiviec;
    @SerializedName("sudung")
    @Expose
    private Integer sudung;
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

    public String getMaloainghiviec() {
        return maloainghiviec;
    }

    public void setMaloainghiviec(String maloainghiviec) {
        this.maloainghiviec = maloainghiviec;
    }

    public String getNghiviec() {
        return nghiviec;
    }

    public void setNghiviec(String nghiviec) {
        this.nghiviec = nghiviec;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
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

