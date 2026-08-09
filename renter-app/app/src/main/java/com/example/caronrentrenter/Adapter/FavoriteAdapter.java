package com.example.caronrentrenter.Adapter;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>{

    String name;


    private ArrayList<DataClass> dataList;
    private Context context;
    public FavoriteAdapter(Context context, ArrayList<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new FavoriteAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);

        holder.recyclerCaption.setText(dataList.get(position).getModelName());
        holder.recyclerPrice.setText(dataList.get(position).getMaximumSpeed());
        holder.recyclerTime.setText(dataList.get(position).getRentPerDay());
        holder.recyclertotalseat.setText(dataList.get(position).getNumberPassengers());

//        String a = dataList.get(position).getPrice();
//        holder.recyclerPrice.setText("₹"+a);
//
//
//        holder.recyclerScore.setText(dataList.get(position).getScore());
//
//        String t = String.valueOf(dataList.get(position).getTime());
//        holder.recyclerTime.setText(t + "min");
//        holder.recyclerTime.setText(dataList.get(position).getPrice());
//        holder.recyclerPrice.setText(dataList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(holder.itemView.getContext(), Detail.class);
                intent.putExtra("object1",dataList.get(holder.getAdapterPosition()));

                holder.itemView.getContext().startActivity(intent);
            }
        });

        /*holder.recyclerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String ur = object.getImageURL().toString();
                String ur = dataList.get(position).getImageURL().toString();
                // Convert string URL to Uri
                Uri actualImageUrl = Uri.parse(ur);


//                String ab = object.getTitle().toString();
                String ab = dataList.get(position).getTitle().toString();
//                String pr = object.getPrice();
                String pr = dataList.get(position).getPrice().toString();
//                String sc = object.getScore();
                String sc = dataList.get(position).getScore().toString();

                name = "DK";
//                name = getIntent().getStringExtra("un");





                // Get reference to the new subfolder where you want to store the actual URL
                DatabaseReference newSubfolderRef = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("imageURL");
                DatabaseReference newSubfolderRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("title");
                DatabaseReference newSubfolderRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("price");
                DatabaseReference newSubfolderRef3 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("score");

                // Store the actual URL in the new subfolder
                newSubfolderRef.setValue(actualImageUrl.toString());
                newSubfolderRef1.setValue(ab);
                newSubfolderRef2.setValue(pr);
                newSubfolderRef3.setValue(sc);
            }
        });*/



    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchdatalist(ArrayList<DataClass> searchList){
        dataList=searchList;
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImage;
        TextView recyclerCaption,recyclerPrice,recyclerScore,recyclerTime,recyclertotalseat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyclerCaption = itemView.findViewById(R.id.recyclerCaption);
            recyclerPrice = itemView.findViewById(R.id.recyclerPrice1);
//            recyclerScore = itemView.findViewById(R.id.totalEachItem);
            recyclerTime = itemView.findViewById(R.id.totalEachItem1);
            recyclertotalseat=itemView.findViewById(R.id.totalseat);




        }
    }
}
