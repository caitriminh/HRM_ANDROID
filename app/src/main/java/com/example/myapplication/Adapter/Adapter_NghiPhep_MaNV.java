package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.NghiPhep;
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

public class Adapter_NghiPhep_MaNV extends RecyclerView.Adapter<Adapter_NghiPhep_MaNV.RecyclerViewHolder> {
    private Context mContext;
    private List<NghiPhep> data = new ArrayList<>();
    private List<NghiPhep> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_NghiPhep_MaNV(Context mContext, List<NghiPhep> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_ct_nhatky_nghiphep, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtTuNgay.setText(data.get(position).getTungay());
        holder.txtDenNgay.setText(data.get(position).getDenngay());

        holder.txtNgayDangKy.setText(data.get(position).getNgaynhap());
        holder.txtSoNgay.setText(data.get(position).getSongay());
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

        @BindView(R.id.txtNgayDangKy)
        TextView txtNgayDangKy;

        @BindView(R.id.txtDenNgay)
        TextView txtDenNgay;

        @BindView(R.id.txtSoNgay)
        TextView txtSoNgay;


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
            for (NghiPhep nghiPhep : temp) {
                if (String.valueOf(nghiPhep.getNgaynhap()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(nghiPhep);
                }
            }
        }
        notifyDataSetChanged();
    }
}