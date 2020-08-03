package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhatKyKhamThai {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("manv")
    @Expose
    private String manv;
    @SerializedName("ngaykhamthai")
    @Expose
    private String ngaykhamthai;
    @SerializedName("sotuan")
    @Expose
    private Double sotuan;
    @SerializedName("ngaydu25tuan")
    @Expose
    private String ngaydu25tuan;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;

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

    public String getNgaykhamthai() {
        return ngaykhamthai;
    }

    public void setNgaykhamthai(String ngaykhamthai) {
        this.ngaykhamthai = ngaykhamthai;
    }

    public Double getSotuan() {
        return sotuan;
    }

    public void setSotuan(Double sotuan) {
        this.sotuan = sotuan;
    }

    public String getNgaydu25tuan() {
        return ngaydu25tuan;
    }

    public void setNgaydu25tuan(String ngaydu25tuan) {
        this.ngaydu25tuan = ngaydu25tuan;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

}
