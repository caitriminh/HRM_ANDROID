package com.example.myapplication.ui.nghiphep;

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
import android.widget.DatePicker;
import android.widget.EditText;
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

import com.example.myapplication.Adapter.Adapter_NghiPhep;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.LoaiNghiPhep;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.example.myapplication.ui.NhatKyQuetThe.NhatKyQuetThe_MaNV_Activity;
import com.example.myapplication.ui.ScanQR.ScanQR_Activity;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

import static android.app.Activity.RESULT_OK;

public class NghiPhepFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<NghiPhep> lstNghiPhep;
    NghiPhep nghiPhep;
    ArrayList<LoaiNghiPhep> lstLoaiNghiPhep;
    LoaiNghiPhep loaiNghiPhep;

    IRequestHttpCallback iRequestHttpCallback;
    Adapter_NghiPhep adapter;
    private Unbinder unbinder;
    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    String StrMaloainghiphep = "", StrTuNgay = "", StrDenNgay = "", StrMaNV = "";
    Context mContext;
    EditText txtMaNV, txtSoNgay, txtGhiChu;
    TextView txtHoTen, txtPhanXuong, txtTuNgay, txtDenNgay, txtLoaiNghiPhep;
    NoboButton btnLuu, btnDong;
    Integer option = 1;
//    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.SetTileActionBar("Đăng Ký Nghỉ Phép");
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Đăng Ký Nghỉ Phép");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nghiphep, container, false);
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
//                nhatKyTop50 = lstNhatKy.get(position);
//                String url = nhatKyTop50.getHinhanh2();
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }

            @Override
            public void onLongClick(View view, int position) {
                NghiPhep nghiPhep = (NghiPhep) lstNghiPhep.get(position);
                Delete_NhanVienNghiPhep(nghiPhep, position);
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

    public void AddNhanVienNghiPhep() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_diaglog_add_nghiphep, null);
        txtMaNV = view.findViewById(R.id.txtMaNV);

        txtSoNgay = view.findViewById(R.id.txtSoNgay);
        txtGhiChu = view.findViewById(R.id.txtGhiChu);
        txtHoTen = view.findViewById(R.id.txtHoTen);
        txtPhanXuong = view.findViewById(R.id.txtPhanXuong);
        txtTuNgay = view.findViewById(R.id.txtTuNgay);
        txtDenNgay = view.findViewById(R.id.txtDenNgay);
        txtLoaiNghiPhep = view.findViewById(R.id.txtLoaiNghiPhep);

        btnLuu = view.findViewById(R.id.btnLuu);
        btnDong = view.findViewById(R.id.btnDong);

        builder.setView(view)
                .setTitle("Thêm Nghỉ Phép")
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        ShowLoaiNghiPhep(txtLoaiNghiPhep);
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
        txtTuNgay.setOnClickListener(new View.OnClickListener() {
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

                                txtTuNgay.setText("Từ ngày: " + strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                StrTuNgay = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        //Thêm ngày nhập
        txtDenNgay.setOnClickListener(new View.OnClickListener() {
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

                                txtDenNgay.setText("Đến ngày: " + strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                StrDenNgay = formatter2.format(calendar.getTime());

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

                if (StrMaloainghiphep.equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng chọn loại nghỉ phép", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (StrTuNgay.equals("") || StrDenNgay.equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng nhập vào ngày nghỉ phép", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (txtSoNgay.getText().toString().equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng nhập vào số ngày nghỉ phép", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                String url = Modules1.BASE_URL + "insert_nhanvien_nghiphep";
                String TAG = "INSERT_NHANVIEN_NGHIPHEP";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("manv", txtMaNV.getText().toString());
                request.params.put("maloainghiphep", StrMaloainghiphep);
                request.params.put("tungay", StrTuNgay);
                request.params.put("denngay", StrDenNgay);
                request.params.put("songay", txtSoNgay.getText().toString());
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
            }
        });

    }

    @OnClick(R.id.btnThem)
    public void ThemNghiPhep() {
        AddNhanVienNghiPhep();
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
            option = 4;
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
                StrTuNgay = formatter.format(calendarStart.getTime());
                StrDenNgay = formatter.format(calendarEnd.getTime());
                LoadData();
                fab_menu.close(false);
            }
        });

    }

    public void LoadData() {
        String url = Modules1.BASE_URL + "load_nghiphep";
        String TAG = "LOAD_NGHIPHEP";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", option);
        request.params.put("tungay", StrTuNgay);
        request.params.put("denngay", StrDenNgay);
        request.params.put("manv", StrMaNV);
        request.execute();
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
                        txtLoaiNghiPhep.setText("Loại nghỉ phép: " + loaiNghiPhep.getLoainghiphep());
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

    private void Delete_NhanVienNghiPhep(final NghiPhep nghiPhep, final int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setTitle("Xác Nhận")
                .setIcon(R.drawable.message_icon)
                .setMessage("Bạn có muốn xóa phiếu đăng ký nghỉ phép của nhân viên (" + nghiPhep.getTennv() + ") này không?")

                .setNegativeButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = String.valueOf(nghiPhep.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien_nghiphep", iRequestHttpCallback, "DELETE_NHANVIEN_NGHIPHEP");
                        request.params.put("id", id);
                        request.extraData.put("position", position);
                        request.execute();
                    }
                })
                .setPositiveButton("BỎ QUA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(false);
        builder.create().show();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "LOAD_NGHIPHEP":
                    Gson gson = new Gson();
                    TypeToken<List<NghiPhep>> token = new TypeToken<List<NghiPhep>>() {
                    };
                    List<NghiPhep> nghiPheps = gson.fromJson(responseText, token.getType());
                    lstNghiPhep = new ArrayList<NghiPhep>();
                    lstNghiPhep.addAll(nghiPheps);
                    adapter = new Adapter_NghiPhep(getActivity(), lstNghiPhep);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "LOAD_LOAINGHIPHEP":
                    Gson gson_loainghiphep = new Gson();
                    TypeToken<List<LoaiNghiPhep>> token_loainghiphep = new TypeToken<List<LoaiNghiPhep>>() {
                    };
                    List<LoaiNghiPhep> donVis = gson_loainghiphep.fromJson(responseText, token_loainghiphep.getType());
                    lstLoaiNghiPhep = new ArrayList<LoaiNghiPhep>();
                    lstLoaiNghiPhep.addAll(donVis);
                    break;

                case "INSERT_NHANVIEN_NGHIPHEP":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(getActivity(), "Đã đăng ký nghỉ phép thành công.", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            LoadData();
                            fab_menu.close(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case "DELETE_NHANVIEN_NGHIPHEP":
                    try {
                        jsonObject = new JSONObject(responseText);
                        int position = Integer.parseInt(extraData.get("position").toString());
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(getActivity(), "Đã xóa thành công đăng ký nghỉ phép của nhân viên (" + lstNghiPhep.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstNghiPhep.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            MDToast.makeText(getActivity(), "Phiếu đăng ký nghỉ phép của nhân viên (" + lstNghiPhep.get(position).getTennv() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
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