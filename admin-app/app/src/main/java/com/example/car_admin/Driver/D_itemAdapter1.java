package com.example.car_admin.Driver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.car_admin.Cars.ImageModel1;
import com.example.car_admin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class D_itemAdapter1 extends RecyclerView.Adapter<D_itemAdapter1.ViewHolder> {
    ArrayList<ImageModel_1_driver> dataList;
    DecimalFormat formatter;
    Context context;
    DatabaseReference databaseReference;
    Intent intent;

    String carName,carCom,carType,rMob,carImage;

    public D_itemAdapter1() {
    }
    public D_itemAdapter1(Context context, ArrayList<ImageModel_1_driver> dataList, String carName, String carCom, String carType, String rMob,String carImage) {
        this.context = context;
        this.dataList = dataList;
        this.carName = carName;
        this.carCom = carCom;
        this.carType = carType;
        this.rMob = rMob;
        this.carImage = carImage;
        this.databaseReference =FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(carCom).child("Company").child(carCom).child(carName);
    }

//    public D_itemAdapter1(Context context, ArrayList<ImageModel_1_driver> dataList, Intent intent) {
//        this.context = context;
//        this.dataList = dataList;
//         carName = intent.getStringExtra("CarName");
//         carModel = intent.getStringExtra("CarCom");
//         carType = intent.getStringExtra("CarType");
//         rmob = intent.getStringExtra("rMob");
//        // Use these values as needed
//        this.databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(carCom).child("Company").child(carCom).child(carName);
//    }


    @NonNull
    @Override
    public D_itemAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.driver_item_view_holder1, parent, false);
        return new D_itemAdapter1.ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull D_itemAdapter1.ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getImageURLUser()).into(holder.pic);
        holder.address.setText(dataList.get(position).getEmail());
        holder.price.setText(dataList.get(position).getMobile_dr());
        holder.title.setText(dataList.get(position).getName());

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIsConfirmedInFirebase(dataList.get(holder.getAdapterPosition()).getMobile_dr(),carName,carCom,carType,rMob,carImage);
                ((Activity) context).finish();

            }
        });
//        holder.title.setText(dataList.get(position).getName());
//        holder.address.setText(dataList.get(position).getEmail());
//        holder.price.setText(dataList.get(position).getMobile_dr());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), Detail_Of_Car.class);
//                intent.putExtra("object", dataList.get(holder.getAdapterPosition()));
//                context.startActivity(intent);
//                Toast.makeText(context, "Lee Bro !!!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, address, price;
        ImageView pic;
        AppCompatButton btnConfirm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            pic = itemView.findViewById(R.id.pic);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
        }
    }


    private void updateIsConfirmedInFirebase(String userMobile,String carname, String carcom, String cartype,String rMob,String carImage) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(cartype).child("Company").child(carcom).child(carname).child("Details");
        db.child("isDriverConnectedWithCar").setValue("true");
        db.child("driverMobile").setValue(userMobile);

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General").child(userMobile);
        db1.child("isCoonectedWithCar").setValue("true");
        db1.child("carName").setValue(carname);
        db1.child("carType").setValue(cartype);
        db1.child("carCompany").setValue(carcom);
        db1.child("carImage").setValue(carImage);
        db1.child("carRenterMobile").setValue(rMob);


//        Toast.makeText(context, userMobile + carcom, Toast.LENGTH_SHORT).show();

//        databaseReference.child(userMobile).child("isCoonectedWithCar").setValue("true")
//                .addOnSuccessListener(aVoid -> {
//                    // Handle success, if needed
//                    Toast.makeText(context, "Connected successful", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    // Handle failure, if needed
//                    Toast.makeText(context, "Failed to confirm payment", Toast.LENGTH_SHORT).show();
//                });
    }

}

