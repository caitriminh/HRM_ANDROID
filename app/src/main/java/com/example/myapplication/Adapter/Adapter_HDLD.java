package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.HopDongLaoDong;
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

public class Adapter_HDLD extends RecyclerView.Adapter<Adapter_HDLD.RecyclerViewHolder> {
    private Context mContext;
    private List<HopDongLaoDong> data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_HDLD(Context mContext, List<HopDongLaoDong> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_hopdong_laodong, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaSoHD.setText(data.get(position).getMasohopdong());
               holder.txtNgayKy.setText(data.get(position).getNgayky());
        holder.txtTinhTrang.setText(data.get(position).getTinhtrang_hd());
        holder.txtMucLuong.setText(data.get(position).getMucluong());
        holder.txtHinhThucTraLuong.setText(data.get(position).getHinhthuctraluong());
        holder.txtNoiDungCongViec.setText(data.get(position).getNoidungcongviec());

        String tinhtrang=data.get(position).getTinhtrang();
        if (tinhtrang.equals("Hết hạn")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#d1e0e0"));
        } else if (tinhtrang.equals("Hết hiệu lực")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffcdd2"));
        }
        else{
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        }

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

        @BindView(R.id.txtTinhTrang)
        TextView txtTinhTrang;

        @BindView(R.id.txtMucLuong)
        TextView txtMucLuong;

        @BindView(R.id.txtHinhThucTraLuong)
        TextView txtHinhThucTraLuong;

        @BindView(R.id.txtNoiDungCongViec)
        TextView txtNoiDungCongViec;

        @BindView(R.id.cardview_id)
        CardView cardview_id;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}