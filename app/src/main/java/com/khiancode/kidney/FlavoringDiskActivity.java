package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiancode.kidney.helper.PrefUtils;

import java.text.DecimalFormat;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlavoringDiskActivity extends BaseActivity {

    @BindView(R.id.pieView)
    PieView pieView;
    @BindView(R.id.txtSodiumValue)
    TextView txtSodiumValue;
    @BindView(R.id.cardBtn1)
    CardView cardBtn1;
    @BindView(R.id.cardBtn2)
    CardView cardBtn2;
    @BindView(R.id.imgTai)
    ImageView imgTai;
    private PieAngleAnimation animation;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavoring_disk);
        ButterKnife.bind(this);

        pieView.setMainBackgroundColor(getResources().getColor(R.color.white20));
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        pieView.setMaxPercentage(100);
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
        updateView();

    }

    private void updateView() {
        PrefUtils utils = new PrefUtils(this);
        DecimalFormat df = new DecimalFormat("0");

        if (utils.getSodiumValue() != 0) {
            txtSodiumValue.setText(df.format(utils.getSodiumValue()) + " มิลลิกรัม");

            float per = (utils.getSodiumValue() / 2000) * 100;
            pieView.setPercentage((int) per);
        }

        if (utils.getSodiumValue() == 0){
            txtSodiumValue.setText("0 มิลลิกรัม");
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_4));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.smile_icon_48));
            pieView.setPercentage(100);
        } else if (utils.getSodiumValue() >= 0 && utils.getSodiumValue() <= 1800) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.smile_icon_48));
        } else if (utils.getSodiumValue() >= 1801 && utils.getSodiumValue() <= 1999) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_2));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.sarah_icon_48));
        } else if (utils.getSodiumValue() >= 2000) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_3));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.icon_tai_over_48));
        }
        pieView.setInnerBackgroundColor(getResources().getColor(R.color.white));
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateView();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.cardBtn1, R.id.cardBtn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardBtn1:
                startActivityForResult(new Intent(this, MeatDiskActivity.class), 1112);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.cardBtn2:
                startActivityForResult(new Intent(this, FlavDiskActivity.class), 1112);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
        }
    }
}
