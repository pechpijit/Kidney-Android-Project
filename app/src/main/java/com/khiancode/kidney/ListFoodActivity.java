package com.khiancode.kidney;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiancode.kidney.adapter.AdapterFoodList;
import com.khiancode.kidney.helper.PrefUtils;
import com.khiancode.kidney.model.FoodModel;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ListFoodActivity extends BaseActivity {
    private RecyclerView recyclerView;
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
        setContentView(R.layout.activity_list_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int ID = getIntent().getExtras().getInt(KEY_FOOD_ID, 0);
        setTitleBar(ID);
    }

    private void setTitleBar(int ID) {

        if (ID == 1) {
            getSupportActionBar().setTitle("หมวดเครื่องปรุง");
            setListFood(getFoodA(), "food_a", 1, "ช้อนชา");
        } else if (ID == 2) {
            getSupportActionBar().setTitle("หมวดเนื้อสัตว์");
            setListFood(getFoodB(), "food_c", 30, "กรัม");
        } else if (ID == 3) {
            getSupportActionBar().setTitle("หมวดอาหารจานเดียว");
        } else if (ID == 4) {
            getSupportActionBar().setTitle("หมวดเครื่องดื่ม");
        }
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

    @SuppressLint("SetTextI18n")
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
                    ToastShow(ListFoodActivity.this, "ถึงปริมาณโซเดียมสูงสุดที่ระบบจำกัดไว้แล้ว");
                    return;
                }
                prefUtils.setSodiumValue(prefUtils.getSodiumValue() + sv);
                txtSodiumValue.setText("ปริมาณโซเดียมปัจจุบัน : " + df.format(prefUtils.getSodiumValue()) + " มิลลิกรัม");
                ToastShow(ListFoodActivity.this, "อัพเดทปริมาณโซเดียม");
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sum <= 0) {
                    ToastShow(ListFoodActivity.this, "ถึงจำนวนต่ำสุดที่ระบบจำกัดไว้แล้ว");
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
                    ToastShow(ListFoodActivity.this, "ถึงจำนวนสูงสุดที่ระบบจำกัดไว้แล้ว");
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

    private ArrayList<FoodModel> getFoodA() {
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

    private ArrayList<FoodModel> getFoodB() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}










































