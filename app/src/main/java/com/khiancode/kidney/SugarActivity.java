package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.khiancode.kidney.helper.PrefUtils;
import com.khiancode.kidney.model.BmiModel;
import com.khiancode.kidney.model.SugarModel;
import com.khiancode.kidney.okhttp.ApiClient;
import com.khiancode.kidney.okhttp.CallServiceListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SugarActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.inputSugar)
    EditText inputSugar;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.btnCal)
    Button btnCal;
    private Realm realm;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        txtTitle.setVisibility(View.INVISIBLE);
        imgLogo.setVisibility(View.INVISIBLE);
        cardBtn.setVisibility(View.INVISIBLE);
        btnCal.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        aimationOne();
                    }
                }, 400);

    }

    private void aimationOne() {
        imgLogo.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(imgLogo);

        txtTitle.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInDown)
                .duration(700)
                .playOn(txtTitle);

        cardBtn.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInDown)
                .duration(700)
                .playOn(cardBtn);

        btnCal.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInUp)
                .duration(700)
                .playOn(btnCal);

    }

    private void setBottomSheet(final String value, final String txt_sugar, String txt_detail_sugar, final int color, final int status) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheets_sugar, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        bottomSheetBehavior.setPeekHeight(1500);

        ImageView imgClose = bottomSheetView.findViewById(R.id.imgClose);
        TextView txtTitle = bottomSheetView.findViewById(R.id.txtTitle);
        TextView txtValue = bottomSheetView.findViewById(R.id.txtValue);
        Button btnOther = bottomSheetView.findViewById(R.id.btnOther);
        Button btnChart = bottomSheetView.findViewById(R.id.btnChart);
        final TextView txtDetail = bottomSheetView.findViewById(R.id.txtDetail);

        txtTitle.setText(txt_sugar);
        txtValue.setText(value);
        txtTitle.setTextColor(getResources().getColor(color));
        txtDetail.setText(txt_detail_sugar);

        if (txt_sugar.equals(getString(R.string.txt_sugar_2))) {
            btnOther.setVisibility(View.INVISIBLE);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.hide();
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SugarActivity.this, DetailSugarActivity.class);
                intent.putExtra("status", txt_sugar);
                intent.putExtra("value", value);
                intent.putExtra("detail",status );
                intent.putExtra("color", color);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                bottomSheetDialog.hide();
            }
        });

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SugarActivity.this,ChartSugarActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                bottomSheetDialog.hide();
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == 1) {
                    bottomSheetDialog.hide();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetDialog.show();
    }

    private void CalSugar() {

        if (inputSugar.getText().toString().isEmpty()) {
            dialogTM("ไม่สามารถคำนวนได้","ไม่สามารถคำนวนระดับน้ำตาลในเลือดได้แน่ชัด โปรดปรึกษาแพทย์ผู้เชี่ยวชาญ");
            return;
        }

        int sugar = Integer.parseInt(inputSugar.getText().toString());

        String value = sugar + " mg." ;

        if (sugar >= 0 && sugar <= 79) {
            setBottomSheet(value,getString(R.string.txt_sugar_1),getString(R.string.txt_detail_sugar_1),R.color.color_txt_sugar_1,1);
        } else if (sugar >= 80 && sugar <= 129) {
            setBottomSheet(value,getString(R.string.txt_sugar_2),getString(R.string.txt_detail_sugar_2),R.color.color_txt_sugar_2,0);
        } else if (sugar >= 130 && sugar <= 179) {
            setBottomSheet(value,getString(R.string.txt_sugar_3),getString(R.string.txt_detail_sugar_3),R.color.color_txt_sugar_3,2);
        } else if (sugar >= 180 && sugar <= 239) {
            setBottomSheet(value, getString(R.string.txt_sugar_4), getString(R.string.txt_detail_sugar_4), R.color.color_txt_sugar_4,2);
        } else if (sugar >= 240){
            setBottomSheet(value, getString(R.string.txt_sugar_5), getString(R.string.txt_detail_sugar_5), R.color.color_txt_sugar_5,2);
        }else {
            dialogTM("ไม่สามารถคำนวนได้","ไม่สามารถคำนวนระดับน้ำตาลในเลือดได้แน่ชัด โปรดปรึกษาแพทย์ผู้เชี่ยวชาญ");
        }
        insertRealm(sugar);
        sendHistory(String.valueOf(sugar));
    }

    private void insertRealm(final float value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String currentDateandTime = sdf.format(new Date());

        realm.beginTransaction();
        realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        String id = UUID.randomUUID().toString();
                        SugarModel model = realm.createObject(SugarModel.class, id);
                        model.setDt(currentDateandTime);
                        model.setValue(value);
                        realm.insertOrUpdate(model);
                    }
                });
        realm.commitTransaction();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void sendHistory(String value) {
        PrefUtils utils = new PrefUtils(this);

        RequestBody requestBody = new FormBody.Builder()
                .add("value", value)
                .add("phone", utils.getPhone())
                .build();

        ApiClient.POST post = new ApiClient.POST(this);
        post.setURL(BASE_URL+"member/addsugar");
        post.setRequestBody(requestBody);
        post.execute();
        post.setListenerCallService(new CallServiceListener() {
            @Override
            public void ResultData(String data) {
                if (data.equals("success")) {
//                    ToastShow(SugarActivity.this,"บันทึกข้อมูล");
                }
            }

            @Override
            public void ResultError(String data) {

            }

            @Override
            public void ResultNull(String data) {

            }
        });

    }

    @OnClick(R.id.btnCal)
    public void onViewClicked() {
        CalSugar();
    }
}
