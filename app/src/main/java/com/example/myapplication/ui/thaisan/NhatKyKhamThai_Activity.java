package com.example.myapplication.ui.thaisan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.Adapter_CheDoBaoHiem;
import com.example.myapplication.Adapter.Adapter_NhatKyKhamThai;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.CheDoBaoHiem;
import com.example.myapplication.Model.NhatKyKhamThai;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NhatKyKhamThai_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<NhatKyKhamThai> lstNhatKy;
    Adapter_NhatKyKhamThai adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;


    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhatky_khamthai);
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
                NhatKyKhamThai nhatKyKhamThai = lstNhatKy.get(position);
                Delete_NhatKyKhamThai(nhatKyKhamThai, position);
            }
        }));

        this.setTitle("Nhật Ký Khám Thai");
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

    private void Delete_NhatKyKhamThai(final NhatKyKhamThai nhatKyKhamThai, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xóa")
                .setMessage("Bạn có muốn xóa nhật ký khám thai của nhân viên này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String id = String.valueOf(nhatKyKhamThai.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhatky_khamthai", iRequestHttpCallback, "DELETE_NHATKY");
                        request.params.put("id", id);
                        request.extraData.put("position", position);
                        request.execute();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Đóng", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    public void LoadData() {
        String url = Modules1.BASE_URL + "load_nhatky_khamthai";
        String TAG = "LOAD_NHATKY";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("manv", Modules1.strMaNV);
        request.params.put("lanmangthai", Modules1.objNhanVienThaiSan.getLanmangthai());
        request.execute();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        JSONObject jsonObject = null;
        if (isSuccess) {
            switch (TAG) {
                case "LOAD_NHATKY":
                    Gson gson = new Gson();
                    TypeToken<List<NhatKyKhamThai>> token = new TypeToken<List<NhatKyKhamThai>>() {
                    };
                    List<NhatKyKhamThai> nhatKyKhamThais = gson.fromJson(responseText, token.getType());
                    lstNhatKy = new ArrayList<NhatKyKhamThai>();
                    lstNhatKy.addAll(nhatKyKhamThais);
                    adapter = new Adapter_NhatKyKhamThai(this, lstNhatKy);
                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "DELETE_NHATKY":
                    try {
                        jsonObject = new JSONObject(responseText);
                        int position = Integer.parseInt(extraData.get("position").toString());
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            lstNhatKy.remove(position);
                            adapter.notifyDataSetChanged();
                            MDToast.makeText(this, "Đã xóa thành công nhật ký khám thai ngày (" + lstNhatKy.get(position).getNgaykhamthai() + ") nhân viên này.", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                        } else {
                            MDToast.makeText(this, "Xóa không thành công.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
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