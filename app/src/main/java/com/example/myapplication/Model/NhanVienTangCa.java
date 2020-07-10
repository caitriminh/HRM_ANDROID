package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhanVienTangCa {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("malenh")
    @Expose
    private String malenh;
    @SerializedName("ngaytangca")
    @Expose
    private String ngaytangca;
    @SerializedName("manv")
    @Expose
    private String manv;
    @SerializedName("tennv")
    @Expose
    private String tennv;
    @SerializedName("giobd")
    @Expose
    private String giobd;
    @SerializedName("giokt")
    @Expose
    private String giokt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMalenh() {
        return malenh;
    }

    public void setMalenh(String malenh) {
        this.malenh = malenh;
    }

    public String getNgaytangca() {
        return ngaytangca;
    }

    public void setNgaytangca(String ngaytangca) {
        this.ngaytangca = ngaytangca;
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

    public String getGiobd() {
        return giobd;
    }

    public void setGiobd(String giobd) {
        this.giobd = giobd;
    }

    public String getGiokt() {
        return giokt;
    }

    public void setGiokt(String giokt) {
        this.giokt = giokt;
    }

}
