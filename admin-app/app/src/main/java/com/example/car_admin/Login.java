package com.example.car_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {
    EditText id,pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id=findViewById(R.id.txt_email_login);
        pass=findViewById(R.id.txt_login_pass);

        login=findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aid=id.getText().toString();
                String apass=pass.getText().toString();

                if(aid.isEmpty())
                {
                    Snackbar.make(login,"ID can't be null",Snackbar.LENGTH_LONG).show();
                }
                else if(apass.isEmpty())
                {
                    Snackbar.make(login,"Password can't be null",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    if(aid.equals("Admin") && apass.equals("VNSGU"))
                    {
//                        startActivity(new Intent(Login.this, SMS.class));
                        startActivity(new Intent(Login.this, MainFragment.class));
                        id.setText("");
                        pass.setText("");
                    }
                    else
                    {
                        if (!aid.equals("Admin"))
                        {
                            Snackbar.make(login,"Incorrect ID",Snackbar.LENGTH_LONG).show();
                        }
                        else
                        {
                            Snackbar.make(login,"Incorrect Password",Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

    }
}