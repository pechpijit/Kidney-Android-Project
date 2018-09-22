package com.khiancode.kidney;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.khiancode.kidney.adapter.AdapterFoodList;
import com.khiancode.kidney.adapter.AdapterFoodList2;
import com.khiancode.kidney.model.Food2Model;
import com.khiancode.kidney.model.FoodModel;

import java.util.ArrayList;


public class ListFoodActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private AdapterFoodList2 adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListFood(getFoodA(), "food_e");
    }

    private void setListFood(final ArrayList<Food2Model> food, final String imfPath) {
        recyclerView = findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new AdapterFoodList2(this, food, imfPath);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new AdapterFoodList2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }

    private ArrayList<Food2Model> getFoodA() {
        ArrayList<Food2Model> food = new ArrayList<Food2Model>();
        food.add(new Food2Model("1", "แคนตาลูป 5 ชิ้น", "6.4", 0, "E_1"));
        food.add(new Food2Model("2", "เงาะ 4 ผล", "12.9", 0, "E_2"));
        food.add(new Food2Model("3", "ลูกพลับ 1 ผล", "25.0", 0, "E_3"));
        food.add(new Food2Model("4", "มะขามหวาน 4 ผล", "30.3", 0, "E_4"));
        food.add(new Food2Model("5", "น้อยหน่าเนื้อ 1 ผล", "13.4", 0, "E_5"));
        food.add(new Food2Model("6", "ขนุน 2 ยวง", "12.9", 0, "E_6"));
        food.add(new Food2Model("7", "อ้อย 1 ชิ้น", "3.4", 0, "E_7"));
        food.add(new Food2Model("8", "สละ 1 ผล", "3.2", 0, "E_8"));
        food.add(new Food2Model("9", "ส้มจีน 1 ผล", "3.2", 0, "E_9"));
        food.add(new Food2Model("10", "ลองกอง 5 ผล", "12.8", 0, "E_10"));
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










































