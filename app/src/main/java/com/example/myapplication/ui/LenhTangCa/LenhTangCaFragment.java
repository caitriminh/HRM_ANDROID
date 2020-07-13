package com.example.myapplication.ui.LenhTangCa;

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

import com.example.myapplication.Adapter.Adapter_LenhTangCa;
import com.example.myapplication.Adapter.Adapter_NhanVienTangCa;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.LenhTangCa;
import com.example.myapplication.Model.LoaiNghiPhep;
import com.example.myapplication.Model.LoaiTangCa;
import com.example.myapplication.Model.NhanVienTangCa;
import com.example.myapplication.Model.NhomMay;
import com.example.myapplication.Model.PhanXuong;
import com.example.myapplication.Model.VeSom;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.example.myapplication.ui.nghiphep.NghiPhep_MaNV_Activity;
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

public class LenhTangCaFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<LenhTangCa> lstLenhTangCa;
    ArrayList<LoaiTangCa> lstLoaiTangCa;
    ArrayList<PhanXuong> lstPhanXuong;
    ArrayList<NhomMay> lstNhomMay;
    LenhTangCa lenhTangCa;
    LoaiTangCa loaiTangCa;
    PhanXuong phanXuong;
    NhomMay nhomMay;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_LenhTangCa adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Context mContext;
    //    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;
    TextView txtMaLenh, txtNgayTangCa, txtPhanXuong, txtLoaiTangCa, txtNhomCongViec;
    EditText txtGhiChu;
    String strTuNgay = "", strDenNgay = "", strNgayTangCa = "", strMaLoaiTangCa = "", strMaPX = "", strMaNhom = "", strMaLenh = "";
    Integer option = 1;
    NoboButton btnLuu, btnDong;

    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.SetTileActionBar("Lệnh Tăng Ca");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Lệnh Tăng Ca");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lenhtangca, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mContext = getActivity();
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
                Modules1.objLenhTangCa = lstLenhTangCa.get(position);
                Intent intent_nghiphep = new Intent(mContext, NhanVienTangCa_MaLenh_Activity.class);
                mContext.startActivity(intent_nghiphep);
            }

            @Override
            public void onLongClick(View view, int position) {
                LenhTangCa lenhTangCa = (LenhTangCa) lstLenhTangCa.get(position);
                Delete_LenhTangCa(lenhTangCa, position);
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

    private void Delete_LenhTangCa(final LenhTangCa lenhTangCa, final int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setTitle("Xác Nhận")
                .setIcon(R.drawable.message_icon)
                .setMessage("Bạn có muốn xóa lệnh tăng ca (" + lenhTangCa.getMalenh() + ") này không?")

                .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String malenh = String.valueOf(lenhTangCa.getMalenh());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_lenhtangca", iRequestHttpCallback, "DELETE_LENHTANGCA");
                        request.params.put("malenh", malenh);
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
        String url = Modules1.BASE_URL + "load_lenhtangca";
        String TAG = "LOAD_LENHTANGCA";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", option);
        request.params.put("tungay", strTuNgay);
        request.params.put("denngay", strDenNgay);
        request.execute();
    }

    public void AddLenhTangCa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_diaglog_add_lenhtangca, null);
        txtMaLenh = view.findViewById(R.id.txtMaLenh);
        txtNgayTangCa = view.findViewById(R.id.txtNgayTangCa);
        txtGhiChu = view.findViewById(R.id.txtGhiChu);
        txtPhanXuong = view.findViewById(R.id.txtPhanXuong);
        txtLoaiTangCa = view.findViewById(R.id.txtLoaiTangCa);
        txtNhomCongViec = view.findViewById(R.id.txtNhomCongViec);


        btnLuu = view.findViewById(R.id.btnLuu);
        btnDong = view.findViewById(R.id.btnDong);

        txtMaLenh.setText("Lệnh tăng ca: " + strMaLenh);

        builder.setView(view)
                .setTitle("Thêm Lệnh Tăng Ca")
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        ShowLoaiTangCa(txtLoaiTangCa);
        ShowPhanXuong(txtPhanXuong);
        ShowNhomMay(txtNhomCongViec);

        //Thêm ngày nhập
        txtNgayTangCa.setOnClickListener(new View.OnClickListener() {
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

                                txtNgayTangCa.setText("Ngày tăng ca: " + strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                strNgayTangCa = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtMaLenh.getText().equals("")) {
                    MDToast.makeText(getActivity(), "Vui lòng nhập vào mã lệnh tăng ca.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (txtLoaiTangCa.getText().equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng chọn loại tăng ca", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (strNgayTangCa.equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng nhập vào ngày tăng ca", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (txtPhanXuong.getText().toString().equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng chọn phân xưởng cần tăng ca", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (txtNhomCongViec.getText().toString().equals("")) {
                    MDToast.makeText(getActivity(), "Bạn vui lòng chọn nhóm công việc cần tăng ca", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                String url = Modules1.BASE_URL + "insert_lenhtangca";
                String TAG = "INSERT_LENHTANGCA";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("malenh", strMaLenh);
                request.params.put("matangca", strMaLoaiTangCa);
                request.params.put("ngaytangca", strNgayTangCa);
                request.params.put("mapx", strMaPX);
                request.params.put("manhom", strMaNhom);
                request.params.put("ghichu", txtGhiChu.getText().toString());
                request.params.put("nguoitd", Modules1.tendangnhap);
                request.extraData.put("malenh", strMaLenh);
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
        LoadMaLenh();
        //  AddLenhTangCa();
        LoadLoaiTangCa();
        LoadPhanXuong();
        LoadNhomMay();

    }

    public void ShowNhomMay(TextView txtNhomCongViec) {
        txtNhomCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn nhóm công việc");
                builder.setCancelable(false);
                String[] arrayNhomMay = new String[lstNhomMay.size()];
                int i = 0;
                for (NhomMay nhomMay : lstNhomMay) {
                    arrayNhomMay[i] = nhomMay.getNhommay();
                    i++;
                }
                builder.setSingleChoiceItems(arrayNhomMay, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NhomMay nhomMay = lstNhomMay.get(i);
                        txtNhomCongViec.setText("Nhóm công việc: " + nhomMay.getNhommay());
                        strMaNhom = nhomMay.getManhom();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void LoadNhomMay() {
        String url = Modules1.BASE_URL + "load_nhommay";
        String TAG = "LOAD_NHOMMAY";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.execute();
    }

    public void ShowPhanXuong(TextView txtPhanXuong) {
        txtPhanXuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn phân xưởng");
                builder.setCancelable(false);
                String[] arrayPhanXuong = new String[lstPhanXuong.size()];
                int i = 0;
                for (PhanXuong phanXuong : lstPhanXuong) {
                    arrayPhanXuong[i] = phanXuong.getTenpx();
                    i++;
                }
                builder.setSingleChoiceItems(arrayPhanXuong, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhanXuong phanXuong = lstPhanXuong.get(i);
                        txtPhanXuong.setText("Phân xưởng: " + phanXuong.getTenpx());
                        strMaPX = phanXuong.getMapx();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void LoadPhanXuong() {
        String url = Modules1.BASE_URL + "load_phanxuong";
        String TAG = "LOAD_PHANXUONG";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.execute();
    }

    public void ShowLoaiTangCa(TextView txtLoaiTangCa) {
        txtLoaiTangCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn loại tăng ca");
                builder.setCancelable(false);
                String[] arrayLoaiTangCa = new String[lstLoaiTangCa.size()];
                int i = 0;
                for (LoaiTangCa loaiTangCa : lstLoaiTangCa) {
                    arrayLoaiTangCa[i] = loaiTangCa.getLoaitangca();
                    i++;
                }
                builder.setSingleChoiceItems(arrayLoaiTangCa, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiTangCa loaiTangCa = lstLoaiTangCa.get(i);
                        txtLoaiTangCa.setText("Loại tăng ca: " + loaiTangCa.getLoaitangca());
                        strMaLoaiTangCa = loaiTangCa.getMatangca();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void LoadLoaiTangCa() {
        String url = Modules1.BASE_URL + "load_loaitangca";
        String TAG = "LOAD_LOAITANGCA";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);

        request.execute();
    }

    public void LoadMaLenh() {
        String url = Modules1.BASE_URL + "getMalenhTangCa";
        String TAG = "TAOMALENH";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.execute();
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
                case "LOAD_LENHTANGCA":
                    Gson gson = new Gson();
                    TypeToken<List<LenhTangCa>> token = new TypeToken<List<LenhTangCa>>() {
                    };
                    List<LenhTangCa> lenhTangCas = gson.fromJson(responseText, token.getType());
                    lstLenhTangCa = new ArrayList<LenhTangCa>();
                    lstLenhTangCa.addAll(lenhTangCas);
                    adapter = new Adapter_LenhTangCa(getActivity(), lstLenhTangCa);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "DELETE_LENHTANGCA":
                    try {
                        jsonObject = new JSONObject(responseText);
                        int position = Integer.parseInt(extraData.get("position").toString());
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(getActivity(), "Đã xóa thành công lệnh tăng ca (" + lstLenhTangCa.get(position).getMalenh() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstLenhTangCa.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            MDToast.makeText(getActivity(), "Lệnh tăng ca (" + lstLenhTangCa.get(position).getMalenh() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
                case "INSERT_LENHTANGCA":
                    LoadData();
                    break;
                case "LOAD_LOAITANGCA":
                    Gson gsonLoaiTangCa = new Gson();
                    TypeToken<List<LoaiTangCa>> tokenLoaiTangCa = new TypeToken<List<LoaiTangCa>>() {
                    };
                    List<LoaiTangCa> loaiTangCas = gsonLoaiTangCa.fromJson(responseText, tokenLoaiTangCa.getType());
                    lstLoaiTangCa = new ArrayList<LoaiTangCa>();
                    lstLoaiTangCa.addAll(loaiTangCas);
                    break;
                case "LOAD_PHANXUONG":
                    Gson gsonPhanXuong = new Gson();
                    TypeToken<List<PhanXuong>> tokenPhanXuong = new TypeToken<List<PhanXuong>>() {
                    };
                    List<PhanXuong> phanXuongs = gsonPhanXuong.fromJson(responseText, tokenPhanXuong.getType());
                    lstPhanXuong = new ArrayList<PhanXuong>();
                    lstPhanXuong.addAll(phanXuongs);
                    break;
                case "LOAD_NHOMMAY":
                    Gson gsonNhomMay = new Gson();
                    TypeToken<List<NhomMay>> tokenNhomMay = new TypeToken<List<NhomMay>>() {
                    };
                    List<NhomMay> nhomMays = gsonNhomMay.fromJson(responseText, tokenNhomMay.getType());
                    lstNhomMay = new ArrayList<NhomMay>();
                    lstNhomMay.addAll(nhomMays);
                    break;
                case "TAOMALENH":
                    try {
                        jsonObject = new JSONObject(responseText);
                        strMaLenh = jsonObject.getString("malenh");
                        AddLenhTangCa();
                    } catch (JSONException e) {
                        MDToast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
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