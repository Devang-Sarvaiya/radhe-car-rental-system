package com.example.car_admin.Driver;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.car_admin.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class D_ItemAdapter extends RecyclerView.Adapter<D_ItemAdapter.ViewHolder> {
    ArrayList<ImageModel_1_driver> dataList;
    DecimalFormat formatter;
    Context context;

    public D_ItemAdapter() {
    }

    public D_ItemAdapter(Context context, ArrayList<ImageModel_1_driver> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.driver_item_view_holder, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getImageURLUser()).into(holder.pic);
        holder.title.setText(dataList.get(position).getName());
        holder.address.setText(dataList.get(position).getEmail());
        holder.price.setText(dataList.get(position).getMobile_dr());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),Detail_driver.class);
                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                Toast.makeText(context, "Lee Bro !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
        }
    }

}

