package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CTPhepNam {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("manv")
    @Expose
    private String manv;
    @SerializedName("tungay")
    @Expose
    private String tungay;
    @SerializedName("denngay")
    @Expose
    private String denngay;
    @SerializedName("songay")
    @Expose
    private String songay;

    @SerializedName("ghichu")
    @Expose
    private String ghichu;

    @SerializedName("tong")
    @Expose
    private String tong;

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

    public String getSongay() {
        return songay;
    }

    public void setSongay(String songay) {
        this.songay = songay;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTong() {
        return tong;
    }

    public void setTong(String tong) {
        this.tong = tong;
    }
}
