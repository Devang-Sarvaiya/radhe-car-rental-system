package com.example.caronrent.E_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.caronrent.Payment.Main_Payment;
import com.example.caronrent.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class address_for_gadget extends AppCompatActivity {
    TextView txt_price, txt_Gst, txt_amt;
    EditText edt_home_num, edt_soc_name, edt_lendmark, edt_pincode;
    String rSMo, rBMo, gName, gCat, gCom, gImg, gDel, gRep;
    Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address_for_gadget);

        txt_price = findViewById(R.id.txt_price);
        txt_Gst = findViewById(R.id.txt_Gst);
        txt_amt = findViewById(R.id.txt_amt);
        edt_home_num = findViewById(R.id.home_number);
        edt_soc_name = findViewById(R.id.soc_name);
        edt_lendmark = findViewById(R.id.landmark);
        edt_pincode = findViewById(R.id.pincode);
        btnregister = findViewById(R.id.btnregister);



        Intent intent = getIntent();
        String pri = intent.getStringExtra("price");
        rSMo = intent.getStringExtra("rSMo");
        rBMo = intent.getStringExtra("rBMo");
        gName = intent.getStringExtra("gName");
        gCat = intent.getStringExtra("gCat");
        gCom = intent.getStringExtra("gCom");
        gImg = intent.getStringExtra("gImg");
        gDel = intent.getStringExtra("gDel");
        gRep = intent.getStringExtra("gRep");
        gImg = intent.getStringExtra("gImg");
        Integer price = Integer.parseInt(pri);
        double gst = (price * 18) / 100;
        txt_price.setText(pri);
        String gs = String.valueOf(gst);
        txt_Gst.setText(gs);

        Integer tamt = (int) (price + 40 + gst);
        String amt = String.valueOf(tamt);
        txt_amt.setText(amt);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homeNum = edt_home_num.getText().toString().trim();
                String scoName = edt_soc_name.getText().toString();
                String lendmark = edt_lendmark.getText().toString();
                String pin = edt_pincode.getText().toString();

                order_Gadget og = new order_Gadget(gImg, rSMo, rBMo, gName, gCom, gCat, homeNum, scoName, lendmark, pin, gDel, gRep, amt);
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rSMo).child("Gadget_Selling_Orders").child(rBMo).push();

//                db.getKey();
                db.setValue(og);

                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rBMo).child("Gadget_Purchasing_Orders").push();
                db1.setValue(og);
                Intent intent1 = new Intent(address_for_gadget.this, Main_Payment.class);
                intent1.putExtra("totalAmt",amt);
                startActivity(intent1);
            }
        });

    }
}