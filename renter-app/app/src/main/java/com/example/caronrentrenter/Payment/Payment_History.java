package com.example.caronrentrenter.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caronrentrenter.BookHistory.bookingHistory;
import com.example.caronrentrenter.R;

import java.util.Locale;

public class Payment_History extends AppCompatActivity {
    TextView txtPrice, txtDriver, txtDeposit, txtGst, txtTotal, txt_read_more, txt_status;
    Button button;
    TextToSpeech texttospeech;
    String gettext = "Thank you";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_history);

        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR){
                    texttospeech.setLanguage(Locale.UK);
                }
            }
        });


        button = findViewById(R.id.button2);

        txtPrice = findViewById(R.id.txt_price);
        txtDriver = findViewById(R.id.txtDriver);
        txtDeposit = findViewById(R.id.txt_deposit);
        txtGst = findViewById(R.id.txt_gst);
        txtTotal = findViewById(R.id.txt_amt);

        Intent in=getIntent();

        txtPrice.setText(in.getStringExtra("txtPrice"));
        txtDriver.setText(in.getStringExtra("txtDriver"));
        txtDeposit.setText(in.getStringExtra("txtDeposit"));
        txtGst.setText(in.getStringExtra("txtGst"));
        txtTotal.setText(in.getStringExtra("txtTotal"));

        String mob=in.getStringExtra("uMob");
        String driver=in.getStringExtra("txtDriver");







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                texttospeech.speak(gettext,TextToSpeech.QUEUE_FLUSH,null);

                Intent i=new Intent(Payment_History.this, bookingHistory.class);
                i.putExtra("uMob", mob);
                i.putExtra("txtPrice",txtPrice.getText().toString());
                i.putExtra("txtDriver",driver);
                i.putExtra("txtDeposit",txtDeposit.getText().toString());
                i.putExtra("txtGst",txtGst.getText().toString());
                i.putExtra("txtTotal",txtTotal.getText().toString());

                startActivity(i);
            }
        });


    }
    @Override
    protected void onPause() {
        if (texttospeech != null){
            texttospeech.stop();
            texttospeech.shutdown();
        }
        super.onPause();
    }
}