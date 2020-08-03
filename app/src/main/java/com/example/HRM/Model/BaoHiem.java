package com.example.HRM.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaoHiem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sobhxh")
    @Expose
    private String sobhxh;
    @SerializedName("sothebhyt")
    @Expose
    private String sothebhyt;
    @SerializedName("ngaybatdau")
    @Expose
    private String ngaybatdau;
    @SerializedName("manv")
    @Expose
    private String manv;

    @SerializedName("manv2")
    @Expose
    private String manv2;

    @SerializedName("masohopdong")
    @Expose
    private String masohopdong;
    @SerializedName("tennv")
    @Expose
    private String tennv;
    @SerializedName("gioitinh")
    @Expose
    private String gioitinh;
    @SerializedName("ngaysinh")
    @Expose
    private String ngaysinh;
    @SerializedName("tenpx")
    @Expose
    private String tenpx;
    @SerializedName("mucdong")
    @Expose
    private String mucdong;
    @SerializedName("traso")
    @Expose
    private String traso;

    @SerializedName("traso2")
    @Expose
    private String traso2;

    @SerializedName("ngaybd")
    @Expose
    private String ngaybd;
    @SerializedName("ngaykt")
    @Expose
    private String ngaykt;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSobhxh() {
        return sobhxh;
    }

    public void setSobhxh(String sobhxh) {
        this.sobhxh = sobhxh;
    }

    public String getSothebhyt() {
        return sothebhyt;
    }

    public void setSothebhyt(String sothebhyt) {
        this.sothebhyt = sothebhyt;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
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

    public String getMasohopdong() {
        return masohopdong;
    }

    public void setMasohopdong(String masohopdong) {
        this.masohopdong = masohopdong;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getTenpx() {
        return tenpx;
    }

    public void setTenpx(String tenpx) {
        this.tenpx = tenpx;
    }

    public String getMucdong() {
        return mucdong;
    }

    public void setMucdong(String mucdong) {
        this.mucdong = mucdong;
    }

    public String getTraso() {
        return traso;
    }

    public void setTraso(String traso) {
        this.traso = traso;
    }

    public String getNgaybd() {
        return ngaybd;
    }

    public void setNgaybd(String ngaybd) {
        this.ngaybd = ngaybd;
    }

    public String getNgaykt() {
        return ngaykt;
    }

    public void setNgaykt(String ngaykt) {
        this.ngaykt = ngaykt;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTraso2() {
        return traso2;
    }

    public void setTraso2(String traso2) {
        this.traso2 = traso2;
    }
}

