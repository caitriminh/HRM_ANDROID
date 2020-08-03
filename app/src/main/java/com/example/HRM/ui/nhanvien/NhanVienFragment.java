package com.example.HRM.ui.nhanvien;

import android.app.SearchManager;
import android.content.Context;
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


import com.example.HRM.Adapter.Adapter_NhanVien;
import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.ClickListener;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.MainActivity;
import com.example.HRM.Model.NhanVien;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.RecyclerTouchListener;
import com.example.HRM.ui.ScanQR.ScanQR_Activity;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;


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

public class NhanVienFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<NhanVien> lstNhanVien;
    NhanVien nhanVien;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_NhanVien adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Context mContext;

    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    String StrMaNV = "", strTuNgay = "", strDenNgay = "";
    Integer option = 1;
//    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.SetTileActionBar("Nhân Viên");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Nhân Viên");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nhanvien, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        iRequestHttpCallback = this;
        mContext = getActivity();
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
//                nhanVien = lstNhanVien.get(position);
//                String url = nhanVien.getHinh();
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
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
                option = 4;
                strTuNgay = formatter.format(calendarStart.getTime());
                strDenNgay = formatter.format(calendarEnd.getTime());
                LoadData();
                fab_menu.close(false);
            }
        });

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
            if(resultCode ==101){
                StrMaNV = data.getStringExtra("result");
                if (StrMaNV.isEmpty()) {
                    fab_menu.close(false);
                    return;
                }
                option = 3;
                LoadData();

            }
            fab_menu.close(false);
        }
    }

    public void LoadData() {
//        shimmerFrameLayout.setVisibility(View.VISIBLE);
//        shimmerFrameLayout.startShimmer();
        String url = Modules1.BASE_URL + "load_nhanvien_android";
        String TAG = "LOAD_NHANVIEN";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", option);
        request.params.put("manv", StrMaNV);
        request.params.put("tungay", strTuNgay);
        request.params.put("denngay", strDenNgay);
        request.execute();
    }


    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "LOAD_NHANVIEN":
                    Gson gson = new Gson();
                    TypeToken<List<NhanVien>> token = new TypeToken<List<NhanVien>>() {
                    };
                    List<NhanVien> nhanViens = gson.fromJson(responseText, token.getType());
                    lstNhanVien = new ArrayList<NhanVien>();
                    lstNhanVien.addAll(nhanViens);
                    //  adapter = new Adapter_NhanVien(getActivity(), lstNhanVien);

                    adapter = new Adapter_NhanVien(getActivity(), lstNhanVien, recycleView);

                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);

//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    shimmerFrameLayout.stopShimmer();
                    break;
            }
        } else {
//            shimmerFrameLayout.setVisibility(View.GONE);
//            shimmerFrameLayout.stopShimmer();
            MDToast.makeText(getActivity(), "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }

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