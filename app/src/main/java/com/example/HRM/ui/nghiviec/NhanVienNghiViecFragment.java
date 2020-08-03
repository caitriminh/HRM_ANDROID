package com.example.HRM.ui.nghiviec;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.HRM.Adapter.Adapter_NhanVienNghiViec;
import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.MainActivity;
import com.example.HRM.Model.LoaiNghiViec;
import com.example.HRM.Model.NhanVienNghiViec;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.ui.ScanQR.ScanQR_Activity;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NhanVienNghiViecFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<NhanVienNghiViec> lstNhanVienNghiViec;
    ArrayList<LoaiNghiViec> lstLoaiNghiViec;
    NhanVienNghiViec nhanVienNghiViec;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_NhanVienNghiViec adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Context mContext;

    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;
    TextInputEditText txtMaNV, txtHoTen, txtPhanXuong, txtNgayNhap, txtNgayNghi, txtLoaiNghiViec, txtGhiChu;
    Button btnLuu, btnDong;
    String strMaNV = "", strNgayNhap = "", strNgayNghi = "", strMaLoaiNghiViec = "", strTuNgay = "", strDenNgay = "";
    Integer option = 1;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.SetTileActionBar("Nhân Viên Nghỉ Việc");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Nhân Viên Nghỉ Việc");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nhanvien_nghiviec, container, false);
        unbinder = ButterKnife.bind(this, view);
        iRequestHttpCallback = this;
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadData();

        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });
    }

    @OnClick(R.id.btnThem)
    public void ThemNghiViec() {
        LoadLoaiNghiViec();
        ThemNhanVienNghiViec();

    }

    void ThemNhanVienNghiViec() {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_bottomsheet_add_nghiviec, null);
        txtMaNV = view_bottom_sheet.findViewById(R.id.txtMaNV);
        txtHoTen = view_bottom_sheet.findViewById(R.id.txtHoTen);
        txtPhanXuong = view_bottom_sheet.findViewById(R.id.txtPhanXuong);
        txtLoaiNghiViec = view_bottom_sheet.findViewById(R.id.txtLoaiNghiViec);
        txtNgayNhap = view_bottom_sheet.findViewById(R.id.txtNgayNhap);
        txtNgayNghi = view_bottom_sheet.findViewById(R.id.txtNgayNghi);
        txtGhiChu = view_bottom_sheet.findViewById(R.id.txtGhiChu);

        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);

        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();


        ShowLoaiNghiViec(txtLoaiNghiViec);

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

        //Thêm ngày nhập
        txtNgayNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtNgayNhap.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                strNgayNhap = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        txtNgayNghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtNgayNghi.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                strNgayNghi = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtMaNV.getText().equals("")) {
                    MDToast.makeText(getActivity(), "Vui lòng nhập vào mã nhân viên.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (strNgayNghi.equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng chọn ngày nghỉ việc", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (strMaLoaiNghiViec.equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng nhập vào loại nghỉ việc", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }


                String url = Modules1.BASE_URL + "insert_nhanvien_nghiviec";
                String TAG = "INSERT_NHANVIEN_NGHIVIEC";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("manv", txtMaNV.getText().toString());
                request.params.put("maloainghiviec", strMaLoaiNghiViec);
                request.params.put("ngaynghi", strNgayNghi);
                request.params.put("ngaynopdon", strNgayNhap);
                request.params.put("lydo", txtGhiChu.getText().toString());
                request.params.put("nguoitd", Modules1.tendangnhap);
                request.extraData.put("tennv", txtHoTen.getText().toString());
                request.execute();

                dialog.setCancelable(true);
                dialog.dismiss();

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

    public void ShowLoaiNghiViec(TextView txtLoaiNghiViec) {
        txtLoaiNghiViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn loại nghỉ việc");
                builder.setCancelable(false);
                String[] arrayLoaiNghiViec = new String[lstLoaiNghiViec.size()];
                int i = 0;
                for (LoaiNghiViec loaiNghiViec : lstLoaiNghiViec) {
                    arrayLoaiNghiViec[i] = loaiNghiViec.getNghiviec();
                    i++;
                }
                ;
                builder.setSingleChoiceItems(arrayLoaiNghiViec, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiNghiViec loaiNghiViec = lstLoaiNghiViec.get(i);
                        txtLoaiNghiViec.setText(loaiNghiViec.getNghiviec());
                        txtGhiChu.setText(loaiNghiViec.getNghiviec());
                        strMaLoaiNghiViec = loaiNghiViec.getMaloainghiviec();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void LoadLoaiNghiViec() {
        String url = Modules1.BASE_URL + "load_loainghiviec";
        String TAG = "LOAD_LOAINGHIVIEC";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.execute();
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
        String url = Modules1.BASE_URL + "load_nhanvien_nghiviec_android";
        String TAG = "LOAD_NHANVIEN_NGHIVIEC";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", option);
        request.params.put("manv", strMaNV);
        request.params.put("tungay", strTuNgay);
        request.params.put("denngay", strDenNgay);
        request.execute();
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
            strMaNV = data.getStringExtra("result");
            option = 3;
            LoadData();
            fab_menu.close(false);
        }
    }

    @OnClick(R.id.btnTimKiem)
    public void TimKiemTheoNgay() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker picker = builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar).build();
        picker.show(getActivity().getSupportFragmentManager(), picker.toString());
        picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.dismiss();
                fab_menu.close(false);
            }
        });

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Object data = selection;
                Pair<Long, Long> result = (Pair<Long, Long>) selection;
                long startDate = result.first;
                long endDate = result.second;
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(startDate);

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(endDate);
                option = 2;
                strTuNgay = formatter.format(calendarStart.getTime());
                strDenNgay = formatter.format(calendarEnd.getTime());
                LoadData();
                fab_menu.close(false);
            }
        });

    }


    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "LOAD_NHANVIEN_NGHIVIEC":
                    Gson gson = new Gson();
                    TypeToken<List<NhanVienNghiViec>> token = new TypeToken<List<NhanVienNghiViec>>() {
                    };
                    List<NhanVienNghiViec> nhanVienNghiViecs = gson.fromJson(responseText, token.getType());
                    lstNhanVienNghiViec = new ArrayList<NhanVienNghiViec>();
                    lstNhanVienNghiViec.addAll(nhanVienNghiViecs);
                    adapter = new Adapter_NhanVienNghiViec(getActivity(), lstNhanVienNghiViec, recycleView);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "LOAD_LOAINGHIVIEC":
                    Gson gsonLoaiNghiViec = new Gson();
                    TypeToken<List<LoaiNghiViec>> tokenLoaiNghiViec = new TypeToken<List<LoaiNghiViec>>() {
                    };
                    List<LoaiNghiViec> loaiNghiViecs = gsonLoaiNghiViec.fromJson(responseText, tokenLoaiNghiViec.getType());
                    lstLoaiNghiViec = new ArrayList<LoaiNghiViec>();
                    lstLoaiNghiViec.addAll(loaiNghiViecs);
                    break;
                case "LOAD_THONGTIN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        txtHoTen.setText(jsonObject.getString("tennv"));
                        txtPhanXuong.setText(jsonObject.getString("tenpx"));

                        strNgayNhap = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
                        txtNgayNhap.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
                    } catch (JSONException e) {
                        MDToast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    }
                    break;
                case "INSERT_NHANVIEN_NGHIVIEC":
                    LoadData();
                    txtMaNV.setText("");
                    txtNgayNghi.setText("");
                    strNgayNghi = "";
                    txtPhanXuong.setText("");
                    txtLoaiNghiViec.setText("");
                    txtGhiChu.setText("");
                    fab_menu.close(false);
                    break;
            }
        } else {
            MDToast.makeText(getActivity(), "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_item, menu);
        menuInflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Tìm kiếm...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter == null) {
                    Log.w("adapter null", "null");
                }
                adapter.filter(newText);
                return false;
            }
        });
    }


}