package com.example.HRM.ui.CheDoBaoHiem;

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

import com.example.HRM.Adapter.Adapter_CheDoBaoHiem;
import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.ClickListener;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.Model.CheDoBaoHiem;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.RecyclerTouchListener;
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


public class CheDoBaoHiem_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<CheDoBaoHiem> lstCheDoBaoHiem;
    Adapter_CheDoBaoHiem adapter;
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
        setContentView(R.layout.activity_chedo_baohiem);
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
                CheDoBaoHiem cheDoBaoHiem = (CheDoBaoHiem) lstCheDoBaoHiem.get(position);
                Delete_ChiTietBaoHiem(cheDoBaoHiem, position);
            }
        }));

        this.setTitle("Chế Độ Bảo Hiểm");
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

    private void Delete_ChiTietBaoHiem(final CheDoBaoHiem cheDoBaoHiem, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xóa")
                .setMessage("Bạn có muốn xóa chế độ bảo hiểm của nhân viên (" + cheDoBaoHiem.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String id = String.valueOf(cheDoBaoHiem.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_chedo_baohiem", iRequestHttpCallback, "DELETE_CHEDO_BAOHIEM");
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
        String url = Modules1.BASE_URL + "load_chedo_baohiem";
        String TAG = "LOAD_CHEDO_BAOHIEM";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("manv", Modules1.strMaNV);
        request.execute();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        JSONObject jsonObject = null;
        if (isSuccess) {
            switch (TAG) {
                case "LOAD_CHEDO_BAOHIEM":
                    Gson gson = new Gson();
                    TypeToken<List<CheDoBaoHiem>> token = new TypeToken<List<CheDoBaoHiem>>() {
                    };
                    List<CheDoBaoHiem> cheDoBaoHiems = gson.fromJson(responseText, token.getType());
                    lstCheDoBaoHiem = new ArrayList<CheDoBaoHiem>();
                    lstCheDoBaoHiem.addAll(cheDoBaoHiems);
                    adapter = new Adapter_CheDoBaoHiem(this, lstCheDoBaoHiem);
                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                    break;
                case "DELETE_CHEDO_BAOHIEM":
                    try {
                        jsonObject = new JSONObject(responseText);
                        int position = Integer.parseInt(extraData.get("position").toString());
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(this, "Đã xóa thành công chế độ bảo hiểm của nhân viên (" + lstCheDoBaoHiem.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstCheDoBaoHiem.remove(position);
                            adapter.notifyDataSetChanged();
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