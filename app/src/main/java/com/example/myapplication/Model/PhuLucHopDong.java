package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhuLucHopDong {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("masohopdong")
    @Expose
    private String masohopdong;
    @SerializedName("ngayky_phuluc")
    @Expose
    private String ngaykyPhuluc;
    @SerializedName("mucluongcu")
    @Expose
    private String mucluongcu;
    @SerializedName("mucluongmoi")
    @Expose
    private String mucluongmoi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMasohopdong() {
        return masohopdong;
    }

    public void setMasohopdong(String masohopdong) {
        this.masohopdong = masohopdong;
    }

    public String getNgaykyPhuluc() {
        return ngaykyPhuluc;
    }

    public void setNgaykyPhuluc(String ngaykyPhuluc) {
        this.ngaykyPhuluc = ngaykyPhuluc;
    }

    public String getMucluongcu() {
        return mucluongcu;
    }

    public void setMucluongcu(String mucluongcu) {
        this.mucluongcu = mucluongcu;
    }

    public String getMucluongmoi() {
        return mucluongmoi;
    }

    public void setMucluongmoi(String mucluongmoi) {
        this.mucluongmoi = mucluongmoi;
    }

}
