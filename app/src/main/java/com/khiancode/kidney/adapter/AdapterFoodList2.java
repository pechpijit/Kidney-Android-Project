package com.khiancode.kidney.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khiancode.kidney.R;
import com.khiancode.kidney.model.Food2Model;
import com.khiancode.kidney.model.FoodModel;

import java.util.ArrayList;


public class AdapterFoodList2 extends RecyclerView.Adapter<AdapterFoodList2.VersionViewHolder> {
    ArrayList<Food2Model> model;
    Context context;
    OnItemClickListener clickListener;
    String imfPath;
    public AdapterFoodList2(Context applicationContext, ArrayList<Food2Model> model, String imfPath) {
        this.context = applicationContext;
        this.model = model;
        this.imfPath = imfPath;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_food, viewGroup, false);
        return new VersionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
        String img = "file:///android_asset/" + imfPath + "/" + model.get(i).getPicture()+".jpg";
        Log.d("image", img);
        versionViewHolder.txtName.setText(model.get(i).getName());
        versionViewHolder.txtSodium.setText("ปริมาณน้ำตาล : "+model.get(i).getEat()+" กรัม");
        Glide.with(context)
                .load(Uri.parse(img))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .error(R.drawable.placeholder)
                .into(versionViewHolder.imgFood);
    }

    @Override
    public int getItemCount() {
        return model == null ? 0 : model.size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName,txtSodium;
        ImageView imgFood;
        public VersionViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtSodium = itemView.findViewById(R.id.txtSodium);
            imgFood = itemView.findViewById(R.id.imgFood);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
