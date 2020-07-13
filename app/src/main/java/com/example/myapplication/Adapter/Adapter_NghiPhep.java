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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Helper.ImageHelper;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.LoaiNghiPhep;
import com.example.myapplication.Model.NghiPhep;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Model.NhanVienTangCa;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.ui.NhatKyQuetThe.NhatKyQuetThe_MaNV_Activity;
import com.example.myapplication.ui.nghiphep.NghiPhep_MaNV_Activity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NghiPhep extends RecyclerView.Adapter<Adapter_NghiPhep.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<NghiPhep> data = new ArrayList<>();
    private List<NghiPhep> temp = new ArrayList<>();
    private Unbinder unbinder;

    IRequestHttpCallback iRequestHttpCallback;
    NghiPhep nghiPhep;
    Integer option = 1;

    public Adapter_NghiPhep(Context mContext, List<NghiPhep> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
        iRequestHttpCallback = this;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_nghiphep, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());

        holder.txtNgayDangKy.setText(data.get(position).getNgaynhap());
        holder.txtNgayNghi.setText(data.get(position).getNgaynghi());

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
                holder.cardview_id.setBackgroundColor(Color.parseColor("#d9ffb3"));
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
                popup.inflate(R.menu.option_menu_nghiphep);
                setForceShowIcon(popup);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        NghiPhep nghiPhep = (NghiPhep) data.get(position);
                        switch (menuItem.getItemId()) {
                            case R.id.menu_xacnhan:
                                if (nghiPhep.getStatus_nhansu().equals("YES") || nghiPhep.getStatus_nhansu().equals("NO")) {
                                    MDToast.makeText(mContext, "Đăng ký nghỉ phép của nhân viên (" + nghiPhep.getTennv() + ") đã được phê duyêt.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                                } else {
                                    XacNhan_NghiPhep(nghiPhep, position);
                                }
                                break;
                            case R.id.menu_pheduyet:
                                PheDuyet_NghiPhep(nghiPhep, position);
                                break;
                            case R.id.menu_ct_nghiphep:
                                Modules1.strMaNV = data.get(position).getManv2();
                                Intent intent_nghiphep = new Intent(mContext, NghiPhep_MaNV_Activity.class);
                                mContext.startActivity(intent_nghiphep);
                                break;
                            case R.id.menu_xemanh:
                                nghiPhep = data.get(position);
                                String url = nghiPhep.getHinh();
                                ArrayList<String> listImage = new ArrayList<>();
                                listImage.add(url);
                                ImageHelper.ViewImageFromList(listImage, mContext);
//                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
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
        String id = String.valueOf(nghiPhep.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_quanly_nghiphep", iRequestHttpCallback, "XACNHAN_NGHIPHEP");
        request.params.put("option", option);
        request.params.put("id", id);
        request.params.put("nguoitd", Modules1.tendangnhap);
        request.extraData.put("position", position);
        request.execute();
    }

    private void XacNhan_NghiPhep(final NghiPhep nghiPhep, final int position) {
        this.nghiPhep = nghiPhep;
        String title = "";
        if (nghiPhep.getStatus_quanly().equals("")) {
            title = "Bạn có muốn xác nhận đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";

        } else if (nghiPhep.getStatus_quanly().equals("YES")) {
            title = "Bạn có muốn thu hồi xác nhận đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";

        } else if (nghiPhep.getStatus_quanly().equals("NO")) {
            title = "Bạn có muốn xác nhận đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";

        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
        if (nghiPhep.getStatus_quanly().equals("")) {
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
        } else if (nghiPhep.getStatus_quanly().equals("YES")) {
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
        } else if (nghiPhep.getStatus_quanly().equals("NO")) {
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

    private void PheDuyet_NghiPhep(final NghiPhep nghiPhep, final int position) {
        this.nghiPhep = nghiPhep;
        String title = "";
        if (nghiPhep.getStatus_nhansu().equals("")) {
            title = "Bạn có muốn phê duyệt đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";
        } else if (nghiPhep.getStatus_nhansu().equals("YES")) {
            title = "Bạn có muốn thu hồi phê duyệt đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";
        } else if (nghiPhep.getStatus_nhansu().equals("NO")) {
            title = "Bạn có muốn phê duyệt đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
        if (nghiPhep.getStatus_nhansu().equals("")) {
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
        } else if (nghiPhep.getStatus_nhansu().equals("YES")) {
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
        } else if (nghiPhep.getStatus_nhansu().equals("NO")) {
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
        String id = String.valueOf(nghiPhep.getId());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_nhansu_nghiphep", iRequestHttpCallback, "PHEDUYET_NGHIPHEP");
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
                case "XACNHAN_NGHIPHEP":
                    Gson g = new Gson();
                    NghiPhep nghiPhep = g.fromJson(responseText, NghiPhep.class);
                    data.get(position).setStatus_quanly(nghiPhep.getStatus_quanly());
                    data.get(position).setNguoiduyet((nghiPhep.getNguoiduyet()));
                    notifyItemChanged(position);
                    break;
                case "PHEDUYET_NGHIPHEP":
                    Gson g_pheduyet = new Gson();
                    NghiPhep nghiPhep_pheduyet = g_pheduyet.fromJson(responseText, NghiPhep.class);
                    data.get(position).setStatus_nhansu(nghiPhep_pheduyet.getStatus_nhansu());
                    data.get(position).setNguoiduyet((nghiPhep_pheduyet.getNguoiduyet()));
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

        @BindView(R.id.txtNgayNghi)
        TextView txtNgayNghi;

        @BindView(R.id.txtSoNgay)
        TextView txtSoNgay;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;


        @BindView(R.id.txtGhiChu)
        TextView txtGhiChu;

        @BindView(R.id.txtNguoiDuyet)
        TextView txtNguoiDuyet;

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
            for (NghiPhep nghiPhep : temp) {
                if (String.valueOf(nghiPhep.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nghiPhep.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nghiPhep.getNgaynghi()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nghiPhep.getLydo()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nghiPhep.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nghiPhep.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(nghiPhep);
                }
            }
        }
        notifyDataSetChanged();
    }
}