package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.khiancode.kidney.helper.PrefUtils;
import com.khiancode.kidney.model.OauthTokenModel;
import com.khiancode.kidney.okhttp.ApiClient;
import com.khiancode.kidney.okhttp.CallServiceListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.txtAppname)
    TextView txtAppname;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.iconPhone)
    ImageView iconPhone;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.cardInput)
    CardView cardInput;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnBack)
    TextView btnBack;
    @BindView(R.id.cardBtn)
    CardView cardBtn;

    String TAG = "RegisterActivity";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        invisibleView(
                txtAppname,
                imgLogo,
                inputPhone,
                cardInput,
                btnLogin,
                btnBack,
                cardBtn,
                iconPhone
        );

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        aimationOne();
                    }
                }, 400);

    }

    private void aimationOne() {
        imgLogo.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(imgLogo);

        txtAppname.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInUp)
                .duration(700)
                .playOn(txtAppname);

        cardBtn.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(cardBtn);

        cardInput.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(cardInput);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        animationTwo();
                    }
                }, 400);

    }

    private void animationTwo() {
        btnBack.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(btnBack);

        btnLogin.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnLogin);

        inputPhone.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(inputPhone);

        iconPhone.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(iconPhone);

    }

    public boolean validate() {
        boolean valid = true;

        String phone = inputPhone.getText().toString().trim();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            inputPhone.setError("กรุณากรอกเบอร์โทรศัพท์ 10 หลัก");
            valid = false;
        } else {
            inputPhone.setError(null);
        }

        return valid;
    }

    public void onClickConfirmLogin() {
        Log.d(TAG, "Login");
        btnLogin.setEnabled(false);

        if (!validate()) {
            btnLogin.setEnabled(true);
            return;
        }

        showProgressDialog(LOGIN);

        final String phone = inputPhone.getText().toString().trim();

        RequestBody requestBody = new FormBody.Builder()
                .add("username", "user"+phone+"@hugtai.com")
                .add("password", phone)
                .add("grant_type", "password")
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .add("scope", "*")
                .build();

        ApiClient.POST post = new ApiClient.POST(this);
        post.setURL(BASE_URL_OAUTH);
        post.setRequestBody(requestBody);
        post.execute();
        post.setListenerCallService(new CallServiceListener() {
            @Override
            public void ResultData(String data) {
                Log.d(TAG, data);
                if (!data.equals("invalid")) {
                    parserJson(data,phone);
                } else {
                    hideProgressDialog();
                    btnLogin.setEnabled(true);
                    dialogTM("ไม่สามารถเข้าสู่ระบบได้","เนื่องจากหมายเลขโทรศัพท์ไม่ถูกต้อง");

                }
            }

            @Override
            public void ResultError(String data) {
                btnLogin.setEnabled(true);
                dialogResultError(data);
            }

            @Override
            public void ResultNull(String data) {
                hideProgressDialog();
                btnLogin.setEnabled(true);
                dialogResultNull();
            }
        });

    }

    private void parserJson(String data, String phone) {
        Gson gson = new Gson();
        OauthTokenModel tokenModel = gson.fromJson(data, OauthTokenModel.class);
        if (tokenModel.getTokenType().equals("Bearer")) {
            PrefUtils utils = new PrefUtils(this);
            utils.setLoginStatus(true);
            utils.setPhone(phone);
            utils.setAccessToken(tokenModel.getAccessToken());
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } else {
            hideProgressDialog();
            dialogTM("ขออภัย",tokenModel.getError());
        }
    }

    @OnClick({R.id.btnLogin, R.id.btnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                onClickConfirmLogin();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}
