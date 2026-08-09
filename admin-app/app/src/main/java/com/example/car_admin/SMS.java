//package com.example.car_admin;
//
//public class Constants {
//    public static final String BASE_URL="https://fcm.googleapis.com";
//    public static final String SERVER_KEY="YOUR_FCM_LEGACY_SERVER_KEY";
//    public static final String CONTENT_TYPE="application/json";
//    public static final String TOPIC="/topics/MeetRamani";
//}
package com.example.car_admin;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.car_admin.MainFragment;
import com.example.car_admin.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.Objects;
import java.util.Random;

public class SMS extends AppCompatActivity {
    Button button;
    boolean check = true;
    String getotp;
    String message;
    String otp;
    EditText otptext;
    String ph;
    EditText phone;
    // Admin phone allowlist — replace with your own admin phone numbers. Original personal numbers redacted.
    String[] recipientPhoneNumbers = new String[]{"+919000000001", "+919000000002", "+919000000003", "+919000000004", "+919000000005", "+919000000006"};
    String[] recipientPhoneNumbers2 = new String[]{"+919000000001", "+919000000002", "+919000000003", "+919000000004", "+919000000005", "+919000000006"};
    String senderPhoneNumber = "+919000000000"; // Configure your own SMS gateway/sender number
    TextView tv;

    private void generateAndSendOTP() {
        this.otp = this.generateOTP();
        if (this.ph.equals((Object)"9000000001")) {
            this.recipientPhoneNumbers = new String[]{"+919000000001"};
            this.message = "Your OTP is : " + this.otp + " for log in as Admin which is valid for 5 minutes";
            this.sendSMS(this.senderPhoneNumber, this.recipientPhoneNumbers, this.message);
            this.recipientPhoneNumbers2 = new String[]{"+919000000002", "+919000000003", "+919000000004", "+919000000005", "+919000000006"};
            this.message = "Admin 1 logged in as an Admin using the phone number :- +919000000001";
            this.sendSMS2(this.senderPhoneNumber, this.recipientPhoneNumbers2, this.message);
        }
        if (this.ph.equals((Object)"9000000002")) {
            this.recipientPhoneNumbers = new String[]{"+919000000002"};
            this.message = "Your OTP is: " + this.otp + " for log in as Admin which is valid for 5 minutes";
            this.sendSMS(this.senderPhoneNumber, this.recipientPhoneNumbers, this.message);
            this.recipientPhoneNumbers2 = new String[]{"+919000000001", "+919000000003", "+919000000004", "+919000000005", "+919000000006"};
            this.message = "Admin 2 logged in as an Admin using the phone number :- +919000000002";
            this.sendSMS2(this.senderPhoneNumber, this.recipientPhoneNumbers2, this.message);
        }
        if (this.ph.equals((Object)"9000000003")) {
            this.recipientPhoneNumbers = new String[]{"+919000000003"};
            this.message = "Your OTP is: " + this.otp + " for log in as Admin which is valid for 5 minutes";
            this.sendSMS(this.senderPhoneNumber, this.recipientPhoneNumbers, this.message);
            this.recipientPhoneNumbers2 = new String[]{"+919000000001", "+919000000002", "+919000000004", "+919000000005", "+919000000006"};
            this.message = "Admin 3 logged in as an Admin using the phone number :- +919000000003";
            this.sendSMS2(this.senderPhoneNumber, this.recipientPhoneNumbers2, this.message);
        }
        if (this.ph.equals((Object)"9000000004")) {
            this.recipientPhoneNumbers = new String[]{"+919000000004"};
            this.message = "Your OTP is: " + this.otp + " for log in as Admin which is valid for 5 minutes";
            this.sendSMS(this.senderPhoneNumber, this.recipientPhoneNumbers, this.message);
            this.recipientPhoneNumbers2 = new String[]{"+919000000002", "+919000000003", "+919000000001", "+919000000005", "+919000000006"};
            this.message = "Admin 4 logged in as an Admin using the phone number :- +919000000004";
            this.sendSMS2(this.senderPhoneNumber, this.recipientPhoneNumbers2, this.message);
        }
        if (this.ph.equals((Object)"9000000005")) {
            this.recipientPhoneNumbers = new String[]{"+919000000005"};
            this.message = "Your OTP is: " + this.otp + " for log in as Admin which is valid for 5 minutes";
            this.sendSMS(this.senderPhoneNumber, this.recipientPhoneNumbers, this.message);
            this.recipientPhoneNumbers2 = new String[]{"+919000000002", "+919000000003", "+919000000004", "+919000000001", "+919000000006"};
            this.message = "Admin 5 logged in as an Admin using the phone number :- +919000000005";
            this.sendSMS2(this.senderPhoneNumber, this.recipientPhoneNumbers2, this.message);
        }
        if (this.ph.equals((Object)"9000000006")) {
            this.recipientPhoneNumbers = new String[]{"+919000000006"};
            this.message = "Your OTP is: " + this.otp + " for log in as Admin which is valid for 5 minutes";
            this.sendSMS(this.senderPhoneNumber, this.recipientPhoneNumbers, this.message);
            this.recipientPhoneNumbers2 = new String[]{"+919000000002", "+919000000003", "+919000000004", "+919000000005", "+919000000001"};
            this.message = "Admin 6 logged in as an Admin using the phone number :- +919000000006";
            this.sendSMS2(this.senderPhoneNumber, this.recipientPhoneNumbers2, this.message);
        }
    }
    private String generateOTP() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; ++i) {
            stringBuilder.append("0123456789".charAt(random.nextInt("0123456789".length())));
        }
        this.otp = stringBuilder.toString();
        return stringBuilder.toString();
    }
    private void sendSMS(String senderPhoneNumber, String[] recipientPhoneNumbers, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            Intent sentIntent = new Intent("SMS_SENT");
            PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, sentIntent, PendingIntent.FLAG_IMMUTABLE);

            for (String recipientPhoneNumber : recipientPhoneNumbers) {
                smsManager.sendTextMessage(recipientPhoneNumber, null, message, sentPendingIntent, null);
                Log.d("SMS", "SMS sent to " + recipientPhoneNumber);
            }
            Toast.makeText(this, "OTP SENT SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("SMS", "Failed to send SMS", e);
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
        }
    }

