package com.khiancode.kidney;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

public class FoodDiskActivity extends BaseActivity {

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
    @BindView(R.id.imgTai)
    ImageView imgTai;
    String TAG = "FoodDiskActivity";

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
        setContentView(R.layout.activity_food_disk);
        ButterKnife.bind(this);

        pieView.setMainBackgroundColor(getResources().getColor(R.color.white20));
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        pieView.setMaxPercentage(100);
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
        updateView();
        setListFood(getFood(), "food_b", 1, "จาน");


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
            setListFood(getFood(), "food_b", 1, "จาน");
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
        setListFood(foodMake, "food_b", 1, "จาน");
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
                    ToastShow(FoodDiskActivity.this, "ถึงปริมาณโซเดียมสูงสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }
                prefUtils.setSodiumValue(prefUtils.getSodiumValue() + sv);
                txtSodiumValue.setText("ปริมาณโซเดียมปัจจุบัน : " + df.format(prefUtils.getSodiumValue()) + " มิลลิกรัม");
                ToastShow(FoodDiskActivity.this, "อัพเดทปริมาณโซเดียม");
                updateView();
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sum <= 0) {
                    ToastShow(FoodDiskActivity.this, "ถึงจำนวนต่ำสุดที่ระบบจำกัดไว้แล้ว");
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
                    ToastShow(FoodDiskActivity.this, "ถึงจำนวนสูงสุดที่ระบบจำกัดไว้แล้ว");
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

            float per = (utils.getSodiumValue() / 2000) * 100;
            pieView.setPercentage((int) per);
        }

