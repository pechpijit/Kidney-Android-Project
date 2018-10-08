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
import com.khiancode.kidney.adapter.AdapterFoodList3;
import com.khiancode.kidney.helper.PrefUtils;
import com.khiancode.kidney.model.Food3Model;
import com.khiancode.kidney.model.FoodModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DrinkDiskActivity extends BaseActivity {

    @BindView(R.id.pieView)
    PieView pieView;
    @BindView(R.id.dummyfrag_scrollableview)
    RecyclerView recyclerView;
    @BindView(R.id.txtSodiumValue)
    TextView txtSodiumValue;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.imgTai)
    ImageView imgTai;
    @BindView(R.id.inputSearch)
    EditText inputSearch;

    String TAG = "DrinkDiskActivity";

    private PieAngleAnimation animation;
    private AdapterFoodList3 adapter;

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
        setContentView(R.layout.activity_drink_disk);
        ButterKnife.bind(this);

        pieView.setMainBackgroundColor(getResources().getColor(R.color.white20));
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.color_percen_1));
        pieView.setMaxPercentage(100);
        animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000);
        pieView.startAnimation(animation);
        updateView();
        setListFood(getFood(), "food_d", 1);

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
            setListFood(getFood(), "food_d", 1);
            return;
        }

        ArrayList<Food3Model> food = getFood();
        ArrayList<Food3Model> foodMake = new ArrayList<Food3Model>();

//        int nStr = search.length();


        for (Food3Model model:food) {
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
        setListFood(foodMake, "food_d", 1);
    }

    private void setListFood(final ArrayList<Food3Model> food, final String imfPath, final float w) {
        recyclerView = findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new AdapterFoodList3(this, food, imfPath);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new AdapterFoodList3.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setBottomSheet(food.get(position), imfPath, w);
            }
        });
    }

    float v;
    float sv;
    int sum = 0;
    DecimalFormat df;
    PrefUtils prefUtils;

    private void setBottomSheet(final Food3Model foodModel, String imfPath, final float w) {
        v = w;
        sv = foodModel.getSodium();
        sum = 1;
        final String n = foodModel.getUnit();
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
                    ToastShow(DrinkDiskActivity.this, "ถึงปริมาณโซเดียมสูงสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }
                prefUtils.setSodiumValue(prefUtils.getSodiumValue() + sv);
                txtSodiumValue.setText("ปริมาณโซเดียมปัจจุบัน : " + df.format(prefUtils.getSodiumValue()) + " มิลลิกรัม");
                ToastShow(DrinkDiskActivity.this, "อัพเดทปริมาณโซเดียม");
                updateView();
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sum <= 0) {
                    ToastShow(DrinkDiskActivity.this, "ถึงจำนวนต่ำสุดที่ระบบจำกัดไว้แล้ว");
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
                    ToastShow(DrinkDiskActivity.this, "ถึงจำนวนสูงสุดที่ระบบจำกัดไว้แล้ว");
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

    private ArrayList<Food3Model> getFood() {
        ArrayList<Food3Model> food = new ArrayList<Food3Model>();
        food.add(new Food3Model("1","สปอนเซอร์","ขวด",220,"D_1"));
        food.add(new Food3Model("2","เนสกาแฟเอ็กซ์ตร้า ริช","กระป๋อง",110,"D_2"));
        food.add(new Food3Model("3","กระทิงแดง","ขวด",160,"D_3"));
        food.add(new Food3Model("4","เนสกาแฟลาเต้","กระป๋อง",100,"D_4"));
        food.add(new Food3Model("5","เอ็ม-150","ขวด",170,"D_5"));
        food.add(new Food3Model("6","เนสกาแฟBlack Roast","กระป๋อง",85,"D_6"));
        food.add(new Food3Model("7","เนสกาแฟEspresso Roast","กระป๋อง",115,"D_7"));
        food.add(new Food3Model("8","น้ำมะพร้าวแท้ ตราโคโค่แม็ก","ขวด",850,"D_8"));
        food.add(new Food3Model("9","นมดีไลท์","ขวด",55,"D_9"));
        food.add(new Food3Model("10","เบอร์ดี้ชาไทย","กระป๋อง",140,"D_10"));
        food.add(new Food3Model("11","นมบีทาเก้น","ขวด",65,"D_11"));
        food.add(new Food3Model("12","นมไมโล","กล่อง",85,"D_12"));
        food.add(new Food3Model("13","นมโอวัลติน","กล่อง",85,"D_13"));
        food.add(new Food3Model("14","นมไวตามิลค์งาดำและข้าวสีนิล","กล่อง",210,"D_14"));
        food.add(new Food3Model("15","นมวัวแดงรสจืด","กล่อง",80,"D_15"));
        food.add(new Food3Model("16","นมไวตามิลค์โลว์ ชูก้าถั่ว","กล่อง",100,"D_16"));
        food.add(new Food3Model("17","นมวัวแดงรสหวาน","กล่อง",80,"D_17"));
        food.add(new Food3Model("18","นมไวตามิลค์โลว์ ชูก้าข้าวโพด","กล่อง",430,"D_18"));
        food.add(new Food3Model("19","นมดีน่ากาบา","กล่อง",75,"D_19"));
        food.add(new Food3Model("20","นมตราหมีเนสเทิลผสมชาขาวกระป๋องสีเหลือง","กระป๋อง",70,"D_20"));
        food.add(new Food3Model("21","นมตราหมีเนสเทิลไวท์มอล","กล่อง",5,"D_21"));
        food.add(new Food3Model("22","นมตราหมีเนสเทิลชาขาวสกัดและโปรตีนถั่วเหลือง","กระป๋อง",85,"D_22"));
        food.add(new Food3Model("23","นมแล็คตาซอยซอยมิล","กล่อง",110,"D_23"));
        food.add(new Food3Model("24","นมแล็คตาซอยรสหวานคลาสสิค","กล่อง",160,"D_24"));
        food.add(new Food3Model("25","น้ำอัดลมแฟนต้า","ขวด",20,"D_25"));
        food.add(new Food3Model("26","น้ำโออิชิน้ำผึ้งมะนาว","ขวด",25,"D_26"));
        food.add(new Food3Model("27","น้ำโออิชิชาเขียว","ขวด",35,"D_27"));
        food.add(new Food3Model("28","น้ำโออิชิBlack Tea","ขวด",30,"D_28"));
        food.add(new Food3Model("29","น้ำส้มแท้มินิท สแปลช","ขวด",95,"D_29"));
        food.add(new Food3Model("30","น้ำส้มแท้มินิท พัลพี","ขวด",55,"D_30"));
        food.add(new Food3Model("31","น้ำโค้ก","ขวด",10,"D_31"));
        food.add(new Food3Model("32","คาราบาวแดง","ขวด",170,"D_32"));
        food.add(new Food3Model("33","น้ำเป๊ปซี่","ขวด",5,"D_33"));
        food.add(new Food3Model("34","น้ำสไปรท์","ขวด",25,"D_34"));
        return food;
    }
}
