package com.example.HRM.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Model.NhatKyKhamThai;
import com.example.HRM.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NhatKyKhamThai extends RecyclerView.Adapter<Adapter_NhatKyKhamThai.RecyclerViewHolder> {
    private Context mContext;
    private List<NhatKyKhamThai> data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_NhatKyKhamThai(Context mContext, List<NhatKyKhamThai> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_nhatky_khamthai, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayKhamThai.setText(data.get(position).getNgaykhamthai());
        holder.txtSoTuan.setText(data.get(position).getSotuan().toString());
        holder.txtNgayDu25Tuan.setText(data.get(position).getNgaydu25tuan());
        holder.txtGhiChu.setText(data.get(position).getGhichu());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNgayKhamThai)
        TextView txtNgayKhamThai;

        @BindView(R.id.txtSoTuan)
        TextView txtSoTuan;

        @BindView(R.id.txtNgayDu25Tuan)
        TextView txtNgayDu25Tuan;

        @BindView(R.id.txtGhiChu)
        TextView txtGhiChu;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}