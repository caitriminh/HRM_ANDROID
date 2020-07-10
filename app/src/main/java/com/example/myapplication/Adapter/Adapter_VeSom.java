package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.VeSom;
import com.example.myapplication.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_VeSom extends RecyclerView.Adapter<Adapter_VeSom.RecyclerViewHolder> {
    private Context mContext;
    private List<VeSom> data = new ArrayList<>();
    private List<VeSom> temp = new ArrayList<>();
    VeSom veSom;
    private Unbinder unbinder;

    public Adapter_VeSom(Context mContext, List<VeSom> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_vesom, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtChuyen.setText(data.get(position).getTenchuyen());
        holder.txtNgayDangKy.setText(data.get(position).getNgaynhap());
        holder.txtGioRa_GioVao.setText(data.get(position).getGioravao());
        holder.txtLyDo.setText(data.get(position).getLydo());
        holder.txtGhiChu.setText(data.get(position).getGhichu());
        holder.txtNguoiDuyet.setText(data.get(position).getNguoiduyet());


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
                PopupMenu popup = new PopupMenu(mContext, holder.txtMenuOpTion);
                popup.inflate(R.menu.option_menu_vesom);
                setForceShowIcon(popup);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_xacnhan:
//                                Modules1.objNhanVien = data.get(position);
//                                Intent intent = new Intent(mContext, NhatKyQuetThe_MaNV_Activity.class);
//                                mContext.startActivity(intent);
                                break;
                            case R.id.menu_pheduyet:
//                                Modules1.objNhanVien = data.get(position);
//                                Intent intent_nghiphep = new Intent(mContext, NghiPhep_MaNV_Activity.class);
//                                mContext.startActivity(intent_nghiphep);
                                break;
                            case R.id.menu_xemanh:
                                veSom = data.get(position);
                                String url = veSom.getHinh();
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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtNgayDangKy)
        TextView txtNgayDangKy;

        @BindView(R.id.txtGioRa_GioVao)
        TextView txtGioRa_GioVao;

        @BindView(R.id.txtNguoiDuyet)
        TextView txtNguoiDuyet;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtGhiChu)
        TextView txtGhiChu;

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
            for (VeSom veSom : temp) {
                if (String.valueOf(veSom.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getNgaynhap()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getLydo()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(veSom);
                }
            }
        }
        notifyDataSetChanged();
    }
}