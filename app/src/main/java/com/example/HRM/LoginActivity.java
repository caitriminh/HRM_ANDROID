package com.example.HRM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HRM.Interface.IRequestHttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements IRequestHttpCallback {
    Button btnExit, btnLogin;
    TextView txtUserName, txtPassWord;
    IRequestHttpCallback iRequestHttpCallback;
    public static Context app_context;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        iRequestHttpCallback = this;
        app_context = getApplicationContext();
        // save sesion
        pref = getSharedPreferences("SESSION", MODE_PRIVATE);
        boolean isLogin = pref.getBoolean("isLogin", false);
        if(isLogin){
            Modules1.strUserName = pref.getString("username", "");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnExit = this.findViewById(R.id.btnExit);
        btnLogin = this.findViewById(R.id.btnLogin);
        txtPassWord = this.findViewById(R.id.txtPassword);
        txtUserName = this.findViewById(R.id.txtUsername);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUser = txtUserName.getText().toString();
                String strPass = txtPassWord.getText().toString();
                if (strPass.length() == 0 || strUser.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập vào tên đăng nhập và mật khẩu.", Toast.LENGTH_LONG).show();
                } else {
                    //Kiểm tra đăng nhập
                    String url = Modules1.BASE_URL + "checklogin";
                    String TAG = "CHECK_LOGIN";
                    AsyncPostHttpRequest request = new AsyncPostHttpRequest(url, iRequestHttpCallback, TAG);
                    //Gửi user va Pass len server
                    request.params.put("username", strUser);
                    request.params.put("password", strPass);
                    //request.extraData.put("aaa", "aaaaaa");
                    request.execute();
                }
            }
        });
    }


    @Override
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData) {
        if (isSuccess) {
            JSONObject jsonObject = null;
            switch (TAG) {
                case "CHECK_LOGIN":
                    try {

                        jsonObject = new JSONObject(responseText);
                        String status = jsonObject.getString("status");
                        if (status.equals("OK")) {
                            String username = jsonObject.getString("username");
                            pref.edit().putBoolean("isLogin", true).commit();
                            pref.edit().putString("username", username).commit();

                            Modules1.strUserName = username;
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            //Toast.makeText(getApplicationContext(), username,Toast.LENGTH_LONG).show();
                        } else {
                            String desc = jsonObject.getString("description");
                            Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case "":
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Mat ket noi may chu.", Toast.LENGTH_LONG).show();
        }
    }
}