package com.example.caronrentrenter.Multiple_Image;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.caronrentrenter.R;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<ImageModel> imageList;

    public ImageAdapter(List<ImageModel> imageList) {
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
        ImageModel imageModel = imageList.get(position);

        Glide.with(holder.imageView.getContext())
                .load(imageModel.getImageUrl())
                .into(holder.imageView);

        // Modify the code to display other details as needed
        if (holder.priceTextView != null && holder.quantityTextView != null) {
            // holder.priceTextView.setText("Price: $" + imageModel.getPrice());
            // holder.quantityTextView.setText("Quantity: " + imageModel.getQuantity());
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
