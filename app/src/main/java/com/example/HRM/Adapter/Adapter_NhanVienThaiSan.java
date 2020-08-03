package com.example.HRM.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.Model.NhanVienThaiSan;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.libs.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.HRM.libs.springyRecyclerView.SpringyAdapterAnimator;
import com.example.HRM.ui.thaisan.NhatKyKhamThai_Activity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NhanVienThaiSan extends RecyclerView.Adapter<Adapter_NhanVienThaiSan.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<NhanVienThaiSan> data = new ArrayList<>();
    private List<NhanVienThaiSan> temp = new ArrayList<>();
    private Unbinder unbinder;
    Integer position_item = -1;
    PowerMenu powerMenu;
    IRequestHttpCallback iRequestHttpCallback;
    private SpringyAdapterAnimator mAnimator;

//    public Adapter_NhanVienThaiSan(Context mContext, List<NhanVienThaiSan> data) {
//        this.data = data;
//        temp.addAll(data);
//        this.mContext = mContext;
//        iRequestHttpCallback = this;
//    }

    public Adapter_NhanVienThaiSan(Context mContext, ArrayList<NhanVienThaiSan> titleList, RecyclerView recyclerView) {
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

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_nhanvien_thaisan, parent, false);
        mAnimator.onSpringItemCreate(view);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtNgayLamLai.setText(data.get(position).getNgaylamlai());
        holder.txtNgayNghiTS.setText(data.get(position).getNgaynghits());
        holder.txtTrangThai.setText(data.get(position).getTrangthaithaisan());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtChuyen.setText(data.get(position).getTenchuyen());
        holder.txtLanMangThai.setText(data.get(position).getLanmangthai2());

        String matrangthai = data.get(position).getMatrangthai();

        if (matrangthai.equals("0")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        } else if (matrangthai.equals("1")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ff80deea"));
        } else if (matrangthai.equals("2")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffcdd2"));
        } else {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffe082"));
        }
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

        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                position_item = position;
                powerMenu = new PowerMenu.Builder(mContext)
                        // .setHeaderView(R.layout.layout_dialog_header)
                        .addItem(new PowerMenuItem("Nhật ký khám thai", R.drawable.ic_nhatky_khamthai, "KHAMTHAI"))
                        .addItem(new PowerMenuItem("Con nhỏ", R.drawable.ic_connho, "CONNHO"))
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
        //Hiệu ứng
        mAnimator.onSpringItemBind(holder.itemView, position);
    }

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            NhanVienThaiSan thaiSan = data.get(position_item);
            Modules1.strMaNV = thaiSan.getManv2();
            Modules1.objNhanVienThaiSan = thaiSan;
            String TAG = item.getTag().toString();
            switch (TAG) {
                case "XOA":
                    Delete_ThaiSan(thaiSan, position_item);
                    break;
                case "KHAMTHAI":
                    Intent intent = new Intent(mContext, NhatKyKhamThai_Activity.class);
                    mContext.startActivity(intent);
                    break;
                case "CONNHO":

                    break;
                case "XEMANH":
                    String url = thaiSan.getHinh();
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    break;
            }
            powerMenu.dismiss();
        }
    };

    private void Delete_ThaiSan(final NhanVienThaiSan thaiSan, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa nhân viên thai sản (" + thaiSan.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String manv = String.valueOf(thaiSan.getManv2());
                        Integer lanmangthai = Integer.valueOf(thaiSan.getLanmangthai());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien_thaisan", iRequestHttpCallback, "DELETE_THAISAN");
                        request.params.put("manv", manv);
                        request.params.put("lanmangthai", lanmangthai);
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
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            int position = Integer.parseInt(extraData.get("position").toString());
            switch (TAG) {
                case "DELETE_THAISAN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công nhân viên thai sản (" + data.get(position).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            data.remove(position);
                            notifyDataSetChanged();
                        } else {
                            MDToast.makeText(mContext, "Xóa không thành công.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
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

        @BindView(R.id.txtNgayNghiTS)
        TextView txtNgayNghiTS;

        @BindView(R.id.txtNgayLamLai)
        TextView txtNgayLamLai;

        @BindView(R.id.txtTrangThai)
        TextView txtTrangThai;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtLanMangThai)
        TextView txtLanMangThai;

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
            for (NhanVienThaiSan nhanVienThaiSan : temp) {
                if (String.valueOf(nhanVienThaiSan.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getTrangthaithaisan()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getNgaylamlai()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getNgaynghits()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(nhanVienThaiSan);
                }
            }
        }
        notifyDataSetChanged();
    }
}