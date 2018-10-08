package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.khiancode.kidney.model.LDLModel;
import com.khiancode.kidney.model.TaiModel;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class ChartLDLActivity extends AppCompatActivity {
    private Realm realm;
    private RealmChangeListener realmListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            invalidateView();
        }
    };
    RealmResults<LDLModel> results;
    String TAG = "ChartTaiActivity";
    LineChart mChart;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_bmi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChart = findViewById(R.id.chart);
        new Components().set_Chart5(mChart);

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(realmListener);
        invalidateView();
    }

    private void addEntry2(float value) {
        LineData data = mChart.getData();

        if (data != null) {
            ILineDataSet set_h = data.getDataSetByIndex(0);


            if (set_h == null) {
                set_h = new Components().createTai();
                data.addDataSet(set_h);
            }

            data.addEntry(new Entry(set_h.getEntryCount(), value), 0);
            data.notifyDataChanged();

            mChart.notifyDataSetChanged();
            mChart.setVisibleXRangeMaximum(50);
            mChart.moveViewToX(data.getEntryCount());

        }
    }

    private void invalidateView() {
//        results = realm.where(BmiModel.class).sort("dt", Sort.ASCENDING).findAll();
        results = realm.where(LDLModel.class).sort("dt", Sort.ASCENDING).findAll();

        for (LDLModel model:results) {
            Log.d(TAG, "id = " + model.getDt());
//            feedMultiple(model.getValue());
            addEntry2(model.getValue());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
