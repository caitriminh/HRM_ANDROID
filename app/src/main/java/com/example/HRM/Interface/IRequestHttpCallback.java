package com.example.HRM.Interface;

import java.util.Map;


public interface IRequestHttpCallback {
    public void OnDoneRequest(boolean isSuccess, String TAG, int statusCode, String responseText, Map<String, Object> extraData);
}
