package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LenhTangCa {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("malenh")
    @Expose
    private String malenh;
    @SerializedName("ngaytangca")
    @Expose
    private String ngaytangca;
    @SerializedName("mapx")
    @Expose
    private String mapx;
    @SerializedName("tenpx")
    @Expose
    private String tenpx;

    @SerializedName("manhom")
    @Expose
    private String manhom;

    @SerializedName("nhommay")
    @Expose
    private String nhommay;

    @SerializedName("giobd")
    @Expose
    private String giobd;

    @SerializedName("giokt")
    @Expose
    private String giokt;

    @SerializedName("status")
    @Expose
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
}
