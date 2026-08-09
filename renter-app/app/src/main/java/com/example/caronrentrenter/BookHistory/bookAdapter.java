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
import com.example.caronrentrenter.Extend.DetailExtend;
import com.example.caronrentrenter.MainActivity;
import com.example.caronrentrenter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.ViewHolder> {
    ArrayList<bookAdmin> dataList;
    DecimalFormat formatter;
    Context context;
    String uMob, cName, slt, rMob;


    DatabaseReference databaseReference;

    public bookAdapter() {
    }

    public bookAdapter(Context context, ArrayList<bookAdmin> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User"); // Replace with your actual database path

    }

    @NonNull
    @Override
    public bookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.payment_place_holder, parent, false);
        return new bookAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull bookAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getModelImageUrl()).into(holder.pic);
        holder.title.setText("Booked Successfully");
        holder.address.setText(dataList.get(position).getCarModelName());
        holder.price.setText(dataList.get(position).getTotalAmount());
        holder.start_date.setText(dataList.get(position).getStartDate());
        holder.end_date.setText(dataList.get(position).getEndDate());

        String startDate = dataList.get(position).getStartDate();
        if (startDate != null && isToday(startDate)) {
//            updateIsConfirmedInFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
//                    dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(),dataList.get(holder.getAdapterPosition()).getRenterMobile());

            holder.btnSelfCanceld.setVisibility(View.GONE);

            Toast.makeText(context, "Today is the start date!", Toast.LENGTH_SHORT).show();
        }

        uMob = dataList.get(position).getUserMobile();
        slt = dataList.get(position).getSlot();
        rMob = dataList.get(position).getRenterMobile();


//        String isEx =  dataList.get(position).getIsUpdated();
//        if (dataList.get(holder.getAdapterPosition()) != null) {
        String isEx1;
        String isEx2;
        String isEx3;
        String isEx4;
        String isEx5;
        isEx1 = dataList.get(position).getIsRenterCanceld();
        isEx2 = dataList.get(position).getIsAppliedReturn();
        isEx3 = dataList.get(position).getIsRefundDone();
        isEx4 = dataList.get(position).getIsRefund();
        isEx5 = dataList.get(position).getIsUpdated();
        String isEx6 = dataList.get(position).getIsCarReceived();
        String isEx7 = dataList.get(position).getIsSelfCanceld();



        holder.btnSelfCanceld.setVisibility(View.VISIBLE);



        if (isEx5 != null && isEx5.equals("true")) {
            holder.btnExtend.setEnabled(false);
            holder.btnExtend.setText("Already Extended");
            holder.btnSelfCanceld.setVisibility(View.GONE);

        }

        holder.btnApplyRefund.setVisibility(View.GONE);
        if (isEx1 != null && isEx1.equals("true")) {
            holder.btnApplyRefund.setVisibility(View.VISIBLE);
        }


        if (isEx2 != null && isEx2.equals("true")) {
            holder.btnApplyRefund.setText("Already Applid for refund");
            holder.btnApplyRefund.setEnabled(false);
            holder.btnExtend.setVisibility(View.GONE);
            holder.btnDownload.setVisibility(View.GONE);
            holder.btnSelfCanceld.setVisibility(View.GONE);

        }

        if (isEx3 != null && isEx3.equals("true")) {
            holder.btnApplyRefund.setVisibility(View.VISIBLE);
            holder.btnApplyRefund.setText("Refund amount Credited");
            holder.btnSelfCanceld.setVisibility(View.GONE);
        }

        if(isEx7.equals("true")){
            holder.btnSelfCanceld.setVisibility(View.VISIBLE);
            holder.btnSelfCanceld.setText("Applied For refund");
            holder.btnSelfCanceld.setEnabled(false);
            holder.btnDownload.setVisibility(View.GONE);
            holder.btnExtend.setVisibility(View.GONE);
        }


        if (isEx4 != null && isEx4.equals("true")) {
            holder.btnApplyRefund.setVisibility(View.GONE);

            holder.btnSelfCanceld.setVisibility(View.VISIBLE);

            holder.btnSelfCanceld.setEnabled(false);
            holder.btnSelfCanceld.setText("Refund amount credited on self");
            holder.btnExtend.setVisibility(View.GONE);
            holder.btnDownload.setVisibility(View.GONE);
        }

        if(isEx6.equals("true")){
            holder.btnApplyRefund.setVisibility(View.GONE);
            holder.btnExtend.setVisibility(View.GONE);
            holder.btnDownload.setEnabled(false);
            holder.btnDownload.setText("Trip Done");
            holder.btnSelfCanceld.setVisibility(View.GONE);
        }

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.btnExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailExtend.class);
                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.btnApplyRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIsRefundInFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
//                Intent intent = new Intent(holder.itemView.getContext(), DetailExtend.class);
//                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
//                context.startActivity(intent);

            }
        });


        holder.btnSelfCanceld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIsConfirmedInFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(),dataList.get(holder.getAdapterPosition()).getRenterMobile());

                holder.btnSelfCanceld.setVisibility(View.VISIBLE);
                holder.btnSelfCanceld.setText("Applied For refund");
                holder.btnSelfCanceld.setEnabled(false);
//                Intent intent = new Intent(holder.itemView.getContext(), DetailExtend.class);
//                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
//                context.startActivity(intent);

            }
        });


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), payment_confirm_deny.class);
//                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
//                context.startActivity(intent);
//                Toast.makeText(context, "Lee Bro !!!", Toast.LENGTH_SHORT).show();
//            }
//        });


        cName = dataList.get(position).getCarModelName();

//
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchdatalist(ArrayList<bookAdmin> searchList){
        dataList=searchList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price, start_date, end_date;
        ImageView pic;
        Button btnDownload, btnExtend, btnApplyRefund, btnSelfCanceld;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);
            btnDownload = itemView.findViewById(R.id.btnDownload);
            btnExtend = itemView.findViewById(R.id.btnExtend);
            btnApplyRefund = itemView.findViewById(R.id.btnApplyRefund);
            btnSelfCanceld = itemView.findViewById(R.id.btnSelfCanceld);

        }
    }

    private boolean isToday(String dateString) {
        // Get current date in "dd/MM/yyyy" format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Compare current date with the provided date string
        return currentDate.equals(dateString);
    }

    private void updateIsRefundInFirebase(String userMobile, String carModelName, String slot,String renterMobile) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child(slot).child("isAppliedReturn").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
//                    Toast.makeText(context, userMobile, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, uMob, Toast.LENGTH_SHORT).show();


                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(userMobile).child("Booking").child(carModelName);

        db.child(slot).child("isAppliedReturn").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
//                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(renterMobile).child("Booking").child(carModelName);

        db1.child(slot).child("isAppliedReturn").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
//                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateIsConfirmedInFirebase(String userMobile, String carModelName, String slot,String renterMobile) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child(slot).child("isSelfCanceld").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
//                    Toast.makeText(context, userMobile, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, slot, Toast.LENGTH_SHORT).show();


                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });


//        Toast.makeText(context, uMob, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, cName, Toast.LENGTH_SHORT).show();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(userMobile).child("Booking").child(carModelName);

        db.child(slot).child("isSelfCanceld").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(renterMobile).child("Booking").child(carModelName);

        db1.child(slot).child("isSelfCanceld").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
//                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });
    }

}