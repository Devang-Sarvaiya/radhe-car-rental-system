package com.example.caronrent.E_commerce;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrent.E_commerce.ImageModel_e_com;
import com.example.caronrent.R;

import java.util.List;

public class ImgeAdapter_e_com extends RecyclerView.Adapter<ImgeAdapter_e_com.ViewHolder> {
    private List<ImageModel_e_com> imageList;

    public ImgeAdapter_e_com(List<ImageModel_e_com> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageModel_e_com ImageModel_e_com = imageList.get(position);

        Glide.with(holder.imageView.getContext())
                .load(ImageModel_e_com.getImageUrl())
                .into(holder.imageView);

        // Modify the code to display other details as needed
        if (holder.priceTextView != null && holder.quantityTextView != null) {
            // holder.priceTextView.setText("Price: $" + ImageModel_e_com.getPrice());
            // holder.quantityTextView.setText("Quantity: " + ImageModel_e_com.getQuantity());
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView priceTextView;
        TextView quantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            // Add references to other TextViews if needed
            // priceTextView = itemView.findViewById(R.id.edtPrice);
            // quantityTextView = itemView.findViewById(R.id.edtQuantity);
        }
    }
}
