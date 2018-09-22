package com.khiancode.kidney.okhttp;

import org.json.JSONException;

public interface CallServiceListener {
    void ResultData(String data);
    void ResultError(String data);
    void ResultNull(String data);
}