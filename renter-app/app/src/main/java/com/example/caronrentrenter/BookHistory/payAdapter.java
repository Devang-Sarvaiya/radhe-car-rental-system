package com.example.caronrentrenter.BookHistory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.R;
import com.example.caronrentrenter.Payment.payAdmin;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class payAdapter extends RecyclerView.Adapter<payAdapter.ViewHolder> {
    ArrayList<payAdmin> dataList;
    DecimalFormat formatter;
    Context context;

    public payAdapter() {
    }

    public payAdapter(Context context, ArrayList<payAdmin> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.payment_place_holder, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getModelImageUrl()).into(holder.pic);
        holder.title.setText("Booked Successfully");
        holder.address.setText(dataList.get(position).getCarModelName());
        holder.price.setText(dataList.get(position).getTotalAmount());
        holder.start_date.setText(dataList.get(position).getStartDate());
        holder.end_date.setText(dataList.get(position).getEndDate());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), payment_confirm_deny.class);
//                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
//                context.startActivity(intent);
//                Toast.makeText(context, "Lee Bro !!!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price, start_date, end_date;
        ImageView pic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);

        }
    }
}