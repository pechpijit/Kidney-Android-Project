package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailSugarActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtValue)
    TextView txtValue;
    @BindView(R.id.txtDetail)
    TextView txtDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sugar);
        ButterKnife.bind(this);

        String status = getIntent().getExtras().getString("status");
        String value = getIntent().getExtras().getString("value");
        int detail = getIntent().getExtras().getInt("detail");
        int color = getIntent().getExtras().getInt("color");

        txtTitle.setText(status);
        txtTitle.setTextColor(getResources().getColor(color));
        txtValue.setText(value);

        if (detail == 1) {
            txtDetail.setText(getString(R.string.txt_recommend_sugar_1));
        } else if (detail == 2) {
            txtDetail.setText(getString(R.string.txt_recommend_sugar_2));
        }
    }
}
