package com.example.HRM.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HRM.AsyncPostHttpRequest;
import com.example.HRM.Helper.ImageHelper;
import com.example.HRM.Interface.IRequestHttpCallback;
import com.example.HRM.Model.LoaiNghiPhep;
import com.example.HRM.Model.NghiPhep;
import com.example.HRM.Modules1;
import com.example.HRM.R;
import com.example.HRM.ui.VeSom.VeSom_Activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    ArrayList<LoaiNghiPhep> lstLoaiNghiPhep;
    IRequestHttpCallback iRequestHttpCallback;
    NghiPhep nghiPhep;
    Integer option = 1, position_item = -1;
    PowerMenu powerMenu;
    String StrMaloainghiphep = "", StrTuNgay = "", StrDenNgay = "", StrMaNV = "";
    TextInputEditText txtMaNV, txtSoNgay, txtGhiChu, txtHoTen, txtPhanXuong, txtTuNgay, txtDenNgay, txtLoaiNghiPhep;
    Button btnLuu, btnDong;
    View view;

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
        view = inflater.inflate(R.layout.cardview_item_nghiphep, parent, false);
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
        holder.txtSoNgay.setText(data.get(position).getSongay());

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
                position_item = position;
//                NghiPhep nghiPhep = data.get(position);
                powerMenu = new PowerMenu.Builder(mContext)
                        // .setHeaderView(R.layout.layout_dialog_header)
                        .addItem(new PowerMenuItem("Xác nhận", R.drawable.ic_baseline_security_24, "XACNHAN")) // add an item.
                        .addItem(new PowerMenuItem("Phê duyệt", R.drawable.ic_menu_pheduyet, "PHEDUYET")) // aad an item list.
                        .addItem(new PowerMenuItem("Chỉnh sửa", R.drawable.ic_chinhsua, "CHINHSUA")) // aad an item list.
                        .addItem(new PowerMenuItem("Nhật ký nghỉ phép", R.drawable.ic_ct_nghiphep, "NHATKY")) // aad an item list.
                        .addItem(new PowerMenuItem("Xem ảnh", R.drawable.ic_menu_xemanh, "XEMANH")) // aad an item list.
                        .addItem(new PowerMenuItem("Xóa", R.drawable.ic_delete, "XOA")) // aad an item list.
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
            nghiPhep = data.get(position_item);
            String TAG = item.getTag().toString();
            switch (TAG) {
                case "XACNHAN":
                    if (nghiPhep.getStatus_nhansu().equals("YES") || nghiPhep.getStatus_nhansu().equals("NO")) {
                        MDToast.makeText(mContext, "Phiếu đăng ký nghỉ phép của nhân viên (" + nghiPhep.getTennv() + ") đã được phê duyêt.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    } else {
                        XacNhan_NghiPhep(nghiPhep, position_item);
                    }
                    break;
                case "CHINHSUA":
                    if (nghiPhep.getStatus_nhansu().equals("YES") || nghiPhep.getStatus_nhansu().equals("NO") || nghiPhep.getStatus_quanly().equals("YES") || nghiPhep.getStatus_quanly().equals("NO")) {
                        MDToast.makeText(mContext, "Phiếu đăng ký nghỉ phép của nhân viên (" + nghiPhep.getTennv() + ") đã được phê duyêt.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    } else {
                        LoadThongTinNhanVien();
                        LoadLoaiNghiPhep();
                        SuaNhanVienNghiPhep();
                    }
                    break;
                case "PHEDUYET":
                    PheDuyet_NghiPhep(nghiPhep, position_item);
                    break;
                case "NHATKY":
                    Modules1.strMaNV = data.get(position_item).getManv2();
                    Intent intent_vesom = new Intent(mContext, VeSom_Activity.class);
                    mContext.startActivity(intent_vesom);
                    break;
                case "XEMANH":
                    nghiPhep = data.get(position_item);
                    String url = nghiPhep.getHinh();
                    ArrayList<String> listImage = new ArrayList<>();
                    listImage.add(url);
                    ImageHelper.ViewImageFromList(listImage, mContext);
                    break;
                case "XOA":
                    if (nghiPhep.getStatus_nhansu().equals("") && nghiPhep.getStatus_quanly().equals("")) {
                        Delete_NhanVienNghiPhep(nghiPhep, position_item);
                    } else {
                        MDToast.makeText(mContext, "Đăng ký nghỉ phép của nhân viên (" + nghiPhep.getTennv() + ") đã được phê duyệt hoặc đã xác nhận.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        break;
                    }
            }
            powerMenu.dismiss();
        }
    };

    public void SuaNhanVienNghiPhep() {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_bottomsheet_add_nghiphep, null);
        txtMaNV = view_bottom_sheet.findViewById(R.id.txtMaNV);

        txtSoNgay = view_bottom_sheet.findViewById(R.id.txtSoNgay);
        txtGhiChu = view_bottom_sheet.findViewById(R.id.txtGhiChu);
        txtHoTen = view_bottom_sheet.findViewById(R.id.txtHoTen);
        txtPhanXuong = view_bottom_sheet.findViewById(R.id.txtPhanXuong);
        txtTuNgay = view_bottom_sheet.findViewById(R.id.txtTuNgay);
        txtDenNgay = view_bottom_sheet.findViewById(R.id.txtDenNgay);
        txtLoaiNghiPhep = view_bottom_sheet.findViewById(R.id.txtLoaiNghiPhep);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        ShowLoaiNghiPhep(txtLoaiNghiPhep);

        //Thêm ngày nhập
        txtTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtTuNgay.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                StrTuNgay = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        //Thêm ngày nhập
        txtDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtDenNgay.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                StrDenNgay = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtMaNV.getText().equals("")) {
                    MDToast.makeText(mContext, "Vui lòng nhập vào mã nhân viên.", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (StrMaloainghiphep.equals("")) {
                    MDToast.makeText(mContext, "Bạn vui lòng chọn loại nghỉ phép", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (StrTuNgay.equals("") || StrDenNgay.equals("")) {
                    MDToast.makeText(mContext, "Bạn vui lòng nhập vào ngày nghỉ phép", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                if (txtSoNgay.getText().toString().equals("")) {
                    MDToast.makeText(mContext, "Bạn vui lòng nhập vào số ngày nghỉ phép", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                    return;
                }

                String url = Modules1.BASE_URL + "update_nhanvien_nghiphep_vesom_congtac";
                String TAG = "UPDATE_NHANVIEN_NGHIPHEP";
                AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                //Gửi user va Pass len server
                request.params.put("option", 2);
                request.params.put("id", nghiPhep.getId());
                request.params.put("maloainghiphep", StrMaloainghiphep);
                request.params.put("tungay", StrTuNgay);
                request.params.put("denngay", StrDenNgay);
                request.params.put("songay", Double.parseDouble(txtSoNgay.getText().toString()));
                request.params.put("ghichu", txtGhiChu.getText().toString());
                request.params.put("nguoitd2", Modules1.tendangnhap);
                request.extraData.put("position", position_item);
                request.execute();

                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });

    }

    public void ShowLoaiNghiPhep(TextView txtLoaiNghiPhep) {
        txtLoaiNghiPhep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Chọn loại nghỉ phép");
                builder.setCancelable(false);
                String[] arrayLoaiNghiPhep = new String[lstLoaiNghiPhep.size()];
                int i = 0;
                for (LoaiNghiPhep loaiNghiPhep : lstLoaiNghiPhep) {
                    arrayLoaiNghiPhep[i] = loaiNghiPhep.getLoainghiphep();
                    i++;
                }
                ;
                builder.setSingleChoiceItems(arrayLoaiNghiPhep, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiNghiPhep loaiNghiPhep = lstLoaiNghiPhep.get(i);
                        txtLoaiNghiPhep.setText(loaiNghiPhep.getLoainghiphep());
                        StrMaloainghiphep = loaiNghiPhep.getMaloainghiphep();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void LoadThongTinNhanVien() {
        String url = Modules1.BASE_URL + "getthongtin_nghiphep_vesom_congtac";
        String TAG = "LOAD_THONGTIN";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("id", nghiPhep.getId());
        request.execute();
    }

    public void LoadLoaiNghiPhep() {
        String url = Modules1.BASE_URL + "load_loainghiphep";
        String TAG = "LOAD_LOAINGHIPHEP";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.params.put("option", 3);
        request.execute();
    }

    private void Delete_NhanVienNghiPhep(final NghiPhep nghiPhep, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa phiếu đăng ký nghỉ phép của nhân viên (" + nghiPhep.getTennv() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        String id = String.valueOf(nghiPhep.getId());
                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien_nghiphep", iRequestHttpCallback, "DELETE_NHANVIEN_NGHIPHEP");
                        request.params.put("id", id);
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
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Xác nhận", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 1;
                            XacNhan(position);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Không xác nhận", R.drawable.ic_khong_duyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 2;
                            XacNhan(position);
                            dialogInterface.dismiss();
                        }
                    })
                    .build();
            mBottomSheetDialog.show();

        } else if (nghiPhep.getStatus_quanly().equals("YES")) {
            title = "Bạn có muốn thu hồi xác nhận đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Thu Hồi")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Thu hồi", R.drawable.ic_thuhoi, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 3;
                            XacNhan(position);
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

        } else if (nghiPhep.getStatus_quanly().equals("NO")) {
            title = "Bạn có muốn xác nhận đơn xin nghi phép nhân viên (" + nghiPhep.getTennv() + ") này không?";
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Xác Nhận")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Xác nhận", R.drawable.ic_menu_pheduyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 1;
                            XacNhan(position);
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

        if (nghiPhep.getStatus_nhansu().equals("")) {
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Phê Duyệt")
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
                    .setNegativeButton("Không phê duyệt", R.drawable.ic_khong_duyet, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 2;
                            PheDuyet(position);
                            dialogInterface.dismiss();
                        }
                    })
                    .build();
            mBottomSheetDialog.show();
        } else if (nghiPhep.getStatus_nhansu().equals("YES")) {
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Thu Hồi")
                    .setMessage(title)
                    .setCancelable(false)
                    .setPositiveButton("Thu hồi", R.drawable.ic_thuhoi, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            option = 3;
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
        } else if (nghiPhep.getStatus_nhansu().equals("NO")) {
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                    .setTitle("Phê Duyệt")
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

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "UPDATE_NHANVIEN_NGHIPHEP":
                    int position4 = Integer.parseInt(extraData.get("position").toString());
                    Gson gUpdate = new Gson();
                    NghiPhep nghiPhep4 = gUpdate.fromJson(responseText, NghiPhep.class);
                    data.get(position4).setNgaynghi(nghiPhep4.getNgaynghi());
                    data.get(position4).setLydo(nghiPhep4.getLydo());
                    data.get(position4).setSongay(nghiPhep4.getSongay());
                    data.get(position4).setGhichu(nghiPhep4.getGhichu());
                    notifyItemChanged(position4);
                    MDToast.makeText(mContext, "Phiếu đăng ký nghỉ phép của nhân viên (" + data.get(position4).getTennv() + ") đã được cập nhật", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                    break;
                case "XACNHAN_NGHIPHEP":
                    int position = Integer.parseInt(extraData.get("position").toString());
                    Gson g = new Gson();
                    NghiPhep nghiPhep = g.fromJson(responseText, NghiPhep.class);
                    data.get(position).setStatus_quanly(nghiPhep.getStatus_quanly());
                    data.get(position).setNguoiduyet((nghiPhep.getNguoiduyet()));
                    notifyItemChanged(position);
                    break;
                case "PHEDUYET_NGHIPHEP":
                    int position2 = Integer.parseInt(extraData.get("position").toString());
                    Gson g_pheduyet = new Gson();
                    NghiPhep nghiPhep_pheduyet = g_pheduyet.fromJson(responseText, NghiPhep.class);
                    data.get(position2).setStatus_nhansu(nghiPhep_pheduyet.getStatus_nhansu());
                    data.get(position2).setNguoiduyet((nghiPhep_pheduyet.getNguoiduyet()));
                    notifyItemChanged(position2);
                    break;
                case "DELETE_NHANVIEN_NGHIPHEP":
                    int position3 = Integer.parseInt(extraData.get("position").toString());
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            MDToast.makeText(mContext, "Đã xóa thành công đăng ký nghỉ phép của nhân viên (" + data.get(position3).getTennv() + ")", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            data.remove(position3);
                            notifyDataSetChanged();
                        } else {
                            MDToast.makeText(mContext, "Phiếu đăng ký nghỉ phép của nhân viên (" + data.get(position3).getTennv() + ") đã được duyệt", Toast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        }
                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                    break;
                case "LOAD_THONGTIN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        StrMaNV = jsonObject.getString("manv");
                        txtMaNV.setText(jsonObject.getString("manv"));
                        txtHoTen.setText(jsonObject.getString("tennv"));
                        txtPhanXuong.setText(jsonObject.getString("tenpx"));
                        txtLoaiNghiPhep.setText(jsonObject.getString("loainghiphep"));
                        StrMaloainghiphep = jsonObject.getString("maloainghiphep");
                        txtSoNgay.setText(jsonObject.getString("songay"));
                        txtTuNgay.setText(jsonObject.getString("tungay"));
                        StrTuNgay = jsonObject.getString("tungay2");
                        txtDenNgay.setText(jsonObject.getString("denngay"));
                        StrDenNgay = jsonObject.getString("denngay2");
                        txtGhiChu.setText(jsonObject.getString("ghichu"));

                    } catch (JSONException e) {
                        MDToast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
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