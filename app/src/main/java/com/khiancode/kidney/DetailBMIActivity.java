package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBMIActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtValue)
    TextView txtValue;
    @BindView(R.id.txtDetail)
    TextView txtDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bmi);
        ButterKnife.bind(this);

        String status = getIntent().getExtras().getString("status");
        String value = getIntent().getExtras().getString("value");
        String detail = getIntent().getExtras().getString("detail");
        int color = getIntent().getExtras().getInt("color");

        txtTitle.setText(status);
        txtTitle.setTextColor(getResources().getColor(color));
        txtValue.setText(value);
        txtDetail.setText(detail);

    }

}
