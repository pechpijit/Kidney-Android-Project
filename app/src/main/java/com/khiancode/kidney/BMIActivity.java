package com.khiancode.kidney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.khiancode.kidney.helper.PrefUtils;
import com.khiancode.kidney.model.BmiModel;
import com.khiancode.kidney.okhttp.ApiClient;
import com.khiancode.kidney.okhttp.CallServiceListener;
import com.khiancode.kidney.viewspinner.NiceSpinner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class BMIActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.niceHeight)
    NiceSpinner niceHeight;
    @BindView(R.id.inputHeight)
    LinearLayout inputHeight;
    @BindView(R.id.niceWeight)
    NiceSpinner niceWeight;
    @BindView(R.id.inputWeight)
    LinearLayout inputWeight;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.btnCal)
    Button btnCal;

    private List<Integer> height;
    private List<Integer> weight;

    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    private int heightValue = 140;
    private int weightValue = 35;

    private Realm realm;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        txtTitle.setVisibility(View.INVISIBLE);
        imgLogo.setVisibility(View.INVISIBLE);
        inputHeight.setVisibility(View.INVISIBLE);
        inputWeight.setVisibility(View.INVISIBLE);
        cardBtn.setVisibility(View.INVISIBLE);
        btnCal.setVisibility(View.INVISIBLE);

        setViewSpinner();

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        aimationOne();
                    }
                }, 400);

    }

    private void setViewSpinner() {
        height = new LinkedList<Integer>();
        weight = new LinkedList<Integer>();

        for (int i = 130; i < 200; i++) {
            height.add(i);
        }

        for (int i = 35; i < 150; i++) {
            weight.add(i);
        }

        niceHeight.attachDataSource(height);
        niceWeight.attachDataSource(weight);

        niceHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                heightValue = height.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        niceWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weightValue = weight.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void aimationOne() {
        imgLogo.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .playOn(imgLogo);

        txtTitle.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInDown)
                .duration(700)
                .playOn(txtTitle);

        cardBtn.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(cardBtn);

        btnCal.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInUp)
                .duration(700)
                .playOn(btnCal);

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        animationTwo();
                    }
                }, 400);

    }

    private void animationTwo() {
        inputHeight.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(700)
                .playOn(inputHeight);

        inputWeight.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInRight)
                .duration(700)
                .playOn(inputWeight);
    }

    private void setBottomSheet(final float value, final String txt_BMI, String txt_detail_BMI, final int color, final String reCom) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheets_bmi, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        bottomSheetBehavior.setPeekHeight(1500);

        ImageView imgClose = bottomSheetView.findViewById(R.id.imgClose);
        TextView txtTitle = bottomSheetView.findViewById(R.id.txtTitle);
        TextView txtValue = bottomSheetView.findViewById(R.id.txtValue);
        Button btnOther = bottomSheetView.findViewById(R.id.btnOther);
        Button btnChart = bottomSheetView.findViewById(R.id.btnChart);
        final TextView txtDetail = bottomSheetView.findViewById(R.id.txtDetail);

        final DecimalFormat df = new DecimalFormat("0.##");

        txtTitle.setText(txt_BMI);
        txtTitle.setTextColor(getResources().getColor(color));
        txtValue.setText(df.format(value));
        txtDetail.setText(txt_detail_BMI);

        if (txt_BMI.equals(getString(R.string.txt_BMI_2))) {
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
                Intent intent = new Intent(BMIActivity.this, DetailBMIActivity.class);
                intent.putExtra("status", txt_BMI);
                intent.putExtra("value", df.format(value));
                intent.putExtra("detail", reCom);
                intent.putExtra("color", color);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                bottomSheetDialog.hide();
            }
        });

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BMIActivity.this,ChartBMIActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                bottomSheetDialog.hide();
            }
        });

//        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                // do something
//            }
//        });
//
//        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                // do something
//            }
//        });
//
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

    private void CalBMI() {

        float w = weightValue;
        float h = (float) heightValue / 100;
        float value = w / (h * h);

        if (value < 18.50) {
            setBottomSheet(value, getString(R.string.txt_BMI_1),getString(R.string.txt_detail_BMI_1),R.color.color_txt_bmi_1,getString(R.string.txt_recommend_BMI_1));
        } else if (value >= 18.50 && value <= 22.99) {
            setBottomSheet(value, getString(R.string.txt_BMI_2),getString(R.string.txt_detail_BMI_2),R.color.color_txt_bmi_2,getString(R.string.txt_recommend_BMI_2));
        } else if (value >= 23.00 && value <= 24.99) {
            setBottomSheet(value, getString(R.string.txt_BMI_3),getString(R.string.txt_detail_BMI_3),R.color.color_txt_bmi_3,getString(R.string.txt_recommend_BMI_3));
        } else if (value >= 25.00 && value <= 29.99) {
            setBottomSheet(value, getString(R.string.txt_BMI_4),getString(R.string.txt_detail_BMI_4),R.color.color_txt_bmi_4,getString(R.string.txt_recommend_BMI_4));
        } else if (value >= 30) {
            setBottomSheet(value, getString(R.string.txt_BMI_5),getString(R.string.txt_detail_BMI_5),R.color.color_txt_bmi_5,getString(R.string.txt_recommend_BMI_5));
        }

        insertRealm(value,h,w);

        sendHistory(String.valueOf(value),String.valueOf(heightValue),String.valueOf(weightValue));
    }

    private void insertRealm(final float value, final float h, final float w) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String currentDateandTime = sdf.format(new Date());

        realm.beginTransaction();
        realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        String id = UUID.randomUUID().toString();
                        BmiModel model = realm.createObject(BmiModel.class, id);
                        model.setDt(currentDateandTime);
                        model.setHeight(h);
                        model.setWeight(w);
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

    @OnClick(R.id.btnCal)
    public void onViewClicked() {
        CalBMI();
    }

    public void sendHistory(String value,String height,String weight) {
        PrefUtils utils = new PrefUtils(this);

        RequestBody requestBody = new FormBody.Builder()
                .add("height", height)
                .add("weight", weight)
                .add("value", value)
                .add("phone", utils.getPhone())
                .build();

        ApiClient.POST post = new ApiClient.POST(this);
        post.setURL(BASE_URL+"member/addbmi");
        post.setRequestBody(requestBody);
        post.execute();
        post.setListenerCallService(new CallServiceListener() {
            @Override
            public void ResultData(String data) {
                if (data.equals("success")) {
//                    ToastShow(BMIActivity.this,"บันทึกข้อมูล");
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
}