        if (utils.getSodiumValue() == 0){
            txtSodiumValue.setText("0 มิลลิกรัม");
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_4));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.smile_icon_48));
            pieView.setPercentage(100);
        } else if (utils.getSodiumValue() >= 0 && utils.getSodiumValue() <= 1800) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.smile_icon_48));
        } else if (utils.getSodiumValue() >= 1801 && utils.getSodiumValue() <= 1999) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_2));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.sarah_icon_48));
        } else if (utils.getSodiumValue() >= 2000) {
            pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_3));
            imgTai.setImageDrawable(getResources().getDrawable(R.drawable.icon_tai_over_48));
        }
        pieView.setInnerBackgroundColor(getResources().getColor(R.color.white));
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
    }

    private ArrayList<FoodModel> getFood() {
        ArrayList<FoodModel> food = new ArrayList<FoodModel>();
        food.add(new FoodModel("1", "ข้าวไข่เจียว", 362, "B_1"));
        food.add(new FoodModel("2", "ข้าวผัดหมู", 613, "B_2"));
        food.add(new FoodModel("3", "ข้าวหมูแดง", 812, "B_3"));
        food.add(new FoodModel("4", "ข้าวไข่พะโล้", 900, "B_4"));
        food.add(new FoodModel("5", "ข้าวหมกไก่", 988, "B_5"));
        food.add(new FoodModel("6", "ข้าวหน้าเป็ด", 1020, "B_6"));
        food.add(new FoodModel("7", "เส้นใหญ่ราดหน้ากุ้ง", 807, "B_7"));
        food.add(new FoodModel("8", "บะหมี่กึ่งสำเร็จรูป", 1954, "B_8"));
        food.add(new FoodModel("9", "ส้มตำอีสาน", 1006, "B_9"));
        food.add(new FoodModel("10", "กระเพาะปลาน้ำแดง", 1450, "B_10"));
        food.add(new FoodModel("11", "ก๋วยจั๊บ", 1204, "B_11"));
        food.add(new FoodModel("12", "ก๋วยเตี๋ยวเส้นเล็กแห้งหมู", 1076, "B_12"));
        food.add(new FoodModel("13", "ก๋วยเตี๋ยวเส้นใหญ่ผัดซีอิ๊ว", 1242, "B_13"));
        food.add(new FoodModel("14", "ก๋วยเตี๋ยวเส้นใหญ่ราดหน้า", 552, "B_14"));
        food.add(new FoodModel("15", "ก๋วยเตี๋ยวแห้งลูกชิ้นปลา", 1107, "B_15"));
        food.add(new FoodModel("16", "ขนมจีนซาวข้าว", 775, "B_16"));
        food.add(new FoodModel("17", "ขนมจีนน้ำพริก", 655, "B_17"));
        food.add(new FoodModel("18", "ขนมจีนน้ำยา", 1525, "B_18"));
        food.add(new FoodModel("19", "ขนมจีนน้ำยาปักษ์ใต้", 1259, "B_19"));
        food.add(new FoodModel("20", "ขนมจีนน้ำเงี้ยว", 1128, "B_20"));
        food.add(new FoodModel("21", "ขนมปังหน้ากุ้ง", 410, "B_21"));
        food.add(new FoodModel("22", "ข้าวคลุกกะปิ", 1412, "B_22"));
        food.add(new FoodModel("23", "ข้าวต้ม", 755, "B_23"));
        food.add(new FoodModel("24", "ข้าวต้มปลา", 805, "B_24"));
        food.add(new FoodModel("25", "ข้าวต้มไก่", 855, "B_25"));
        food.add(new FoodModel("26", "ข้าวผัดหมูใส่ไข่", 1257, "B_26"));
        food.add(new FoodModel("27", "ข้าวผัดฮ่องกง", 1186, "B_27"));
        food.add(new FoodModel("28", "ข้าวมันไก่ทอด", 934, "B_28"));
        food.add(new FoodModel("29", "ข้าวยำปักษ์ใต้", 1090, "B_29"));
        food.add(new FoodModel("30", "ข้าวราดต้มข่าไก่", 988, "B_30"));
        food.add(new FoodModel("31", "ข้าวราดต้มยำกุ้ง+หมูทอด", 865, "B_31"));
        food.add(new FoodModel("32", "ข้าวราดน้ำพริกกะปิผักต้ม", 1122, "B_32"));
        food.add(new FoodModel("33", "ข้าวรากน้ำพริกอ่อง+ผัก+ไข่ต้ม", 1890, "B_33"));
        food.add(new FoodModel("34", "ข้าวราดผัดสะตอ", 682, "B_34"));
        food.add(new FoodModel("35", "ข้าวราดผัดเปรี้ยวหวาน", 673, "B_35"));
        food.add(new FoodModel("36", "ข้าวราดพะแนงไก่", 641, "B_36"));
        food.add(new FoodModel("37", "ข้าวราดสะเดาน้ำปลาหวาน+ปลาดุกย่าง", 474, "B_37"));
        food.add(new FoodModel("38", "ข้าวราดแกงป่าปลาดุก", 1829, "B_38"));
        food.add(new FoodModel("39", "ข้าวราดแกงมัสมั่นเนื้อ", 790, "B_39"));
        food.add(new FoodModel("40", "ข้าวราดแกงส้มมะละกอ+ไข่เจียว", 822, "B_40"));
        food.add(new FoodModel("41", "ข้าวราดแกงเลียง+หมูทอด", 685, "B_41"));
        food.add(new FoodModel("42", "ข้าวราดไก่ผัดใบกะเพรา", 1299, "B_42"));
        food.add(new FoodModel("43", "ข้าวหน้าหมู", 1154, "B_43"));
        food.add(new FoodModel("44", "ข้าวหน้าเนื้อ", 1176, "B_44"));
        food.add(new FoodModel("45", "ข้าวหน้าเป็ดย่าง", 1231, "B_45"));
        food.add(new FoodModel("46", "ข้าวหน้าไก่", 1066, "B_46"));
        food.add(new FoodModel("47", "ข้าวหมกไก่", 1018, "B_47"));
        food.add(new FoodModel("48", "ข้าวหมูกรอบ", 700, "B_48"));
        food.add(new FoodModel("49", "ข้าวหมูทอด", 682, "B_49"));
        food.add(new FoodModel("50", "ข้าวหมูแดง", 909, "B_50"));

        return food;
    }

}


