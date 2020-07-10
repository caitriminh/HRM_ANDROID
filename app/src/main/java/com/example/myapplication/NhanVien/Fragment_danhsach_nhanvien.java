package com.example.myapplication.NhanVien;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Adapter_NhanVien;
import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.ClickListener;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.Model.NhanVien;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerTouchListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment_danhsach_nhanvien extends Fragment implements IRequestHttpCallback {
    //RecyclerView recyclerView;
    ArrayList<NhanVien> lst_nhanvien;
    IRequestHttpCallback iRequestHttpCallback;
    Adapter_NhanVien adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;


    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_danhsach_nhanvien, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        //recyclerView = rootView.findViewById(R.id.recycleView);
        iRequestHttpCallback = this;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadData();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

//                NhanVien nhanVien =  (NhanVien) lst_nhanvien.get(position);
//                Delete_NhanVien(nhanVien, position);
            }
        }));
    }


    @OnClick(R.id.btnAdd)
    void Add_NhanVien(){
        openDialog();
    }

    public void openDialog() {
        EditText txtUser;
        EditText txtPassword;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_diaglog_add_nhanvien, null);
        txtUser = view.findViewById(R.id.txtUser);
        txtPassword = view.findViewById(R.id.txtPassword);
        builder.setView(view)
                .setTitle("Thêm nhân viên")
                .setNegativeButton("đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = txtUser.getText().toString();
                        String password = txtPassword.getText().toString();
                        if (password.length() == 0 || username.length() == 0) {
                            Toast.makeText(getActivity(), "Vui lòng nhập vào tên đăng nhập và mật khẩu.", Toast.LENGTH_LONG).show();
                        } else {
                            //Kiểm tra đăng nhập
                            String url = Modules1.BASE_URL + "insert_user";
                            String TAG = "INSERT_USER";
                            AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                            //Gửi user va Pass len server
                            request.params.put("tendangnhap", username);
                            request.params.put("matkhau", password);
                            request.params.put("nguoitd", Modules1.strUserName);
                            request.extraData.put("tendangnhap", username);
                            request.execute();
                        }

                    }
                });

        builder.create();
        builder.show();
    }


//    private void Delete_NhanVien(final NhanVien nhanVien, final int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
//                .setTitle("Thông Báo!")
//                .setIcon(R.drawable.message_icon)
//                .setMessage("Bạn có muốn xóa nhan vien " + nhanVien.getTendangnhap() + " này không?")
//
//                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String tendangnhap = String.valueOf(nhanVien.getTendangnhap());
//                        AsyncPostHttpRequest request = new AsyncPostHttpRequest(Modules1.BASE_URL + "delete_nhanvien", iRequestHttpCallback, "DELETE_NHANVIEN");
//                        request.params.put("tendangnhap", tendangnhap);
//                        request.extraData.put("position", position);
//                        request.execute();
//
//                    }
//                })
//                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//
//                .setCancelable(false);
//        builder.create().show();
//    }


    public void  LoadData(){
        String url = Modules1.BASE_URL + "load_data_user";
        String TAG = "LOAD_DATA";
        AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
        request.execute();
    }


    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
                    JSONObject jsonObject = null;
                    switch (TAG) {
                        case "LOAD_DATA":
                            Gson gson = new Gson();
                            TypeToken<List<NhanVien>> token = new TypeToken<List<NhanVien>>() {};
                            List<NhanVien> nhanviens = gson.fromJson(responseText, token.getType());
                            lst_nhanvien = new ArrayList<NhanVien>();
                            lst_nhanvien.addAll(nhanviens);
                            adapter = new Adapter_NhanVien(getActivity(), lst_nhanvien);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            recycleView.setLayoutManager(layoutManager);
                            recycleView.setAdapter(adapter);
                            break;

                case "DELETE_NHANVIEN":
                    try {
                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if(status.equals("OK")){
                            int position = Integer.parseInt( extraData.get("position").toString());
                            lst_nhanvien.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Đã xóa thành công.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;

                case "INSERT_USER":
                    try {

                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if(status.equals("OK")){
                            String strUser=extraData.get("tendangnhap").toString();
                            Toast.makeText(getActivity(), strUser + " đã thêm thành công.",Toast.LENGTH_LONG).show();

//                            lst_nhanvien.add(0, new NhanVien(strUser, "", "https://laptrinhvb.net/uploads/users/24effd321a2379bffb23a4ace4d0e83d.png"));
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getActivity(), "Lưu không thành công.",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }else{
            Toast.makeText(getActivity(),"Kết nối với máy chủ thất bại.",Toast.LENGTH_LONG).show();
        }
    }



}
