package com.khiancode.kidney;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiancode.kidney.adapter.AdapterFoodList;
import com.khiancode.kidney.helper.PrefUtils;
import com.khiancode.kidney.model.FoodModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FlavDiskActivity extends BaseActivity {

    @BindView(R.id.pieView)
    PieView pieView;
    @BindView(R.id.dummyfrag_scrollableview)
    RecyclerView recyclerView;
    @BindView(R.id.txtSodiumValue)
    TextView txtSodiumValue;
    @BindView(R.id.inputSearch)
    EditText inputSearch;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    
    String TAG = "DrinkDiskActivity";

    private PieAngleAnimation animation;
    private AdapterFoodList adapter;

    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flav_disk);
        ButterKnife.bind(this);

        pieView.setMainBackgroundColor(getResources().getColor(R.color.white20));
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        pieView.setMaxPercentage(100);
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
        updateView();
        setListFood(getFood(), "food_a", 1, "ช้อนชา");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                searchArray(cs.toString());
                if (cs.toString().isEmpty()){
                    imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_magnify_white_48dp));
                    imgSearch.setEnabled(false);
                }else {
                    imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_white_48dp));
                    imgSearch.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputSearch.setText("");
                imgSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_magnify_white_48dp));
                imgSearch.setEnabled(false);
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void performSearch() {
        inputSearch.clearFocus();
        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(inputSearch.getWindowToken(), 0);
    }

    private void searchArray(String search) {
        if (search.isEmpty()) {
            setListFood(getFood(), "food_a", 1, "ช้อนชา");
            return;
        }

        ArrayList<FoodModel> food = getFood();
        ArrayList<FoodModel> foodMake = new ArrayList<FoodModel>();

//        int nStr = search.length();


        for (FoodModel model:food) {
//            if (model.getName().length() >= nStr) {
//                String subStr = model.getName().substring(0, nStr);
//                if (search.equals(subStr)) {
//                    foodMake.add(model);
//                }
//            }

            if (model.getName().contains(search)) {
                    foodMake.add(model);
            }
        }
        setListFood(foodMake, "food_a", 1, "ช้อนชา");
    }

    private void setListFood(final ArrayList<FoodModel> food, final String imfPath, final float w, final String n) {
        recyclerView = findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new AdapterFoodList(this, food, imfPath);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new AdapterFoodList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setBottomSheet(food.get(position), imfPath, w, n);
            }
        });
    }

    float v;
    float sv;
    int sum = 0;
    DecimalFormat df;
    PrefUtils prefUtils;
    private void setBottomSheet(final FoodModel foodModel, String imfPath, final float w, final String n) {
        v = w;
        sv = foodModel.getSodium();
        sum = 1;

        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheets_list_sodium_flav, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        ImageView imgClose = bottomSheetView.findViewById(R.id.imgClose);
        ImageView imgFood = bottomSheetView.findViewById(R.id.imgFood);
        TextView txtName = bottomSheetView.findViewById(R.id.txtName);
        final TextView txtSodium = bottomSheetView.findViewById(R.id.txtSodium);
        final TextView txtSodiumValue = bottomSheetView.findViewById(R.id.txtSodiumValue);
        final TextView txtWeight = bottomSheetView.findViewById(R.id.txtWeight);
        final TextView txtWeightValue = bottomSheetView.findViewById(R.id.txtWeightValue);
        Button btnCal = bottomSheetView.findViewById(R.id.btnCal);
        ImageView btnP = bottomSheetView.findViewById(R.id.btnP);
        ImageView btnM = bottomSheetView.findViewById(R.id.btnM);

        df = new DecimalFormat("0");
        prefUtils = new PrefUtils(this);

        txtName.setText(foodModel.getName());
        txtWeight.setText("ปริมาณ : " + df.format(v) + " " + n);
//        txtWeightValue.setText(df.format(v) + " " + n);
        txtWeightValue.setText(df.format(v));
        txtSodium.setText("ปริมาณโซเดียม : " + foodModel.getSodium() + " มิลลิกรัม");

        txtSodiumValue.setText("ปริมาณโซเดียมปัจจุบัน : " + df.format(prefUtils.getSodiumValue()) + " มิลลิกรัม");

        String img = "file:///android_asset/" + imfPath + "/" + foodModel.getPicture() + ".png";
        Glide.with(this)
                .load(Uri.parse(img))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .error(R.drawable.placeholder)
                .into(imgFood);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.hide();
            }
        });

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefUtils.getSodiumValue() >= 4000) {
                    ToastShow(FlavDiskActivity.this, "ถึงปริมาณโซเดียมสูงสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }
                prefUtils.setSodiumValue(prefUtils.getSodiumValue() + sv);
                txtSodiumValue.setText("ปริมาณโซเดียมปัจจุบัน : " + df.format(prefUtils.getSodiumValue()) + " มิลลิกรัม");
                ToastShow(FlavDiskActivity.this, "อัพเดทปริมาณโซเดียม");
                updateView();
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (sum <= 0) {
//                    ToastShow(FlavDiskActivity.this, "ถึงจำนวนต่ำสุดที่ระบบจำกัดไว้แล้ว");
//                    return;
//                }

                float a = w / 2;
                v -= a;
                String s = String.valueOf(v);
                if (s.substring(s.length() - 2).equals(".0")) {
//                    txtWeightValue.setText(df.format(v) + " " + n);
                    txtWeightValue.setText(df.format(v));
                    txtWeight.setText("ปริมาณ : " + df.format(v) + " " + n);
                } else {
//                    txtWeightValue.setText(v + " " + n);
                    txtWeightValue.setText(String.valueOf(v));
                    txtWeight.setText("ปริมาณ : " + v + " " + n);
                }

                float a2 = foodModel.getSodium() / 2;
                sv -= a2;
                String s2 = String.valueOf(v);
                if (s2.substring(s2.length() - 2).equals(".0")) {
                    txtSodium.setText("ปริมาณโซเดียม : " + df.format(sv) + " มิลลิกรัม");
                } else {
                    txtSodium.setText("ปริมาณโซเดียม : " + sv + " มิลลิกรัม");
                }

                sum--;
            }
        });

        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sum >= 99) {
                    ToastShow(FlavDiskActivity.this, "ถึงจำนวนสูงสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }

                float a1 = w / 2;
                v += a1;
                String s1 = String.valueOf(v);
                if (s1.substring(s1.length() - 2).equals(".0")) {
//                    txtWeightValue.setText(df.format(v) + " " + n);
                    txtWeightValue.setText(df.format(v));
                    txtWeight.setText("ปริมาณ : " + df.format(v) + " " + n);
                } else {
//                    txtWeightValue.setText(v + " " + n);
                    txtWeightValue.setText(String.valueOf(v));
                    txtWeight.setText("ปริมาณ : " + v + " " + n);
                }

                float a2 = foodModel.getSodium() / 2;
                sv += a2;
                String s2 = String.valueOf(v);
                if (s2.substring(s2.length() - 2).equals(".0")) {
                    txtSodium.setText("ปริมาณโซเดียม : " + df.format(sv) + " มิลลิกรัม");
                } else {
                    txtSodium.setText("ปริมาณโซเดียม : " + sv + " มิลลิกรัม");
                }

                sum++;
            }
        });

        bottomSheetDialog.show();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void updateView() {
        PrefUtils utils = new PrefUtils(this);
        DecimalFormat df = new DecimalFormat("0");

        if (utils.getSodiumValue() != 0) {
            txtSodiumValue.setText(df.format(utils.getSodiumValue()) + " มิลลิกรัม");

            float per = (utils.getSodiumValue() / 2400) * 100;
            pieView.setPercentage((int) per);
        }

        if (utils.getSodiumValue() >= 0 && utils.getSodiumValue() <= 1800) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        } else if (utils.getSodiumValue() > 1801 && utils.getSodiumValue() <= 2000) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_2));
        } else if (utils.getSodiumValue() > 2000) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_3));
        }

        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
    }

    private ArrayList<FoodModel> getFood() {
        ArrayList<FoodModel> food = new ArrayList<FoodModel>();
        food.add(new FoodModel("1", "เกลือ", 2000, "A_1"));
        food.add(new FoodModel("2", "ซอสเยาะปรุงรสตราแม็กกี้", 400, "A_2"));
        food.add(new FoodModel("3", "ซีอิ้วขาว", 520, "A_3"));
        food.add(new FoodModel("4", "กะปิ", 500, "A_4"));
        food.add(new FoodModel("5", "ซอสเปรี้ยว", 55, "A_5"));
        food.add(new FoodModel("6", "ผงฝู", 340, "A_6"));
        food.add(new FoodModel("7", "ผงชูรส", 500, "A_7"));
        food.add(new FoodModel("8", "ผงปรุงรสตรารสดี", 815, "A_8"));
        food.add(new FoodModel("9", "ซุปก้อน(คนอร์)", 2640, "A_9"));
        food.add(new FoodModel("10", "ซอสมะเขือเทสตราโรซ่า", 50, "A_10"));
        food.add(new FoodModel("11", "เต้าเจี้ยว", 400, "A_11"));
        food.add(new FoodModel("12", "น้ำจิ้มสุกี้", 280, "A_12"));
        food.add(new FoodModel("13", "น้ำจิ้มข้าวมันไก่", 214, "A_13"));
        food.add(new FoodModel("14", "เนยเทียมชนิดเค็ม", 125, "A_14"));
        food.add(new FoodModel("15", "พริกแกงเหลือง", 750, "A_15"));
        food.add(new FoodModel("16", "เต้าหู้ยี้", 555, "A_16"));
        food.add(new FoodModel("17", "น้ำจิ้มไก่", 210, "A_17"));
        food.add(new FoodModel("18", "ปลาร้า", 2000, "A_18"));
        food.add(new FoodModel("19", "น้ำพริกเผา", 410, "A_19"));
        food.add(new FoodModel("20", "น้ำำริกตาแดง", 560, "A_20"));
        food.add(new FoodModel("21", "ซุปผงคนอร์", 857, "A_21"));
        food.add(new FoodModel("22", "ซีอิ้วดำ", 175, "A_22"));
        food.add(new FoodModel("23", "น้ำปลาตราหอยหลอด", 540, "A_23"));
        food.add(new FoodModel("24", "น้ำปลาตราปลาหมึก", 413, "A_24"));
        food.add(new FoodModel("25", "น้ำปลาตราทิพรส", 390, "A_25"));
        food.add(new FoodModel("26", "น้ำปลาตรารวมรส", 400, "A_26"));
        food.add(new FoodModel("27", "ซอสพริกตราภูเขา", 107, "A_27"));
        food.add(new FoodModel("28", "ซอสหอยนางรมตราแม่ครัว", 0, "A_28"));
        food.add(new FoodModel("29", "ซอสตราภูเขาทอง", 403, "A_29"));
        food.add(new FoodModel("30", "น้ำกะทิกล่อง", 4000, "A_30"));
        food.add(new FoodModel("31", "แป้งโกกิ", 1400, "A_31"));
        return food;
    }
}
