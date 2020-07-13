package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoaiTangCa {

    @SerializedName("matangca")
    @Expose
    private String matangca;
    @SerializedName("loaitangca")
    @Expose
    private String loaitangca;
    @SerializedName("giobd")
    @Expose
    private String giobd;
    @SerializedName("giokt")
    @Expose
    private String giokt;
    @SerializedName("ghichu")
    @Expose
    private String ghichu;
    @SerializedName("sudung")
    @Expose
    private Integer sudung;
    @SerializedName("nguoitd")
    @Expose
    private String nguoitd;
    @SerializedName("thoigian")
    @Expose
    private String thoigian;
    @SerializedName("nguoitd2")
    @Expose
    private String nguoitd2;
    @SerializedName("thoigian2")
    @Expose
    private String thoigian2;

    public String getMatangca() {
        return matangca;
    }

    public void setMatangca(String matangca) {
        this.matangca = matangca;
    }

    public String getLoaitangca() {
        return loaitangca;
    }

    public void setLoaitangca(String loaitangca) {
        this.loaitangca = loaitangca;
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

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public String getNguoitd() {
        return nguoitd;
    }

    public void setNguoitd(String nguoitd) {
        this.nguoitd = nguoitd;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getNguoitd2() {
        return nguoitd2;
    }

    public void setNguoitd2(String nguoitd2) {
        this.nguoitd2 = nguoitd2;
    }

    public String getThoigian2() {
        return thoigian2;
    }

    public void setThoigian2(String thoigian2) {
        this.thoigian2 = thoigian2;
    }

}

