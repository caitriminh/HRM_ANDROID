package com.example.HRM.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Model.DiemDanh;
import com.example.HRM.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_DiemDanh extends RecyclerView.Adapter<Adapter_DiemDanh.RecyclerViewHolder> {
    private Context mContext;
    private List<DiemDanh> data = new ArrayList<>();
    private List<DiemDanh> temp = new ArrayList<>();
    private Unbinder unbinder;
    View view;

    public Adapter_DiemDanh(Context mContext, List<DiemDanh> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_row_diemdanh, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtSTT.setText(data.get(position).getStt());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtSiSo.setText(data.get(position).getSiso().toString());
        holder.txtHienDien.setText(data.get(position).getHiendien().toString());
        holder.txtVang.setText(data.get(position).getVang().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSTT)
        TextView txtSTT;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtSiSo)
        TextView txtSiSo;

        @BindView(R.id.txtHienDien)
        TextView txtHienDien;

        @BindView(R.id.txtVang)
        TextView txtVang;

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
            for (DiemDanh diemDanh : temp) {
                if (String.valueOf(diemDanh.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(diemDanh);
                }
            }
        }
        notifyDataSetChanged();
    }
}