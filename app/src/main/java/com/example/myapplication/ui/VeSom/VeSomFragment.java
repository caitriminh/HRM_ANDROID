package com.example.myapplication.ui.VeSom;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_VeSom;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.LoaiNghiPhep;
import com.example.myapplication.Model.VeSom;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.example.myapplication.ui.ScanQR.ScanQR_Activity;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ornach.nobobutton.NoboButton;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VeSomFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<VeSom> lstVesom;
    ArrayList<LoaiNghiPhep> lstLoaiNghiPhep;
    VeSom veSom;
    LoaiNghiPhep loaiNghiPhep;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_VeSom adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Context mContext;
    String StrMaloainghiphep = "", StrGioRa = "", StrGioVao = "", StrTuNgay = "", StrDenNgay = "", StrMaNV = "";
    Integer mMinute = 0, mHour = 0, option = 1;
    TextInputEditText txtGioRa, txtMaNV, txtHoTen, txtPhanXuong, txtGioVao, txtGhiChu, txtLyDo;
    Button btnLuu, btnDong;
//    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
        MainActivity.SetTileActionBar("Đăng Ký Về Sớm");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Đăng Ký Về Sớm");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vesom, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        iRequestHttpCallback = this;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadData();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });
    }


    public void LoadData() {
        String url = Modules1.BASE_URL + "load_vesom";
        String TAG = "LOAD_VESOM";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", option);
        request.params.put("tungay", StrTuNgay);
        request.params.put("denngay", StrDenNgay);
        request.params.put("manv", StrMaNV);
        request.execute();
    }

    @OnClick(R.id.btnThem)
    public void ThemVeSom(View view) {
        AddNhanVienVeSom(view);
        LoadLoaiNghiPhep();
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
            StrMaNV = data.getStringExtra("result");
            //MDToast.makeText(mContext, StrMaNV, Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
            option = 3;
            LoadData();
            fab_menu.close(false);
        }
    }

    @OnClick(R.id.btnTimKiem)
    public void TimKiemTheoNgay() {
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2020, 07, 06);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2020, 07, 10);
//
//        CalendarConstraints.Builder builderCongtraints = new CalendarConstraints.Builder();
//        builderCongtraints.setStart(startDate.getTimeInMillis());
//        builderCongtraints.setEnd(startDate.getTimeInMillis());

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

//        builder.setSelection(new Pair(startDate.getTimeInMillis(), endDate.getTimeInMillis()));
//        builder.setCalendarConstraints(builderCongtraints.build());
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
                StrTuNgay = formatter.format(calendarStart.getTime());
                StrDenNgay = formatter.format(calendarEnd.getTime());
                LoadData();
                fab_menu.close(false);
            }
        });

    }

    void AddNhanVienVeSom(View view) {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_bottomsheet_add_vesom, null);
        txtMaNV = view_bottom_sheet.findViewById(R.id.txtMaNV);
        txtHoTen = view_bottom_sheet.findViewById(R.id.txtHoTen);
        txtPhanXuong = view_bottom_sheet.findViewById(R.id.txtPhanXuong);
        txtGioRa = view_bottom_sheet.findViewById(R.id.txtGioRa);
        txtGioVao = view_bottom_sheet.findViewById(R.id.txtGioVao);
        txtLyDo = view_bottom_sheet.findViewById(R.id.txtLyDo);
        txtGhiChu = view_bottom_sheet.findViewById(R.id.txtGhiChu);

        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);

        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();


        ShowLoaiNghiPhep(txtLyDo);

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


        txtGioRa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtGioRa.setText(hourOfDay + ":" + minute);
                        StrGioRa = hourOfDay + ":" + minute;
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        txtGioVao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtGioVao.setText(hourOfDay + ":" + minute);
                        StrGioVao = hourOfDay + ":" + minute;
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtMaNV.getText().equals("")) {
                    MDToast.makeText(getActivity(), "Vui lòng nhập vào mã nhân viên.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (StrMaloainghiphep.equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng chọn loại về sớm", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (StrGioRa.equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng nhập vào giờ ra", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }


                String url = Modules1.BASE_URL + "insert_nhanvien_vesom";
                String TAG = "INSERT_NHANVIEN_VESOM";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("manv", txtMaNV.getText().toString());
                request.params.put("maloainghiphep", StrMaloainghiphep);
                request.params.put("giora", StrGioRa);
                request.params.put("giovao", StrGioVao);

                request.params.put("ghichu", txtGhiChu.getText().toString());
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


    public void ShowLoaiNghiPhep(TextView txtLoaiNghiPhep) {
        txtLoaiNghiPhep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn loại nghỉ phép");
                builder.setCancelable(false);
                String[] arrayLoaiNghiPhep = new String[lstLoaiNghiPhep.size()];
                int i = 0;
                for (LoaiNghiPhep loaiNghiPhep : lstLoaiNghiPhep) {
                    arrayLoaiNghiPhep[i] = loaiNghiPhep.getLoainghiphep();
                    i++;
                }
                ;
                builder.setSingleChoiceItems(arrayLoaiNghiPhep, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiNghiPhep loaiNghiPhep = lstLoaiNghiPhep.get(i);
                        txtLoaiNghiPhep.setText(loaiNghiPhep.getLoainghiphep());
                        StrMaloainghiphep = loaiNghiPhep.getMaloainghiphep();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void LoadLoaiNghiPhep() {
        String url = Modules1.BASE_URL + "load_loainghiphep";
        String TAG = "LOAD_LOAINGHIPHEP";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 3);
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

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "LOAD_VESOM":
                    Gson gson = new Gson();
                    TypeToken<List<VeSom>> token = new TypeToken<List<VeSom>>() {
                    };
                    List<VeSom> veSoms = gson.fromJson(responseText, token.getType());
                    lstVesom = new ArrayList<VeSom>();
                    lstVesom.addAll(veSoms);
                    adapter = new Adapter_VeSom(getActivity(), lstVesom);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "INSERT_NHANVIEN_VESOM":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(getActivity(), "Đã đăng ký về sớm thành công.", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            LoadData();
                            fab_menu.close(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "LOAD_LOAINGHIPHEP":
                    Gson gson_loainghiphep = new Gson();
                    TypeToken<List<LoaiNghiPhep>> token_loainghiphep = new TypeToken<List<LoaiNghiPhep>>() {
                    };
                    List<LoaiNghiPhep> donVis = gson_loainghiphep.fromJson(responseText, token_loainghiphep.getType());
                    lstLoaiNghiPhep = new ArrayList<LoaiNghiPhep>();
                    lstLoaiNghiPhep.addAll(donVis);
                    break;

                case "LOAD_THONGTIN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String tennv = jsonObject.getString("tennv");
                        String tenpx = jsonObject.getString("tenpx");
                        txtHoTen.setText(tennv);
                        txtPhanXuong.setText(tenpx);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
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

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        //searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
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