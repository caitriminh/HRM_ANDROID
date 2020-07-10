package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.LenhTangCa;
import com.example.myapplication.Model.PhuLucHopDong;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_PhuLucHopDong extends RecyclerView.Adapter<Adapter_PhuLucHopDong.RecyclerViewHolder> {
    private Context mContext;
    private List<PhuLucHopDong> data = new ArrayList<>();
    private List<PhuLucHopDong> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_PhuLucHopDong(Context mContext, List<PhuLucHopDong> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_phuluc_hopdong, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaSoHD.setText(data.get(position).getMasohopdong());
        holder.txtNgayKy.setText(data.get(position).getNgaykyPhuluc());
        holder.txtMucLuong.setText(data.get(position).getMucluongcu());
        holder.txtMucLuongMoi.setText(data.get(position).getMucluongmoi());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaSoHD)
        TextView txtMaSoHD;

        @BindView(R.id.txtNgayKy)
        TextView txtNgayKy;

        @BindView(R.id.txtMucLuong)
        TextView txtMucLuong;

        @BindView(R.id.txtMucLuongMoi)
        TextView txtMucLuongMoi;






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
            for (PhuLucHopDong phuLucHopDong : temp) {
                if (String.valueOf(phuLucHopDong.getMasohopdong()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(phuLucHopDong);
                }
            }
        }
        notifyDataSetChanged();
    }
}