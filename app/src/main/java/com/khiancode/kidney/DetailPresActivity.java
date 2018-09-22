package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPresActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtValue)
    TextView txtValue;
    @BindView(R.id.viewDetail_2)
    LinearLayout viewDetail2;
    @BindView(R.id.txtDetail)
    TextView txtDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pres);
        ButterKnife.bind(this);

        String status = getIntent().getExtras().getString("status");
        String value = getIntent().getExtras().getString("value");
        int detail = getIntent().getExtras().getInt("detail");
        int color = getIntent().getExtras().getInt("color");

        txtTitle.setText(status);
        txtTitle.setTextColor(getResources().getColor(color));
        txtValue.setText(value);

        if (detail == 2) {
            txtDetail.setText(getString(R.string.txt_recommend_pres_1));
            txtDetail.setVisibility(View.VISIBLE);
            viewDetail2.setVisibility(View.INVISIBLE);
        } else if (detail == 1) {
            txtDetail.setVisibility(View.INVISIBLE);
            viewDetail2.setVisibility(View.VISIBLE);
        }
    }
}
