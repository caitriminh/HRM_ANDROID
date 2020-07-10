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
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_LenhTangCa extends RecyclerView.Adapter<Adapter_LenhTangCa.RecyclerViewHolder> {
    private Context mContext;
    private List<LenhTangCa> data = new ArrayList<>();
    private List<LenhTangCa> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_LenhTangCa(Context mContext, List<LenhTangCa> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_lenhtangca, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaLenh.setText(data.get(position).getMalenh());
        holder.txtNgayTangCa.setText(data.get(position).getNgaytangca());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtGioBD.setText(data.get(position).getGiobd());
        holder.txtGioKT.setText(data.get(position).getGiokt());
        String status = data.get(position).getStatus();
        if (status.equals("NO")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#eb7a7a"));
        } else {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaLenh)
        TextView txtMaLenh;

        @BindView(R.id.txtNgayTangCa)
        TextView txtNgayTangCa;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtGioBD)
        TextView txtGioBD;

        @BindView(R.id.txtGioKT)
        TextView txtGioKT;

        @BindView(R.id.cardview_id)
        CardView cardview_id;


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
            for (LenhTangCa lenhTangCa : temp) {
                if (String.valueOf(lenhTangCa.getMalenh()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(lenhTangCa.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(lenhTangCa);
                }
            }
        }
        notifyDataSetChanged();
    }
}