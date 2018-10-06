package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailSugarActivity extends BaseActivity {

    LinearLayout view11,view12,view13,view21,view22, view23;
    TextView txtValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sugar);

        view11 = findViewById(R.id.view11);
        view12 = findViewById(R.id.view12);
        view13 = findViewById(R.id.view13);
        view21 = findViewById(R.id.view21);
        view22 = findViewById(R.id.view22);
        view23 = findViewById(R.id.view23);
        txtValue = findViewById(R.id.txtValue);

        int status = getIntent().getExtras().getInt("status");
        String value = getIntent().getExtras().getString("value");

        txtValue.setText(value);

        if (status == 11) {
            view11.setVisibility(View.VISIBLE);
        } else if (status == 12) {
            view12.setVisibility(View.VISIBLE);
        } else if (status == 13) {
            view13.setVisibility(View.VISIBLE);
        }else if (status == 21) {
            view21.setVisibility(View.VISIBLE);
        }else if (status == 22) {
            view22.setVisibility(View.VISIBLE);
        }else if (status == 23) {
            view23.setVisibility(View.VISIBLE);
        }
    }
}
