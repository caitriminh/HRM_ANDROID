package com.example.myapplication;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.example.myapplication.Model.HopDongLaoDong;
import com.example.myapplication.Model.LenhTangCa;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.PhepNam;
import com.example.myapplication.Model.VeSom;

public class Modules1 {
    public static RequestQueue main_volley_queue;
    public static Context app_context;
    public static String BASE_URL = "http://caitriminh.xyz/index.php/api_hrm/";
    public static String strUserName;
    public static String tendangnhap = "ADMIN";
    public static NhanVien objNhanVien;
    public static LenhTangCa objLenhTangCa;
    public static HopDongLaoDong objHopDong;
    public static PhepNam objPhepNam;
    public static VeSom objVeSom;
    public static String strMaNV;

}
