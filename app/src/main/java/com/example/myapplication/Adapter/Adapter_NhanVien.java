package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Helper.ImageHelper;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.CheDoBaoHiem;
import com.example.myapplication.Model.CongTac;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.NhanVienNghiViec;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimator;
import com.example.myapplication.ui.NhatKyQuetThe.NhatKyQuetThe_MaNV_Activity;
import com.example.myapplication.ui.PhepNam.CTPhepNam_Activity;
import com.example.myapplication.ui.VeSom.VeSom_Activity;
import com.example.myapplication.ui.nghiphep.NghiPhep_MaNV_Activity;
import com.example.myapplication.ui.nhanvien.HDLD_Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class Adapter_NhanVien extends RecyclerView.Adapter<Adapter_NhanVien.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<NhanVien> data = new ArrayList<>();
    private List<NhanVien> temp = new ArrayList<>();
    private Unbinder unbinder;
    public NhanVien nhanVien;
    IRequestHttpCallback iRequestHttpCallback;
    private SpringyAdapterAnimator mAnimator;
    Integer position_item;
    PowerMenu powerMenu;
//
//    public Adapter_NhanVien(Context mContext, List<NhanVien> data) {
//        this.data = data;
//        temp.addAll(data);
//        this.mContext = mContext;
//        iRequestHttpCallback = this;
//    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_nhanvien, parent, false);

        mAnimator.onSpringItemCreate(view);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtNgaySinh.setText(data.get(position).getNgaysinh());
        holder.txtNgayVaoLam.setText(data.get(position).getNgayvaolam());
        holder.txtNoiSinh.setText(data.get(position).getNoisinh());
        holder.txtNguyenQuan.setText(data.get(position).getNguyenquan());
        holder.txtDiaChi.setText(data.get(position).getDiachi());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtChuyen.setText(data.get(position).getTenchuyen());
        holder.txtTo.setText(data.get(position).getTento());

        String status = data.get(position).getStatus();
        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                // .resize(70,70)
                .error(R.drawable.no_avatar)//hien thi hinh mac dinh khi ko co hinh
                // .centerCrop()
                .placeholder(R.drawable.no_avatar)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.imgView);

        if (status.equals("NO")) {
            setBW(holder.imgView);
        } else {
            holder.imgView.setColorFilter(null);
        }

        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                position_item = position;
                powerMenu = new PowerMenu.Builder(mContext)
                        .addItem(new PowerMenuItem("Phép năm", R.drawable.ic_menu_phepnam2, "PHEPNAM")) // add an item.
                        .addItem(new PowerMenuItem("Nhật ký quét thẻ", R.drawable.ic_menu_quetthe2, "QUETTHE")) // aad an item list.
                        .addItem(new PowerMenuItem("Nhật ký nghỉ phép", R.drawable.ic_menu_nghiphep, "NGHIPHEP")) // aad an item list.
                        .addItem(new PowerMenuItem("Hợp đồng lao động", R.drawable.ic_menu_hopdong2, "HOPDONG")) // aad an item list.
                        .addItem(new PowerMenuItem("Xem ảnh", R.drawable.ic_menu_xemanh, "XEMANH")) // aad an item list.
                        .addItem(new PowerMenuItem("Xóa", R.drawable.ic_delete, "XOA")) // aad an item list.
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
        mAnimator.onSpringItemBind(holder.itemView, position);
    }

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            NhanVien nhanVien = data.get(position_item);
            Modules1.strMaNV = data.get(position_item).getManv2();
            String TAG = item.getTag().toString();

            switch (TAG) {
                case "QUETTHE":
                    Intent intent = new Intent(mContext, NhatKyQuetThe_MaNV_Activity.class);
                    mContext.startActivity(intent);
                    break;
                case "NGHIPHEP":
                    Intent intent_nghiphep = new Intent(mContext, NghiPhep_MaNV_Activity.class);
                    mContext.startActivity(intent_nghiphep);
                    break;
                case "HOPDONG":
                    Intent intentHDLD = new Intent(mContext, HDLD_Activity.class);
                    mContext.startActivity(intentHDLD);
                    break;
                case "PHEPNAM":
                    Intent intentPhepNam = new Intent(mContext, CTPhepNam_Activity.class);
                    mContext.startActivity(intentPhepNam);
                    break;
                case "XEMANH":
                    String url = nhanVien.getHinh();
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    break;
                case "XOA":
                    Delete_NhanVien(nhanVien, position_item);

            }
            powerMenu.dismiss();
        }
    };

    public Adapter_NhanVien(Context mContext, ArrayList<NhanVien> titleList, RecyclerView recyclerView) {
        this.data = titleList;
        this.mContext = mContext;
        if (titleList.size() > 0) {

            temp.addAll(titleList);
        }
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_RIGHT);
        mAnimator.addConfig(100, 15);
        iRequestHttpCallback = this;
    }


    //Set màu trắng đen cho Image View
    private void setBW(ImageView iv) {
        float brightness = 10; // change values to suite your need
        float[] colorMatrix = {
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0, 0, 0, 1, 0
        };
        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        iv.setColorFilter(colorFilter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void Delete_NhanVien(final NhanVien nhanVien, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xóa")
                .setMessage("Bạn có muốn xóa nhân viên (" + nhanVien.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String manv = String.valueOf(nhanVien.getManv2());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien", iRequestHttpCallback, "DELETE_TTNHANVIEN");
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
            JSONObject jsonObject = null;
            int position = Integer.parseInt(extraData.get("position").toString());
            switch (TAG) {
                case "DELETE_TTNHANVIEN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công thông tin của nhân viên (" + data.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            data.remove(position);
                            notifyDataSetChanged();
                        } else {
                            MDToast.makeText(mContext, "Nhân viên (" + data.get(position).getTennv() + ") đã được áp dụng.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
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

        @BindView(R.id.txtNgaySinh)
        TextView txtNgaySinh;

        @BindView(R.id.txtNgayVaoLam)
        TextView txtNgayVaoLam;

        @BindView(R.id.txtNoiSinh)
        TextView txtNoiSinh;

        @BindView(R.id.txtNguyenQuan)
        TextView txtNguyenQuan;

        @BindView(R.id.txtDiaChi)
        TextView txtDiaChi;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtTo)
        TextView txtTo;

        @BindView(R.id.imgView)
        ImageView imgView;

        @BindView(R.id.txtMenuOpTion)
        TextView txtMenuOpTion;

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
            for (NhanVien nhanVien : temp) {
                if (String.valueOf(nhanVien.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVien.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVien.getNoisinh()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVien.getNgaysinh()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVien.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVien.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVien.getTento()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(nhanVien);
                }
            }
        }
        notifyDataSetChanged();
    }
}