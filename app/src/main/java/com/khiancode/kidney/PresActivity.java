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
import com.khiancode.kidney.model.PresModel;
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

public class PresActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.inputOver)
    EditText inputOver;
    @BindView(R.id.inputLower)
    EditText inputLower;
    @BindView(R.id.cardBtn)
    CardView cardBtn;
    @BindView(R.id.btnCal)
    Button btnCal;
    private Realm realm;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pres);
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

    private void setBottomSheet(final String value, final String txt_pres, String txt_detail_pres, final int color, final int status) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheets_pres, null);
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

        txtTitle.setText(txt_pres);
        txtValue.setText(value);
        txtTitle.setTextColor(getResources().getColor(color));
        txtDetail.setText(txt_detail_pres);

        if (txt_pres.equals(getString(R.string.txt_pres_2))){
            btnOther.setVisibility(View.INVISIBLE);
        }

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PresActivity.this, DetailPresActivity.class);
                intent.putExtra("status", txt_pres);
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
                startActivity(new Intent(PresActivity.this,ChartPresActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                bottomSheetDialog.hide();
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void CalPres() {

        if (inputOver.getText().toString().isEmpty() || inputLower.getText().toString().isEmpty()) {
            dialogTM("ไม่สามารถคำนวณได้","ไม่สามารถคำนวณระดับความดันได้แน่ชัด โปรดปรึกษาแพทย์ผู้เชี่ยวชาญ");
            return;
        }

        int over = Integer.parseInt(inputOver.getText().toString());
        int lower = Integer.parseInt(inputLower.getText().toString());

        String value = over + "/" + lower;

//        if (over >= 60 && over<= 79 && lower >= 40 && lower <= 59) {
//            setBottomSheet(value,getString(R.string.txt_pres_1),getString(R.string.txt_detail_pres_1),R.color.color_txt_pres_1,1);
//        } else if (over >= 80 && over<= 119 && lower >= 60 && lower <= 79) {
//            setBottomSheet(value,getString(R.string.txt_pres_2),getString(R.string.txt_detail_pres_2),R.color.color_txt_pres_2,0);
//        } else if (over >= 120 && over<= 159 && lower >= 80 && lower <= 99) {
//            setBottomSheet(value,getString(R.string.txt_pres_3),getString(R.string.txt_detail_pres_3),R.color.color_txt_pres_3,2);
//        } else if (over >= 160 && over <= 210 && lower >= 100 && lower <= 120) {
//            setBottomSheet(value, getString(R.string.txt_pres_4), getString(R.string.txt_detail_pres_4), R.color.color_txt_pres_4,2);
//        } else {
//            dialogTM("ไม่สามารถคำนวณได้","ไม่สามารถคำนวณระดับความดันได้แน่ชัด โปรดปรึกษาแพทย์ผู้เชี่ยวชาญ");
//        }

        if (over >= 60 && over<= 79 ) {
            setBottomSheet(value,getString(R.string.txt_pres_1),getString(R.string.txt_detail_pres_1),R.color.color_txt_pres_1,1);
        } else if (over >= 80 && over<= 119) {
            setBottomSheet(value,getString(R.string.txt_pres_2),getString(R.string.txt_detail_pres_2),R.color.color_txt_pres_2,0);
        } else if (over >= 120 && over<= 159) {
            setBottomSheet(value,getString(R.string.txt_pres_3),getString(R.string.txt_detail_pres_3),R.color.color_txt_pres_3,2);
        } else if (over >= 160 && over <= 210) {
            setBottomSheet(value, getString(R.string.txt_pres_4), getString(R.string.txt_detail_pres_4), R.color.color_txt_pres_4,2);
        } else {
            dialogTM("ไม่สามารถคำนวณได้","ไม่สามารถคำนวณระดับความดันได้แน่ชัด โปรดปรึกษาแพทย์ผู้เชี่ยวชาญ");
        }
        insertRealm(value,over,lower);
        sendHistory(String.valueOf(value),String.valueOf(over),String.valueOf(lower));
    }

    private void insertRealm(final String value, final float over, final float lower) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String currentDateandTime = sdf.format(new Date());

        realm.beginTransaction();
        realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        String id = UUID.randomUUID().toString();
                        PresModel model = realm.createObject(PresModel.class, id);
                        model.setDt(currentDateandTime);
                        model.setOver(over);
                        model.setLower(lower);
                        model.setValue(value);
                        realm.insertOrUpdate(model);
                    }
                });
        realm.commitTransaction();
    }

    public void sendHistory(String value,String over,String lower) {
        PrefUtils utils = new PrefUtils(this);

        RequestBody requestBody = new FormBody.Builder()
                .add("over", over)
                .add("lower", lower)
                .add("value", value)
                .add("phone", utils.getPhone())
                .build();

        ApiClient.POST post = new ApiClient.POST(this);
        post.setURL(BASE_URL+"member/addpres");
        post.setRequestBody(requestBody);
        post.execute();
        post.setListenerCallService(new CallServiceListener() {
            @Override
            public void ResultData(String data) {
                if (data.equals("success")) {
//                    ToastShow(PresActivity.this,"บันทึกข้อมูล");
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
        CalPres();
    }
}
