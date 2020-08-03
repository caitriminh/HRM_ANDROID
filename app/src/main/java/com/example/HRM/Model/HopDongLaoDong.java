package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HopDongLaoDong {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("masohopdong")
    @Expose
    private String masohopdong;

    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("manv2")
    @Expose
    private String manv2;

    @SerializedName("tennv")
    @Expose
    private String tennv;
    @SerializedName("ngayky")
    @Expose
    private String ngayky;
    @SerializedName("ngayhieuluc")
    @Expose
    private String ngayhieuluc;
    @SerializedName("tinhtrang_hd")
    @Expose
    private String tinhtrang_hd;
    @SerializedName("tenpx")
    @Expose
    private String tenpx;
    @SerializedName("chucvu")
    @Expose
    private String chucvu;
    @SerializedName("loaihopdong")
    @Expose
    private String loaihopdong;
    @SerializedName("mucluong")
    @Expose
    private String mucluong;
    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("hinhthuctraluong")
    @Expose
    private String hinhthuctraluong;

    @SerializedName("noidungcongviec")
    @Expose
    private String noidungcongviec;

    @SerializedName("tinhtrang")
    @Expose
    private String tinhtrang;

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

    public String getNgayky() {
        return ngayky;
    }

    public void setNgayky(String ngayky) {
        this.ngayky = ngayky;
    }

    public String getNgayhieuluc() {
        return ngayhieuluc;
    }

    public void setNgayhieuluc(String ngayhieuluc) {
        this.ngayhieuluc = ngayhieuluc;
    }

    public String getTinhtrang_hd() {
        return tinhtrang_hd;
    }

    public void setTinhtrang_hd(String tinhtrang_hb) {
        this.tinhtrang_hd = tinhtrang_hd;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getTenpx() {
        return tenpx;
    }

    public void setTenpx(String tenpx) {
        this.tennv = tenpx;
    }

    public String getLoaihopdong() {
        return loaihopdong;
    }

    public void setLoaihopdong(String loaihopdong) {
        this.loaihopdong = loaihopdong;
    }

    public String getMucluong() {
        return mucluong;
    }

    public void setMucluong(String mucluong) {
        this.mucluong = mucluong;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getHinhthuctraluong() {
        return hinhthuctraluong;
    }

    public void setHinhthuctraluong(String hinhthuctraluong) {
        this.hinhthuctraluong = hinhthuctraluong;
    }

    public String getNoidungcongviec() {
        return noidungcongviec;
    }

    public void setNoidungcongviec(String noidungcongviec) {
        this.noidungcongviec = noidungcongviec;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
}

