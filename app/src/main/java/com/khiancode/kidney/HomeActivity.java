package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.txtAppname)
    TextView txtAppname;
    @BindView(R.id.subTxtAppname)
    TextView subTxtAppname;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.cardBtn)
    CardView cardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
    }

    private void startAnimation() {
        imgLogo.setVisibility(View.INVISIBLE);
        txtAppname.setVisibility(View.INVISIBLE);
        subTxtAppname.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);
        btnRegister.setVisibility(View.INVISIBLE);
        cardBtn.setVisibility(View.INVISIBLE);

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

        subTxtAppname.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInUp)
                .duration(700)
                .playOn(subTxtAppname);

        cardBtn.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(cardBtn);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        animationTwo();
                    }
                }, 400);

    }

    private void animationTwo() {

        btnLogin.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(btnLogin);

        btnRegister.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnRegister);

    }

    @OnClick({R.id.btnLogin, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnRegister:
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }
}
