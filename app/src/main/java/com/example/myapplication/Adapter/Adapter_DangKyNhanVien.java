package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.CTPhepNam;
import com.example.myapplication.Model.DangKyNhanVien;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.PhuLucHopDong;
import com.example.myapplication.R;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.myapplication.libs.springyRecyclerView.SpringyAdapterAnimator;
import com.skydoves.powermenu.PowerMenu;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_DangKyNhanVien extends RecyclerView.Adapter<Adapter_DangKyNhanVien.RecyclerViewHolder> {
    private Context mContext;
    private List<DangKyNhanVien> data = new ArrayList<>();
    private Unbinder unbinder;

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_nhanvien_quanly, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtNgaySinh.setText(data.get(position).getNgaysinh());
        holder.txtNoiSinh.setText(data.get(position).getNoisinh());
        holder.txtChucVu.setText(data.get(position).getChucvu());
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

    public Adapter_DangKyNhanVien(Context mContext, List<DangKyNhanVien> data) {
        this.data = data;
        this.mContext = mContext;
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


        @BindView(R.id.txtNoiSinh)
        TextView txtNoiSinh;

        @BindView(R.id.txtChucVu)
        TextView txtChucVu;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtTo)
        TextView txtTo;

        @BindView(R.id.imgView)
        ImageView imgView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }


}