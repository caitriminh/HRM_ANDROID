package com.example.HRM.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiemDanh {
    @SerializedName("stt")
    @Expose
    private String stt;

    @SerializedName("tenpx")
    @Expose
    private String tenpx;
    @SerializedName("siso")
    @Expose
    private Integer siso;
    @SerializedName("hiendien")
    @Expose
    private Integer hiendien;
    @SerializedName("vang")
    @Expose
    private Integer vang;

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getTenpx() {
        return tenpx;
    }

    public void setTenpx(String tenpx) {
        this.tenpx = tenpx;
    }

    public Integer getSiso() {
        return siso;
    }

    public void setSiso(Integer siso) {
        this.siso = siso;
    }

    public Integer getHiendien() {
        return hiendien;
    }

    public void setHiendien(Integer hiendien) {
        this.hiendien = hiendien;
    }

    public Integer getVang() {
        return vang;
    }

    public void setVang(Integer vang) {
        this.vang = vang;
    }

}