//    private void sendSMS(String string2, String[] arrstring, String string3) {
//        SmsManager smsManager = SmsManager.getDefault();
//        int n = arrstring.length;
//        for (int i = 0; i < n; ++i) {
//            String string4 = arrstring[i];
//            smsManager.sendTextMessage(string4, string2, string3, null, null);
//            Log.d((String)"SMS", (String)("SMS sent to " + string4));
//        }
//        try {
//            Toast.makeText((Context)this, (CharSequence)"OTP SENT SUCCESSFULLY", (int)0).show();
//            return;
//        }
//        catch (Exception exception) {
//            Log.e((String)"SMS", (String)"Failed to send SMS", (Throwable)exception);
//            Toast.makeText((Context)this, (CharSequence)"Failed to send SMS", (int)0).show();
//            return;
//        }
//    }

    private void sendSMS2(String senderPhoneNumber, String[] recipientPhoneNumbers, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        int recipientCount = recipientPhoneNumbers.length;
        for (int i = 0; i < recipientCount; ++i) {
            String recipientPhoneNumber = recipientPhoneNumbers[i];
            smsManager.sendTextMessage(recipientPhoneNumber, senderPhoneNumber, message, null, null);
            Log.d((String)"SMS", (String)("SMS sent to " + recipientPhoneNumber));
        }
        try {
            Toast.makeText((Context)this, (CharSequence)"MESSAGE SENT TO ALL ADMINS", (int)0).show();
            return;
        }
        catch (Exception exception) {
            Log.e((String)"SMS", (String)"Failed to send SMS", (Throwable)exception);
            Toast.makeText((Context)this, (CharSequence)"Failed to send SMS", (int)0).show();
            return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable((ComponentActivity)this);
        this.setContentView(R.layout.activity_sms);
        this.button = (Button)this.findViewById(R.id.button2);
        this.phone = (EditText)this.findViewById(R.id.editTextText);
        this.otptext = (EditText)this.findViewById(R.id.editTextText2);
        this.tv = (TextView)this.findViewById(R.id.tv);
        SpannableString spannableString = new SpannableString((CharSequence)"Request for OTP");
        spannableString.setSpan((Object)new UnderlineSpan(), 0, spannableString.length(), 0);
        this.tv.setText((CharSequence)spannableString);
        this.tv.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SMS.this.ph = SMS.this.phone.getText().toString();
                if (SMS.this.checkSelfPermission("android.permission.SEND_SMS") == PackageManager.PERMISSION_GRANTED) {
                    if (SMS.this.valid()) {
                        SMS.this.tv.setVisibility(View.GONE);
                        SMS.this.phone.setEnabled(false);
                        SMS.this.button.setVisibility(View.VISIBLE);
                        Toast.makeText((Context)SMS.this, (CharSequence)SMS.this.ph, (int)0).show();
                        SMS.this.generateAndSendOTP();
                        return;
                    }
                } else {
                    SMS.this.requestPermissions(new String[]{"android.permission.SEND_SMS"}, 1);
                }
            }
        });
        this.button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SMS.this.getotp = SMS.this.otptext.getText().toString();
                if (!SMS.this.getotp.equals((Object)SMS.this.otp)) {
                    Snackbar.make((View)SMS.this.button, (CharSequence)"OTP not match", (int)0).show();
                    return;
                }
                SMS.this.startActivity(new Intent((Context)SMS.this, MainFragment.class));
                SMS.this.finish();
            }
        });
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        super.onRequestPermissionsResult(n, arrstring, arrn);
        if (n == 1) {
            if (arrn.length > 0 && arrn[0] == 0) {
                this.generateAndSendOTP();
                return;
            }
            Toast.makeText((Context)this, (CharSequence)"Permission denied to send SMS", (int)0).show();
        }
    }

    boolean valid() {
        if (Objects.equals((Object)this.ph, (Object)"")) {
            Snackbar.make((View)this.button, (CharSequence)"Enter a phone number", (int)0).show();
            this.check = false;
        } else {
            this.check = true;
        }
        this.check = Objects.equals((Object)this.ph, (Object)"9000000001") || Objects.equals((Object)this.ph, (Object)"9000000003") | Objects.equals((Object)this.ph, (Object)"9000000004") || Objects.equals((Object)this.ph, (Object)"9000000005") || Objects.equals((Object)this.ph, (Object)"9000000006") || Objects.equals((Object)this.ph, (Object)"9000000002");
        return this.check;
    }

}
        
        