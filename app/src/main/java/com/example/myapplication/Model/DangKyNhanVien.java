package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DangKyNhanVien {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("manv2")
    @Expose
    private String manv2;

    @SerializedName("tennv")
    @Expose
    private String tennv;



    @SerializedName("ngaysinh")
    @Expose
    private String ngaysinh;

    @SerializedName("noisinh")
    @Expose
    private String noisinh;

    @SerializedName("tenpx")
    @Expose
    private String tenpx;

    @SerializedName("tenchuyen")
    @Expose
    private String tenchuyen;

    @SerializedName("tento")
    @Expose
    private String tento;

    @SerializedName("chucvu")
    @Expose
    private String chucvu;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("status")
    @Expose
    private String status;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getNoisinh() {
        return noisinh;
    }

    public void setNoisinh(String noisinh) {
        this.noisinh = noisinh;
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

    public String getTento() {
        return tento;
    }

    public void setTento(String tento) {
        this.tento = tento;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getChucvu() {
        return chucvu;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
