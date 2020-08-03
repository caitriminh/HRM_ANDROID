package com.example.HRM.ui.nhanvien;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Adapter.Adapter_HDLD;
import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.ClickListener;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.Model.HopDongLaoDong;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.RecyclerTouchListener;
import com.example.HRM.ui.PhuLucHopDong.PhuLucHopDong_Activity;
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


public class HDLD_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<HopDongLaoDong> lstHDLD;
    Adapter_HDLD adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hopdong_laodong);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        iRequestHttpCallback = this;
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadData();

        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this, recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Modules1.objHopDong = lstHDLD.get(position);
                Intent intent = new Intent(mContext, PhuLucHopDong_Activity.class);
                mContext.startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                HopDongLaoDong hopDongLaoDong = lstHDLD.get(position);
                Delete_HDLD(hopDongLaoDong, position);
            }
        }));
        this.setTitle("Hợp Đồng Lao Động");
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

    public void LoadData() {
        String url = Modules1.BASE_URL + "load_hopdonglaodong";
        String TAG = "LOAD_HDLD";

        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 6);
        request.params.put("manv", Modules1.strMaNV);
        request.params.put("tungay", "");
        request.params.put("denngay", "");
        request.execute();
    }

    private void Delete_HDLD(final HopDongLaoDong hopDongLaoDong, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa hợp đồng lao động của nhân viên (" + hopDongLaoDong.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String id = String.valueOf(hopDongLaoDong.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_hopdong_laodong", iRequestHttpCallback, "DELETE_HDLD");
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

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;

            switch (TAG) {
                case "LOAD_HDLD":
                    Gson gson = new Gson();
                    TypeToken<List<HopDongLaoDong>> token = new TypeToken<List<HopDongLaoDong>>() {
                    };
                    List<HopDongLaoDong> hopDongLaoDongs = gson.fromJson(responseText, token.getType());
                    lstHDLD = new ArrayList<HopDongLaoDong>();
                    lstHDLD.addAll(hopDongLaoDongs);
                    adapter = new Adapter_HDLD(this, lstHDLD);
                    //Tổng thành tiền

                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
                    recycleView.setAdapter(adapter);
                    break;
                case "DELETE_HDLD":
                    int position = Integer.parseInt(extraData.get("position").toString());
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công hợp đồng lao động của nhân viên (" + lstHDLD.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            lstHDLD.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            MDToast.makeText(mContext, "Phiếu đăng ký nghỉ phép của nhân viên (" + lstHDLD.get(position).getTennv() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
            }
        } else {
            MDToast.makeText(this, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
}