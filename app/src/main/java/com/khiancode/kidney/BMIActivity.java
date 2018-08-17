package com.khiancode.kidney;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.khiancode.kidney.viewspinner.NiceSpinner;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private void setBottomSheet(float value, String txt_BMI,String txt_detail_BMI,int color) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheets_bmi, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        bottomSheetBehavior.setPeekHeight(260);

        ImageView imgClose = bottomSheetView.findViewById(R.id.imgClose);
        final Button btnOther = bottomSheetView.findViewById(R.id.btnOther);
        TextView txtTitle = bottomSheetView.findViewById(R.id.txtTitle);
        TextView txtValue = bottomSheetView.findViewById(R.id.txtValue);
        final FrameLayout line = bottomSheetView.findViewById(R.id.line);
        final TextView txtDetail = bottomSheetView.findViewById(R.id.txtDetail);

        DecimalFormat df = new DecimalFormat("0.##");

        txtTitle.setText(txt_BMI);
        txtTitle.setTextColor(getResources().getColor(color));
        txtValue.setText(df.format(value));
        txtDetail.setText(txt_detail_BMI);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.hide();
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                btnOther.setVisibility(View.INVISIBLE);
            }
        });

        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // do something
            }
        });

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // do something
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == 3) {

                    YoYo.with(Techniques.FadeOutUp)
                            .duration(200)
                            .playOn(btnOther);
                    btnOther.setVisibility(View.INVISIBLE);

                    txtDetail.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.ZoomIn)
                            .duration(200)
                            .playOn(txtDetail);

                    line.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.ZoomIn)
                            .duration(200)
                            .playOn(line);


                } else if (newState == 4) {

                    btnOther.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInDown)
                            .duration(200)
                            .playOn(btnOther);

                    YoYo.with(Techniques.ZoomOut)
                            .duration(200)
                            .playOn(txtDetail);
                    txtDetail.setVisibility(View.INVISIBLE);

                    YoYo.with(Techniques.ZoomOut)
                            .duration(200)
                            .playOn(line);
                    line.setVisibility(View.INVISIBLE);
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
            setBottomSheet(value, getString(R.string.txt_BMI_1),getString(R.string.txt_detail_BMI_1),R.color.color_txt_bmi_1);
        } else if (value >= 18.50 && value <= 22.99) {
            setBottomSheet(value, getString(R.string.txt_BMI_2),getString(R.string.txt_detail_BMI_2),R.color.color_txt_bmi_2);
        } else if (value >= 23.00 && value <= 24.99) {
            setBottomSheet(value, getString(R.string.txt_BMI_3),getString(R.string.txt_detail_BMI_3),R.color.color_txt_bmi_3);
        } else if (value >= 25.00 && value <= 29.99) {
            setBottomSheet(value, getString(R.string.txt_BMI_4),getString(R.string.txt_detail_BMI_4),R.color.color_txt_bmi_4);
        } else if (value >= 30) {
            setBottomSheet(value, getString(R.string.txt_BMI_5),getString(R.string.txt_detail_BMI_5),R.color.color_txt_bmi_5);
        }
    }

    @OnClick(R.id.btnCal)
    public void onViewClicked() {
        CalBMI();
    }
}
