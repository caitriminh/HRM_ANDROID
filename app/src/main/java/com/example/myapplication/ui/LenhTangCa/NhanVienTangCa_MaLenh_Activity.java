package com.example.myapplication.ui.LenhTangCa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_NghiPhep_MaNV;
import com.example.myapplication.Adapter.Adapter_NhanVienTangCa_MaLenh;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.LoaiNghiPhep;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.NhanVienTangCa;
import com.example.myapplication.Model.NhatKyQuetThe;
import com.example.myapplication.Model.NhomMay;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.example.myapplication.ui.ScanQR.ScanQR_Activity;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ornach.nobobutton.NoboButton;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class NhanVienTangCa_MaLenh_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<NhanVienTangCa> lstNhanVienTangCa;
    ArrayList<NhomMay> lstNhomMay;
    ArrayList<NhanVien> lstNhanVien;
    NhanVien nhanVien;
    NhomMay nhomMay;
    Adapter_NhanVienTangCa_MaLenh adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    Integer option = 3;
    String strTuNgay = "", strDenNgay = "", strMaNV = "", strNhomMay = "";

    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    TextInputEditText txtMaLenh, txtPhanXuong, txtNhomCongViec, txtPhanXuongTC, txtHoTen, txtGhiChu, txtMaNV;
    Button btnLuu, btnDong;

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nhanvien_tangca);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        iRequestHttpCallback = this;
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadData();

        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                NhanVienTangCa nhanVienTangCa = (NhanVienTangCa) lstNhanVienTangCa.get(position);
                Delete_NhanVienTangCa(nhanVienTangCa, position);
            }
        }));

        this.setTitle("Đăng Ký (" + Modules1.objLenhTangCa.getNgaytangca() + ", " + Modules1.objLenhTangCa.getNhommay() + ")");
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });
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

    private void Delete_NhanVienTangCa(final NhanVienTangCa nhanVienTangCa, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa đăng ký tăng ca của nhân viên (" + nhanVienTangCa.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String id = String.valueOf(nhanVienTangCa.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien_tangca", iRequestHttpCallback, "DELETE_NHANVIEN_TANGCA");
                        request.params.put("id", id);
                        request.extraData.put("position", position);
                        request.execute();
                        dialogInterface.dismiss();
                    }

                })
                .setNegativeButton("Bỏ Qua", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }

                })
                .build();
        mBottomSheetDialog.show();
    }

    public void LoadData() {
        String url = Modules1.BASE_URL + "load_nhanvien_tangca";
        String TAG = "LOAD_NHANVIEN_TANGCA";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 2);
        request.params.put("tungay", "");
        request.params.put("denngay", "");
        request.params.put("malenh", Modules1.objLenhTangCa.getMalenh());
        request.params.put("manv", "");
        request.execute();
    }

    public void AddNhanVienTangCa(View view) {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_bottomsheet_add_dangky_tangca, null);
        txtMaLenh = view_bottom_sheet.findViewById(R.id.txtMaLenh);
        txtGhiChu = view_bottom_sheet.findViewById(R.id.txtGhiChu);
        txtPhanXuong = view_bottom_sheet.findViewById(R.id.txtPhanXuong);
        txtPhanXuongTC = view_bottom_sheet.findViewById(R.id.txtPhanXuongTC);
        txtMaNV = view_bottom_sheet.findViewById(R.id.txtMaNV);
        txtHoTen = view_bottom_sheet.findViewById(R.id.txtHoTen);
        txtNhomCongViec = view_bottom_sheet.findViewById(R.id.txtNhomCongViec);


        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        txtMaLenh.setText(Modules1.objLenhTangCa.getMalenh());
        txtPhanXuongTC.setText(Modules1.objLenhTangCa.getTenpx());
        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        ShowLoaiNhomMay(txtNhomCongViec);

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
                    MDToast.makeText(mContext, "Vui lòng nhập vào mã nhân viên để đăng ký tăng ca.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }
                if (txtNhomCongViec.getText().toString().equals("")) {
                    MDToast.makeText(mContext, "Bạn vui lòng chọn nhóm công việc cần tăng ca", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                String url = Modules1.BASE_URL + "insert_dangky_tangca";
                String TAG = "INSERT_DANGKYTANGCA";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("malenh", Modules1.objLenhTangCa.getMalenh());
                request.params.put("manv", txtMaNV.getText());
                request.params.put("manhom", strNhomMay);

                request.params.put("ghichu", txtGhiChu.getText().toString());
                request.params.put("nguoitd", Modules1.tendangnhap);
                request.extraData.put("malenh", Modules1.objLenhTangCa.getMalenh());
                request.execute();
                txtMaNV.setText("");
                txtPhanXuong.setText("");
                txtHoTen.setText("");

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

    public void ShowLoaiNhomMay(TextView txtNhomCongViec) {
        txtNhomCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Chọn nhóm công việc");
                builder.setCancelable(false);
                String[] arrayNhomMay = new String[lstNhomMay.size()];
                int i = 0;
                for (NhomMay nhomMay : lstNhomMay) {
                    arrayNhomMay[i] = nhomMay.getNhommay();
                    i++;
                }
                ;
                builder.setSingleChoiceItems(arrayNhomMay, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NhomMay nhomMay = lstNhomMay.get(i);
                        txtNhomCongViec.setText("Nhóm công việc: " + nhomMay.getNhommay());
                        strNhomMay = nhomMay.getManhom();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void LoadNhomMay() {
        String url = Modules1.BASE_URL + "load_nhommay_lenhtangca";
        String TAG = "LOAD_NHOMMAY";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("malenh", Modules1.objLenhTangCa.getMalenh());
        request.params.put("mapx", Modules1.objLenhTangCa.getMapx());
        request.execute();
    }


    @OnClick(R.id.btnThem)
    public void ThemLenhTangCa(View view) {
        AddNhanVienTangCa(view);
        LoadNhomMay();

    }

    @OnClick(R.id.btnQR)
    public void ScanQR() {
        Intent intent = new Intent(mContext, ScanQR_Activity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 101) {
                strMaNV = data.getStringExtra("result");
                if (strMaNV.isEmpty()) {
                    fab_menu.close(false);
                    return;
                }

                String url = Modules1.BASE_URL + "insert_dangky_tangca";
                String TAG = "INSERT_DANGKYTANGCA";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("malenh", Modules1.objLenhTangCa.getMalenh());
                request.params.put("manv", strMaNV);
                request.params.put("manhom", Modules1.objLenhTangCa.getManhom());

                request.params.put("ghichu", "SCAN QR");
                request.params.put("nguoitd", Modules1.tendangnhap);
                request.extraData.put("malenh", Modules1.objLenhTangCa.getMalenh());
                request.execute();
            }

        }
    }


    public void LoadThongTinNhanVien(String manv) {
        String url = Modules1.BASE_URL + "load_thongtin_nhanvien";
        String TAG = "LOAD_THONGTIN";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 2);
        request.params.put("manv", manv);
        request.execute();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        JSONObject jsonObject = null;
        if (isSuccess) {
            switch (TAG) {
                case "LOAD_NHANVIEN_TANGCA":
                    Gson gson = new Gson();
                    TypeToken<List<NhanVienTangCa>> token = new TypeToken<List<NhanVienTangCa>>() {
                    };
                    List<NhanVienTangCa> nhanVienTangCas = gson.fromJson(responseText, token.getType());
                    lstNhanVienTangCa = new ArrayList<NhanVienTangCa>();
                    lstNhanVienTangCa.addAll(nhanVienTangCas);
                    adapter = new Adapter_NhanVienTangCa_MaLenh(this, lstNhanVienTangCa);
                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "DELETE_NHANVIEN_TANGCA":
                    try {
                        jsonObject = new JSONObject(responseText);
                        int position = Integer.parseInt(extraData.get("position").toString());
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(this, "Đã xóa thành công đăng ký tăng ca của nhân viên (" + lstNhanVienTangCa.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstNhanVienTangCa.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            MDToast.makeText(this, "Đăng ký tăng ca của nhân viên (" + lstNhanVienTangCa.get(position).getTennv() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(this, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
                case "INSERT_DANGKYTANGCA":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(this, "Đã đăng ký thành công.", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                            LoadData();
                            fab_menu.close(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "LOAD_THONGTIN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String tennv = jsonObject.getString("tennv");
                        String tenpx = jsonObject.getString("tenpx");
                        txtHoTen.setText(tennv);
                        txtPhanXuong.setText(tenpx);
                    } catch (JSONException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case "LOAD_NHOMMAY":
                    Gson gsonNhomMay = new Gson();
                    TypeToken<List<NhomMay>> tokenNhomMay = new TypeToken<List<NhomMay>>() {
                    };
                    List<NhomMay> nhomMays = gsonNhomMay.fromJson(responseText, tokenNhomMay.getType());
                    lstNhomMay = new ArrayList<NhomMay>();
                    lstNhomMay.addAll(nhomMays);
                    break;
            }
        } else {
            MDToast.makeText(this, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
}