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
import com.example.caronrentrenter.DataClass;
import com.example.caronrentrenter.Detail;
import com.example.caronrentrenter.R;
import com.example.caronrentrenter.ReadWriteUserDetails;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RenterAdapter extends RecyclerView.Adapter<RenterAdapter.ViewHolder> {
    ArrayList<DataClass> dataList;
    ArrayList<ReadWriteUserDetails> dataList1;

    DecimalFormat formatter;
    Context context1;

    public RenterAdapter() {
    }

//    public RenterAdapter(ArrayList<ItemDomain> item) {
//        this.item = item;
//        formatter=new DecimalFormat("###,###,###,###.##");
//    }

//    public RenterAdapter(Context context, ArrayList<DataClass> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }

    public RenterAdapter(Context context1, ArrayList<ReadWriteUserDetails> dataList1) {
        this.context1 = context1;
        this.dataList1 = dataList1;
    }
   /* @NonNull
    @Override
    public RenterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(context).inflate(R.layout.item_view_holder1,parent,false);
//        context=parent.getContext();
        return new RenterAdapter.ViewHolder(inflate);
    }

    */

    @NonNull
    @Override
    public RenterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(context1).inflate(R.layout.item_view_holder,parent,false);
//        context=parent.getContext();
        return new RenterAdapter.ViewHolder(inflate);
    }



    @Override
    public void onBindViewHolder(@NonNull RenterAdapter.ViewHolder holder1,  int position) {

        /*
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder1.pic);
        holder1.title.setText(dataList.get(position).getModelName());
        holder1.address.setText(dataList.get(position).getModelDescription());
        holder1.price.setText(dataList.get(position).getMaximumSpeed());
        */


        Glide.with(context1).load(dataList1.get(position).getImageURLUser()).into(holder1.pic);
        holder1.title.setText(dataList1.get(position).getName());
        holder1.address.setText(dataList1.get(position).getEmail());
        holder1.price.setText(dataList1.get(position).getMobile());

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(holder1.itemView.getContext(), Detail.class);
//                intent1.putExtra("object",item.get(position));
                intent1.putExtra("no",dataList1.get(holder1.getAdapterPosition()));
                context1.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return item.size();
        return dataList1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,address,price;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            address=itemView.findViewById(R.id.address);
            price=itemView.findViewById(R.id.price);

            pic=itemView.findViewById(R.id.pic);
        }
    }

}



