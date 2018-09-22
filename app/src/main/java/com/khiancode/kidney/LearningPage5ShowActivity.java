package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;

public class LearningPage5ShowActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_page5_show);

        imageView = findViewById(R.id.image);

        int id = getIntent().getExtras().getInt("id");

        if (id == 1) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.page5_1));
        } else if (id == 2) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.page5_2));
        } else if (id == 3) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.page5_3));
        } else if (id == 4) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.page5_4));
        }


        imageView.setOnTouchListener(new ImageMatrixTouchHandler(this));

    }

}
