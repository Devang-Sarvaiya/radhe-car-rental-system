package com.example.caronrent.E_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caronrent.DataClass;
import com.example.caronrent.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Detail_e_com extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageView img_gadget;
    TextView txt_m_name,txt_c_name,txt_desc,txt_deli_date,txt_repl_date,txt_pri;
    Button buy_now;
    String eem,mob;
    private DatabaseReference usersRef;

    ImageModel_1_e_com object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ecom);

        img_gadget = findViewById(R.id.img_gadget);
        txt_m_name = findViewById(R.id.txt_m_name);
        txt_c_name = findViewById(R.id.txt_c_name);
        txt_desc = findViewById(R.id.txt_desc);
        txt_deli_date = findViewById(R.id.txt_deli_date);
        txt_repl_date = findViewById(R.id.txt_repl_date);
        txt_pri = findViewById(R.id.txt_pri);
        buy_now = findViewById(R.id.buy_now);


        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");




        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }
                if(object.getRenterMobile().equals(mob)){
                    buy_now.setVisibility(View.GONE);
                }else{
                    buy_now.setVisibility(View.VISIBLE);
                    buy_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Detail_e_com.this,address_for_gadget.class);
                            intent.putExtra("price",object.getGadgetPrice());
                            intent.putExtra("rSMo",object.getRenterMobile());
                            intent.putExtra("rBMo",mob);
                            intent.putExtra("gName",object.getModelName());
                            intent.putExtra("gCom",object.getGadgetCompany());
                            intent.putExtra("gCat",object.getGadgetCategory());
                            intent.putExtra("gImg",object.getImageURL());
                            intent.putExtra("gDel",object.getDeliveryDay());
                            intent.putExtra("gRep",object.getReplacementDay());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        object = (ImageModel_1_e_com) getIntent().getSerializableExtra("object_e_com");

        String firebaseImageUrl = object.getImageURL();
        Glide.with(this)
                .load(firebaseImageUrl)
                .into(img_gadget);


        txt_m_name.setText(object.getModelName());
        txt_c_name.setText(object.getGadgetCompany());
        txt_desc.setText(object.getModelDescription());

//        String del = object.getDeliveryDay();
        txt_deli_date.setText("Delivery in " + object.getDeliveryDay().toString() + " Days");
        txt_repl_date.setText("Replace in " + object.getReplacementDay().toString() + " Days");
        txt_pri.setText(object.getGadgetPrice());






    }
}