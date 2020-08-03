package com.example.HRM.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Model.CongTac;
import com.example.HRM.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_CongTacMaNV extends RecyclerView.Adapter<Adapter_CongTacMaNV.RecyclerViewHolder> {
    private Context mContext;
    private List<CongTac> data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_CongTacMaNV(Context mContext, List<CongTac> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_ct_nhatky_congtac, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtGioRa.setText(data.get(position).getGiodi());
        holder.txtNgayDangKy.setText(data.get(position).getNgaynhap());
        holder.txtLyDo.setText(data.get(position).getLydo());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtGioRa)
        TextView txtGioRa;

        @BindView(R.id.txtNgayDangKy)
        TextView txtNgayDangKy;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

}