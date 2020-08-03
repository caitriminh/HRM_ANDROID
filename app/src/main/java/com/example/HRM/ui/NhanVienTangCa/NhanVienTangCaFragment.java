package com.example.HRM.ui.NhanVienTangCa;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.HRM.Adapter.Adapter_NhanVienTangCa;
import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.ClickListener;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.MainActivity;
import com.example.HRM.Model.NhanVienTangCa;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.RecyclerTouchListener;
import com.example.HRM.ui.ScanQR.ScanQR_Activity;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class NhanVienTangCaFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<NhanVienTangCa> lstNhanVienTangCa;
    NhanVienTangCa nhanVienTangCa;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_NhanVienTangCa adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Context mContext;
//    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    Integer option = 1;
    String strMalenh = "", strTuNgay="", strDenNgay="", strMaNV="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        setHasOptionsMenu(true);
        MainActivity.SetTileActionBar("Nhật Ký Tăng Ca");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Nhật Ký Tăng Ca");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nhanvien_tangca, container, false);
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
                NhanVienTangCa nhanVienTangCa = (NhanVienTangCa) lstNhanVienTangCa.get(position);
                Delete_NhanVienTangCa(nhanVienTangCa, position);
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
        String url = Modules1.BASE_URL + "load_nhanvien_tangca";
        String TAG = "LOAD_NHANVIEN_TANGCA";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", option);
        request.params.put("malenh", strMalenh);
        request.params.put("tungay", strTuNgay);
        request.params.put("denngay", strDenNgay);
        request.params.put("manv", strMaNV);
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
                option = 3;
                strTuNgay = formatter.format(calendarStart.getTime());
                strDenNgay = formatter.format(calendarEnd.getTime());
                LoadData();
                fab_menu.close(false);
            }
        });

    }

    @OnClick(R.id.btnQR)
    public void ScanQR(){
        Intent intent = new Intent(mContext, ScanQR_Activity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            strMaNV =  data.getStringExtra("result");
            //MDToast.makeText(mContext, StrMaNV, Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
            option=4;
            LoadData();
            fab_menu.close(false);
        }
    }

    private void Delete_NhanVienTangCa(final NhanVienTangCa nhanVienTangCa, final int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setTitle("Xác Nhận")
                .setIcon(R.drawable.message_icon)
                .setMessage("Bạn có muốn xóa đăng ký tăng ca của nhân viên (" + nhanVienTangCa.getTennv() + ") này không?")

                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = String.valueOf(nhanVienTangCa.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien_tangca", iRequestHttpCallback, "DELETE_NHANVIEN_TANGCA");
                        request.params.put("id", id);
                        request.extraData.put("position", position);
                        request.execute();
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
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
                case "LOAD_NHANVIEN_TANGCA":
                    Gson gson = new Gson();
                    TypeToken<List<NhanVienTangCa>> token = new TypeToken<List<NhanVienTangCa>>() {
                    };
                    List<NhanVienTangCa> nhanVienTangCas = gson.fromJson(responseText, token.getType());
                    lstNhanVienTangCa = new ArrayList<NhanVienTangCa>();
                    lstNhanVienTangCa.addAll(nhanVienTangCas);
                    adapter = new Adapter_NhanVienTangCa(getActivity(), lstNhanVienTangCa);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
                            MDToast.makeText(getActivity(), "Đã xóa thành công đăng ký tăng ca của nhân viên (" + lstNhanVienTangCa.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstNhanVienTangCa.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            MDToast.makeText(getActivity(), "Đăng ký tăng ca của nhân viên (" + lstNhanVienTangCa.get(position).getTennv() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
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