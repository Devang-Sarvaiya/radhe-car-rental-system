package com.example.caronrent.E_commerce;

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
import com.example.caronrent.BookingHistory.bookAdapter;
import com.example.caronrent.BookingHistory.bookAdmin;
import com.example.caronrent.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class order_adapter extends RecyclerView.Adapter<order_adapter.ViewHolder> {
    ArrayList<order_Gadget> dataList;
    DecimalFormat formatter;
    Context context;

    public order_adapter() {
    }

    public order_adapter(Context context, ArrayList<order_Gadget> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.gadget_holder_detail, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getGadgetImage()).into(holder.pic);
        holder.title.setText("Order Successfully Done");
        holder.address.setText(dataList.get(position).getGadgetName());
        holder.price.setText(dataList.get(position).getTotalAmount());
        holder.start_date.setText(dataList.get(position).getDeliveryDay());
        holder.end_date.setText(dataList.get(position).getReplaceDay());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), payment_confirm_deny.class);
//                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
//                context.startActivity(intent);
//                Toast.makeText(context, "Lee Bro !!!", Toast.LENGTH_SHORT).show();
//            }
//        });
////
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchdatalist(ArrayList<order_Gadget> searchList){
        dataList=searchList;
        notifyDataSetChanged();
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