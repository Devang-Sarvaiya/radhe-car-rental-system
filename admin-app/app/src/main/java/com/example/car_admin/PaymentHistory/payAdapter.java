package com.example.car_admin.PaymentHistory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.car_admin.DataClass;
import com.example.car_admin.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class payAdapter extends RecyclerView.Adapter<payAdapter.ViewHolder> {
    ArrayList<payAdmin> dataList;
    DecimalFormat formatter;
    Context context;
    DatabaseReference databaseReference;


    public payAdapter() {
    }

    public payAdapter(Context context, ArrayList<payAdmin> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Payment").child("Income").child("Car_book").child("User"); // Replace with your actual database path

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
        holder.title.setText("Contact the Renter by " + dataList.get(position).getRenterMobile());
        holder.address.setText(dataList.get(position).getCarModelName());
        holder.price.setText(dataList.get(position).getTotalAmount());
        holder.start_date.setText(dataList.get(position).getStartDate());
        holder.end_date.setText(dataList.get(position).getEndDate());

        holder.btnConfirm.setEnabled(false);
        holder.btnCancel.setEnabled(false);
        holder.btnConfirm.setVisibility(View.GONE);
        holder.btnCancel.setVisibility(View.GONE);

        String yes = "true";
        String yes1 = dataList.get(position).getIsConfirmed();
        if(yes.equals(yes1)){
            holder.btnConfirm.setVisibility(View.VISIBLE);
            holder.btnConfirm.setText("Done");
            holder.btnConfirm.setEnabled(false);
        }else{
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setText("Cancel");
            holder.btnCancel.setEnabled(false);
        }




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
//        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
//                updateIsConfirmedInFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
//                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot());
//            }
//        });
//
//        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
//                cancelIsConfirmdFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
//                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot());
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public payAdmin getItem(int position) {
        return dataList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price, start_date, end_date;
        ImageView pic;
        Button btnConfirm, btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }

    private void updateIsConfirmedInFirebase(String userMobile, String carModelName,String slot) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child("slot"+slot).child("isConfirmed").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });
    }
    private void cancelIsConfirmdFirebase(String userMobile, String carModelName,String slot) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child("slot"+slot).child("isConfirmed").setValue("false")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, "Confirmation cancel", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to cancel payment", Toast.LENGTH_SHORT).show();
                });
    }
}
//package com.example.car_admin.PaymentHistory;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.car_admin.DataClass;
//import com.example.car_admin.R;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//
//public class payAdapter extends RecyclerView.Adapter<payAdapter.ViewHolder> {
//    ArrayList<payAdmin> dataList;
//    DecimalFormat formatter;
//    Context context;
//    DatabaseReference databaseReference;
//
//
//    public payAdapter() {
//    }
//
//    public payAdapter(Context context, ArrayList<payAdmin> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//        this.databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Payment").child("Income").child("Car_book").child("User"); // Replace with your actual database path
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(context).inflate(R.layout.payment_place_holder, parent, false);
//        return new ViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        Glide.with(context).load(dataList.get(position).getModelImageUrl()).into(holder.pic);
//        holder.title.setText("Contact the Renter by " + dataList.get(position).getRenterMobile());
//        holder.address.setText(dataList.get(position).getCarModelName());
//        holder.price.setText(dataList.get(position).getTotalAmount());
//        holder.start_date.setText(dataList.get(position).getStartDate());
//        holder.end_date.setText(dataList.get(position).getEndDate());
//
//
//
//        String yes = "true";
//        String yes1 = dataList.get(position).getIsConfirmed();
//        String yes2 = dataList.get(position).getIsBooked();
//
//        if(yes.equals(yes1)){
//            holder.btnCancel.setVisibility(View.GONE);
//            holder.btnConfirm.setText("Done");
//            holder.btnConfirm.setEnabled(false);
//        }
//        else if(yes.equals(yes2)){
//            holder.btnConfirm.setVisibility(View.GONE);
//            holder.btnCancel.setText("Canceld");
//            holder.btnCancel.setEnabled(false);
//        }else{
//            holder.btnConfirm.setVisibility(View.VISIBLE);
//            holder.btnCancel.setVisibility(View.VISIBLE);
//        }
//
////        holder.itemView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(holder.itemView.getContext(), payment_confirm_deny.class);
////                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
////                context.startActivity(intent);
////                Toast.makeText(context, "Lee Bro !!!", Toast.LENGTH_SHORT).show();
////            }
////        });
//
////        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//////                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
////                updateIsConfirmedInFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
////                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot());
////            }
////        });
//
////        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//////                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
////                cancelIsConfirmdFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
////                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot());
////            }
////        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//    public payAdmin getItem(int position) {
//        return dataList.get(position);
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView title, address, price, start_date, end_date;
//        ImageView pic;
//        Button btnConfirm, btnCancel;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.title);
//            address = itemView.findViewById(R.id.address);
//            price = itemView.findViewById(R.id.price);
//            pic = itemView.findViewById(R.id.pic);
//            start_date = itemView.findViewById(R.id.start_date);
//            end_date = itemView.findViewById(R.id.end_date);
//            btnConfirm = itemView.findViewById(R.id.btnConfirm);
//            btnCancel = itemView.findViewById(R.id.btnCancel);
//        }
//    }
//
//    private void updateIsConfirmedInFirebase(String userMobile, String carModelName,String slot) {
//        // Update the isConfirmed value in Firebase Database using user mobile number and model name
//        databaseReference.child(userMobile).child(carModelName).child("slot"+slot).child("isConfirmed").setValue("true")
//                .addOnSuccessListener(aVoid -> {
//                    // Handle success, if needed
//                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    // Handle failure, if needed
//                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
//                });
//    }
//    private void cancelIsConfirmdFirebase(String userMobile, String carModelName, String slot) {
//        // Update the isBooked value in Firebase Database using user mobile number and model name
//        databaseReference.child(userMobile).child(carModelName).child("slot" + slot).child("isBooked").setValue("true")
//                .addOnSuccessListener(aVoid -> {
//                    // Handle success, if needed
//                    Toast.makeText(context, "Confirmation canceled", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    // Handle failure, if needed
//                    Toast.makeText(context, "Failed to cancel confirmation", Toast.LENGTH_SHORT).show();
//                });
//    }
//
//}