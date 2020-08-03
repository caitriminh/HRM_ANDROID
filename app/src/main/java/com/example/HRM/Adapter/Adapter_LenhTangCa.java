package com.example.HRM.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.Model.LenhTangCa;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.ui.LenhTangCa.NhanVienTangCa_MaLenh_Activity;
import com.google.gson.Gson;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_LenhTangCa extends RecyclerView.Adapter<Adapter_LenhTangCa.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<LenhTangCa> data = new ArrayList<>();
    private List<LenhTangCa> temp = new ArrayList<>();
    private Unbinder unbinder;
    LenhTangCa lenhTangCa;
    PowerMenu powerMenu;
    IRequestHttpCallback iRequestHttpCallback;

    Integer position_item = -1, option = 1;
    String strMaLenh = "";

    public Adapter_LenhTangCa(Context mContext, List<LenhTangCa> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
        iRequestHttpCallback = this;
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
        holder.txtNhomCongViec.setText(data.get(position).getNhommay());
        holder.txtTinhTrang.setText(data.get(position).getTinhtrang());
        holder.txtMaTangCa.setText(data.get(position).getLoaitangca());

        String status = data.get(position).getStatus();
        if (status.equals("NO")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffb0bec5"));
        } else {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                position_item = position;
                powerMenu = new PowerMenu.Builder(mContext)
                        // .setHeaderView(R.layout.layout_dialog_header)

                        .addItem(new PowerMenuItem("Phê duyệt", R.drawable.ic_menu_pheduyet)) // aad an item list.
                        .addItem(new PowerMenuItem("Duyệt lệnh", R.drawable.ic_duyet_tatca)) // aad an item list.
                        .addItem(new PowerMenuItem("Đăng ký tăng ca", R.drawable.ic_ct_nghiphep)) // aad an item list.
                        .addItem(new PowerMenuItem("Xóa", R.drawable.ic_delete)) // aad an item list.
                        .setAnimation(MenuAnimation.ELASTIC_CENTER)
                        .setWidth(600)
                        .setDivider(new ColorDrawable(ContextCompat.getColor(mContext, R.color.bluegrey200))) // sets a divider.
                        .setDividerHeight(1)
                        .setMenuRadius(10f) // sets the corner radius.
                        .setMenuShadow(10f) // sets the shadow.
                        .setTextColor(ContextCompat.getColor(mContext, R.color.grey700))
                        .setTextGravity(Gravity.LEFT)
                        .setTextSize(16)
                        .setSelectedTextColor(Color.WHITE)
                        .setMenuColor(Color.WHITE)
                        .setSelectedMenuColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                        .setOnMenuItemClickListener(onMenuItemClickListener)
                        .build();
                //powerMenu.showAsAnchorCenter(view.getRootView(), 0, 0);
                powerMenu.showAsAnchorCenter(view, 0, 0);

            }
        });
    }

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            LenhTangCa lenhTangCa = data.get(position_item);
            if (position == 0) {
                PheDuyet_LenhTangCa(lenhTangCa, position_item);
            } else if (position == 1) {
                PheDuyetTatCa_LenhTangCa(lenhTangCa, position_item);
            } else if (position == 2) {
                if (lenhTangCa.getStatus().equals("YES")) {
                    Modules1.objLenhTangCa = data.get(position_item);
                    Intent intent_nghiphep = new Intent(mContext, NhanVienTangCa_MaLenh_Activity.class);
                    mContext.startActivity(intent_nghiphep);
                } else {
                    MDToast.makeText(mContext, "Lệnh tăng ca (" + lenhTangCa.getMalenh() + ") chưa phê duyệt.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                }
            } else if (position == 3) {
                if (lenhTangCa.getStatus().equals("NO")) {
                    Delete_LenhTangCa(lenhTangCa, position_item);
                } else {
                    MDToast.makeText(mContext, "Lệnh tăng ca (" + lenhTangCa.getMalenh() + ") đã phê duyệt.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                }
            }
            powerMenu.dismiss();
        }
    };

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            int position = Integer.parseInt(extraData.get("position").toString());
            switch (TAG) {
                case "DELETE_LENHTANGCA":
                    try {
                        jsonObject = new JSONObject(responseText);

                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công lệnh tăng ca (" + data.get(position).getMalenh() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            data.remove(position);
                            notifyDataSetChanged();
                        } else {
                            MDToast.makeText(mContext, "Lệnh tăng ca (" + data.get(position).getMalenh() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
                case "PHEDUYET_LENHTANGCA":
                    Gson g_pheduyet = new Gson();
                    LenhTangCa lenhTangCa = g_pheduyet.fromJson(responseText, LenhTangCa.class);
                    data.get(position).setStatus(lenhTangCa.getStatus());
                    if(option==1){
                        notifyItemChanged(position);
                    }
                    else{
                        notifyDataSetChanged();
                    }
                    break;
            }
        } else {
            MDToast.makeText(mContext, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaLenh)
        TextView txtMaLenh;

        @BindView(R.id.txtNgayTangCa)
        TextView txtNgayTangCa;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtNhomCongViec)
        TextView txtNhomCongViec;

        @BindView(R.id.cardview_id)
        CardView cardview_id;

        @BindView(R.id.txtMenuOpTion)
        TextView txtMenuOpTion;

        @BindView(R.id.txtTinhTrang)
        TextView txtTinhTrang;

        @BindView(R.id.txtMaTangCa)
        TextView txtMaTangCa;

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

    private void Delete_LenhTangCa(final LenhTangCa lenhTangCa, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa lệnh tăng ca (" + lenhTangCa.getMalenh() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String id = String.valueOf(lenhTangCa.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_lenhtangca", iRequestHttpCallback, "DELETE_LENHTANGCA");
                        request.params.put("id", id);
                        request.extraData.put("position", position);
                        request.execute();
                        dialogInterface.dismiss();
                    }

                })
                .setNegativeButton("Bỏ Qua", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }

                })
                .build();
        mBottomSheetDialog.show();
    }

    private void PheDuyet_LenhTangCa(final LenhTangCa lenhTangCa, final int position) {
        this.lenhTangCa = lenhTangCa;
        String title = "";
        if (lenhTangCa.getStatus().equals("NO")) {
            title = "Bạn có muốn phê duyệt lệnh tăng ca (" + lenhTangCa.getMalenh() + ") có chi tiết công việc ("+lenhTangCa.getNhommay()+") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Phê duyệt", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 1;
                            PheDuyet(position);
                            dialogInterface.dismiss();
                        }

                    })
                    .setNegativeButton("Đóng", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }

                    })
                    .build();
            mBottomSheetDialog.show();
        } else {
            title = "Bạn có muốn thu hồi phê duyệt lệnh tăng ca (" + lenhTangCa.getMalenh() + ") có chi tiết công việc ("+lenhTangCa.getNhommay()+") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Thu hồi", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 1;
                            PheDuyet(position);
                            dialogInterface.dismiss();
                        }

                    })
                    .setNegativeButton("Đóng", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }

                    })
                    .build();
            mBottomSheetDialog.show();
        }


    }

    private void PheDuyetTatCa_LenhTangCa(final LenhTangCa lenhTangCa, final int position) {
        this.lenhTangCa = lenhTangCa;
        String title = "";
        if (lenhTangCa.getStatus().equals("NO")) {
            title = "Bạn có muốn phê duyệt lệnh tăng ca (" + lenhTangCa.getMalenh() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Phê duyệt", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 2;
                            strMaLenh = lenhTangCa.getMalenh();
                            PheDuyet(position);
                            dialogInterface.dismiss();
                        }

                    })
                    .setNegativeButton("Đóng", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }

                    })
                    .build();
            mBottomSheetDialog.show();
        } else {
            title = "Bạn có muốn thu hồi phê duyệt lệnh tăng ca (" + lenhTangCa.getMalenh() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Thu hồi", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 2;
                            strMaLenh = lenhTangCa.getMalenh();
                            PheDuyet(position);
                            dialogInterface.dismiss();
                        }

                    })
                    .setNegativeButton("Đóng", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }

                    })
                    .build();
            mBottomSheetDialog.show();
        }


    }

    public void PheDuyet(int position) {
        String id = String.valueOf(lenhTangCa.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_lenhtangca_android", iRequestHttpCallback, "PHEDUYET_LENHTANGCA");
        request.params.put("option", option);
        request.params.put("malenh", strMaLenh);
        request.params.put("id", id);
        request.params.put("nguoitd", Modules1.tendangnhap);
        request.extraData.put("position", position);
        request.execute();
    }

}