package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.NhanVienNghiViec;
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

public class Adapter_NhanVienNghiViec extends RecyclerView.Adapter<Adapter_NhanVienNghiViec.RecyclerViewHolder> {
    private Context mContext;
    private List<NhanVienNghiViec> data = new ArrayList<>();
    private List<NhanVienNghiViec> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_NhanVienNghiViec(Context mContext, List<NhanVienNghiViec> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_nhanvien_nghiviec, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtNgaySinh.setText(data.get(position).getNgaysinh());
        holder.txtLyDo.setText(data.get(position).getLydo());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtChuyen.setText(data.get(position).getTenchuyen());
        holder.txtTo.setText(data.get(position).getTento());
        holder.txtNgayNghi.setText(data.get(position).getNgaynghi());
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

        @BindView(R.id.txtNgaySinh)
        TextView txtNgaySinh;

        @BindView(R.id.txtNgayNghi)
        TextView txtNgayNghi;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtTo)
        TextView txtTo;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

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
            for (NhanVienNghiViec nhanVienNghiViec : temp) {
                if (String.valueOf(nhanVienNghiViec.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getTento()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getLydo()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(nhanVienNghiViec);
                }
            }
        }
        notifyDataSetChanged();
    }
}