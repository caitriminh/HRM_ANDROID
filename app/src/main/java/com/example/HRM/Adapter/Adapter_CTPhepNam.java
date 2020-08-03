package com.example.HRM.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Model.CTPhepNam;
import com.example.HRM.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_CTPhepNam extends RecyclerView.Adapter<Adapter_CTPhepNam.RecyclerViewHolder> {
    private Context mContext;
    private List<CTPhepNam> data = new ArrayList<>();
       private Unbinder unbinder;

    public Adapter_CTPhepNam(Context mContext, List<CTPhepNam> data) {
        this.data = data;
             this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_ct_phepnam, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtTuNgay.setText(data.get(position).getTungay());
        holder.txtDenNgay.setText(data.get(position).getDenngay());
        holder.txtSoNgay.setText(data.get(position).getSongay());
        holder.txtLyDo.setText(data.get(position).getGhichu());
           }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtTuNgay)
        TextView txtTuNgay;

        @BindView(R.id.txtDenNgay)
        TextView txtDenNgay;

        @BindView(R.id.txtSoNgay)
        TextView txtSoNgay;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

}