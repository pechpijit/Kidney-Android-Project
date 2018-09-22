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

public class MeatDiskActivity extends BaseActivity {

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
        setContentView(R.layout.activity_meat_disk);
        ButterKnife.bind(this);

        pieView.setMainBackgroundColor(getResources().getColor(R.color.white20));
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        pieView.setMaxPercentage(100);
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
        updateView();
        setListFood(getFood(), "food_c", 30, "ช้อนโต๊ะ");

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
            setListFood(getFood(), "food_c", 30, "กรัม");
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
        setListFood(foodMake, "food_c", 30, "กรัม");
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

        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheets_list_sodium, null);
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
        txtWeightValue.setText(df.format(v) + " " + n);
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
                    ToastShow(MeatDiskActivity.this, "ถึงปริมาณโซเดียมสูงสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }
                prefUtils.setSodiumValue(prefUtils.getSodiumValue() + sv);
                txtSodiumValue.setText("ปริมาณโซเดียมปัจจุบัน : " + df.format(prefUtils.getSodiumValue()) + " มิลลิกรัม");
                ToastShow(MeatDiskActivity.this, "อัพเดทปริมาณโซเดียม");
                updateView();
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sum <= 0) {
                    ToastShow(MeatDiskActivity.this, "ถึงจำนวนต่ำสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }

                float a = w / 2;
                v -= a;
                String s = String.valueOf(v);
                if (s.substring(s.length() - 2).equals(".0")) {
                    txtWeightValue.setText(df.format(v) + " " + n);
                    txtWeight.setText("ปริมาณ : " + df.format(v) + " " + n);
                } else {
                    txtWeightValue.setText(v + " " + n);
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
                    ToastShow(MeatDiskActivity.this, "ถึงจำนวนสูงสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }

                float a1 = w / 2;
                v += a1;
                String s1 = String.valueOf(v);
                if (s1.substring(s1.length() - 2).equals(".0")) {
                    txtWeightValue.setText(df.format(v) + " " + n);
                    txtWeight.setText("ปริมาณ : " + df.format(v) + " " + n);
                } else {
                    txtWeightValue.setText(v + " " + n);
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
        food.add(new FoodModel("1", "เนื้อหมูสุก", 107, "C_1"));
        food.add(new FoodModel("2", "เนื้อปลาน้ำจืดสุก", 17, "C_2"));
        food.add(new FoodModel("3", "เนื้อปลาน้ำเค็มสุก", 28, "C_3"));
        food.add(new FoodModel("4", "เนื้อไก่สุก", 32, "C_4"));
        food.add(new FoodModel("5", "ปลาหมึกสุก", 76, "C_5"));
        food.add(new FoodModel("6", "ปลาหมึกอบ", 862, "C_6"));
        food.add(new FoodModel("7", "ไข่ไก่สุก", 107, "C_7"));
        food.add(new FoodModel("8", "ลูกชิ้นหมูสุก", 200, "C_8"));
        food.add(new FoodModel("9", "ลูกชิ้นหมู", 640, "C_9"));
        food.add(new FoodModel("10", "เนื้อกุ้งสุก", 207, "C_10"));
        food.add(new FoodModel("11", "กุ้งสด", 400, "C_11"));
        food.add(new FoodModel("12", "กุ้งก้ามกรามสุก", 1400, "C_12"));
        food.add(new FoodModel("13", "กุ้งก้ามกรามสด", 300, "C_13"));
        food.add(new FoodModel("14", "หมูยอสุก", 227, "C_14"));
        food.add(new FoodModel("15", "หมูยอ", 114, "C_15"));
        food.add(new FoodModel("16", "ปลาทูทอด", 305, "C_16"));
        food.add(new FoodModel("17", "หมูแฮมสุก", 356, "C_17"));
        food.add(new FoodModel("18", "แฮมรมควันดิบ", 2530, "C_18"));
        food.add(new FoodModel("19", "แฮมเค็มต้ม", 350, "C_19"));
        food.add(new FoodModel("20", "ไส้กรอกหมูสุก (ฮอทด็อก)", 350, "C_20"));
        food.add(new FoodModel("21", "เบคอนทอด", 168, "C_21"));
        food.add(new FoodModel("22", "ปลาอินทรีย์เค็ม", 3200, "C_22"));
        food.add(new FoodModel("23", "ปลาอินทรีย์เค็มในน้ำมัน", 5642, "C_23"));
        food.add(new FoodModel("24", "ปลาส้มตัวใหญ่ ทอด", 1989, "C_24"));
        food.add(new FoodModel("25", "ปลาส้มตัวเล็ก กินดิบ", 1084, "C_25"));
        food.add(new FoodModel("26", "เนื้อปลานิลเผาเกลือ", 350, "C_26"));
        food.add(new FoodModel("27", "ปลาทูเค็ม", 4772, "C_27"));
        food.add(new FoodModel("28", "ปลาช่อนทะเลแห้งเค็ม", 2396, "C_28"));
        food.add(new FoodModel("29", "ปลาไหลรมควัน", 800, "C_29"));
        food.add(new FoodModel("30", "กุ้งแห้งมีเปลือก", 3240, "C_30"));
        food.add(new FoodModel("31", "ไส้กรอกหมู(ค็อกเทล)", 0, "C_31"));
        food.add(new FoodModel("32", "กุนเชียงหมู", 350, "C_32"));
        food.add(new FoodModel("33", "โบโลน่าหมู", 410, "C_33"));
        food.add(new FoodModel("34", "แหนมย่าง", 480, "C_34"));
        food.add(new FoodModel("35", "ไข่เค็ม", 450, "C_35"));
        food.add(new FoodModel("36", "ไข่เยี่ยวม้า", 280, "C_36"));
        food.add(new FoodModel("37", "หมูแผ่น", 862, "C_37"));
        food.add(new FoodModel("38", "ปลาทูน่ากระป๋อง", 360, "C_38"));
        food.add(new FoodModel("39", "เนื้อแกะ", 90, "C_39"));
        food.add(new FoodModel("40", "เนื้อซี่โครงแกะ", 78, "C_40"));
        food.add(new FoodModel("41", "ตับหมู", 77, "C_41"));
        food.add(new FoodModel("42", "หมูสับ", 62, "C_42"));
        food.add(new FoodModel("43", "เนื้อซี่โครงหมู", 0, "C_43"));
        food.add(new FoodModel("44", "ห่าน", 85, "C_44"));
        food.add(new FoodModel("45", "ไก่", 80, "C_45"));
        food.add(new FoodModel("46", "ไก่งวง", 66, "C_46"));
        food.add(new FoodModel("47", "ตับห่าน", 140, "C_47"));
        food.add(new FoodModel("48", "เนื้อเป็ดโดยไม่ต้องไขมัน", 85, "C_48"));
        food.add(new FoodModel("49", "ปลาทู", 140, "C_49"));
        food.add(new FoodModel("50", "ปลาแซลมอนสด", 50, "C_50"));
        return food;
    }
}
