package com.example.caronrent.Report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caronrent.BookingHistory.bookAdapter;
import com.example.caronrent.BookingHistory.bookAdmin;
import com.example.caronrent.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class report_show_adapter extends RecyclerView.Adapter<report_show_adapter.ViewHolder> {
    ArrayList<report_adapter> dataList;
    DecimalFormat formatter;
    Context context;
    String uMob, cName, slt, rMob;
    DatabaseReference databaseReference;


    public report_show_adapter() {
    }

    public report_show_adapter(Context context, ArrayList<report_adapter> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User"); // Replace with your actual database path

    }

    @NonNull
    @Override
    public report_show_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.payment_place_holder_report, parent, false);
        return new report_show_adapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull report_show_adapter.ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getModelImageUrl()).into(holder.pic);
        holder.title.setText(dataList.get(position).getCarModelName());

        holder.price.setText(dataList.get(position).getTotalAmount());


        if (dataList.get(position).getIsAlcohol().equals("Yes")) {
            holder.imageView8.setImageResource(R.drawable.cbyes);
        } else {
            holder.imageView8.setImageResource(R.drawable.cbno);
        }

        if (dataList.get(position).getIsGunPowder().equals("Yes")) {
            holder.imageView9.setImageResource(R.drawable.cbyes);
        } else {
            holder.imageView9.setImageResource(R.drawable.cbno);
        }

        if (dataList.get(position).getIsDamage0().equals("Yes")) {
            holder.imageView2.setImageResource(R.drawable.cbno);
            holder.end_date.setText("Damage (0%)");
        } else if (dataList.get(position).getIsDamage20().equals("Yes")) {
            holder.imageView2.setImageResource(R.drawable.cbyes);
            holder.end_date.setText("Damage (20%)");
        } else if (dataList.get(position).getIsDamage40().equals("Yes")) {
            holder.imageView2.setImageResource(R.drawable.cbyes);
            holder.end_date.setText("Damage (40%)");
        } else if (dataList.get(position).getIsDamage80().equals("Yes")) {
            holder.imageView2.setImageResource(R.drawable.cbyes);
            holder.end_date.setText("Damage (80%)");
        } else if (dataList.get(position).getIsDamage100().equals("Yes")) {
            holder.imageView2.setImageResource(R.drawable.cbyes);
            holder.end_date.setText("Damage (100%)");
        } else {
            holder.imageView2.setImageResource(R.drawable.cbno);
        }

        holder.userMob.setText(dataList.get(position).getUserMobile());


        String startDate = dataList.get(position).getStartDate();
        String endDate = dataList.get(position).getEndDate();

        // Call isDateInRange method and handle cancel button visibility inside it
//        if (isDateInRange(startDate, endDate)) {
//            holder.btnCancelBooking.setVisibility(View.GONE);
//            holder.imgWs.setVisibility(View.GONE);
//        } else {
//            holder.btnCancelBooking.setVisibility(View.VISIBLE);
//        }

//
//        uMob = dataList.get(position).getUserMobile();
//        String want = dataList.get(position).getIsExtended();
//        String want2 = dataList.get(position).getIsUpdated();
//        String want3 = dataList.get(position).getIsCarReceived();
//        String want4 = dataList.get(position).getIsSelfCanceld();
//        String want6 = dataList.get(position).getIsRenterCanceld();
////
//
//        holder.btnConfirm.setVisibility(View.GONE);
//        holder.btnCancel.setVisibility(View.GONE);
//        holder.txtStat.setVisibility(View.GONE);
//
//        if (dataList.get(position).getIsExtended().equals("true")) {
//            holder.btnConfirm.setVisibility(View.VISIBLE);
//            holder.btnCancel.setVisibility(View.VISIBLE);
//            holder.txtStat.setVisibility(View.VISIBLE);
//        }
//        if (want2.equals("true")) {
//            holder.btnConfirm.setEnabled(false);
//            holder.btnConfirm.setText("Extended");
//            holder.btnCancel.setEnabled(false);
//            holder.btnCancelBooking.setVisibility(View.GONE);
//            holder.txtStat.setVisibility(View.GONE);
//            holder.imgWs.setVisibility(View.GONE);
//        }
//
//        if (want3.equals("true")) {
//            holder.btnCarRecieved.setEnabled(false);
//            holder.btnCarRecieved.setText("Car recieved successfully");
//            holder.btnConfirm.setVisibility(View.GONE);
//            holder.btnCancel.setVisibility(View.GONE);
//            holder.btnCancelBooking.setVisibility(View.GONE);
//            holder.txtStat.setVisibility(View.GONE);
//
//        }
//
//        if (want4.equals("true")) {
//            holder.btnCancelBooking.setVisibility(View.VISIBLE);
//            holder.btnCancelBooking.setEnabled(false);
//            holder.btnCancelBooking.setText("User Cancel");
//            holder.imgWs.setVisibility(View.GONE);
//            holder.btnCarRecieved.setVisibility(View.GONE);
//        }
//
//        if (want6.equals("true")) {
//            holder.btnCancelBooking.setVisibility(View.VISIBLE);
//            holder.btnCancelBooking.setEnabled(false);
//            holder.btnCancelBooking.setText("You Cancel");
//            holder.imgWs.setVisibility(View.GONE);
//            holder.btnCarRecieved.setVisibility(View.GONE);
//        }

//        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
//                updateIsConfirmedInFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
//                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
//            }
//        });
//
//        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
//                cancelIsConfirmdFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
//                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
//            }
//        });
//        holder.btnCancelBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dataList.get(holder.getAdapterPosition()).setIsConfirmed("true");
//                cancelIsRenterFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
//                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
//            }
//        });
//
//        holder.btnCarRecieved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancelIsCarDoneFirebase(dataList.get(holder.getAdapterPosition()).getUserMobile(),
//                        dataList.get(holder.getAdapterPosition()).getCarModelName(), dataList.get(holder.getAdapterPosition()).getSlot(), dataList.get(holder.getAdapterPosition()).getRenterMobile());
//            }
//        });
//
//        holder.imgWs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // WhatsApp number
//                String phoneNumber = "+91" + uMob;
//
//                // Message to send
//                String message = "Hello! Nice to meet you.";
//
//                // Create the Intent
//
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//
//                // Set the data for the intent (WhatsApp URI)
//                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message)));
//
//                // Start the activity
//                context.startActivity(intent);
//            }
//        });


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

    public void searchdatalist(ArrayList<report_adapter> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price, start_date, end_date, userMob;
        ImageView pic, imgWs, imageView2, imageView9, imageView8;
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
            imageView2 = itemView.findViewById(R.id.imageView2);
            imageView9 = itemView.findViewById(R.id.imageView9);
            imageView8 = itemView.findViewById(R.id.imageView8);
        }
    }

}
