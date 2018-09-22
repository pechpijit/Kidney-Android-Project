package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.khiancode.kidney.helper.PrefUtils;
import com.khiancode.kidney.model.FoodModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txtAppname)
    TextView txtAppname;
    @BindView(R.id.btnLearning)
    Button btnLearning;
    @BindView(R.id.btnBMI)
    Button btnBMI;
    @BindView(R.id.btnSo)
    Button btnSo;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.btnMenu)
    ImageView btnMenu;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        imgLogo.setVisibility(View.INVISIBLE);
        txtAppname.setVisibility(View.INVISIBLE);
        btnLearning.setVisibility(View.INVISIBLE);
        btnBMI.setVisibility(View.INVISIBLE);
        btnSo.setVisibility(View.INVISIBLE);
        cardBtn.setVisibility(View.INVISIBLE);

//        PrefUtils utils = new PrefUtils(this);
//        ToastShow(this,utils.getPhone());

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

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        animationTwo();
                    }
                }, 400);

    }

    private void animationTwo() {

        btnLearning.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInUp)
                .duration(700)
                .playOn(btnLearning);

        btnBMI.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnBMI);

        btnSo.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(1000)
                .playOn(btnSo);
    }

    @OnClick({R.id.btnLearning, R.id.btnBMI, R.id.btnSo,R.id.btnMenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLearning:
                startActivity(new Intent(this,LearningActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnBMI:
                startActivity(new Intent(this,ShapeCalActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
            case R.id.btnSo:
                startActivity(new Intent(this, SodiumActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.btnMenu:
                ToastShow(this, "ยังไม่เปิดใช้งาน");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        ToastShow(this,"กดอีกครั้งเพื่อออกจากแอป");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
