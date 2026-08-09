// EmailSender.java
package com.example.caronrentrenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender extends AsyncTask<Void, Void, String> {

    private static final String TAG = "EmailSender";
    private final Context context;
    private final String username = "YOUR_EMAIL@gmail.com"; // Sender Gmail address used for SMTP auth
    private final String password = "YOUR_GMAIL_APP_PASSWORD"; // Gmail App Password (Google Account > Security > App Passwords) — do not commit a real value

    public EmailSender(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("email_1", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email)); // Recipient's email
            message.setSubject("Your One Time Password");

            String otp = generateOTP();

            message.setText("Your OTP is : " + otp);

            Transport.send(message);

            Log.d(TAG, "Email sent successfully");

            return otp;

        } catch (MessagingException e) {
            Log.e(TAG, "Error sending email", e);
        }
        return null;
    }

    private String generateOTP() {
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append((int) (Math.random() * 10));
        }
        return otp.toString();
    }
}
