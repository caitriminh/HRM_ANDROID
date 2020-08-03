package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhanVienThaiSan {
    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("manv2")
    @Expose
    private String manv2;

    @SerializedName("tennv")
    @Expose
    private String tennv;

    @SerializedName("lanmangthai")
    @Expose
    private Integer lanmangthai;

    @SerializedName("lanmangthai2")
    @Expose
    private String lanmangthai2;

    @SerializedName("ngaynghits")
    @Expose
    private String ngaynghits;

    @SerializedName("ngaylamlai")
    @Expose
    private String ngaylamlai;

    @SerializedName("trangthaithaisan")
    @Expose
    private String trangthaithaisan;

    @SerializedName("tenpx")
    @Expose
    private String tenpx;

    @SerializedName("tenchuyen")
    @Expose
    private String tenchuyen;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    @SerializedName("matrangthai")
    @Expose
    private String matrangthai;

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

    public Integer getLanmangthai() {
        return lanmangthai;
    }

    public void setLanmangthai(Integer lanmangthai) {
        this.lanmangthai = lanmangthai;
    }

    public String getLanmangthai2() {
        return lanmangthai2;
    }

    public void setLanmangthai2(String lanmangthai2) {
        this.lanmangthai2 = lanmangthai2;
    }

    public String getNgaynghits() {
        return ngaynghits;
    }

    public void setNgaynghits(String ngaynghits) {
        this.ngaynghits = ngaynghits;
    }

    public String getNgaylamlai() {
        return ngaylamlai;
    }

    public void setNgaylamlai(String ngaylamlai) {
        this.ngaylamlai = ngaylamlai;
    }

    public String getTrangthaithaisan() {
        return trangthaithaisan;
    }

    public void setTrangthaithaisan(String trangthaithaisan) {
        this.trangthaithaisan = trangthaithaisan;
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

    public String getMatrangthai() {
        return matrangthai;
    }

    public void setMatrangthai(String matrangthai) {
        this.matrangthai = matrangthai;
    }


}
