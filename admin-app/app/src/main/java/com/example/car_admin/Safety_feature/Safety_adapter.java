package com.example.car_admin.Safety_feature;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.car_admin.R;
import com.example.car_admin.Tourism.Tourism_Place_Details;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class Safety_adapter extends RecyclerView.Adapter<Safety_adapter.MyViewHolder>{
    ArrayList<SafetyClass> dataList;
    Context context;

    public Safety_adapter(ArrayList<SafetyClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staggere_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getPlaceImageUrl()).into(holder.staggeredImages);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Tourism_Place_Details.class);
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
    public class MyViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView staggeredImages;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            staggeredImages = itemView.findViewById(R.id.staggeredImages);
        }
    }
}
