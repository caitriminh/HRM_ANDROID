package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.NhanVienTangCa;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NhanVienTangCa_MaLenh extends RecyclerView.Adapter<Adapter_NhanVienTangCa_MaLenh.RecyclerViewHolder> {
    private Context mContext;
    private List<NhanVienTangCa> data = new ArrayList<>();
    private List<NhanVienTangCa> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_NhanVienTangCa_MaLenh(Context mContext, List<NhanVienTangCa> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_nhanvien_tangca, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayTangCa.setText(data.get(position).getNgaytangca());
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtGioBD.setText(data.get(position).getGiobd());
        holder.txtGioKT.setText(data.get(position).getGiokt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNgayTangCa)
        TextView txtNgayTangCa;

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtGioBD)
        TextView txtGioBD;

        @BindView(R.id.txtGioKT)
        TextView txtGioKT;

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
            for (NhanVienTangCa nhanVienTangCa : temp) {
                if (String.valueOf(nhanVienTangCa.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienTangCa.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(nhanVienTangCa);
                }
            }
        }
        notifyDataSetChanged();
    }
}