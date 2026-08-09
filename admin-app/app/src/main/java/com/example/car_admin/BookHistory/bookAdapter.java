package com.example.car_admin.BookHistory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.car_admin.MainActivity;
import com.example.car_admin.Payment.Main_Payment;
import com.example.car_admin.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.ViewHolder> {
    ArrayList<bookAdmin> dataList;
    DecimalFormat formatter;
    Context context;
    String amt;
    String amt1;

    public bookAdapter() {
    }

    public bookAdapter(Context context, ArrayList<bookAdmin> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.booked_place_holder, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getModelImageUrl()).into(holder.pic);
        holder.title.setText("Booked Successfully");
        holder.address.setText(dataList.get(position).getCarModelName());
        holder.txtUser.setText(dataList.get(position).getTotalAmount());
        amt1 = dataList.get(position).getTotalAmount();

        holder.price.setText(dataList.get(position).getTotalAmount());
        holder.start_date.setText(dataList.get(position).getStartDate());
        holder.end_date.setText(dataList.get(position).getEndDate());

        amt = dataList.get(position).getTotalAmount();
        double tamt = Double.parseDouble(amt);
        tamt = tamt - 10000f;

        double totalAmt = tamt - (tamt * 10) / 100f;

        double toto = tamt - (tamt * 5) / 100f;

        double ta = tamt - totalAmt;
        double ta2 = tamt - toto;

        double taamt = tamt - ta - ta2;

// Format double values to display up to 2 decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#");

        String aamt = decimalFormat.format(taamt);
        holder.txtRenterPayment.setText(aamt);

        double totDriver = tamt - toto;
        String aaaamt = decimalFormat.format(totDriver);
        holder.txtdriver.setText(aaaamt);

//
//        String amt = dataList.get(position).getTotalAmount();
//        double tamt = Double.parseDouble(amt);
//        tamt = tamt - 10000f;
//
//        double totalAmt = tamt - (tamt * 10) / 100f;
//
//        double toto = tamt - (tamt * 5) / 100f;
//
//
//        double ta = tamt - totalAmt;
//        double ta2 = tamt - toto;
//
//        double taamt = tamt - ta - ta2;
//
//        String aamt = String.valueOf(taamt);
//        holder.txtRenterPayment.setText(aamt);
//
//        double totDriver = tamt - toto;
//        String aaaamt = String.valueOf(totDriver);
//        holder.txtdriver.setText(aaaamt);


//        holder.txtPickupPaymment.setText(dataList.get(position).getPickupPoint());
        holder.txtPickupPaymment.setText("10000");

        String yes = "yes";
        String yes1 = dataList.get(position).getIsDepositPaymentDone();
        String yes2 = dataList.get(position).getIsDriverPaymentDone();
        String yes3 = dataList.get(position).getIsRenterPaymentDone();

        String yes4 = dataList.get(position).getIsAppliedReturn();
        String yes5 = dataList.get(position).getIsRefundDone();
        String yes6 = dataList.get(position).getIsSelfCanceld();
        String yes8 = dataList.get(position).getIsRefund();
        String yes7 = dataList.get(position).getIsCarReceived();
        String yes9 = dataList.get(position).getIsRenterCanceld();

        holder.btnPickUpPayment.setVisibility(View.GONE);
        holder.btnDriver.setVisibility(View.GONE);
        holder.btnRenterPayment.setVisibility(View.GONE);
        holder.txtPickupPaymment.setVisibility(View.GONE);
        holder.txtdriver.setVisibility(View.GONE);
        holder.txtRenterPayment.setVisibility(View.GONE);
        holder.btn_User.setVisibility(View.GONE);
        holder.txtUser.setVisibility(View.GONE);

        holder.txtRenttxt.setVisibility(View.GONE);
        holder.txtpictxt.setVisibility(View.GONE);
        holder.txtdrtxt.setVisibility(View.GONE);
        holder.txtrefundtxt.setVisibility(View.GONE);

        if (yes9.equals("true") || yes6.equals("true")) {
            if(yes9.equals("true")){
                holder.btn_User.setVisibility(View.VISIBLE);
                holder.txtrefundtxt.setVisibility(View.VISIBLE);

                holder.btn_User.setText("Pay (Renter Canceld)");
                holder.txtUser.setVisibility(View.VISIBLE);
            }else{
                holder.txtrefundtxt.setVisibility(View.VISIBLE);
                holder.btn_User.setVisibility(View.VISIBLE);
                holder.btn_User.setText("Pay (Self Canceld)");
                holder.txtUser.setVisibility(View.VISIBLE);
            }
        }

        if (yes5.equals("true")) {
            holder.txtUser.setText(dataList.get(holder.getAdapterPosition()).getTotalAmount());
            holder.btn_User.setText("Done (Renter Canceld)");
            holder.btn_User.setEnabled(false);
        }

        if (yes8.equals("true")) {
            holder.txtUser.setText(dataList.get(holder.getAdapterPosition()).getTotalAmount());
            holder.btn_User.setText("Done (Self Canceld)");
            holder.btn_User.setEnabled(false);
        }

        if (yes7.equals("true")) {
            holder.txtRenttxt.setVisibility(View.VISIBLE);
            holder.txtpictxt.setVisibility(View.VISIBLE);
            holder.txtdrtxt.setVisibility(View.VISIBLE);
            holder.txtrefundtxt.setVisibility(View.GONE);

            holder.btnPickUpPayment.setVisibility(View.VISIBLE);
            holder.btnDriver.setVisibility(View.VISIBLE);
            holder.btnRenterPayment.setVisibility(View.VISIBLE);
            holder.txtPickupPaymment.setVisibility(View.VISIBLE);
            holder.txtdriver.setVisibility(View.VISIBLE);
            holder.txtRenterPayment.setVisibility(View.VISIBLE);
        }


        if (yes.equals(yes1)) {
            holder.txtPickupPaymment.setVisibility(View.VISIBLE);
            holder.btnPickUpPayment.setText("Done");
            holder.btnPickUpPayment.setEnabled(false);
        }
        if (yes.equals(yes2)) {
            holder.txtdriver.setVisibility(View.VISIBLE);
            holder.btnDriver.setText("Done");
            holder.btnDriver.setEnabled(false);
        }
        if (yes.equals(yes3)) {
            holder.txtRenterPayment.setVisibility(View.VISIBLE);
            holder.btnRenterPayment.setText("Done");
            holder.btnRenterPayment.setEnabled(false);
        }

        holder.btnRenterPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main_Payment.class);

                // Pass data to the new activity using intent.putExtra
