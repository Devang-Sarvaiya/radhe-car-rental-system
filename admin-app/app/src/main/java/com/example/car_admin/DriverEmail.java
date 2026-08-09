package com.example.car_admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// DriverEmail.java
public class DriverEmail extends AsyncTask<Void, Void, String[]> { // Change return type to String[]

    private static final String TAG = "EmailSender";
    private final Context context;
    private final String username = "YOUR_EMAIL@gmail.com"; // Sender Gmail address used for SMTP auth
    private final String password = "YOUR_GMAIL_APP_PASSWORD"; // Gmail App Password (Google Account > Security > App Passwords) — do not commit a real value

    public DriverEmail(Context context) {
        this.context = context;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("email_1", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        if(email==null)
        {
            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
        }
        else
        {
//            Toast.makeText(context, email, Toast.LENGTH_SHORT).show();

        }

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
            message.setSubject("Driver ID and Password");

            String randomID = generateRandomID();
            String randomPassword = generateRandomPassword();

//            message.setText("Welcome to the Radhe Car Drivers\nYour Driver ID and PASSWORD is generated and valid from today!!! \uD83D\uDE00 \n\nYour Driver ID is : " + randomID + "\nYour Password is : " + randomPassword);
            String htmlMessage = "<html><body><p style=\"font-size:20px; font-weight:bold; text-align:center;\">\uD83E\uDD73\uD83C\uDF8A\uD83C\uDF89Congratulations\uD83C\uDF89\uD83C\uDF8A\uD83E\uDD73</p><p style=\"font-size:16px;\"><b>Welcome to the Radhe Car Drivers</b></p><p style=\"font-size:16px;\">Your Driver ID and Password is generated and valid from today!!! \uD83D\uDE00 </p><p>Your Driver ID is : " + randomID + "</p><p>Your Password is : " + randomPassword + "</p></body></html>";
            message.setContent(htmlMessage, "text/html");
            Transport.send(message);

            Log.d(TAG, "Email sent successfully");

            // Return both the generated ID and password
            return new String[]{randomID, randomPassword};

        } catch (MessagingException e) {
            Log.e(TAG, "Error sending email", e);
        }
        return null;
    }

    private String generateRandomID() {
        // Generate random alphabets for the first 5 characters
        String alphabetCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder idBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            idBuilder.append(alphabetCharacters.charAt(random.nextInt(alphabetCharacters.length())));
        }
        // Generate random digits for the last 3 characters
        String digitCharacters = "0123456789";
        for (int i = 0; i < 3; i++) {
            idBuilder.append(digitCharacters.charAt(random.nextInt(digitCharacters.length())));
        }
        return idBuilder.toString();
    }

    private String generateRandomPassword() {
        // Generate a random password with the first digit greater than 0
        Random random = new Random();
        int firstDigit = random.nextInt(9) + 1; // Random number between 1 and 9
        StringBuilder sb = new StringBuilder();
        sb.append(firstDigit);

        String characters = "0123456789"; // Include '0' this time
        for (int i = 1; i < 8; i++) { // Start from index 1 as we already have the first digit
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}