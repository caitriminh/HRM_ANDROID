package com.example.myapplication.NhanVien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AsyncPostHttpRequest;
import com.example.myapplication.Interface.IRequestHttpCallback;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Modules1;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Them_Nhanvien extends Fragment implements IRequestHttpCallback {

//    TextView txtTitle;
//    EditText txtPassWord,txtUserName;
//    Button btnSave, btnLogout;
private Unbinder unbinder;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.txtPassword)
    EditText txtPassWord;

    @BindView(R.id.txtUsername)
    EditText txtUserName;


    IRequestHttpCallback iRequestHttpCallback;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_them_nhanvien, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        //initialize your UI
        iRequestHttpCallback = this;
        context = getActivity();

           txtTitle.setText("WELCOME " + Modules1.strUserName.toUpperCase());


        return rootView;

    }

    @OnClick(R.id.btnLogout)
    void logout() {
        SharedPreferences pref = context.getSharedPreferences("SESSION", MODE_PRIVATE);
        pref.edit().clear().commit();
        Intent intent = new Intent(context , LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.btnSave)
    void Save(){
        String strUser = txtUserName.getText().toString();
        String strPass = txtPassWord.getText().toString();
        if (strPass.length() == 0 || strUser.length() == 0) {
            Toast.makeText(context, "Vui lòng nhập vào tên đăng nhập và mật khẩu.", Toast.LENGTH_LONG).show();
        } else {
            //Kiểm tra đăng nhập
            String url = Modules1.BASE_URL + "insert_user";
            String TAG = "INSERT_USER";
            AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
            //Gửi user va Pass len server
            request.params.put("tendangnhap", strUser);
            request.params.put("matkhau", strPass);
            request.params.put("nguoitd", Modules1.strUserName);
            request.extraData.put("tendangnhap", strUser);
            request.execute();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "INSERT_USER":
                    try {

                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if(status.equals("OK")){
                            String strUser=extraData.get("tendangnhap").toString();
                            Toast.makeText(context, strUser + " đã thêm thành công.",Toast.LENGTH_LONG).show();
                        }else{
                             Toast.makeText(context, "Lưu không thành công.",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case "LOAD_DATA":
                    break;
            }
        }else{
            Toast.makeText(context,"Mat ket noi may chu.",Toast.LENGTH_LONG).show();
        }
    };

}