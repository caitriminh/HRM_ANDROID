package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.NhanVienNghiViec;
import com.example.myapplication.Model.NhanVienThaiSan;
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

public class Adapter_NhanVienThaiSan extends RecyclerView.Adapter<Adapter_NhanVienThaiSan.RecyclerViewHolder> {
    private Context mContext;
    private List<NhanVienThaiSan> data = new ArrayList<>();
    private List<NhanVienThaiSan> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_NhanVienThaiSan(Context mContext, List<NhanVienThaiSan> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_nhanvien_thaisan, parent, false);

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
        holder.txtTo.setText(data.get(position).getTento());

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

        @BindView(R.id.txtTo)
        TextView txtTo;

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
            for (NhanVienThaiSan nhanVienThaiSan : temp) {
                if (String.valueOf(nhanVienThaiSan.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienThaiSan.getTento()).toLowerCase(Locale.getDefault()).contains(charText)
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