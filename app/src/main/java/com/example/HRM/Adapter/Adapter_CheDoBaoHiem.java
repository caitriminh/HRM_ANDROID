package com.example.HRM.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.Model.CheDoBaoHiem;
import com.example.HRM.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_CheDoBaoHiem extends RecyclerView.Adapter<Adapter_CheDoBaoHiem.RecyclerViewHolder> {
    private Context mContext;
    private List<CheDoBaoHiem> data = new ArrayList<>();
    private List<CheDoBaoHiem> temp = new ArrayList<>();
    private Unbinder unbinder;
    public CheDoBaoHiem cheDoBaoHiem;
    // private SpringyAdapterAnimator mAnimator;

    public Adapter_CheDoBaoHiem(Context mContext, List<CheDoBaoHiem> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_chedo_baohiem, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayNhap.setText(data.get(position).getNgaynhap());
        holder.txtNoiKham.setText(data.get(position).getNoikham());
        holder.txtTuNgay.setText(data.get(position).getTungay());
        holder.txtDenNgay.setText(data.get(position).getDenngay());
        holder.txtSoTien.setText(data.get(position).getSotien());
        holder.txtTinhTrang.setText(data.get(position).getDathanhtoan());
        holder.txtGhiChu.setText(data.get(position).getGhichu());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNgayNhap)
        TextView txtNgayNhap;

        @BindView(R.id.txtNoiKham)
        TextView txtNoiKham;

        @BindView(R.id.txtTuNgay)
        TextView txtTuNgay;

        @BindView(R.id.txtDenNgay)
        TextView txtDenNgay;

        @BindView(R.id.txtSoTien)
        TextView txtSoTien;

        @BindView(R.id.txtGhiChu)
        TextView txtGhiChu;

        @BindView(R.id.txtTinhTrang)
        TextView txtTinhTrang;


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
            for (CheDoBaoHiem cheDoBaoHiem : temp) {
                if (String.valueOf(cheDoBaoHiem.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(cheDoBaoHiem.getTungay()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(cheDoBaoHiem.getDenngay()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(cheDoBaoHiem.getGhichu()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(cheDoBaoHiem);
                }
            }
        }
        notifyDataSetChanged();
    }
}