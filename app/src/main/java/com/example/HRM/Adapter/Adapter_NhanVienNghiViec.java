package com.example.HRM.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.Model.NhanVienNghiViec;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.libs.springyRecyclerView.SpringyAdapterAnimationType;
import com.example.HRM.libs.springyRecyclerView.SpringyAdapterAnimator;
import com.example.HRM.ui.nhanvien.HDLD_Activity;
import com.google.gson.Gson;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
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

public class Adapter_NhanVienNghiViec extends RecyclerView.Adapter<Adapter_NhanVienNghiViec.RecyclerViewHolder> implements IRequestHttpCallback {
    private Context mContext;
    private List<NhanVienNghiViec> data = new ArrayList<>();
    private List<NhanVienNghiViec> temp = new ArrayList<>();
    private Unbinder unbinder;
    NhanVienNghiViec nghiViec;
    Integer position_item = -1;
    PowerMenu powerMenu;
    IRequestHttpCallback iRequestHttpCallback;
    private SpringyAdapterAnimator mAnimator;
    View view;

    //    public Adapter_NhanVienNghiViec(Context mContext, List<NhanVienNghiViec> data) {
//        this.data = data;
//        temp.addAll(data);
//        this.mContext = mContext;
//        iRequestHttpCallback = this;
//    }
    public Adapter_NhanVienNghiViec(Context mContext, ArrayList<NhanVienNghiViec> titleList, RecyclerView recyclerView) {
        this.data = titleList;
        this.mContext = mContext;
        if (titleList.size() > 0) {
            temp.addAll(titleList);
        }
        mAnimator = new SpringyAdapterAnimator(recyclerView);
        mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_RIGHT);
        mAnimator.addConfig(100, 15);
        iRequestHttpCallback = this;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_nhanvien_nghiviec, parent, false);
        mAnimator.onSpringItemCreate(view);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaNV.setText(data.get(position).getManv());
        holder.txtHoTen.setText(data.get(position).getTennv());
        holder.txtNgaySinh.setText(data.get(position).getNgaysinh());
        holder.txtLyDo.setText(data.get(position).getLydo());
        holder.txtPhanXuong.setText(data.get(position).getTenpx());
        holder.txtChuyen.setText(data.get(position).getTenchuyen());
        holder.txtTo.setText(data.get(position).getTento());
        holder.txtNgayNghi.setText(data.get(position).getNgaynghi());
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

        String duyet = data.get(position).getDuyet();

        if (duyet.equals("NO")) {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#d1e0e0"));
        } else {
            holder.cardview_id.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.txtMenuOpTion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                position_item = position;
                powerMenu = new PowerMenu.Builder(mContext)
                        // .setHeaderView(R.layout.layout_dialog_header)
                        .addItem(new PowerMenuItem("Phê duyệt", R.drawable.ic_menu_pheduyet, "PHEDUYET"))
                        .addItem(new PowerMenuItem("Quá trình làm việc", R.drawable.ic_quatrinh_lamviec, "QUATRINH"))
                        .addItem(new PowerMenuItem("Hợp đồng lao động", R.drawable.ic_menu_hopdong2, "HOPDONG"))
                        .addItem(new PowerMenuItem("Chỉnh sửa", R.drawable.ic_chinhsua, "CHINHSUA"))
                        .addItem(new PowerMenuItem("Xem ảnh", R.drawable.ic_menu_xemanh, "XEMANH"))
                        .addItem(new PowerMenuItem("Xóa", R.drawable.ic_delete, "XOA"))
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
        //Hiệu ứng
        mAnimator.onSpringItemBind(holder.itemView, position);
    }

    public void PheDuyet(int position) {
        String manv = String.valueOf(nghiViec.getManv2());
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "duyet_nhanvien_nghiviec", iRequestHttpCallback, "PHEDUYET");
        request.params.put("manv", manv);
        request.params.put("nguoitd2", Modules1.tendangnhap);
        request.extraData.put("position", position);
        request.execute();
    }

    private void PheDuyetNghiViec(final NhanVienNghiViec nghiViec, final int position) {
        this.nghiViec = nghiViec;
        String title = "";
        if (nghiViec.getDuyet().equals("NO")) {
            title = "Bạn có muốn phê duyệt đơn xin nghỉ việc của nhân viên (" + nghiViec.getTennv() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Phê duyệt", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
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
            title = "Bạn có muốn thu hồi phê duyệt đơn xin nghỉ việc của nhân viên (" + nghiViec.getTennv() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Thu Hồi")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Thu hồi", R.drawable.ic_thuhoi, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
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

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            nghiViec = data.get(position_item);
            Modules1.strMaNV = nghiViec.getManv2();
            String TAG = item.getTag().toString();
            switch (TAG) {
                case "PHEDUYET":
                    PheDuyetNghiViec(nghiViec, position_item);
                    break;
                case "XOA":
                    if (nghiViec.getDuyet().equals("NO")) {
                        Delete_NhanVienNghiViec(nghiViec, position_item);
                    } else {
                        MDToast.makeText(mContext, "Nhân viên (" + nghiViec.getTennv() + ") đã được phê duyệt.");
                    }
                    break;
                case "HOPDONG":
                    Intent intentHDLD = new Intent(mContext, HDLD_Activity.class);
                    mContext.startActivity(intentHDLD);
                    break;
                case "XEMANH":
                    String url = nghiViec.getHinh();
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    break;
            }
            powerMenu.dismiss();
        }
    };

    private void Delete_NhanVienNghiViec(final NhanVienNghiViec nghiViec, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa nhân viên (" + nghiViec.getTennv() + ") khỏi danh sách nghỉ việc không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String manv = String.valueOf(nghiViec.getManv2());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien_nghiviec", iRequestHttpCallback, "DELETE_NHANVIEN_NGHIVIEC");
                        request.params.put("manv", manv);
                        request.extraData.put("position", position);
                        request.execute();
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

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "DELETE_NHANVIEN_NGHIVIEC":
                    try {
                        int position = Integer.parseInt(extraData.get("position").toString());
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công nhân viên (" + data.get(position).getTennv() + ") khỏi danh sách nghỉ việc.", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            data.remove(position);
                            notifyDataSetChanged();
                        } else {
                            MDToast.makeText(mContext, "Xóa không thành công.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
                case "PHEDUYET":
                    int position = Integer.parseInt(extraData.get("position").toString());
                    Gson g = new Gson();
                    NhanVienNghiViec nghiViec = g.fromJson(responseText, NhanVienNghiViec.class);
                    data.get(position).setDuyet(nghiViec.getDuyet());
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

        @BindView(R.id.txtNgaySinh)
        TextView txtNgaySinh;

        @BindView(R.id.txtNgayNghi)
        TextView txtNgayNghi;

        @BindView(R.id.txtPhanXuong)
        TextView txtPhanXuong;

        @BindView(R.id.txtChuyen)
        TextView txtChuyen;

        @BindView(R.id.txtTo)
        TextView txtTo;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

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
            for (NhanVienNghiViec nhanVienNghiViec : temp) {
                if (String.valueOf(nhanVienNghiViec.getTennv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getManv()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getTenpx()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getTenchuyen()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getTento()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(nhanVienNghiViec.getLydo()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(nhanVienNghiViec);
                }
            }
        }
        notifyDataSetChanged();
    }
}