//                intent.putExtra("object", dataList.get(position));
                intent.putExtra("amt", aamt);

                intent.putExtra("pay", "renter");

                // Start the activity
                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });


        holder.btnPickUpPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main_Payment.class);

                // Pass data to the new activity using intent.putExtra
//                intent.putExtra("object", dataList.get(position));
                String aaamt = "10000";
                intent.putExtra("amt", aaamt);
                intent.putExtra("pay", "deposit");

                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
                // Start the activity
                context.startActivity(intent);
            }
        });

        holder.btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main_Payment.class);

                // Pass data to the new activity using intent.putExtra
//                intent.putExtra("object", dataList.get(position));
                intent.putExtra("amt", aaaamt);
                intent.putExtra("pay", "driver");

                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));

                // Start the activity
                context.startActivity(intent);
            }
        });


        holder.btn_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main_Payment.class);

                intent.putExtra("amt", dataList.get(holder.getAdapterPosition()).getTotalAmount());
                intent.putExtra("pay", "user");

                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));

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


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price, start_date, end_date, txtRenterPayment, txtPickupPaymment, txtdriver, txtUser,txtRenttxt,txtpictxt,txtdrtxt,txtrefundtxt;
        ImageView pic;
        Button btnRenterPayment, btnPickUpPayment, btnDriver, btn_User;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);
            txtRenterPayment = itemView.findViewById(R.id.txtRenterPaymet);
            txtPickupPaymment = itemView.findViewById(R.id.txtPicupPayemet);
            btnRenterPayment = itemView.findViewById(R.id.btnRenterPayment);
            btnPickUpPayment = itemView.findViewById(R.id.btnPicupPayment);
            btnPickUpPayment = itemView.findViewById(R.id.btnPicupPayment);
            btnDriver = itemView.findViewById(R.id.btn_driver);
            btn_User = itemView.findViewById(R.id.btn_User);
            txtUser = itemView.findViewById(R.id.txtUser);
            txtdriver = itemView.findViewById(R.id.txtdriver);

            txtRenttxt = itemView.findViewById(R.id.txtRenttxt);
            txtpictxt = itemView.findViewById(R.id.txtpictxt);
            txtdrtxt = itemView.findViewById(R.id.txtdrtxt);
            txtrefundtxt = itemView.findViewById(R.id.txtrefundtxt);


        }
    }
}