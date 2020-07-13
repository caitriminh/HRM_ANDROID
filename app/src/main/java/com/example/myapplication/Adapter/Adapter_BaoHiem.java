package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.BaoHiem;
import com.example.myapplication.Model.CongTac;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimator;
import com.example.myapplication.ui.CheDoBaoHiem.CheDoBaoHiem_Activity;
import com.example.myapplication.ui.NhatKyQuetThe.NhatKyQuetThe_MaNV_Activity;
import com.example.myapplication.ui.nghiphep.NghiPhep_MaNV_Activity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

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


        if (status_traso2.equals("YES")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#d1e0e0"));
        } else {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.txtMenuOpTion);
                popup.inflate(R.menu.option_menu_baohiem);
                setForceShowIcon(popup);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        BaoHiem baoHiem = (BaoHiem) data.get(position);
                        switch (menuItem.getItemId()) {
                            case R.id.menu_traso:
                                XacNhan_TraSo(baoHiem, position);
                                break;
                            case R.id.menu_nhatky_chedo_baohiem:
                                Modules1.strMaNV = data.get(position).getManv2();
                                Intent intent = new Intent(mContext, CheDoBaoHiem_Activity.class);
                                mContext.startActivity(intent);
                                break;
                            case R.id.menu_xemanh:
                                baoHiem = data.get(position);
                                String url = baoHiem.getHinh();
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                break;
                        }
                        return false;

                    }

                });
                //displaying the popup
                popup.show();
            }
        });
        //   mAnimator.onSpringItemBind(holder.itemView, position);
    }

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
        if (baoHiem.getTraso2().equals("1")) {
            title = "Bạn có muốn xác nhận trả sổ bảo hiểm cho  nhân viên (" + baoHiem.getTennv() + ") này không?";

        } else {
            title = "Bạn có muốn thu hồi xác nhận trả sổ bảo hiểm cho nhân viên (" + baoHiem.getTennv() + ") này không?";

        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);

        builder.setTitle("Xác Nhận")
                .setIcon(R.drawable.message_icon)
                .setMessage(title)

                .setNegativeButton("XÁC NHẬN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TraSo(position);
                    }
                })
                .setPositiveButton("BỎ QUA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setCancelable(false);
        builder.create().show();

    }


    //Set icon menu popup
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            int position = Integer.parseInt(extraData.get("position").toString());
            switch (TAG) {
                case "TRASO":
                    Gson g = new Gson();
                    BaoHiem baoHiem = g.fromJson(responseText, BaoHiem.class);
                    data.get(position).setTraso(baoHiem.getTraso());
                    data.get(position).setTraso2(baoHiem.getTraso2().toString());
                    notifyItemChanged(position);
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