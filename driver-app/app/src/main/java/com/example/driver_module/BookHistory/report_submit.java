package com.example.driver_module.BookHistory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.driver_module.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class report_submit extends AppCompatActivity {

    CheckBox chb_alco,chb_gun,chb_dm_0,chb_dn_20,chb_dm_40,chb_dm_80,chb_dm_100;
    Button btnSubmit;
    bookAdmin object;
    String alcoValue, gunValue, dm0Value, dn20Value, dm40Value, dm80Value, dm100Value;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User");;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_submit);

        chb_alco = findViewById(R.id.chb_alco);
        chb_gun = findViewById(R.id.chb_gun);
        chb_dm_0 = findViewById(R.id.chb_dm_0);
        chb_dn_20 = findViewById(R.id.chb_dn_20);
        chb_dm_40 = findViewById(R.id.chb_dm_40);
        chb_dm_80 = findViewById(R.id.chb_dm_80);
        chb_dm_100 = findViewById(R.id.chb_dm_100);
        btnSubmit = findViewById(R.id.btnSubmit);

        updateCheckBoxValues();

        // Set OnCheckedChangeListener for each checkbox
        chb_alco.setOnCheckedChangeListener((buttonView, isChecked) -> updateCheckBoxValues());
        chb_gun.setOnCheckedChangeListener((buttonView, isChecked) -> updateCheckBoxValues());
        chb_dm_0.setOnCheckedChangeListener((buttonView, isChecked) -> updateCheckBoxValues());
        chb_dn_20.setOnCheckedChangeListener((buttonView, isChecked) -> updateCheckBoxValues());
        chb_dm_40.setOnCheckedChangeListener((buttonView, isChecked) -> updateCheckBoxValues());
        chb_dm_80.setOnCheckedChangeListener((buttonView, isChecked) -> updateCheckBoxValues());
        chb_dm_100.setOnCheckedChangeListener((buttonView, isChecked) -> updateCheckBoxValues());


//        alcoValue = chb_alco.isChecked() ? "Yes" : "No";
//        gunValue = chb_gun.isChecked() ? "Yes" : "No";
//        dm0Value = chb_dm_0.isChecked() ? "Yes" : "No";
//        dn20Value = chb_dn_20.isChecked() ? "Yes" : "No";
//        dm40Value = chb_dm_40.isChecked() ? "Yes" : "No";
//        dm80Value = chb_dm_80.isChecked() ? "Yes" : "No";
//        dm100Value = chb_dm_100.isChecked() ? "Yes" : "No";


        object = (bookAdmin) getIntent().getSerializableExtra("object");



        String s1 = object.getModelImageUrl().toString();
        String s2 = object.getRenterMobile().toString();
        String s3 = object.getUserMobile().toString();
        String s4 = object.getCarModelName().toString();
        String s5 = object.getStartDate().toString();
        String s6 = object.getEndDate().toString();
        String s7 = object.getTotalAmount().toString();
        String s8 = object.getPickupPoint().toString();
        String s9 = object.getDriver().toString();
        String s10 = object.getIsRenterPaymentDone().toString();
        String s11 = object.getIsDriverPaymentDone().toString();
        String s12 = object.getIsDepositPaymentDone().toString();
        String s13 = object.getSlot().toString();
        String s14 = object.getIsRefund().toString();
        String s15 = object.getIsExtended().toString();
        String s16 = object.getIsUpdated().toString();
        String s17 = object.getIsSelfCanceld().toString();
        String s18 = object.getIsRenterCanceld().toString();
        String s19 = object.getIsRenterAccepted().toString();
        String s20 = object.getIsAppliedReturn().toString();
        String s21 = object.getIsCarReceived().toString();
        String s22 = object.getIsRefundDone().toString();
        String s23 = object.getIsCanceld().toString();
        String s24 = object.getRentPerDay().toString();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_adapter rp = new report_adapter(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,alcoValue, gunValue, dm0Value, dn20Value, dm40Value, dm80Value, dm100Value);

                DatabaseReference db3 = FirebaseDatabase.getInstance().getReference("Admin").child("Report").child(s8).child(s13);

                db3.setValue(rp);

                Toast.makeText(report_submit.this, "Report Submitted", Toast.LENGTH_SHORT).show();
                cancelIsCarDoneFirebase(s3,s4,s13,s2);
                finish();
            }
        });



    }
    private void updateCheckBoxValues() {
        alcoValue = chb_alco.isChecked() ? "Yes" : "No";
        gunValue = chb_gun.isChecked() ? "Yes" : "No";
        dm0Value = chb_dm_0.isChecked() ? "Yes" : "No";
        dn20Value = chb_dn_20.isChecked() ? "Yes" : "No";
        dm40Value = chb_dm_40.isChecked() ? "Yes" : "No";
        dm80Value = chb_dm_80.isChecked() ? "Yes" : "No";
        dm100Value = chb_dm_100.isChecked() ? "Yes" : "No";
    }


    private void cancelIsCarDoneFirebase(String userMobile, String carModelName, String slot, String renterMobile) {
        // Update the isConfirmed value in Firebase Database using user mobile number and model name
        databaseReference.child(userMobile).child(carModelName).child(slot).child("isCarReceived").setValue("true");

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(userMobile).child("Booking").child(carModelName);

        db.child(slot).child("isCarReceived").setValue("true");

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(renterMobile).child("Booking").child(carModelName);

        db1.child(slot).child("isCarReceived").setValue("true");


    }
}