package com.example.myapplication.ui.nghiphep;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_NghiPhep;
import com.example.myapplication.Adapter.Adapter_NghiPhep_MaNV;
import com.example.myapplication.Adapter.Adapter_NhatKyQuetThe;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.NhatKyQuetThe;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
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


public class NghiPhep_MaNV_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<NghiPhep> lstNghiPhep;
    Adapter_NghiPhep_MaNV adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    Integer option = 3;
    String strTuNgay = "", strDenNgay = "";

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_nhatky_nghiphep);
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
//                NhatKyQuetThe nhatKyQuetThe = (NhatKyQuetThe) lstNhatKyQuetThe.get(position);
//                Delete_NhatKyQuetThe(nhatKyQuetThe, position);
            }
        }));

        this.setTitle("Nhật Ký Nghỉ Phép");
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

    private void Delete_NhatKyQuetThe(final NhatKyQuetThe nhatKyQuetThe, final int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setTitle("Xác Nhận")
                .setIcon(R.drawable.message_icon)
                .setMessage("Bạn có muốn xóa chi tiết quét thẻ nhân viên (" + nhatKyQuetThe.getTennv() + ") này không?")

                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = String.valueOf(nhatKyQuetThe.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhatky_quetthe", iRequestHttpCallback, "DELETE_NHATKY_QUETTHE");
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

    public void LoadData() {
        String url = Modules1.BASE_URL + "load_nghiphep";
        String TAG = "LOAD_NGHIPHEP";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", option);
        request.params.put("tungay", strTuNgay);
        request.params.put("denngay", strDenNgay);
        request.params.put("manv", Modules1.objNhanVien.getManv2());
        request.execute();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        JSONObject jsonObject = null;
        if (isSuccess) {
            switch (TAG) {
                case "LOAD_NGHIPHEP":
                    Gson gson = new Gson();
                    TypeToken<List<NghiPhep>> token = new TypeToken<List<NghiPhep>>() {
                    };
                    List<NghiPhep> nghiPheps = gson.fromJson(responseText, token.getType());
                    lstNghiPhep = new ArrayList<NghiPhep>();
                    lstNghiPhep.addAll(nghiPheps);
                    adapter = new Adapter_NghiPhep_MaNV(this, lstNghiPhep);
                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
                    recycleView.setAdapter(adapter);

                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;

            }
        } else {
            MDToast.makeText(this, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
}