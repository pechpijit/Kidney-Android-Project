package com.khiancode.kidney;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.khiancode.kidney.helper.PrefUtils;

import java.text.DecimalFormat;

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
    @BindView(R.id.txtSodium)
    TextView txtSodium;
    @BindView(R.id.txtSodiumValue)
    TextView txtSodiumValue;
    @BindView(R.id.btnCat1)
    Button btnCat1;
    @BindView(R.id.btnCat2)
    Button btnCat2;
    @BindView(R.id.btnCat3)
    Button btnCat3;
    @BindView(R.id.btnCat4)
    Button btnCat4;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.viewBtn1)
    LinearLayout viewBtn1;
    @BindView(R.id.viewBtn2)
    LinearLayout viewBtn2;
    @BindView(R.id.btnRef)
    ImageView btnRef;

    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior bottomSheetBehavior;
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

        PrefUtils utils = new PrefUtils(this);
        utils.setSodiumValue(0.0f);

        viewBtn1.setVisibility(View.INVISIBLE);
        viewBtn2.setVisibility(View.INVISIBLE);
        txtTitle.setVisibility(View.INVISIBLE);
        viewValue.setVisibility(View.INVISIBLE);
        txtSodium.setVisibility(View.INVISIBLE);
        txtSodiumValue.setVisibility(View.INVISIBLE);
        cardBtn.setVisibility(View.INVISIBLE);
        btnRef.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        aimationOne();
                    }
                }, 400);

        pieView.setMainBackgroundColor(getResources().getColor(R.color.white20));
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        pieView.setMaxPercentage(100);
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
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

        txtSodium.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInRight)
                .duration(700)
                .playOn(txtSodium);

        txtSodiumValue.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(700)
                .playOn(txtSodiumValue);


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

        viewBtn2.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInUp)
                .duration(700)
                .playOn(viewBtn2);

        btnRef.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(700)
                .playOn(btnRef);
    }

    @OnClick({R.id.btnCat1, R.id.btnCat2, R.id.btnCat3, R.id.btnCat4,R.id.btnRef})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCat1:
                intentListFood(1);
                break;
            case R.id.btnCat2:
                intentListFood(2);
                break;
            case R.id.btnCat3:
                ToastShow(this, "ยังไม่เปิดใช้งาน");
                break;
            case R.id.btnCat4:
                ToastShow(this, "ยังไม่เปิดใช้งาน");
                break;
            case R.id.btnRef:
                DecimalFormat df = new DecimalFormat("0");
                PrefUtils utils = new PrefUtils(this);
                utils.setSodiumValue(0);
                txtSodiumValue.setText(df.format(utils.getSodiumValue()) + " มิลลิกรัม");
                pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
                pieView.setPercentage(0);
                ToastShow(this, "รีเซ็ตปริมาณโซเดียม");
                break;
        }
    }

    private void intentListFood(int id) {
        startActivityForResult(new Intent(this, ListFoodActivity.class).putExtra(KEY_FOOD_ID, id), 1150);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
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

            float per = (utils.getSodiumValue() / 2400) * 100;
            pieView.setPercentage((int)per);
        }

        if (utils.getSodiumValue() >= 0 && utils.getSodiumValue() <= 2000) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        } else if (utils.getSodiumValue() > 2000 && utils.getSodiumValue() <= 2400) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_2));
        } else if (utils.getSodiumValue() > 2400) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_3));
        }

        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
    }
}
