package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;

public class LearningPage6Activity extends AppCompatActivity {
    ImageView imageView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_page6);
        imageView = findViewById(R.id.image);
        imageView.setOnTouchListener(new ImageMatrixTouchHandler(this));
    }

}
