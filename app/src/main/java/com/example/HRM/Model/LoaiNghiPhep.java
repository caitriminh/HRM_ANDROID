package com.example.HRM.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoaiNghiPhep {

    @SerializedName("maloainghiphep")
    @Expose
    private String maloainghiphep;
    @SerializedName("loainghiphep")
    @Expose
    private String loainghiphep;

    public String getMaloainghiphep() {
        return maloainghiphep;
    }

    public void setMaloainghiphep(String maloainghiphep) {
        this.maloainghiphep = maloainghiphep;
    }

    public String getLoainghiphep() {
        return loainghiphep;
    }

    public void setLoainghiphep(String loainghiphep) {
        this.loainghiphep = loainghiphep;
    }

}

