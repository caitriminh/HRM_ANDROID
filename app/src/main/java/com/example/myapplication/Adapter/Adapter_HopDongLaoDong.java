package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.HopDongLaoDong;
import com.example.myapplication.Model.VeSom;
import com.example.myapplication.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_HopDongLaoDong extends RecyclerView.Adapter<Adapter_HopDongLaoDong.RecyclerViewHolder> {
    private Context mContext;
    private List<HopDongLaoDong> data = new ArrayList<>();
    private List<HopDongLaoDong> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_HopDongLaoDong(Context mContext, List<HopDongLaoDong> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_hopdong_laodong, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtMaSoHD.setText(data.get(position).getMasohopdong());
        holder.txtLoaiHopDong.setText(data.get(position).getLoaihopdong());
        holder.txtNgayKy.setText(data.get(position).getNgayky());
        holder.txtTinhTrang.setText(data.get(position).getTinhtrang_hd());
        holder.txtMucLuong.setText(data.get(position).getMucluong());
        holder.txtChucVu.setText(data.get(position).getChucvu());


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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtMaSoHD)
        TextView txtMaSoHD;

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtChucVu)
        TextView txtChucVu;

        @BindView(R.id.txtNgayKy)
        TextView txtNgayKy;

        @BindView(R.id.txtMucLuong)
        TextView txtMucLuong;

        @BindView(R.id.txtTinhTrang)
        TextView txtTinhTrang;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtLoaiHopDong)
        TextView txtLoaiHopDong;

        @BindView(R.id.imgView)
        ImageView imgView;

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
            for (HopDongLaoDong hopDongLaoDong : temp) {
                if (String.valueOf(hopDongLaoDong.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(hopDongLaoDong.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(hopDongLaoDong.getNgayky()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(hopDongLaoDong.getLoaihopdong()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(hopDongLaoDong);
                }
            }
        }
        notifyDataSetChanged();
    }
}