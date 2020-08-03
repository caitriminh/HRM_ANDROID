package com.example.HRM.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LenhTangCa {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("malenh")
    @Expose
    private String malenh;

    @SerializedName("malenh2")
    @Expose
    private String malenh2;

    @SerializedName("ngaytangca")
    @Expose
    private String ngaytangca;
    @SerializedName("mapx")
    @Expose
    private String mapx;
    @SerializedName("tenpx")
    @Expose
    private String tenpx;

    @SerializedName("tenpx2")
    @Expose
    private String tenpx2;

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

    @SerializedName("tinhtrang")
    @Expose
    private String tinhtrang;

    @SerializedName("loaitangca")
    @Expose
    private String loaitangca;

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

    public String getMalenh2() {
        return malenh2;
    }

    public void setMalenh2(String malenh2) {
        this.malenh2 = malenh2;
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

    public String getTenpx2() {
        return tenpx2;
    }

    public void setTenpx2(String tenpx2) {
        this.tenpx2 = tenpx2;
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

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public String getLoaitangca() {
        return loaitangca;
    }

    public void setLoaitangca(String loaitangca) {
        this.loaitangca = loaitangca;
    }
}
