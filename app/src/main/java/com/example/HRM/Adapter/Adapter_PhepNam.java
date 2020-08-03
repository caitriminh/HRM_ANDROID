package com.example.HRM.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Model.PhepNam;
import com.example.HRM.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_PhepNam extends RecyclerView.Adapter<Adapter_PhepNam.RecyclerViewHolder> {
    private Context mContext;
    private List<PhepNam> data = new ArrayList<>();
    private List<PhepNam> temp = new ArrayList<>();
    private Unbinder unbinder;


    public Adapter_PhepNam(Context mContext, List<PhepNam> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_phepnam, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtTongPhepNam.setText(data.get(position).getPhepnam());
        holder.txtNghiPhepNam.setText(data.get(position).getNghiphepnam());
        holder.txtPhepNamConLai.setText(data.get(position).getPnconlai());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtChuyen.setText(data.get(position).getTenchuyen());
        holder.txtThamNien.setText(data.get(position).getThamnien());

        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                // .resize(70,70)
                .error(R.drawable.no_avatar)//hien thi hinh mac dinh khi ko co hinh
                // .centerCrop()
                .placeholder(R.drawable.no_avatar)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.imgView);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtTongPhepNam)
        TextView txtTongPhepNam;

        @BindView(R.id.txtNghiPhepNam)
        TextView txtNghiPhepNam;

        @BindView(R.id.txtPhepNamConLai)
        TextView txtPhepNamConLai;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtThamNien)
        TextView txtThamNien;

        @BindView(R.id.imgView)
        ImageView imgView;



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
            for (PhepNam phepNam : temp) {
                if (String.valueOf(phepNam.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(phepNam.getManv()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(phepNam);
                }
            }
        }
        notifyDataSetChanged();
    }
}