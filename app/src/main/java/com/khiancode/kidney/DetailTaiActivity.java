package com.khiancode.kidney;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTaiActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtValue)
    TextView txtValue;
    @BindView(R.id.viewDetail_1)
    LinearLayout viewDetail1;
    @BindView(R.id.viewDetail_2)
    LinearLayout viewDetail2;
    @BindView(R.id.viewDetail_3)
    LinearLayout viewDetail3;
    @BindView(R.id.viewDetail_4)
    LinearLayout viewDetail4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tai);
        ButterKnife.bind(this);

        String status = getIntent().getExtras().getString("status");
        String value = getIntent().getExtras().getString("value");
        int detail = getIntent().getExtras().getInt("detail");
        int color = getIntent().getExtras().getInt("color");

        txtTitle.setText(status);
        txtTitle.setTextColor(getResources().getColor(color));
        txtValue.setText(value);

        if (detail == 2) {
            viewDetail4.setVisibility(View.VISIBLE);
        } else if (detail == 3) {
            viewDetail3.setVisibility(View.VISIBLE);
        }else if (detail == 4) {
            viewDetail2.setVisibility(View.VISIBLE);
        }else if (detail == 5) {
            viewDetail1.setVisibility(View.VISIBLE);
        }
    }
}
