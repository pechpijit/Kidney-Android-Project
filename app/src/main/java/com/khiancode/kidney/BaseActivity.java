package com.khiancode.kidney;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    protected String KEY_FOOD_ID = "ID";
    private static Toast mToast;
    @VisibleForTesting
    private SweetAlertDialog pDialog;

    public void showProgressDialog(String message) {

        if (pDialog == null) {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#03a9f4"));
            pDialog.setContentText("");
            pDialog.setTitleText(message);
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }

        pDialog.show();
    }

    public void hideProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public void dialogResultErrorInternet() {
        dialogResultError("ไม่สามารถเชื่อมอินเทร์เน็ตได้\nกรุณาตรวจสอบอินเทอร์เน็ต\nและลองใหม่อีกครั้ง");
    }

    public void dialogResultError(String string) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ขออภัย")
                .setContentText(string+" กรุณาลองใหม่อีกครั้ง")
                .setConfirmText("ตกลง")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public static void ToastShow(Context context, String text) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
