package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShapeCalActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnBMI)
    Button btnBMI;
    @BindView(R.id.cardBtn1)
    CardView cardBtn1;
    @BindView(R.id.btnPres)
    Button btnPres;
    @BindView(R.id.cardBtn2)
    CardView cardBtn2;
    @BindView(R.id.btnSugar)
    Button btnSugar;
    @BindView(R.id.cardBtn3)
    CardView cardBtn3;
    @BindView(R.id.btnTai)
    Button btnTai;
    @BindView(R.id.cardBtn4)
    CardView cardBtn4;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_cal);
        ButterKnife.bind(this);

        invisibleView(
                txtTitle,
                btnBMI,
                cardBtn1,
                btnPres,
                cardBtn2,
                btnSugar,
                cardBtn3,
                btnTai,
                cardBtn4
        );

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        aimationOne();
                    }
                }, 400);

    }

    private void aimationOne() {
        txtTitle.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInDown)
                .duration(700)
                .playOn(txtTitle);

        cardBtn1.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(cardBtn1);

        cardBtn2.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(cardBtn2);

        cardBtn3.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(cardBtn3);

        cardBtn4.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(cardBtn4);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        animationTwo();
                    }
                }, 100);
    }

    private void animationTwo() {

        btnBMI.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnBMI);

        btnPres.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(btnPres);

        btnSugar.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnSugar);

        btnTai.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(btnTai);

    }

    @OnClick({R.id.btnBMI, R.id.btnPres, R.id.btnSugar, R.id.btnTai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBMI:
                startActivity(new Intent(this,BMIActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
            case R.id.btnPres:
                startActivity(new Intent(this,PresActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
            case R.id.btnSugar:
                startActivity(new Intent(this,SugarActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
            case R.id.btnTai:
                startActivity(new Intent(this,TAIActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
        }
    }
}
