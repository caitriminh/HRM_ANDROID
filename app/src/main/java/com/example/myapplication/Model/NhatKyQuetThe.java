package com.example.myapplication.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class NhatKyQuetThe {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("manv")
        @Expose
        private String manv;

        @SerializedName("masothe")
        @Expose
        private String masothe;

        @SerializedName("tennv")
        @Expose
        private String tennv;
        @SerializedName("ngayquet")
        @Expose
        private String ngayquet;
        @SerializedName("lanquet")
        @Expose
        private Integer lanquet;

        @SerializedName("thoigian")
        @Expose
        private String thoigian;

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

        public String getMasothe() {
            return masothe;
        }

        public void setMasothe(String masothe) {
            this.masothe = masothe;
        }

        public String getTennv() {
            return tennv;
        }

        public void setTennv(String tennv) {
            this.tennv = tennv;
        }

        public String getNgayquet() {
            return ngayquet;
        }

        public void setNgayquet(String ngayquet) {
            this.ngayquet = ngayquet;
        }

        public Integer getLanquet() {
            return lanquet;
        }

        public void setLanquet(Integer lanquet) {
            this.lanquet = lanquet;
        }

        public String getThoigian() {
            return thoigian;
        }

        public void setThoigian(String thoigian) {
            this.thoigian = thoigian;
        }

    }


