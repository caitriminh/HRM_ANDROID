package com.example.myapplication.ui.LenhTangCa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_NghiPhep_MaNV;
import com.example.myapplication.Adapter.Adapter_NhanVienTangCa_MaLenh;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.NhanVienTangCa;
import com.example.myapplication.Model.NhatKyQuetThe;
import com.example.myapplication.Model.NhomMay;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ornach.nobobutton.NoboButton;
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
    ArrayList<NhanVien> lstNhanVien;
    NhanVien nhanVien;
    Adapter_NhanVienTangCa_MaLenh adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    Integer option = 3;
    String strTuNgay = "", strDenNgay = "", strMaNV="";

    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    TextView txtMaLenh, txtPhanXuong, txtNhomCongViec,  txtPhanXuongTC, txtHoTen;
    EditText txtGhiChu, txtMaNV;
    NoboButton btnLuu, btnDong;

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

        this.setTitle("Nhân Viên Tăng Ca");
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
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setTitle("Xác Nhận")
                .setIcon(R.drawable.message_icon)
                .setMessage("Bạn có muốn xóa đăng ký tăng ca của nhân viên (" + nhanVienTangCa.getTennv() + ") này không?")

                .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = String.valueOf(nhanVienTangCa.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien_tangca", iRequestHttpCallback, "DELETE_NHANVIEN_TANGCA");
                        request.params.put("id", id);
                        request.extraData.put("position", position);
                        request.execute();
                    }
                })
                .setPositiveButton("Bỏ Qua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(false);
        builder.create().show();
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

    public void AddNhanVienTangCa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_diaglog_add_dangky_tangca, null);
        txtMaLenh = view.findViewById(R.id.txtMaLenh);
        txtGhiChu = view.findViewById(R.id.txtGhiChu);
        txtPhanXuong = view.findViewById(R.id.txtPhanXuong);
        txtPhanXuongTC = view.findViewById(R.id.txtPhanXuongTC);
        txtMaNV = view.findViewById(R.id.txtMaNV);
        txtHoTen=view.findViewById(R.id.txtHoTen);
        txtNhomCongViec = view.findViewById(R.id.txtNhomCongViec);


        btnLuu = view.findViewById(R.id.btnLuu);
        btnDong = view.findViewById(R.id.btnDong);


        builder.setView(view)
                .setTitle("Đăng Ký Tăng Ca")
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

//        ShowLoaiTangCa(txtLoaiTangCa);
//        ShowPhanXuong(txtPhanXuong);
//        ShowNhomMay(txtNhomCongViec);

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

                String url = Modules1.BASE_URL + "insert_lenhtangca";
                String TAG = "INSERT_LENHTANGCA";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
//                request.params.put("malenh", strMaLenh);
//                request.params.put("matangca", strMaLoaiTangCa);
//                request.params.put("ngaytangca", strNgayTangCa);
//                request.params.put("mapx", strMaPX);
//                request.params.put("manhom", strMaNhom);
//                request.params.put("ghichu", txtGhiChu.getText().toString());
//                request.params.put("nguoitd", Modules1.tendangnhap);
//                request.extraData.put("malenh", strMaLenh);
                request.execute();

//                dialog.setCancelable(true);
//                dialog.dismiss();

                txtNhomCongViec.setText(" Chọn nhóm công việc: ");
                txtGhiChu.setText("");
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

    @OnClick(R.id.btnThem)
    public void ThemLenhTangCa() {
        AddNhanVienTangCa();
//        LoadLoaiTangCa();
//        LoadPhanXuong();
//        LoadNhomMay();

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
            }
        } else {
            MDToast.makeText(this, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
}