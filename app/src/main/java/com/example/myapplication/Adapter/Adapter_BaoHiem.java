package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.BaoHiem;
import com.example.myapplication.Model.CongTac;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.VeSom;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimator;
import com.example.myapplication.ui.CheDoBaoHiem.CheDoBaoHiem_Activity;
import com.example.myapplication.ui.NhatKyQuetThe.NhatKyQuetThe_MaNV_Activity;
import com.example.myapplication.ui.VeSom.VeSom_Activity;
import com.example.myapplication.ui.nghiphep.NghiPhep_MaNV_Activity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_BaoHiem extends RecyclerView.Adapter<Adapter_BaoHiem.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<BaoHiem> data = new ArrayList<>();
    private List<BaoHiem> temp = new ArrayList<>();
    private Unbinder unbinder;
    IRequestHttpCallback iRequestHttpCallback;
    public BaoHiem baoHiem;

    Integer position_item = 1;
    PowerMenu powerMenu;
    // private SpringyAdapterAnimator mAnimator;

    public Adapter_BaoHiem(Context mContext, List<BaoHiem> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
        iRequestHttpCallback = this;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_baohiem, parent, false);

        //  mAnimator.onSpringItemCreate(view);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtMaSoHD.setText(data.get(position).getMasohopdong());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtSoBHXH.setText(data.get(position).getSobhxh());
        holder.txtSoBHYT.setText(data.get(position).getSothebhyt());
        holder.txtNgayBatDau.setText(data.get(position).getNgaybatdau());
        holder.txtMucDong.setText(data.get(position).getMucdong());
        holder.txtTraSo.setText(data.get(position).getTraso());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());

        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                .error(R.drawable.no_avatar)//hien thi hinh mac dinh khi ko co hinh
                .placeholder(R.drawable.no_avatar)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.imgView);

        String status_traso2 = data.get(position).getTraso2();


        if (status_traso2.equals("NO")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#d1e0e0"));
        } else {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                position_item = position;
                powerMenu = new PowerMenu.Builder(mContext)
                        // .setHeaderView(R.layout.layout_dialog_header)
                        .addItem(new PowerMenuItem("Trả sổ", R.drawable.ic_traso, "TRASO"))
                        .addItem(new PowerMenuItem("Chế độ bảo hiểm", R.drawable.ic_ct_nghiphep, "CHEDO"))
                        .addItem(new PowerMenuItem("Xem ảnh", R.drawable.ic_menu_xemanh, "XEMANH"))
                        .addItem(new PowerMenuItem("Xóa", R.drawable.ic_delete, "XOA"))
                        .setAnimation(MenuAnimation.ELASTIC_CENTER)
                        .setWidth(600)
                        .setDivider(new ColorDrawable(ContextCompat.getColor(mContext, R.color.bluegrey200))) // sets a divider.
                        .setDividerHeight(1)
                        .setMenuRadius(10f) // sets the corner radius.
                        .setMenuShadow(10f) // sets the shadow.
                        .setTextColor(ContextCompat.getColor(mContext, R.color.grey700))
                        .setTextGravity(Gravity.LEFT)
                        .setTextSize(16)
                        .setSelectedTextColor(Color.WHITE)
                        .setMenuColor(Color.WHITE)
                        .setSelectedMenuColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                        .setOnMenuItemClickListener(onMenuItemClickListener)
                        .build();
                //powerMenu.showAsAnchorCenter(view.getRootView(), 0, 0);
                powerMenu.showAsAnchorCenter(view, 0, 0);
            }
        });
    }

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            BaoHiem baoHiem = data.get(position_item);
            String TAG = item.getTag().toString();
            switch (TAG) {
                case "TRASO":
                    XacNhan_TraSo(baoHiem, position_item);
                    break;
                case "CHEDO":
                    Modules1.strMaNV = baoHiem.getManv2();
                    Intent intent = new Intent(mContext, CheDoBaoHiem_Activity.class);
                    mContext.startActivity(intent);
                    break;
                case "XEMANH":
                    String url = baoHiem.getHinh();
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    break;
                case "XOA":
                    Delete_BaoHiem(baoHiem, position_item);
                    break;
            }
            powerMenu.dismiss();
        }
    };

    public void TraSo(int position) {
        String id = String.valueOf(baoHiem.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "traso_baohiem", iRequestHttpCallback, "TRASO");
        request.params.put("id", id);
        request.extraData.put("position", position);
        request.execute();
    }

    private void XacNhan_TraSo(final BaoHiem baoHiem, final int position) {
        this.baoHiem = baoHiem;
        String title = "";
        if (baoHiem.getTraso2().equals("0")) {
            title = "Bạn có muốn xác nhận trả sổ bảo hiểm cho  nhân viên (" + baoHiem.getTennv() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Xác nhận", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            TraSo(position);
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
        } else {
            title = "Bạn có muốn thu hồi xác nhận trả sổ bảo hiểm cho nhân viên (" + baoHiem.getTennv() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Thu Hồi")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Thu hồi", R.drawable.ic_thuhoi, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            TraSo(position);
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

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void Delete_BaoHiem(final BaoHiem baoHiem, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa thông tin bảo hiểm của nhân viên (" + baoHiem.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String manv = String.valueOf(baoHiem.getManv2());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_baohiem", iRequestHttpCallback, "DELETE_BAOHIEM");
                        request.params.put("manv", manv);
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
            int position = Integer.parseInt(extraData.get("position").toString());
            JSONObject jsonObject = null;
            switch (TAG) {
                case "TRASO":
                    Gson g = new Gson();
                    BaoHiem baoHiem = g.fromJson(responseText, BaoHiem.class);
                    data.get(position).setTraso(baoHiem.getTraso());
                    data.get(position).setTraso2(baoHiem.getTraso2().toString());
                    notifyItemChanged(position);
                    break;
                case "DELETE_BAOHIEM":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công thông tin bảo hiểm của nhân viên (" + data.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            data.remove(position);
                            notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
            }
        } else {
            MDToast.makeText(mContext, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtMaSoHD)
        TextView txtMaSoHD;

        @BindView(R.id.txtSoBHXH)
        TextView txtSoBHXH;

        @BindView(R.id.txtSoBHYT)
        TextView txtSoBHYT;

        @BindView(R.id.txtNgayBatDau)
        TextView txtNgayBatDau;

        @BindView(R.id.txtMucDong)
        TextView txtMucDong;

        @BindView(R.id.txtTraSo)
        TextView txtTraSo;

        @BindView(R.id.imgView)
        ImageView imgView;


        @BindView(R.id.txtMenuOpTion)
        TextView txtMenuOpTion;

        @BindView(R.id.cardview_id)
        CardView cardview_id;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public void filter(String keys) {
        data.clear();
        String charText = keys.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            data.addAll(temp);
        } else {
            for (BaoHiem baoHiem : temp) {
                if (String.valueOf(baoHiem.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(baoHiem.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(baoHiem.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(baoHiem.getMasohopdong()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(baoHiem.getSobhxh()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(baoHiem.getSothebhyt()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(baoHiem);
                }
            }
        }
        notifyDataSetChanged();
    }
}