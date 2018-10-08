package com.khiancode.kidney;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public static String CLIENT_ID = "3";
    public static String CLIENT_SECRET = "86ZAZ5hAXyyPJMlXT4Dl1inZjFAn2uXzRyiwXUia";
    public static String BASE_URL_OAUTH = "http://3onedata.co.th/hugtai/oauth/token";
    public static String BASE_URL= "http://3onedata.co.th/hugtai/api/";
    protected String REGIS = "กำลังสมัครสมาชิก...";
    protected String LOGIN = "กำลังเข้าสู่ระบบ...";
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

    public void invisibleView(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    public void visibleView(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    public String getAppversion(Activity context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public void dialogTM(String title, String message) {
        new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ตกลง", null)
                .setCancelable(false)
                .show();
    }

    public void dialogTM(String title, String message, String btn1, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(btn1, listener)
                .setCancelable(false)
                .show();
    }

    public void dialogResultError() {
        new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                .setTitle("Alert")
                .setMessage("ไม่สามารถเข้าใช้งานได้ กรุณาลองใหม่อีกครั้ง")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public void dialogResultError2() {
        new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                .setTitle("Alert")
                .setMessage("ไม่สามารถเข้าใช้งานได้ กรุณาลองใหม่อีกครั้ง")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(false)
                .show();
    }

    public void dialogResultError(String string) {
        new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                .setTitle("Alert")
                .setMessage("ไม่สามารถเข้าใช้งานได้ กรุณาลองใหม่ภายหลัง error code = " + string)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }



    public void dialogResultNull() {
        new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                .setTitle("Alert")
                .setMessage("ไม่พบข้อมูล")
                .setNegativeButton("OK", null)
                .setCancelable(false)
                .show();
    }

    public void dialogResultNull(String message) {
        new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                .setTitle("Alert")
                .setMessage(message)
                .setNegativeButton("OK", null)
                .setCancelable(false)
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
