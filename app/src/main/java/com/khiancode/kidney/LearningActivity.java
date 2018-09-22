package com.khiancode.kidney;

import android.content.Intent;
import android.net.Uri;
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

public class LearningActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnMenu1)
    Button btnMenu1;
    @BindView(R.id.cardBtn1)
    CardView cardBtn1;
    @BindView(R.id.btnMenu2)
    Button btnMenu2;
    @BindView(R.id.cardBtn2)
    CardView cardBtn2;
    @BindView(R.id.btnMenu3)
    Button btnMenu3;
    @BindView(R.id.cardBtn3)
    CardView cardBtn3;
    @BindView(R.id.btnMenu4)
    Button btnMenu4;
    @BindView(R.id.cardBtn4)
    CardView cardBtn4;
    @BindView(R.id.btnMenu5)
    Button btnMenu5;
    @BindView(R.id.cardBtn5)
    CardView cardBtn5;
    @BindView(R.id.btnMenu6)
    Button btnMenu6;
    @BindView(R.id.cardBtn6)
    CardView cardBtn6;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        ButterKnife.bind(this);

        invisibleView(
                txtTitle,
                btnMenu1,
                cardBtn1,
                btnMenu2,
                cardBtn2,
                btnMenu3,
                cardBtn3,
                btnMenu4,
                cardBtn4,
                btnMenu5,
                cardBtn5,
                btnMenu6,
                cardBtn6
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

        cardBtn5.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(cardBtn5);

        cardBtn6.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(cardBtn6);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        animationTwo();
                    }
                }, 100);
    }

    private void animationTwo() {

        btnMenu1.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnMenu1);

        btnMenu2.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(btnMenu2);

        btnMenu3.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnMenu3);

        btnMenu4.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(btnMenu4);

        btnMenu5.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(btnMenu5);

        btnMenu6.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(btnMenu6);

//        startActivity(new Intent(this, BMIActivity.class));
//        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @OnClick({R.id.btnMenu1, R.id.btnMenu2, R.id.btnMenu3, R.id.btnMenu4, R.id.btnMenu5, R.id.btnMenu6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMenu1:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=XV9bBPfzUZM"));
                intent.putExtra("force_fullscreen",true);
                startActivity(intent);
                break;
            case R.id.btnMenu2:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=7cHC-YlkEhY"));
                intent2.putExtra("force_fullscreen",true);
                startActivity(intent2);
                break;
            case R.id.btnMenu3:
                startActivity(new Intent(this, ListFoodActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.btnMenu4:
                startActivity(new Intent(this, LearningPage4Activity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.btnMenu5:
                startActivity(new Intent(this, LearningPage5Activity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.btnMenu6:
                startActivity(new Intent(this, LearningPage6Activity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
    }
}
