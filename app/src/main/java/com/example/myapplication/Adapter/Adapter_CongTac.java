package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.CongTac;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.VeSom;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
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

public class Adapter_CongTac extends RecyclerView.Adapter<Adapter_CongTac.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<CongTac> data = new ArrayList<>();
    CongTac congTac;
    private List<CongTac> temp = new ArrayList<>();
    private Unbinder unbinder;

    Integer option = 1;
    IRequestHttpCallback iRequestHttpCallback;

    public Adapter_CongTac(Context mContext, List<CongTac> data) {
        this.data = data;
        temp.addAll(data);
        iRequestHttpCallback = this;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_congtac, parent, false);
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
        holder.txtNgayCongTac.setText(data.get(position).getNgaycongtac());
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
                PopupMenu popup = new PopupMenu(mContext, holder.txtMenuOpTion);
                popup.inflate(R.menu.option_menu_congtac);
                setForceShowIcon(popup);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        CongTac congTac = (CongTac) data.get(position);
                        switch (menuItem.getItemId()) {
                            case R.id.menu_xacnhan:
                                if(congTac.getStatus_nhansu().equals("YES")|| congTac.getStatus_nhansu().equals("NO")){
                                    MDToast.makeText(mContext, "Đăng ký đi công tác của nhân viên (" + congTac.getTennv() + ") đã được phê duyêt.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                                }
                                else{
                                    XacNhan_CongTac(congTac, position);
                                }

                                break;
                            case R.id.menu_pheduyet:
                                PheDuyet_CongTac(congTac, position);
                                break;
                            case R.id.menu_xemanh:
                                congTac = data.get(position);
                                String url = congTac.getHinh();
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                break;
                        }
                        return false;

                    }

                });
                //displaying the popup
                popup.show();
            }
        });
    }

    public void XacNhan(int position) {
        String id = String.valueOf(congTac.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_quanly_nghiphep", iRequestHttpCallback, "XACNHAN_CONGTAC");
        request.params.put("option", option);
        request.params.put("id", id);
        request.params.put("nguoitd", Modules1.tendangnhap);
        request.extraData.put("position", position);
        request.execute();
    }

    private void XacNhan_CongTac(final CongTac congTac, final int position) {
        this.congTac = congTac;
        String title = "";
        if (congTac.getStatus_quanly().equals("")) {
            title = "Bạn có muốn xác nhận đơn xin nghi phép nhân viên (" + congTac.getTennv() + ") này không?";

        } else if (congTac.getStatus_quanly().equals("YES")) {
            title = "Bạn có muốn thu hồi xác nhận đơn xin nghi phép nhân viên (" + congTac.getTennv() + ") này không?";

        } else if (congTac.getStatus_quanly().equals("NO")) {
            title = "Bạn có muốn xác nhận đơn xin nghi phép nhân viên (" + congTac.getTennv() + ") này không?";

        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
        if (congTac.getStatus_quanly().equals("")) {
            builder.setTitle("Xác Nhận")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)

                    .setNegativeButton("XÁC NHẬN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = 1;
                            XacNhan(position);
                        }
                    })
                    .setPositiveButton("KHÔNG XÁC NHẬN", new DialogInterface.OnClickListener() {
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
        } else if (congTac.getStatus_quanly().equals("YES")) {
            builder
                    .setTitle("Xác Nhận")
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
        } else if (congTac.getStatus_quanly().equals("NO")) {
            builder
                    .setTitle("Xác Nhận")
                    .setIcon(R.drawable.message_icon)
                    .setMessage(title)

                    .setNegativeButton("XÁC NHẬN", new DialogInterface.OnClickListener() {
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

    private void PheDuyet_CongTac(final CongTac congTac, final int position) {
        this.congTac = congTac;
        String title = "";
        if (congTac.getStatus_nhansu().equals("")) {
            title = "Bạn có muốn phê duyệt đi công tác nhân viên (" + congTac.getTennv() + ") này không?";
        } else if (congTac.getStatus_nhansu().equals("YES")) {
            title = "Bạn có muốn thu hồi phê duyệt đi công tác nhân viên (" + congTac.getTennv() + ") này không?";
        } else if (congTac.getStatus_nhansu().equals("NO")) {
            title = "Bạn có muốn phê duyệt đơn xin nghi phép nhân viên (" + congTac.getTennv() + ") này không?";
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
        if (congTac.getStatus_nhansu().equals("")) {
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
        } else if (congTac.getStatus_nhansu().equals("YES")) {
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
        } else if (congTac.getStatus_nhansu().equals("NO")) {
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
        String id = String.valueOf(congTac.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_nhansu_nghiphep", iRequestHttpCallback, "PHEDUYET_CONGTAC");
        request.params.put("option", option);
        request.params.put("id", id);
        request.params.put("nguoitd", Modules1.tendangnhap);
        request.extraData.put("position", position);
        request.execute();
    }

    //Set icon menu popup
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            int position = Integer.parseInt(extraData.get("position").toString());
            switch (TAG) {
                case "XACNHAN_CONGTAC":
                    Gson g = new Gson();
                    CongTac congTac = g.fromJson(responseText, CongTac.class);
                    data.get(position).setStatus_quanly(congTac.getStatus_quanly());
                    data.get(position).setNguoiduyet((congTac.getNguoiduyet()));
                    notifyItemChanged(position);
                    break;
                case "PHEDUYET_CONGTAC":
                    Gson g_pheduyet = new Gson();
                    CongTac congTac1 = g_pheduyet.fromJson(responseText, CongTac.class);
                    data.get(position).setStatus_nhansu(congTac1.getStatus_nhansu());
                    data.get(position).setNguoiduyet((congTac1.getNguoiduyet()));
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

        @BindView(R.id.txtNgayCongTac)
        TextView txtNgayCongTac;

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
            for (CongTac congTac : temp) {
                if (String.valueOf(congTac.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(congTac.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(congTac.getNgaynhap()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(congTac.getLydo()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(congTac.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(congTac.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(congTac);
                }
            }
        }
        notifyDataSetChanged();
    }
}