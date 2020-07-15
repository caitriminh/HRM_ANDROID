package com.example.myapplication.ui.nghiviec;

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

import com.example.myapplication.Adapter.Adapter_NhanVien;
import com.example.myapplication.Adapter.Adapter_NhanVienNghiViec;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.NhanVienNghiViec;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.ui.ScanQR.ScanQR_Activity;
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

public class NhanVienNghiViecFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<NhanVienNghiViec> lstNhanVienNghiViec;
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
    //    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    String strMaNV = "", strTuNgay = "", strDenNgay = "";
    Integer option = 1;

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
        View rootView = inflater.inflate(R.layout.fragment_nhanvien_nghiviec, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        iRequestHttpCallback = this;
        mContext = getActivity();
        return rootView;
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