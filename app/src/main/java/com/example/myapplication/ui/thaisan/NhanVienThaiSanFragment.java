package com.example.myapplication.ui.thaisan;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_NhanVienNghiViec;
import com.example.myapplication.Adapter.Adapter_NhanVienThaiSan;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.NhanVienNghiViec;
import com.example.myapplication.Model.NhanVienThaiSan;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NhanVienThaiSanFragment extends Fragment implements IRequestHttpCallback {

    ArrayList<NhanVienThaiSan> lstNhanVienThaiSan;
    NhanVienThaiSan nhanVienThaiSan;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_NhanVienThaiSan adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Context mContext;
//    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.SetTileActionBar("Nhân Viên Thai Sản");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.SetTileActionBar("Nhân Viên Thai Sản");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nhanvien_thaisan, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        iRequestHttpCallback = this;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadData();
//        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
//                recycleView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
////                nhatKyTop50 = lstNhatKy.get(position);
////                String url = nhatKyTop50.getHinhanh2();
////                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });
    }

    public void LoadData() {
//        shimmerFrameLayout.setVisibility(View.VISIBLE);
//        shimmerFrameLayout.startShimmer();
        String url = Modules1.BASE_URL + "load_nhanvien_thaisan_android";
        String TAG = "LOAD_NHANVIEN_THAISAN";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.execute();
    }


    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "LOAD_NHANVIEN_THAISAN":
                    Gson gson = new Gson();
                    TypeToken<List<NhanVienThaiSan>> token = new TypeToken<List<NhanVienThaiSan>>() {
                    };
                    List<NhanVienThaiSan> nhanVienThaiSans = gson.fromJson(responseText, token.getType());
                    lstNhanVienThaiSan = new ArrayList<NhanVienThaiSan>();
                    lstNhanVienThaiSan.addAll(nhanVienThaiSans);
                    adapter = new Adapter_NhanVienThaiSan(getActivity(), lstNhanVienThaiSan);
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