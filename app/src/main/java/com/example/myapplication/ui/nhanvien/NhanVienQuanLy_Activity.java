package com.example.myapplication.ui.nhanvien;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_DangKyNhanVien;
import com.example.myapplication.Adapter.Adapter_HDLD;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.DangKyNhanVien;
import com.example.myapplication.Model.HopDongLaoDong;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.example.myapplication.ui.PhuLucHopDong.PhuLucHopDong_Activity;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class NhanVienQuanLy_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<DangKyNhanVien> lstDangKyNhanVien;
    Adapter_DangKyNhanVien adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    TextInputEditText txtMaNV, txtHoTen, txtPhanXuong, txtChuyen, txtTo;
    Button btnLuu, btnDong;
    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky_nhanvien);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        iRequestHttpCallback = this;
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadData();

        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this, recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                DangKyNhanVien dangKyNhanVien = lstDangKyNhanVien.get(position);
                Delete_DangKyNhanVien(dangKyNhanVien, position);
            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });

        this.setTitle("Quản Lý Nhân Viên");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnThem)
    public void ThemDangKyNhanVien(View view) {
        AddDangKyNhanVien(view);
    }

    void AddDangKyNhanVien(View view) {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_bottomsheet_add_dangky_nhanvien, null);
        txtMaNV = view_bottom_sheet.findViewById(R.id.txtMaNV);
        txtHoTen = view_bottom_sheet.findViewById(R.id.txtHoTen);
        txtPhanXuong = view_bottom_sheet.findViewById(R.id.txtPhanXuong);
        txtChuyen = view_bottom_sheet.findViewById(R.id.txtChuyen);
        txtTo = view_bottom_sheet.findViewById(R.id.txtTo);

        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);

        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();


        txtMaNV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String manv = txtMaNV.getText().toString();
                if (manv.length() == 6) {
                    LoadThongTinNhanVien(manv);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtMaNV.getText().equals("")) {
                    MDToast.makeText(mContext, "Vui lòng nhập vào mã nhân viên.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }
                String url = Modules1.BASE_URL + "insert_dangky_nhanvien";
                String TAG = "INSERT_DANGKY_NHANVIEN";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("manv", txtMaNV.getText().toString());
                request.params.put("maquanly", Modules1.strMaNV);
                request.params.put("nguoitd", Modules1.tendangnhap);
                request.extraData.put("tennv", txtHoTen.getText().toString());
                request.execute();
            }
        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(true);
                dialog.dismiss();
                fab_menu.close(false);
            }
        });
    }

    public void LoadThongTinNhanVien(String manv) {
        String url = Modules1.BASE_URL + "load_thongtin_nhanvien";
        String TAG = "LOAD_THONGTIN";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 2);
        request.params.put("manv", manv);
        request.execute();
    }


    public void LoadData() {
        String url = Modules1.BASE_URL + "load_nhanvien_quanly";
        String TAG = "LOAD_NHANVIEN_QUANLY";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("maquanly", Modules1.strMaNV);
        request.execute();
    }

    private void Delete_DangKyNhanVien(final DangKyNhanVien dangKyNhanVien, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa đăng ký nhân viên (" + dangKyNhanVien.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String id = String.valueOf(dangKyNhanVien.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_dangky_nhanvien", iRequestHttpCallback, "DELETE_DANGKYNHANVIEN");
                        request.params.put("id", id);
                        request.extraData.put("position", position);
                        request.execute();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Đóng", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "LOAD_NHANVIEN_QUANLY":
                    Gson gson = new Gson();
                    TypeToken<List<DangKyNhanVien>> token = new TypeToken<List<DangKyNhanVien>>() {
                    };
                    List<DangKyNhanVien> dangKyNhanViens = gson.fromJson(responseText, token.getType());
                    lstDangKyNhanVien = new ArrayList<DangKyNhanVien>();
                    lstDangKyNhanVien.addAll(dangKyNhanViens);
                    adapter = new Adapter_DangKyNhanVien(this, lstDangKyNhanVien);
                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "DELETE_DANGKYNHANVIEN":
                    int position = Integer.parseInt(extraData.get("position").toString());
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công đăng ký nhân viên (" + lstDangKyNhanVien.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstDangKyNhanVien.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            MDToast.makeText(mContext, "Đăng ký nhân viên (" + lstDangKyNhanVien.get(position).getTennv() + ") đã được xóa.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
                case "LOAD_THONGTIN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        txtHoTen.setText(jsonObject.getString("tennv"));
                        txtPhanXuong.setText(jsonObject.getString("tenpx"));
                        txtChuyen.setText(jsonObject.getString("tenchuyen"));
                        txtTo.setText(jsonObject.getString("tento"));
                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    }
                    break;
                case "INSERT_DANGKY_NHANVIEN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã đăng ký nhân viên (" + txtHoTen.getText() + ") thành công.", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            LoadData();
                            //Xóa text
                            txtMaNV.setText("");
                            txtHoTen.setText("");
                            txtTo.setText("");
                            txtChuyen.setText("");
                            txtPhanXuong.setText("");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            MDToast.makeText(this, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
}