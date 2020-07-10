package com.example.myapplication.ui.LenhTangCa;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_NghiPhep_MaNV;
import com.example.myapplication.Adapter.Adapter_NhanVienTangCa_MaLenh;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.NhanVienTangCa;
import com.example.myapplication.Model.NhatKyQuetThe;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NhanVienTangCa_MaLenh_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<NhanVienTangCa> lstNhanVienTangCa;
    Adapter_NhanVienTangCa_MaLenh adapter;
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
        setContentView(R.layout.fragment_nhanvien_tangca);
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
                NhanVienTangCa nhanVienTangCa = (NhanVienTangCa) lstNhanVienTangCa.get(position);
                Delete_NhanVienTangCa(nhanVienTangCa, position);
            }
        }));

        this.setTitle("Nhân Viên Tăng Ca");
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

    private void Delete_NhanVienTangCa(final NhanVienTangCa nhanVienTangCa, final int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
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

    public void LoadData() {
        String url = Modules1.BASE_URL + "load_nhanvien_tangca";
        String TAG = "LOAD_NHANVIEN_TANGCA";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 2);
        request.params.put("tungay", "");
        request.params.put("denngay", "");
        request.params.put("malenh", Modules1.objLenhTangCa.getMalenh());
        request.params.put("manv", "");
        request.execute();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        JSONObject jsonObject = null;
        if (isSuccess) {
            switch (TAG) {
                case "LOAD_NHANVIEN_TANGCA":
                    Gson gson = new Gson();
                    TypeToken<List<NhanVienTangCa>> token = new TypeToken<List<NhanVienTangCa>>() {
                    };
                    List<NhanVienTangCa> nhanVienTangCas = gson.fromJson(responseText, token.getType());
                    lstNhanVienTangCa = new ArrayList<NhanVienTangCa>();
                    lstNhanVienTangCa.addAll(nhanVienTangCas);
                    adapter = new Adapter_NhanVienTangCa_MaLenh(this, lstNhanVienTangCa);
                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
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
                            MDToast.makeText(this, "Đã xóa thành công đăng ký tăng ca của nhân viên (" + lstNhanVienTangCa.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstNhanVienTangCa.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            MDToast.makeText(this, "Đăng ký tăng ca của nhân viên (" + lstNhanVienTangCa.get(position).getTennv() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(this, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
            }
        } else {
            MDToast.makeText(this, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
}