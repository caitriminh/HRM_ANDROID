package com.example.HRM.ui.DiemDanh;

import android.app.SearchManager;
import android.content.Context;
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

import com.example.HRM.Adapter.Adapter_DiemDanh;
import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.MainActivity;
import com.example.HRM.Model.DiemDanh;
import com.example.HRM.Modules1;
import com.example.HRM.R;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DiemDanhFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<DiemDanh> lstDiemDanh;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_DiemDanh adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    String strNgayQuetThe = "", strNgay = "";

    @BindView(R.id.menu_list)
    FloatingActionMenu fab_menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        strNgay = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        MainActivity.SetTileActionBar("Điểm Danh (" + strNgay + ")");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Điểm Danh (" + strNgay + ")");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diemdanh, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        iRequestHttpCallback = this;
        mContext = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strNgayQuetThe = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        LoadData();
    }

    public void LoadData() {
        String url = Modules1.BASE_URL + "load_diemdanh";
        String TAG = "LOAD_DIEMDANH";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("ngayquetthe", strNgayQuetThe);
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
                DateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(startDate);

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(endDate);
                strNgayQuetThe = formatter.format(calendarStart.getTime());
                strNgayQuetThe = formatter.format(calendarEnd.getTime());
                strNgay= formatter2.format(calendarStart.getTime());
                MainActivity.SetTileActionBar("Điểm Danh (" + strNgay + ")");
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
                case "LOAD_DIEMDANH":
                    Gson gson = new Gson();
                    TypeToken<List<DiemDanh>> token = new TypeToken<List<DiemDanh>>() {
                    };
                    List<DiemDanh> diemDanhs = gson.fromJson(responseText, token.getType());
                    lstDiemDanh = new ArrayList<DiemDanh>();
                    lstDiemDanh.addAll(diemDanhs);
                    adapter = new Adapter_DiemDanh(getActivity(), lstDiemDanh);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycleView.setAdapter(adapter);
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
        //searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
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