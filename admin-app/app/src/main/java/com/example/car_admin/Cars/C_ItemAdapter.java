package com.example.car_admin.Cars;

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
import com.example.car_admin.PaymentHistory.payAdmin;
import com.example.car_admin.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class C_ItemAdapter extends RecyclerView.Adapter<C_ItemAdapter.ViewHolder> {
    ArrayList<ImageModel1> dataList;
    DecimalFormat formatter;
    Context context;

    public C_ItemAdapter() {
    }

    public C_ItemAdapter(Context context, ArrayList<ImageModel1> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.car_item_view_holder, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.pic);
        holder.title.setText(dataList.get(position).getModelName());
        holder.address.setText(": "+dataList.get(position).getCarCompany());
        holder.price.setText(": "+dataList.get(position).getRentPerDay());
        holder.rMO.setText(": "+dataList.get(position).getRenterMobile());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Detail_of_car.class);
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

    public ImageModel1 getItem(int position) {
        return dataList.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price,rMO;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            rMO = itemView.findViewById(R.id.rMO);
        }
    }

}

