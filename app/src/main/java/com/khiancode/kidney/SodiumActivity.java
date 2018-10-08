package com.khiancode.kidney;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.khiancode.kidney.helper.PrefUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SodiumActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.pieView)
    PieView pieView;
    @BindView(R.id.viewValue)
    FrameLayout viewValue;
    @BindView(R.id.txtSodiumValue)
    TextView txtSodiumValue;
    @BindView(R.id.btnCat1)
    Button btnCat1;
    @BindView(R.id.btnCat2)
    Button btnCat2;
    @BindView(R.id.btnCat3)
    Button btnCat3;
    @BindView(R.id.btnRecomente)
    Button btnRecomente;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.viewBtn1)
    LinearLayout viewBtn1;
    @BindView(R.id.btnRef)
    ImageView btnRef;
    @BindView(R.id.imgTai)
    ImageView imgTai;
    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.cardStatus)
    CardView cardStatus;

    private PieAngleAnimation animation;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sodium);
        ButterKnife.bind(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String currentDateandTime = sdf.format(new Date());

        PrefUtils utils = new PrefUtils(this);
        if (utils.getDay().isEmpty()) {
            utils.setDay(currentDateandTime);
            utils.setSodiumValue(0.0f);
        } else if (!utils.getDay().equals(currentDateandTime)) {
            utils.setSodiumValue(0.0f);
            utils.setDay(currentDateandTime);
        }

        viewBtn1.setVisibility(View.INVISIBLE);
        txtTitle.setVisibility(View.INVISIBLE);
        viewValue.setVisibility(View.INVISIBLE);
        txtSodiumValue.setVisibility(View.INVISIBLE);
        cardBtn.setVisibility(View.INVISIBLE);
        btnRef.setVisibility(View.INVISIBLE);
        cardStatus.setVisibility(View.INVISIBLE);
        txtStatus.setVisibility(View.INVISIBLE);
        btnRecomente.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        aimationOne();
                    }
                }, 400);

        pieView.setMainBackgroundColor(getResources().getColor(R.color.white20));
        pieView.setMaxPercentage(100);
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);

        updateView();
    }

    private void aimationOne() {
        viewValue.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(viewValue);

        txtTitle.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInDown)
                .duration(700)
                .playOn(txtTitle);

        cardBtn.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(cardBtn);

        txtSodiumValue.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(700)
                .playOn(txtSodiumValue);

        cardStatus.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(cardStatus);


        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        animationTwo();
                    }
                }, 200);

    }

    private void animationTwo() {
        viewBtn1.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInDown)
                .duration(700)
                .playOn(viewBtn1);

        btnRef.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(700)
                .playOn(btnRef);

        txtStatus.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(txtStatus);
    }

    @OnClick({R.id.btnCat1, R.id.btnCat2, R.id.btnCat3, R.id.btnRef, R.id.btnRecomente})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCat1:
                startActivityForResult(new Intent(this, FlavoringDiskActivity.class), 1150);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.btnCat2:
                startActivityForResult(new Intent(this, FoodDiskActivity.class), 1150);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.btnCat3:
                startActivityForResult(new Intent(this, DrinkDiskActivity.class), 1150);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.btnRef:
                PrefUtils utils = new PrefUtils(this);
                utils.setSodiumValue(0);
                updateView();
                ToastShow(this, "รีเซ็ตปริมาณโซเดียม");
                break;
            case R.id.btnRecomente:
                startActivityForResult(new Intent(this, RecommentSodiumActivity.class), 1150);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateView();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("SetTextI18n")
    private void updateView() {
        PrefUtils utils = new PrefUtils(this);
        DecimalFormat df = new DecimalFormat("0");

        if (utils.getSodiumValue() != 0) {
            txtSodiumValue.setText(df.format(utils.getSodiumValue()) + " มิลลิกรัม");

            float per = (utils.getSodiumValue() / 2000) * 100;
            pieView.setPercentage((int) per);
        }

        if (utils.getSodiumValue() == 0) {
            txtSodiumValue.setText("0 มิลลิกรัม");
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_4));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.smile_icon));
            pieView.setPercentage(100);
            btnRecomente.setVisibility(View.INVISIBLE);
        } else if (utils.getSodiumValue() >= 0 && utils.getSodiumValue() <= 1800) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
            cardStatus.setCardBackgroundColor(getResources().getColor(R.color.color_percen_1));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.smile_icon));
            txtStatus.setText("เหมาะสม");
            btnRecomente.setVisibility(View.INVISIBLE);
        } else if (utils.getSodiumValue() >= 1801 && utils.getSodiumValue() <= 1999) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_2));
            cardStatus.setCardBackgroundColor(getResources().getColor(R.color.color_percen_2));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.sarah_icon));
            txtStatus.setText("เสี่ยง! เกลือจะเกินแล้ว");
            btnRecomente.setVisibility(View.INVISIBLE);
        } else if (utils.getSodiumValue() >= 2000) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_3));
            cardStatus.setCardBackgroundColor(getResources().getColor(R.color.color_percen_3));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.icon_tai_over_200));
            txtStatus.setText("เกลือเกินแล้ว!");
            btnRecomente.setVisibility(View.VISIBLE);
        }
        pieView.setInnerBackgroundColor(getResources().getColor(R.color.white));
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
    }
}
