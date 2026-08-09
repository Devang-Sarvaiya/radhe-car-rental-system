package com.example.caronrent.BookingHistory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.caronrent.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.ParseException;
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
        holder.userMob.setText(dataList.get(position).getUserMobile());
        String startDate = dataList.get(position).getStartDate();
        String endDate = dataList.get(position).getEndDate();

        // Call isDateInRange method and handle cancel button visibility inside it
        if (isDateInRange(startDate, endDate)) {
            holder.btnCancelBooking.setVisibility(View.GONE);
            holder.imgWs.setVisibility(View.GONE);
        } else {
            holder.btnCancelBooking.setVisibility(View.VISIBLE);
        }


        uMob = dataList.get(position).getUserMobile();
        String want = dataList.get(position).getIsExtended();
        String want2 = dataList.get(position).getIsUpdated();
        String want3 = dataList.get(position).getIsCarReceived();
        String want4 = dataList.get(position).getIsSelfCanceld();
        String want6 = dataList.get(position).getIsRenterCanceld();
        String want7 = dataList.get(position).getIsRenterPaymentDone();
//

        holder.btnConfirm.setVisibility(View.GONE);
        holder.btnCancel.setVisibility(View.GONE);
        holder.txtStat.setVisibility(View.GONE);

        if (dataList.get(position).getIsExtended().equals("true")) {
            holder.btnConfirm.setVisibility(View.VISIBLE);
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.txtStat.setVisibility(View.VISIBLE);
        }
        if (want2.equals("true")) {
            holder.btnConfirm.setEnabled(false);
            holder.btnConfirm.setText("Extended");
            holder.btnCancel.setEnabled(false);
            holder.btnCancelBooking.setVisibility(View.GONE);
            holder.txtStat.setVisibility(View.GONE);
            holder.imgWs.setVisibility(View.GONE);
        }

        if (want3.equals("true")) {
            holder.btnCarRecieved.setEnabled(false);
            holder.btnCarRecieved.setText("Car recieved successfully");
            holder.imgWs.setVisibility(View.GONE);
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnCancelBooking.setVisibility(View.GONE);
            holder.txtStat.setVisibility(View.GONE);

        }

        if(want4.equals("true")){
            holder.btnCancelBooking.setVisibility(View.VISIBLE);
            holder.btnCancelBooking.setEnabled(false);
            holder.btnCancelBooking.setText("User Cancel");
            holder.imgWs.setVisibility(View.GONE);
            holder.btnCarRecieved.setVisibility(View.GONE);
        }

        if(want6.equals("true")){
            holder.btnCancelBooking.setVisibility(View.VISIBLE);
            holder.btnCancelBooking.setEnabled(false);
            holder.btnCancelBooking.setText("You Cancel");
            holder.imgWs.setVisibility(View.GONE);
            holder.btnCarRecieved.setVisibility(View.GONE);
        }

        if(want7.equals("yes")){
            holder.btnCarRecieved.setEnabled(false);
            holder.btnCarRecieved.setText("Trip amount credited");
            holder.imgWs.setVisibility(View.GONE);
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnCancelBooking.setVisibility(View.GONE);
            holder.txtStat.setVisibility(View.GONE);
        }

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
                updateIsConfirmedInFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
            }
        });

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
                cancelIsConfirmdFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
            }
        });
        holder.btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
                cancelIsRenterFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
            }
        });

        holder.btnCarRecieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelIsCarDoneFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
            }
        });

        holder.imgWs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // WhatsApp number
                String phoneNumber = "+91" + uMob;

                // Message to send
                String message = "So Sorry! Unfortunately Your booking is canceld";

                // Create the Intent

                Intent intent = new Intent(Intent.ACTION_VIEW);

                // Set the data for the intent (WhatsApp URI)
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message)));

                // Start the activity
                context.startActivity(intent);
            }
        });


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


    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchdatalist(ArrayList<bookAdmin> searchList){
        dataList=searchList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price, start_date, end_date,userMob;
        ImageView pic, imgWs;
        Button btnConfirm, btnCancel, btnCancelBooking, btnCarRecieved;
        TextView txtStat;

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
            btnCancelBooking = itemView.findViewById(R.id.btnCancelBooking);
            imgWs = itemView.findViewById(R.id.imgWs);
            txtStat = itemView.findViewById(R.id.txtStat);
            btnCarRecieved = itemView.findViewById(R.id.btnCarRecieved);
            userMob = itemView.findViewById(R.id.userMob);
        }
    }

    private boolean isDateInRange(String startDate, String endDate) {
        // Get current date
        Date currentDate = new Date();

        try {
            // Parse start and end dates
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date startDateObj = sdf.parse(startDate);
            Date endDateObj = sdf.parse(endDate);

            // Check if current date is between start and end dates
            return (currentDate.after(startDateObj) && currentDate.before(endDateObj)) ||
                    currentDate.equals(startDateObj) || currentDate.equals(endDateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Handle parse exception gracefully
        }
    }



    private void updateIsConfirmedInFirebase(String userMobile, String carModelName, String slot, String renterMobile) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child(slot).child("isRenterAccepted").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, userMobile, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, uMob, Toast.LENGTH_SHORT).show();


                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(userMobile).child("Booking").child(carModelName);

        db.child(slot).child("isRenterAccepted").setValue("true")
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

        db1.child(slot).child("isRenterAccepted").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });
    }


    private void cancelIsConfirmdFirebase(String userMobile, String carModelName, String slot, String renterMobile) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child(slot).child("isExtended").setValue("false")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, "Confirmation cancel", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to cancel payment", Toast.LENGTH_SHORT).show();
                });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(userMobile).child("Booking").child(carModelName);

        db.child(slot).child("isExtended").setValue("false")
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

        db1.child(slot).child("isExtended").setValue("false")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });


        databaseReference.child(userMobile).child(carModelName).child(slot).child("isRenterAccepted").setValue("false")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, userMobile, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, uMob, Toast.LENGTH_SHORT).show();


                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });

//        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(uMob).child("Booking").child(cName);

        db.child(slot).child("isRenterAccepted").setValue("false")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });

//        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMob).child("Booking").child(cName);

        db1.child(slot).child("isRenterAccepted").setValue("false")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });

    }

    private void cancelIsRenterFirebase(String userMobile, String carModelName, String slot, String renterMobile) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child(slot).child("isRenterCanceld").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, "Confirmation cancel", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to cancel payment", Toast.LENGTH_SHORT).show();
                });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(userMobile).child("Booking").child(carModelName);

        db.child(slot).child("isRenterCanceld").setValue("true")
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

        db1.child(slot).child("isRenterCanceld").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });


    }

    private void cancelIsCarDoneFirebase(String userMobile, String carModelName, String slot, String renterMobile) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child(slot).child("isCarReceived").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, "Confirmation cancel", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to cancel payment", Toast.LENGTH_SHORT).show();
                });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(userMobile).child("Booking").child(carModelName);

        db.child(slot).child("isCarReceived").setValue("true")
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

        db1.child(slot).child("isCarReceived").setValue("true")
                .addOnSuccessListener(aVoid -> {
                    // Handle success, if needed
                    Toast.makeText(context, carModelName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Confirmation successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure, if needed
                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
                });


    }
}