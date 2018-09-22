package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LearningPage5Activity extends AppCompatActivity {

    @BindView(R.id.btnImg1)
    ImageView btnImg1;
    @BindView(R.id.btnImg2)
    ImageView btnImg2;
    @BindView(R.id.btnImg3)
    ImageView btnImg3;
    @BindView(R.id.btnImg4)
    ImageView btnImg4;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_page5);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnImg1, R.id.btnImg2, R.id.btnImg3, R.id.btnImg4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnImg1:
                startActivity(new Intent(this,LearningPage5ShowActivity.class).putExtra("id",1));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnImg2:
                startActivity(new Intent(this,LearningPage5ShowActivity.class).putExtra("id",2));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnImg3:
                startActivity(new Intent(this,LearningPage5ShowActivity.class).putExtra("id",3));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnImg4:
                startActivity(new Intent(this,LearningPage5ShowActivity.class).putExtra("id",4));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }
}
