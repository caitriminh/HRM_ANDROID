package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.NhanVienNghiViec;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimator;
import com.example.myapplication.ui.NhatKyQuetThe.NhatKyQuetThe_MaNV_Activity;
import com.example.myapplication.ui.nghiphep.NghiPhep_MaNV_Activity;
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

public class Adapter_NhanVien extends RecyclerView.Adapter<Adapter_NhanVien.RecyclerViewHolder> {
    private Context mContext;
    private List<NhanVien> data = new ArrayList<>();
    private List<NhanVien> temp = new ArrayList<>();
    private Unbinder unbinder;
    Fragment NhanVienFragment;
    public NhanVien nhanVien;
    private SpringyAdapterAnimator mAnimator;

    public Adapter_NhanVien(Context mContext, List<NhanVien> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

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
        }
        else{
            holder.imgView.setColorFilter(null);
        }

        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.txtMenuOpTion);
                popup.inflate(R.menu.option_menu_nhanvien);
                setForceShowIcon(popup);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_nhatky_quetthe:
                                Modules1.objNhanVien = data.get(position);
                                Intent intent = new Intent(mContext, NhatKyQuetThe_MaNV_Activity.class);
                                mContext.startActivity(intent);
                                break;
                            case R.id.menu_nghiphep:
                                Modules1.strMaNV = data.get(position).getManv2();
                                Intent intent_nghiphep = new Intent(mContext, NghiPhep_MaNV_Activity.class);
                                mContext.startActivity(intent_nghiphep);
                                break;
                            case R.id.menu_xemanh:
                                nhanVien = data.get(position);
                                String url = nhanVien.getHinh();
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
        mAnimator.onSpringItemBind(holder.itemView, position);
    }

    public Adapter_NhanVien(Context mContext, ArrayList<NhanVien> titleList, RecyclerView recyclerView) {
        this.data = titleList;
        this.mContext = mContext;
        if (titleList.size() > 0) {

            temp.addAll(titleList);
        }
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_RIGHT);
        mAnimator.addConfig(100, 15);
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