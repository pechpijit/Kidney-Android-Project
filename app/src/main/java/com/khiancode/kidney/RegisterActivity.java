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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.txtAppname)
    TextView txtAppname;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.cardInput)
    CardView cardInput;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnBack)
    TextView btnBack;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.iconName)
    ImageView iconName;
    @BindView(R.id.iconPhone)
    ImageView iconPhone;

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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        invisibleView(
                txtAppname,
                imgLogo,
                inputName,
                inputPhone,
                cardInput,
                btnRegister,
                btnBack,
                cardBtn,
                iconName,
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

        btnRegister.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnRegister);

        inputName.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(inputName);

        inputPhone.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(inputPhone);

        iconName.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(iconName);

        iconPhone.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(iconPhone);

    }

    public boolean validate() {
        boolean valid = true;

        String name = inputName.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();

        if (name.isEmpty()) {
            inputName.setError("กรุณากรอกชื่อ");
            valid = false;
        } else {
            inputName.setError(null);
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            inputPhone.setError("กรุณากรอกเบอร์โทรศัพท์ 10 หลัก");
            valid = false;
        } else {
            inputPhone.setError(null);
        }

        return valid;
    }

    public void onClickConfirmSignUp() {
        Log.d(TAG, "Register");
        btnRegister.setEnabled(false);

        if (!validate()) {
            btnRegister.setEnabled(true);
            return;
        }

        showProgressDialog(REGIS);

        final String name = inputName.getText().toString().trim();
        final String phone = inputPhone.getText().toString().trim();

        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .add("phone",phone)
                .build();

        ApiClient.POST post = new ApiClient.POST(this);
        post.setURL(BASE_URL+"member/register");
        post.setRequestBody(requestBody);
        post.execute();
        post.setListenerCallService(new CallServiceListener() {
            @Override
            public void ResultData(String data) {
                if (data.equals("already")) {
                    hideProgressDialog();
                    btnRegister.setEnabled(true);
                    dialogTM("Alert","เบอร์โทรศัพท์ถูกใช้งานแล้ว กรุณาลองใหม่อีกครั้ง");
                } else if (data.equals("201")) {
                    callbackLogin(phone);
                } else {
                    dialogTM("Alert",data);
                }
            }

            @Override
            public void ResultError(String data) {
                hideProgressDialog();
                btnRegister.setEnabled(true);
                dialogResultError(data);
            }

            @Override
            public void ResultNull(String data) {
                hideProgressDialog();
                btnRegister.setEnabled(true);
                dialogResultNull();
            }
        });

    }

    protected void callbackLogin(final String phone) {
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
                parserJson(data,phone);
            }

            @Override
            public void ResultError(String data) {
                btnRegister.setEnabled(true);
                dialogResultError(data);
            }

            @Override
            public void ResultNull(String data) {
                hideProgressDialog();
                btnRegister.setEnabled(true);
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
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } else {
            hideProgressDialog();
            dialogTM("ขออภัย","สมัครสมาชิกสำเร็จแล้ว แต่ไม่สามารถเข้าสู๋ระบบได้ กรุณาติดต่อเจ้าหน้าที่");
        }
    }

    @OnClick({R.id.btnRegister, R.id.btnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                onClickConfirmSignUp();
                break;
            case R.id.btnBack:
               onBackPressed();
                break;
        }
    }
}
