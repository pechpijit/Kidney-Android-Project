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
import com.khiancode.kidney.model.TaiModel;
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

public class TAIActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.inputEgfr)
    EditText inputEgfr;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.btnCal)
    Button btnCal;
    private Realm realm;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai);
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

    private void setBottomSheet(final String value, final String txt_tai, String txt_detail_tai, final int color, final int status) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheets_tai, null);
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

        txtTitle.setText(txt_tai);
        txtValue.setText(value);
        txtTitle.setTextColor(getResources().getColor(color));
        txtDetail.setText(txt_detail_tai);

        if (status == 6 || status == 1) {
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
                Intent intent = new Intent(TAIActivity.this, DetailTaiActivity.class);
                intent.putExtra("status", txt_tai);
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
                startActivity(new Intent(TAIActivity.this,ChartTaiActivity.class));
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

    private void CalTai() {

        if (inputEgfr.getText().toString().isEmpty()) {
            dialogTM("ไม่สามารถคำนวณได้","ไม่สามารถคำนวณอัตราการกรองของไตได้แน่ชัด โปรดปรึกษาแพทย์ผู้เชี่ยวชาญ");
            return;
        }

        int tai = Integer.parseInt(inputEgfr.getText().toString());

        String value = tai + " มล./นาที" ;

        if (tai < 15) {
            setBottomSheet(value,getString(R.string.txt_tai_1),getString(R.string.txt_detail_tai_1),R.color.color_txt_tai_1,1);
        } else if (tai >= 15 && tai <= 29) {
            setBottomSheet(value,getString(R.string.txt_tai_2),getString(R.string.txt_detail_tai_2),R.color.color_txt_tai_2,2);
        } else if (tai >= 30 && tai <= 59) {
            setBottomSheet(value,getString(R.string.txt_tai_3),getString(R.string.txt_detail_tai_3),R.color.color_txt_tai_3,3);
        } else if (tai >= 60 && tai <= 89) {
            setBottomSheet(value, getString(R.string.txt_tai_4), getString(R.string.txt_detail_tai_4), R.color.color_txt_tai_4,4);
        } else if (tai >= 90 && tai <= 99) {
            setBottomSheet(value, getString(R.string.txt_tai_5), getString(R.string.txt_detail_tai_5), R.color.color_txt_tai_5,5);
        } else if (tai >= 100){
            setBottomSheet(value, getString(R.string.txt_tai_6), getString(R.string.txt_detail_tai_6), R.color.color_txt_tai_6,6);
        }else {
            dialogTM("ไม่สามารถคำนวณได้","ไม่สามารถคำนวณอัตราการกรองของไตได้แน่ชัด โปรดปรึกษาแพทย์ผู้เชี่ยวชาญ");
        }
        insertRealm(tai);
        sendHistory(String.valueOf(tai));
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
                        TaiModel model = realm.createObject(TaiModel.class, id);
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
        post.setURL(BASE_URL+"member/addtai");
        post.setRequestBody(requestBody);
        post.execute();
        post.setListenerCallService(new CallServiceListener() {
            @Override
            public void ResultData(String data) {
                if (data.equals("success")) {
//                    ToastShow(TAIActivity.this,"บันทึกข้อมูล");
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
        CalTai();
    }
}
