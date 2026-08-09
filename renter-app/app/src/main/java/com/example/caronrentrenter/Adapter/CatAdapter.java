package com.example.caronrentrenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Car_Menu;
import com.example.caronrentrenter.Cat_menu;
import com.example.caronrentrenter.DataClass1;
import com.example.caronrentrenter.Detail;
import com.example.caronrentrenter.R;
import com.example.caronrentrenter.ReadWriteUserDetails;

import java.text.DecimalFormat;
import java.util.ArrayList;

// CatAdapter.java
public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    ArrayList<DataClass1> dataList3;
    Context context2;

    public CatAdapter(Context context2, ArrayList<DataClass1> dataList3) {
        this.context2 = context2;
        this.dataList3 = dataList3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context2).inflate(R.layout.cat_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Display only car type in the viewCat RecyclerView
        holder.title.setText(dataList3.get(position).getCarType());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(), Cat_menu.class);
//                intent.putExtra("object",item.get(position));
//                intent.putExtra("object1",dataList3.get(holder.getAdapterPosition()));
                intent.putExtra("obj",dataList3.get(holder.getAdapterPosition()));
                context2.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataList3.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtCat);
        }
    }
}
