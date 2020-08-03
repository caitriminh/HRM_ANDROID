package com.example.HRM;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.HRM.Interface.IRequestHttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class AsyncPostHttpRequest
        implements Response.Listener<String>,Response.ErrorListener {
    protected final IRequestHttpCallback requestCallback;
    protected final int TIME_OUT = 10000;
    protected final String url;
    protected final String TAG;
    public final Map<String,Object> params = new HashMap<String, Object>();
    protected final Map <String,String> stringParams = new HashMap<>();
    //protected String responseText = "";
    protected int statusCode = 1;
    public final Map<String,Object> extraData = new HashMap<String, Object>();
    protected long startTime ;
    protected Request.Priority mPriority = null;
    protected final StringRequest stRequest;

    public AsyncPostHttpRequest (String url, IRequestHttpCallback requestCallback,
                                 final String TAG){
        this.requestCallback = requestCallback;
        this.url = url;
        this.TAG = TAG;
        this.stRequest = new StringRequest(Request.Method.POST,url,
                this,this){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                Log.w("found response",
                        TAG + " " +
                            (System.currentTimeMillis() - startTime) + " milliseconds");
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return stringParams;
            }

            @Override
            protected String getParamsEncoding() {
                return "UTF-8";
            }

            @Override
            public Priority getPriority() {
//                Priority chek = mPriority;
//                if (mPriority == null){
//                    chek = super.getPriority();
//                    Log.w("stan Priority",chek + " " + TAG);
//                    return super.getPriority();
//                }else {
//                    Log.w("Priority",mPriority + "");
//                    return mPriority;
//                }
                return super.getPriority();
            }

            @Override
            public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                return super.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            }
        };
    }

    public void execute (){
        startTime = System.currentTimeMillis();
        Log.w("volley start",TAG + " connecting to " + url);
        stringParams.clear();
        if (params.size() > 0){
            JSONObject joinparam = new JSONObject();
            try {
                for (String key : params.keySet()){
                    String kvalue = params.get(key) + "";
                    joinparam.put(key,kvalue);
                    stringParams.put(key,kvalue);
                    Log.w("params", key + "=" + kvalue);
                }
                if (!params.containsKey("jdata")){
                    String jdata_str = joinparam.toString();
                    stringParams.put("jdata",jdata_str);
                    Log.w("params", "jdata=" + jdata_str);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("join param err", e.getMessage() + " ");
                stringParams.clear();
            }

        }
        if (Modules1.main_volley_queue == null){
            Context context = LoginActivity.app_context;
            Modules1.main_volley_queue = Volley.newRequestQueue(context);
        }
        Modules1.main_volley_queue.add(stRequest);
    }

//    public void executeOnExecutor (Executor executor){
//        if (executor == AsyncTask.THREAD_POOL_EXECUTOR){
//            mPriority = Request.Priority.HIGH;
//        }
//        execute();
//    }

    @Override
    public void onResponse(String response) {
        Log.w("success response",TAG + ": " + response);
        Log.w("success time",
                TAG + " " +
                    (System.currentTimeMillis() - startTime) + " milliseconds" +
                        " - status code: " + statusCode);
        requestCallback.OnDoneRequest(true,TAG,statusCode,
                response,extraData);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errMsg = "error response";
        if (error instanceof NetworkError || error instanceof NoConnectionError){
            statusCode = 404;
            errMsg = "Không thể kết nối đến máy chủ";
        }else if (error instanceof TimeoutError) {
            statusCode = 404;
            errMsg = "Lỗi máy chủ phản hồi quá lâu";
        }else if (error.networkResponse == null){
            statusCode = 404;
            errMsg = "Máy chủ không trả lời";
        }else {
            NetworkResponse netResponse = error.networkResponse;
            statusCode = netResponse.statusCode;
            String charset = HttpHeaderParser.parseCharset(netResponse.headers);
            try {
                errMsg = new String(netResponse.data,charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Log.e("error response",TAG + " " + errMsg);
        Log.e("error time",
                TAG + " " +
                    (System.currentTimeMillis() - startTime) + " milliseconds" +
                        " - status code: " + statusCode);
        requestCallback.OnDoneRequest(false,TAG,statusCode,
                errMsg,extraData);
    }




}

