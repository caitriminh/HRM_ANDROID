package com.example.HRM.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Model.NhatKyQuetThe;
import com.example.HRM.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_CT_QuetThe extends RecyclerView.Adapter<Adapter_CT_QuetThe.RecyclerViewHolder> {
    private Context mContext;
    private List<NhatKyQuetThe> data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_CT_QuetThe(Context mContext, List<NhatKyQuetThe> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_ct_quetthe, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayQuet.setText(data.get(position).getNgayquet());
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtMaSoThe.setText(data.get(position).getMasothe());
        holder.txtThoiGian.setText(data.get(position).getThoigian());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNgayQuet)
        TextView txtNgayQuet;

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtMaSoThe)
        TextView txtMaSoThe;

        @BindView(R.id.txtThoiGian)
        TextView txtThoiGian;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

}