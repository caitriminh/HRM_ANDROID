package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.CongTac;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.VeSom;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.ui.VeSom.VeSom_Activity;
import com.example.myapplication.ui.nghiphep.NghiPhep_MaNV_Activity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_VeSom extends RecyclerView.Adapter<Adapter_VeSom.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<VeSom> data = new ArrayList<>();
    private List<VeSom> temp = new ArrayList<>();
    IRequestHttpCallback iRequestHttpCallback;

    VeSom veSom;
    private Unbinder unbinder;
    PowerMenu powerMenu;
    Integer option = 1, position_item=-1;

    public Adapter_VeSom(Context mContext, List<VeSom> data) {
        this.data = data;
        temp.addAll(data);
        iRequestHttpCallback = this;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_vesom, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtChuyen.setText(data.get(position).getTenchuyen());
        holder.txtNgayDangKy.setText(data.get(position).getNgaynhap());
        holder.txtGioRa_GioVao.setText(data.get(position).getGioravao());
        holder.txtLyDo.setText(data.get(position).getLydo());
        holder.txtGhiChu.setText(data.get(position).getGhichu());
        holder.txtNguoiDuyet.setText(data.get(position).getNguoiduyet());

        String status_quanly = data.get(position).getStatus_quanly();
        String status_nhansu = data.get(position).getStatus_nhansu();

        if (status_nhansu.equals("NO")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#d1e0e0"));
        } else if (status_nhansu.equals("YES")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            if (status_quanly.equals("NO")) {
                holder.cardview_id.setBackgroundColor(Color.parseColor("#d1e0e0"));
            } else if (status_quanly.equals("YES")) {
                holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                holder.cardview_id.setBackgroundColor(Color.parseColor("#ffcce0"));
            }
        }
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

        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                position_item=position;
                powerMenu = new PowerMenu.Builder(mContext)
                        // .setHeaderView(R.layout.layout_dialog_header)
                        .addItem(new PowerMenuItem("Xác nhận", R.drawable.ic_baseline_security_24)) // add an item.
                        .addItem(new PowerMenuItem("Phê duyệt", R.drawable.ic_menu_pheduyet)) // aad an item list.
                        .addItem(new PowerMenuItem("Nhật ký về sớm", R.drawable.ic_ct_nghiphep)) // aad an item list.
                        .addItem(new PowerMenuItem("Xem ảnh", R.drawable.ic_menu_xemanh)) // aad an item list.
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
            VeSom veSom = data.get(position_item);
            if (position == 0) {
                if (veSom.getStatus_nhansu().equals("YES") || veSom.getStatus_nhansu().equals("NO")) {
                    MDToast.makeText(mContext, "Phiếu đăng ký về sớm của nhân viên (" + veSom.getTennv() + ") đã được phê duyêt.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                } else {
                    XacNhan_VeSom(veSom, position_item);
                }
            } else if (position == 1) {
                PheDuyet_VeSom(veSom, position_item);
            } else if (position == 2) {
                Modules1.strMaNV = data.get(position_item).getManv2();
                Intent intent_vesom = new Intent(mContext, VeSom_Activity.class);
                mContext.startActivity(intent_vesom);
            } else if (position == 3) {
                veSom = data.get(position_item);
                String url = veSom.getHinh();
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
            powerMenu.dismiss();
        }
    };

    //Set icon menu popup
//    public static void setForceShowIcon(PopupMenu popupMenu) {
////        try {
////            Field[] fields = popupMenu.getClass().getDeclaredFields();
////            for (Field field : fields) {
////                if ("mPopup".equals(field.getName())) {
////                    field.setAccessible(true);
////                    Object menuPopupHelper = field.get(popupMenu);
////                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
////                            .getClass().getName());
////                    Method setForceIcons = classPopupHelper.getMethod(
////                            "setForceShowIcon", boolean.class);
////                    setForceIcons.invoke(menuPopupHelper, true);
////                    break;
////                }
////            }
////        } catch (Throwable e) {
////            e.printStackTrace();
////        }
////    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void XacNhan(int position) {
        String id = String.valueOf(veSom.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_quanly_nghiphep", iRequestHttpCallback, "XACNHAN_VESOM");
        request.params.put("option", option);
        request.params.put("id", id);
        request.params.put("nguoitd", Modules1.tendangnhap);
        request.extraData.put("position", position);
        request.execute();
    }

    private void XacNhan_VeSom(final VeSom veSom, final int position) {
        this.veSom = veSom;
        String title = "";
        if (veSom.getStatus_quanly().equals("")) {
            title = "Bạn có muốn phê duyệt phiếu đăng ký về sớm của nhân viên (" + veSom.getTennv() + ") này không?";

        } else if (veSom.getStatus_quanly().equals("YES")) {
            title = "Bạn có muốn thu hồi phê duyệt phiếu đăng ký về sớm của nhân viên (" + veSom.getTennv() + ") này không?";

        } else if (veSom.getStatus_quanly().equals("NO")) {
            title = "Bạn có muốn xác nhận phiếu đăng ký về sớm của nhân viên (" + veSom.getTennv() + ") này không?";

        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
        if (veSom.getStatus_quanly().equals("")) {
            builder.setTitle("Phê Duyệt")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)

                    .setNegativeButton("PHÊ DUYỆT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 1;
                            XacNhan(position);
                        }
                    })
                    .setPositiveButton("KHÔNG PHÊ DUYỆT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 2;
                            XacNhan(position);
                        }
                    })
                    .setNeutralButton("BỎ QUA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(false);
            builder.create().show();
        } else if (veSom.getStatus_quanly().equals("YES")) {
            builder
                    .setTitle("Phê Duyệt")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)

                    .setNegativeButton("THU HỒI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 3;
                            XacNhan(position);
                        }
                    })
                    .setPositiveButton("BỎ QUA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    })
                    .setCancelable(false);
            builder.create().show();
        } else if (veSom.getStatus_quanly().equals("NO")) {
            builder
                    .setTitle("Phê Duyệt")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)

                    .setNegativeButton("PHÊ DUYỆT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 1;
                            XacNhan(position);
                        }
                    })
                    .setPositiveButton("BỎ QUA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .setCancelable(false);
            builder.create().show();
        }
    }

    private void PheDuyet_VeSom(final VeSom veSom, final int position) {
        this.veSom = veSom;
        String title = "";
        if (veSom.getStatus_nhansu().equals("")) {
            title = "Bạn có muốn phê duyệt phiếu đăng ký về sớm của nhân viên (" + veSom.getTennv() + ") này không?";
        } else if (veSom.getStatus_nhansu().equals("YES")) {
            title = "Bạn có muốn thu hồi phê duyệt phiếu đăng ký về sớm của nhân viên (" + veSom.getTennv() + ") này không?";
        } else if (veSom.getStatus_nhansu().equals("NO")) {
            title = "Bạn có muốn phê duyệt phiếu đăng ký về sớm của nhân viên (" + veSom.getTennv() + ") này không?";
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
        if (veSom.getStatus_nhansu().equals("")) {
            builder.setTitle("Phê Duyệt")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)
                    .setNegativeButton("PHÊ DUYỆT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 1;
                            PheDuyet(position);
                        }
                    })
                    .setPositiveButton("KHÔNG PHÊ DUYỆT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 2;
                            PheDuyet(position);
                        }
                    })
                    .setNeutralButton("BỎ QUA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(false);
            builder.create().show();
        } else if (veSom.getStatus_nhansu().equals("YES")) {
            builder
                    .setTitle("Phê Duyệt")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)
                    .setNegativeButton("THU HỒI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 3;
                            PheDuyet(position);
                        }
                    })
                    .setPositiveButton("BỎ QUA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    })
                    .setCancelable(false);
            builder.create().show();
        } else if (veSom.getStatus_nhansu().equals("NO")) {
            builder
                    .setTitle("Phê Duyệt")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)
                    .setNegativeButton("PHÊ DUYỆT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 1;
                            PheDuyet(position);
                        }
                    })
                    .setPositiveButton("BỎ QUA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(false);
            builder.create().show();
        }
    }

    public void PheDuyet(int position) {
        String id = String.valueOf(veSom.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_nhansu_nghiphep", iRequestHttpCallback, "PHEDUYET_VESOM");
        request.params.put("option", option);
        request.params.put("id", id);
        request.params.put("nguoitd", Modules1.tendangnhap);
        request.extraData.put("position", position);
        request.execute();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            int position = Integer.parseInt(extraData.get("position").toString());
            switch (TAG) {
                case "XACNHAN_VESOM":
                    Gson g = new Gson();
                    VeSom veSom = g.fromJson(responseText, VeSom.class);
                    data.get(position).setStatus_quanly(veSom.getStatus_quanly());
                    data.get(position).setNguoiduyet((veSom.getNguoiduyet()));
                    notifyItemChanged(position);
                    break;
                case "PHEDUYET_VESOM":
                    Gson g_pheduyet = new Gson();
                    VeSom veSom1 = g_pheduyet.fromJson(responseText, VeSom.class);
                    data.get(position).setStatus_nhansu(veSom1.getStatus_nhansu());
                    data.get(position).setNguoiduyet((veSom1.getNguoiduyet()));
                    notifyItemChanged(position);
                    break;
            }
        } else {
            MDToast.makeText(mContext, "Kết nối với máy chủ thất bại.", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaNV)
        TextView txtMaNV;

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtNgayDangKy)
        TextView txtNgayDangKy;

        @BindView(R.id.txtGioRa_GioVao)
        TextView txtGioRa_GioVao;

        @BindView(R.id.txtNguoiDuyet)
        TextView txtNguoiDuyet;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtGhiChu)
        TextView txtGhiChu;

        @BindView(R.id.imgView)
        ImageView imgView;

        @BindView(R.id.txtMenuOpTion)
        TextView txtMenuOpTion;

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
            for (VeSom veSom : temp) {
                if (String.valueOf(veSom.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getNgaynhap()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getLydo()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(veSom.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(veSom);
                }
            }
        }
        notifyDataSetChanged();
    }
}