package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.NhatKyQuetThe;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NhatKyQuetThe_MaNV extends RecyclerView.Adapter<Adapter_NhatKyQuetThe_MaNV.RecyclerViewHolder> {
    private Context mContext;
    private List<NhatKyQuetThe> data = new ArrayList<>();
    private List<NhatKyQuetThe> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_NhatKyQuetThe_MaNV(Context mContext, List<NhatKyQuetThe> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_nhatkyquetthe, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayQuet.setText(data.get(position).getNgayquet());
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtGioQuet.setText(data.get(position).getThoigian());
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

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtGioQuet)
        TextView txtGioQuet;



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
            for (NhatKyQuetThe nhatKyQuetThe : temp) {
                if (String.valueOf(nhatKyQuetThe.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhatKyQuetThe.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(nhatKyQuetThe);
                }
            }
        }
        notifyDataSetChanged();
    }
}