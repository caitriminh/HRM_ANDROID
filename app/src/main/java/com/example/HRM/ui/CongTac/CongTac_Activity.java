package com.example.HRM.ui.CongTac;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Adapter.Adapter_CongTacMaNV;
import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.ClickListener;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.Model.CongTac;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.RecyclerTouchListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CongTac_Activity extends AppCompatActivity implements IRequestHttpCallback {

    IRequestHttpCallback iRequestHttpCallback;
    ArrayList<CongTac> lstCongTac;
    Adapter_CongTacMaNV adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    @BindView(R.id.txtTongCong)
    TextView txtTongCong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_nhatky_congtac);
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

            }
        }));

        this.setTitle("Nhật Ký Công Tác");

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
        String url = Modules1.BASE_URL + "load_congtac";
        String TAG = "LOAD_CONGTAC";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 4);
        request.params.put("tungay", "");
        request.params.put("denngay", "");
        request.params.put("manv", Modules1.strMaNV);
        request.execute();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            switch (TAG) {
                case "LOAD_CONGTAC":
                    Gson gson = new Gson();
                    TypeToken<List<CongTac>> token = new TypeToken<List<CongTac>>() {
                    };
                    List<CongTac> congTacs = gson.fromJson(responseText, token.getType());
                    lstCongTac = new ArrayList<CongTac>();
                    lstCongTac.addAll(congTacs);
                    adapter = new Adapter_CongTacMaNV(this, lstCongTac);

                    //Tổng thành tiền
                    //  txtTongCong.setText(lstCTPhepNam.get(0).getTong());

                    recycleView.setLayoutManager(new GridLayoutManager(this, 1));
                    recycleView.setAdapter(adapter);
                    break;

            }
        } else {
            MDToast.makeText(this, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
}