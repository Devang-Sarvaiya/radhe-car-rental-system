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
import com.example.caronrentrenter.Detail;
import com.example.caronrentrenter.DetailClass;
import com.example.caronrentrenter.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

//public class CarDetailAdapter extends RecyclerView.Adapter<CarDetailAdapter.ViewHolder> {
public class CarDetailAdapter  {
    //    ArrayList<ItemDomain> item;
//    ArrayList<DetailClass> dataList;
//    DecimalFormat formatter;
//    Context context;
//
//    public CarDetailAdapter() {
//    }
//
////    public CarDetailAdapter(ArrayList<ItemDomain> item) {
////        this.item = item;
////        formatter=new DecimalFormat("###,###,###,###.##");
////    }
//
//    public CarDetailAdapter(Context context, ArrayList<DetailClass> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }
//    @NonNull
//    @Override
//    public CarDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate= LayoutInflater.from(context).inflate(R.layout.car_detail_show,parent,false);
////        context=parent.getContext();
//        return new CarDetailAdapter.ViewHolder(inflate);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
////        holder.title.setText(item.get(position).getTitle());
////        holder.address.setText(item.get(position).getAddress());
////        //holder.price.setText(item.get(position).getPrice());
////        holder.price.setText(formatter.format(item.get(position).getPrice()));
//
////        int drawableResourceId=holder.itemView.getResources().getIdentifier(item.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
//
//
//        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.pic);
////        holder.title.setText(dataList.get(position).getModelName());
////        holder.address.setText(dataList.get(position).getModelDescription());
////        holder.price.setText(dataList.get(position).getMaximumSpeed());
//
//
////        Glide.with(holder.itemView.getContext())
////                .load(drawableResourceId)
////                .into(holder.pic);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(holder.itemView.getContext(), Detail.class);
////                intent.putExtra("object",item.get(position));
//                intent.putExtra("object",dataList.get(holder.getPosition()));
////               holder.itemView.getContext().startActivity(intent);
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
////        return item.size();
//        return dataList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView title,address,price;
//        ImageView pic;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
////            title=itemView.findViewById(R.id.title);
////            address=itemView.findViewById(R.id.address);
////            price=itemView.findViewById(R.id.price);
//
//            pic=itemView.findViewById(R.id.pic);
//        }
//    }

}
